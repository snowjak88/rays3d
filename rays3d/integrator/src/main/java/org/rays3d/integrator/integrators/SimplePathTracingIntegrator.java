package org.rays3d.integrator.integrators;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.codehaus.groovy.control.CompilationFailedException;
import org.rays3d.Primitive;
import org.rays3d.bxdf.BSDF;
import org.rays3d.geometry.Normal3D;
import org.rays3d.geometry.Point3D;
import org.rays3d.geometry.Ray;
import org.rays3d.geometry.Vector3D;
import org.rays3d.interact.Interaction;
import org.rays3d.message.sample.Sample;
import org.rays3d.spectrum.RGBSpectrum;
import org.rays3d.spectrum.Spectrum;
import org.rays3d.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("path-tracing-integrator")
public class SimplePathTracingIntegrator extends AbstractIntegrator {

	private final static Logger	LOG						= LoggerFactory.getLogger(SimplePathTracingIntegrator.class);

	/**
	 * By default, each {@link Ray} will be allowed to descend so many levels of
	 * recursive reflection and transmission before it is cut short.
	 */
	public static final int		DEFAULT_MAX_RAY_DEPTH	= 4;

	private int					maxRayDepth;

	public SimplePathTracingIntegrator(World world, String extraConfiguration) {
		super(world, extraConfiguration);

		LOG.info("Reading extra-configuration ...");

		Map<String, Object> config;
		if (extraConfiguration == null || extraConfiguration.isEmpty()) {
			LOG.info("No extra-configuration provided. Assuming default values only.");
			config = Collections.emptyMap();

		} else {
			try {
				config = parseStringAsGroovyMap(extraConfiguration);
			} catch (CompilationFailedException e) {
				LOG.error("Cannot parse extra-configuration. Assuming default values only.");
				config = Collections.emptyMap();
			}
		}

		try {
			this.maxRayDepth = Integer
					.parseInt((String) config.getOrDefault("maxRayDepth", Integer.toString(DEFAULT_MAX_RAY_DEPTH)));

			LOG.info("Setting [maxRayDepth] = {}", maxRayDepth);

		} catch (NumberFormatException e) {
			LOG.error("Cannot parse given [maxRayDepth] = {} as an integer. Sticking with default value {}.",
					config.get("maxRayDepth"), DEFAULT_MAX_RAY_DEPTH);
			this.maxRayDepth = DEFAULT_MAX_RAY_DEPTH;
		}
	}

	@Override
	public Spectrum estimateRadiance(Sample sample) {

		LOG.trace("Estimating radiance: render {}, sample at [{},{}]", sample.getRenderId(),
				sample.getFilmPoint().getX(), sample.getFilmPoint().getY());

		LOG.trace("Constructing initial Ray from World.Camera ...");
		final Ray ray = getWorld().getCamera().getRay(sample);

		LOG.trace("Radiance estimate must be scaled by the number of samples per pixel ( 1 / {} )",
				sample.getSamplesPerPixel());
		final double sampleFraction = 1d / ( (double) sample.getSamplesPerPixel() );

		LOG.trace("Following initial ray ...");
		final Spectrum result = followRay(ray, sample).multiply(sampleFraction);

		LOG.trace("Finished estimating radiance: render {}, sample at [{},{}]", sample.getRenderId(),
				sample.getFilmPoint().getX(), sample.getFilmPoint().getY());
		return result;
	}

	@SuppressWarnings("unused")
	private Spectrum followRay(Ray thisRay, Sample sample) {

		LOG.trace("Following Ray (depth {})", thisRay.getDepth());

		LOG.trace("Looking for intersections with the World.");
		final Optional<Interaction<Primitive>> op_interaction = getWorld().getClosestInteraction(thisRay);

		if (!op_interaction.isPresent()) {
			LOG.trace("No intersection anywhere. Ray results in RGBSpectrum.BLACK");
			return RGBSpectrum.BLACK;
		}

		LOG.trace("Got an intersection.");
		final Interaction<Primitive> foundInteraction = op_interaction.get();

		//
		//
		// It may be that the surface Normal is pointing away from the eye --
		// i.e., it's on the other side of the surface from the eye.
		//
		// In this case, we should flip the Normal around so it is on the same
		// side of the surface as the eye.
		//
		final Interaction<Primitive> interaction;
		if (( foundInteraction.getW_e().dotProduct(Vector3D.from(foundInteraction.getNormal()).normalize()) ) < 0d) {

			// Flip this normal around.
			//
			interaction = new Interaction<Primitive>(foundInteraction.getInteracted(),
					foundInteraction.getInteractingRay(), foundInteraction.getPoint(),
					foundInteraction.getNormal().negate(), foundInteraction.getParam());
			LOG.trace("Flipping surface-normal from {} to {}", foundInteraction.getNormal().toString(),
					interaction.getNormal().toString());

		} else {

			// Keep this normal as it is.
			//
			interaction = foundInteraction;
			LOG.trace("Not flipping surface-normal");
		}

		final Primitive primitive = interaction.getInteracted();
		final BSDF bsdf = interaction.getInteracted().getBsdf();
		final Point3D point = interaction.getPoint();
		final Ray ray = interaction.getInteractingRay();
		final Vector3D w_e = interaction.getW_e().normalize();
		final Normal3D normal = interaction.getNormal();

		//
		//
		//
		final Spectrum incidentIrradiance;

		//
		//
		//
		LOG.trace("Estimating incident irradiance due to direct illumination ...");
		final Spectrum directIrradiance = getWorld().getEmissives().stream().map(p -> {

			final Point3D emissiveSurfacePoint = p.sampleSurfaceFacing(point, sample).getPoint();
			final Vector3D toEmissiveVector = Vector3D.from(emissiveSurfacePoint.subtract(point)).normalize();

			//
			// If the vector from the surface-point toward the sampled
			// illumination-point is on the other side of the surface from the
			// normal (and hence from the eye-point), it will contribute nothing
			// to our direct-illumination estimate.
			if (toEmissiveVector.dotProduct(Vector3D.from(normal)) <= 0d)
				return RGBSpectrum.BLACK;

			//
			// Check if the sampled direct-illumination point is visible from
			// the surface-point.
			final Ray toEmissivePointRay = new Ray(point, toEmissiveVector);
			final Optional<Interaction<Primitive>> op_emissiveInteraction = getWorld()
					.getClosestInteraction(toEmissivePointRay);
			if (!op_emissiveInteraction.isPresent()) {
				//
				// This shouldn't happen -- we should be able to get at least
				// one interaction -- but we'll include this as a safety check
				// all the same.
				return RGBSpectrum.BLACK;
			}

			if (op_emissiveInteraction.get().getInteracted() != p) {
				// The emissive-ray interacted with a Primitive other than our
				// current emissive Primitive. This indicates that something is
				// occluding direct illumination from this emissive, and so it
				// contributes nothing to our direct-illumination estimate.
				return RGBSpectrum.BLACK;
			}

			//
			// By now, we know that the sampled emissive-point is visible to our
			// surface-point.
			// Now we determine how much energy actually reaches that
			// surface-point.
			final double emissiveDistance = op_emissiveInteraction.get().getInteractingRay().getT();
			final double falloffFraction = 1d / ( emissiveDistance * emissiveDistance );

			return p
					.getBsdf()
						.sampleL_e(op_emissiveInteraction.get(), sample)
						.multiply(bsdf.f_r(interaction, sample, toEmissiveVector))
						.multiply(bsdf.cos_i(interaction, toEmissiveVector))
						.multiply(falloffFraction);

		}).reduce(RGBSpectrum.BLACK, (s1, s2) -> s1.add(s2));

		//
		//
		//
		final Spectrum indirectIrradiance;
		if (ray.getDepth() >= maxRayDepth) {

			LOG.trace("Not estimating incident irradiance due to indirect illumination -- max ray-depth {} exceeded.",
					maxRayDepth);
			indirectIrradiance = RGBSpectrum.BLACK;

		} else {

			LOG.trace("Estiamting incident irradiance due to indirect illumination ...");

			final Vector3D reflectionDirection = bsdf.sampleW_i(interaction, sample);
			final Ray reflectionRay = new Ray(point, reflectionDirection, 0.0, ray.getDepth() + 1);

			indirectIrradiance = followRay(reflectionRay, sample)
					.multiply(bsdf.f_r(interaction, sample, reflectionDirection))
						.multiply(bsdf.cos_i(interaction, reflectionDirection));

		}

		//
		//
		//
		if (ray.getDepth() >= maxRayDepth) {
			LOG.trace("Irradiance estimate = direct illumination");
			incidentIrradiance = directIrradiance;
		} else {
			LOG.trace("Irradiance estimate = ( direct illumination + indirect illumination ) / 2");
			incidentIrradiance = ( directIrradiance.add(indirectIrradiance) ).multiply(1d / 2d);
		}

		//
		//
		//
		LOG.trace("Total radiance = incident irradiance + emissive radiance");
		return incidentIrradiance.add(bsdf.sampleL_e(interaction, sample));
	}

}

package org.rays3d.integrator.integrators;

import org.rays3d.message.sample.Sample;
import org.rays3d.spectrum.Spectrum;
import org.rays3d.world.World;

/**
 * An AbstractIntegrator is responsible for transforming a {@link Sample} into a
 * {@link Spectrum} (which result is usually then stored within the originating
 * Sample).
 * 
 * @author snowjak88
 */
public abstract class AbstractIntegrator {

	private World	world;
	private String	extraConfiguration;

	/**
	 * Construct a new AbstractIntegrator, operating on the given {@link World}.
	 * <p>
	 * Implementations must handle parsing <code>extraConfiguration</code>
	 * themselves. If <code>extraConfiguration == null</code>, then no
	 * extra-configuration is specified.
	 * </p>
	 * 
	 * @param world
	 * @param extraConfiguration
	 */
	public AbstractIntegrator(World world, String extraConfiguration) {
		this.world = world;
		this.extraConfiguration = extraConfiguration;
	}

	/**
	 * Estimate the total radiance (expressed as a {@link Spectrum}) derived
	 * from the given {@link Sample}.
	 * 
	 * @param sample
	 * @return
	 */
	public abstract Spectrum estimateRadiance(Sample sample);

	/**
	 * @return the {@link World} which this integrator acts upon
	 */
	public World getWorld() {

		return world;
	}

	/**
	 * @return any extra configuration for this integrator, as provided in the original
	 *         render-descriptor
	 */
	public String getExtraConfiguration() {

		return extraConfiguration;
	}
}

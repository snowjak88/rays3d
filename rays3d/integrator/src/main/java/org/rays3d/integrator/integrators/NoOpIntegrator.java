package org.rays3d.integrator.integrators;

import org.rays3d.message.sample.Sample;
import org.rays3d.spectrum.RGB;
import org.rays3d.spectrum.RGBSpectrum;
import org.rays3d.spectrum.Spectrum;
import org.rays3d.world.World;

/**
 * An {@link AbstractIntegrator} implementation that simply returns
 * {@link RGBSpectrum#BLACK} for all {@link Sample}s.
 * 
 * @author snowjak88
 */
@Named("no-op-integrator")
public class NoOpIntegrator extends AbstractIntegrator {

	public NoOpIntegrator(World world, String extraConfiguration) {
		super(world, extraConfiguration);
	}

	@Override
	public Spectrum estimateRadiance(Sample sample) {

		return new RGBSpectrum(RGB.RED);
	}

}

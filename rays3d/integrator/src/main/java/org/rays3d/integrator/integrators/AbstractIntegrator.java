package org.rays3d.integrator.integrators;

import static org.apache.commons.math3.util.FastMath.min;

import java.util.Map;

import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.rays3d.message.sample.Sample;
import org.rays3d.spectrum.Spectrum;
import org.rays3d.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

/**
 * An AbstractIntegrator is responsible for transforming a {@link Sample} into a
 * {@link Spectrum} (which result is usually then stored within the originating
 * Sample).
 * 
 * @author snowjak88
 */
public abstract class AbstractIntegrator {

	private final static Logger	LOG	= LoggerFactory.getLogger(AbstractIntegrator.class);

	private World				world;
	private String				extraConfiguration;

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
	 * @return any extra configuration for this integrator, as provided in the
	 *         original render-descriptor
	 */
	public String getExtraConfiguration() {

		return extraConfiguration;
	}

	/**
	 * Parse a String as a Groovy Map definition, and return that Map.
	 * 
	 * @param groovyMapText
	 * @return
	 * @throws CompilationFailedException
	 *             if the given text cannot be successfully parsed as a Groovy
	 *             Map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseStringAsGroovyMap(String groovyMapText) throws CompilationFailedException {

		try {

			final CompilerConfiguration config = CompilerConfiguration.DEFAULT;
			return (Map<String, Object>) new GroovyShell(new Binding(), config).evaluate(groovyMapText);

		} catch (CompilationFailedException e) {

			LOG.error("Cannot parse text \"" + groovyMapText.substring(0, min(32, groovyMapText.length()))
					+ "\" as a Groovy Map!", e);
			throw e;

		}
	}
}

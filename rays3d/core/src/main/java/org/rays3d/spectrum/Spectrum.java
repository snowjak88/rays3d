package org.rays3d.spectrum;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a measurement of radiant energy distributed across several
 * wavelengths.
 * 
 * @author snowjak88
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Spectrum extends Serializable {

	/**
	 * @return <code>true</code> if this Spectrum has 0 (or even very close to
	 *         0) energy associated with it.
	 */
	@JsonIgnore
	public boolean isBlack();

	/**
	 * Compute the result of adding this Spectrum's energy with another.
	 */
	public Spectrum add(Spectrum addend);

	/**
	 * Compute the result of multiplying this Spectrum's energy with another.
	 * (Usually used to model filtering or fractional selection of radiant
	 * energy.)
	 */
	public Spectrum multiply(Spectrum multiplicand);

	/**
	 * Compute the result of multiplying this Spectrum's energy by a scalar
	 * factor -- essentially scaling this Spectrum's energy linearly.
	 * 
	 * @param scalar
	 * @return
	 */
	public Spectrum multiply(double scalar);

	/**
	 * Compute this Spectrum's amplitude -- a measure of its average intensity
	 * over time, across all wavelengths.
	 * 
	 * @return
	 */
	@JsonIgnore
	public double getAmplitude();

	/**
	 * Convert this Spectrum to a RGB-trio for subsequent display.
	 */
	@JsonIgnore
	public RGB toRGB();
}

package org.rays3d.geometry.util;

import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * Represents a vector of <code>n</code> values.
 * 
 * @author snowjak88
 */
public class NVector {

	private final double[] values;

	/**
	 * Create a new NVector composed of <code>n</code> 0-values.
	 * 
	 * @param n
	 */
	public NVector(int n) {
		this.values = new double[n];
		Arrays.setAll(values, i -> 0d);
	}

	/**
	 * Create a new NVector with order <code>n</code> implicitly given by the
	 * length of the given array.
	 * 
	 * @param values
	 */
	public NVector(double... values) {
		this(true, values);
	}

	private NVector(boolean copyIntoNewArray, double... values) {

		if (copyIntoNewArray)
			this.values = Arrays.copyOf(values, values.length);
		else
			this.values = values;
	}

	/**
	 * @return the length of this NVector
	 */
	public int getN() {

		return this.values.length;
	}

	/**
	 * Return the <code>i</code>th element of this NVector, where <code>i</code>
	 * is in [0, {@link #getN()}-1].
	 * 
	 * @param i
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 *             if i is not within the bounds of this NVector
	 * @see #getN()
	 */
	public double get(int i) {

		return this.values[i];
	}

	/**
	 * Apply the given {@link UnaryOperator} to this NVector, producing another
	 * NVector as a result.
	 * 
	 * @param operator
	 * @return
	 */
	public NVector apply(UnaryOperator<Double> operator) {

		final double[] result = new double[this.values.length];

		for (int i = 0; i < this.values.length; i++)
			result[i] = operator.apply(this.values[i]);

		return new NVector(false, result);
	}

	/**
	 * Apply the given {@link BinaryOperator} to this and another NVector,
	 * producing a third NVector as a result.
	 * <h1>Processing NVectors of different lengths</h1>
	 * <p>
	 * When applying an operation to two NVectors of differing lengths, the
	 * shorter of the two is padded with 0s to equal the length of the longer.
	 * </p>
	 * <p>
	 * Given two NVectors <code>v<sub>1</sub></code> and
	 * <code>v<sub>2</sub></code>, where
	 * 
	 * <pre>
	 * v<sub>1</sub> := { 1, 2 }
	 * v<sub>2</sub> := { 1, 2, 3 }
	 * operation := (d1, d2) -> d1 + d2
	 * </pre>
	 * 
	 * applying <code>operation</code> to <code>v<sub>1</sub></code> and
	 * <code>v<sub>2</sub></code> produces a third NVector
	 * <code>v<sub>3</sub> = { (1+1), (2+2), (3+0) } = { 2, 4, 3 }</code>
	 * </p>
	 * 
	 * @param other
	 * @param operator
	 * @return
	 */
	public NVector apply(NVector other, BinaryOperator<Double> operator) {

		final int longerLength = ( this.values.length > other.values.length ) ? this.values.length
				: other.values.length;

		final double[] thisPadded = Arrays.copyOf(this.values, longerLength);
		final double[] otherPadded = Arrays.copyOf(other.values, longerLength);
		final double[] result = new double[longerLength];

		for (int i = 0; i < longerLength; i++)
			result[i] = operator.apply(thisPadded[i], otherPadded[i]);

		return new NVector(false, result);
	}

	/**
	 * Returns the negated form of this NVector.
	 * 
	 * <pre>
	 * v := { 1, 2, 3 }
	 * negate(v) := { -1, -2, -3 }
	 * </pre>
	 * 
	 * @return
	 */
	public NVector negate() {

		return this.apply((d) -> -d);
	}

	/**
	 * Returns the reciprocal form of this NVector.
	 * 
	 * <pre>
	 * v := { 1, 2, 3 }
	 * reciprocal(v) := { 1/1, 1/2,  1/3 }
	 * </pre>
	 * 
	 * @return
	 */
	public NVector reciprocal() {

		return this.apply((d) -> 1d / d);
	}

	/**
	 * Adds this NVector to another, producing a third NVector as a result.
	 * <p>
	 * See {@link #apply(NVector, BinaryOperator)} for information about
	 * behavior when adding NVectors of differing lengths.
	 * </p>
	 * 
	 * @param addend
	 * @return
	 */
	public NVector add(NVector addend) {

		return this.apply(addend, (d1, d2) -> d1 + d2);
	}

	/**
	 * Adds a constant value to every element in this NVector, producing another
	 * NVector as a result.
	 * 
	 * @param addend
	 * @return
	 */
	public NVector add(double addend) {

		return this.apply(d -> d + addend);
	}

	/**
	 * Subtract another NVector from this NVector, producing a third NVector as
	 * a result.
	 * <p>
	 * See {@link #apply(NVector, BinaryOperator)} for information about
	 * behavior when subtracting NVectors of differing lengths.
	 * </p>
	 * 
	 * @param subtrahend
	 * @return
	 */
	public NVector subtract(NVector subtrahend) {

		return this.apply(subtrahend, (d1, d2) -> d1 - d2);
	}

	/**
	 * Subtract a constant value from every element in this NVector, producing
	 * another NVector as a result.
	 * 
	 * @param subtrahend
	 * @return
	 */
	public NVector subtract(double subtrahend) {

		return this.apply(d -> d - subtrahend);
	}

	/**
	 * Multiply this NVector by another NVector, producing a third NVector as a
	 * result.
	 * <p>
	 * See {@link #apply(NVector, BinaryOperator)} for information about
	 * behavior when multiplying NVectors of differing lengths.
	 * </p>
	 * 
	 * @param multiplicand
	 * @return
	 */
	public NVector multiply(NVector multiplicand) {

		return this.apply(multiplicand, (d1, d2) -> d1 * d2);
	}

	/**
	 * Multiply every element in this NVector by a constant value, producing
	 * another NVector as a result.
	 * 
	 * @param multiplicand
	 * @return
	 */
	public NVector multiply(double multiplicand) {

		return this.apply(d -> d * multiplicand);
	}

	/**
	 * Divide this NVector by another NVector, producing a third NVector as a
	 * result.
	 * <p>
	 * See {@link #apply(NVector, BinaryOperator)} for information about
	 * behavior when dividing NVectors of differing lengths.
	 * </p>
	 * 
	 * @param divisor
	 * @return
	 */
	public NVector divide(NVector divisor) {

		return this.apply(divisor, (d1, d2) -> d1 / d2);
	}

	/**
	 * Divide every element in this NVector by a constant value, producing
	 * another NVector as a result.
	 * 
	 * @param divisor
	 * @return
	 */
	public NVector divide(double divisor) {

		return this.apply(d -> d / divisor);
	}

}

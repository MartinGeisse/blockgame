/**
 * Copyright (c) 2012 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.blockgame.geometry;

/**
 * Specifies the size of a cluster. Clusters must have a
 * power-of-two size (represented by this class), the same
 * size along each axis, and a position aligned to that size.
 */
public final class ClusterSize implements Comparable<ClusterSize> {

	/**
	 * the shiftBits
	 */
	private final int shiftBits;

	/**
	 * Constructor.
	 * @param shiftBits the log2 of the cluster size along each axis
	 */
	public ClusterSize(final int shiftBits) {
		if (shiftBits < 0) {
			throw new IllegalArgumentException("cluster shift bits cannot be negative: " + shiftBits);
		}
		this.shiftBits = shiftBits;
	}

	/**
	 * Getter method for the shiftBits.
	 * @return the shiftBits
	 */
	public int getShiftBits() {
		return shiftBits;
	}

	/**
	 * Getter method for the size.
	 * @return the size
	 */
	public int getSize() {
		return (1 << shiftBits);
	}
	
	/**
	 * Getter method for the squared size.
	 * @return the squared size
	 */
	public int getSquaredSize() {
		return ((1 << shiftBits) << shiftBits);
	}

	/**
	 * Getter method for the mask.
	 * @return the mask
	 */
	public int getMask() {
		return (1 << shiftBits) - 1;
	}

	// override
	@Override
	public boolean equals(final Object o) {
		if (o instanceof ClusterSize) {
			final ClusterSize other = (ClusterSize)o;
			return shiftBits == other.shiftBits;
		}
		return false;
	}

	// override
	@Override
	public int hashCode() {
		return shiftBits;
	}

	// override
	@Override
	public int compareTo(final ClusterSize other) {
		return shiftBits - other.shiftBits;
	}

	/**
	 * Returns the maximum of this size and the specified other size.
	 * @param other the other size
	 * @return the maximum
	 */
	public ClusterSize max(final ClusterSize other) {
		return compareTo(other) > 0 ? this : other;
	}

	/**
	 * Returns the minimum of this size and the specified other size.
	 * @param other the other size
	 * @return the minimum
	 */
	public ClusterSize min(final ClusterSize other) {
		return compareTo(other) < 0 ? this : other;
	}

	/**
	 * Returns the product of this size and the specified other size.
	 * @param other the other size
	 * @return the product
	 */
	public ClusterSize multiply(final ClusterSize other) {
		return new ClusterSize(shiftBits + other.shiftBits);
	}

	/**
	 * Returns the quotient of this size and the specified other size.
	 * It is an error if the result is fractional, i.e. if the other
	 * size is greater than this size.
	 * 
	 * @param other the other size
	 * @return the quotient
	 */
	public ClusterSize divide(final ClusterSize other) {
		return new ClusterSize(shiftBits - other.shiftBits);
	}

	/**
	 * Returns the number of cells in a cluster of this size. The cluster
	 * has an edge length of (size), so it has (size^2) cells.
	 * @return the number of cells
	 */
	public int getCellCount() {
		return (1 << (2 * shiftBits));
	}

	/**
	 * Returns the largest cluster size that is contained by the specified
	 * number of units.
	 * 
	 * @param units the number of units
	 * @return the inner cluster size
	 */
	public static ClusterSize getInner(int units) {
		if (units < 1) {
			throw new IllegalAccessError("cannot generate ClusterSize from less than one unit: " + units);
		}
		int shift = 0;
		while (units > 1) {
			units >>= 1;
			shift++;
		}
		return new ClusterSize(shift);
	}

	/**
	 * Returns the smallest cluster size that contains the specified
	 * size (number of units).
	 * 
	 * @param size the number of units
	 * @return the outer cluster size
	 */
	public static ClusterSize getOuter(final int size) {
		return getInner((size << 1) - 1);
	}

}

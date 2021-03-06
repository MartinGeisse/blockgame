/**
 * Copyright (c) 2010 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.blockgame.geometry;

import name.martingeisse.blockworld.geometry.BaseVector2i;
import name.martingeisse.blockworld.geometry.ReadableVector2d;
import name.martingeisse.blockworld.geometry.ReadableVector2i;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * A section ID is used as the identifying key for a section, and also
 * stores the location of the section in the grid. The location is measured
 * in cluster-size units.
 * 
 * Although possible in general, this class is not derived from {@link BaseVector2i}
 * since section IDs use coordinates in cluster-size units, not cube units, so
 * extending a vector class would encourage mistakes that could be caught by the
 * type system.
 */
public final class SectionId {

	/**
	 * the x
	 */
	private final int x;
	
	/**
	 * the y
	 */
	private final int y;

	/**
	 * Constructor for known (x, y) coordinates of the section in cluster-size units.
	 * @param x the x coordinate of the location in the grid, measured in cluster-size units
	 * @param y the y coordinate of the location in the grid, measured in cluster-size units
	 */
	public SectionId(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructor for scaling a cell-unit (x, y) position to cluster-size units.
	 * @param x the x coordinate of the cube position
	 * @param y the y coordinate of the cube position
	 * @param clusterSize the cluster size
	 */
	public SectionId(int x, int y, ClusterSize clusterSize) {
		int shift = clusterSize.getShiftBits();
		this.x = x >> shift;
		this.y = y >> shift;
	}
	
	/**
	 * Constructor for scaling a cell-unit (x, y) position to cluster-size units.
	 * @param position the cube position
	 * @param clusterSize the cluster size
	 */
	public SectionId(ReadableVector2i position, ClusterSize clusterSize) {
		int shift = clusterSize.getShiftBits();
		this.x = position.getX() >> shift;
		this.y = position.getY() >> shift;
	}

	/**
	 * Constructor for scaling a cell-unit (x, y) position to cluster-size units.
	 * @param position the cube position
	 * @param clusterSize the cluster size
	 */
	public SectionId(ReadableVector2d position, ClusterSize clusterSize) {
		int shift = clusterSize.getShiftBits();
		this.x = (int)Math.floor(position.getX()) >> shift;
		this.y = (int)Math.floor(position.getY()) >> shift;
	}

	/**
	 * Constructor.
	 * @param identifierText the text returned by {@link #getIdentifierText()}.
	 * @throws IllegalArgumentException if the identifier text is malformed
	 */
	public SectionId(String identifierText) throws IllegalArgumentException {
		final String[] idTextSegments = StringUtils.split(identifierText, '_');
		if (idTextSegments.length != 2) {
			throw new IllegalArgumentException(identifierText);
		}
		try {
			x = Integer.parseInt(idTextSegments[0]);
			y = Integer.parseInt(idTextSegments[1]);
		} catch (final NumberFormatException e) {
			throw new IllegalArgumentException(identifierText);
		}
	}

	/**
	 * Getter method for the x.
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Getter method for the y.
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Converts this ID to a string that is unique among all IDs, i.e. that can be
	 * converted back to an ID equal to this one.
	 * @return the identifier text
	 */
	public String getIdentifierText() {
		StringBuilder builder = new StringBuilder();
		builder.append(x);
		builder.append('_');
		builder.append(y);
		return builder.toString();
	}
	
	// override
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SectionId) {
			SectionId other = (SectionId)obj;
			return (x == other.x && y == other.y);
		}
		return false;
	}
	
	// override
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(x).append(y).toHashCode();
	}
	
	// override
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	/**
	 * Returns the ID of the neighboring section by stepping in the specified
	 * direction.
	 * @param direction the direction
	 * @return the neighbor's ID
	 */
	public SectionId getNeighbor(AxisAlignedDirection direction) {
		return new SectionId(x + direction.getSignX(), y + direction.getSignY());
	}
	
}

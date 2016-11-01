/**
 * Copyright (c) 2010 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.blockgame.geometry;

/**
 * Common constants for the Stack'd engine.
 */
public class GeometryConstants {

	/**
	 * The number of cubes along one edge of a section.
	 */
	public static final int SECTION_SIZE = 32;

	/**
	 * This value is the log2 of the {@link #SECTION_SIZE} and can be used
	 * for bit shifting.
	 */
	public static final int SECTION_SHIFT = 5;

	/**
	 * A {@link ClusterSize} for {@link #SECTION_SIZE}.
	 */
	public static final ClusterSize SECTION_CLUSTER_SIZE = new ClusterSize(SECTION_SHIFT);

	/**
	 * Prevent instantiation.
	 */
	private GeometryConstants() {
	}
	
}

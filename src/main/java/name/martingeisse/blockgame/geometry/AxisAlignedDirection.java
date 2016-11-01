/**
 * Copyright (c) 2012 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.blockgame.geometry;

import name.martingeisse.blockworld.geometry.ReadableVector2d;
import name.martingeisse.blockworld.geometry.ReadableVector2i;

/**
 * Selects an axis-aligned direction, that is, one of +x, -x, +y, -y.
 */
public enum AxisAlignedDirection {

	/**
	 * Selects the negative X direction.
	 */
	NEGATIVE_X(0, true),

	/**
	 * Selects the positive X direction.
	 */
	POSITIVE_X(0, false),

	/**
	 * Selects the negative Y direction.
	 */
	NEGATIVE_Y(1, true),

	/**
	 * Selects the positive Y direction.
	 */
	POSITIVE_Y(1, false);

	/**
	 * the axis
	 */
	private final int axis;
	
	/**
	 * the negative
	 */
	private final boolean negative;
	
	/**
	 * Constructor.
	 */
	private AxisAlignedDirection(int axis, boolean negative) {
		this.axis = axis;
		this.negative = negative;
	}

	/**
	 * Getter method for the axis.
	 * @return the axis
	 */
	public int getAxis() {
		return axis;
	}
	
	/**
	 * Getter method for the negative.
	 * @return the negative
	 */
	public boolean isNegative() {
		return negative;
	}
	
	/**
	 * @return the opposite direction
	 */
	public AxisAlignedDirection getOpposite() {
		if (axis == 0) {
			return negative ? POSITIVE_X : NEGATIVE_X;
		} else {
			return negative ? POSITIVE_Y : NEGATIVE_Y;
		}
	}

	/**
	 * @return the direction along the other axis (x, y)
	 */
	public AxisAlignedDirection getOtherAxis() {
		if (axis == 0) {
			return negative ? NEGATIVE_Y : POSITIVE_Y;
		} else {
			return negative ? NEGATIVE_X : POSITIVE_X;
		}
	}

	/**
	 * @return the sign of this direction along the X axis, which is
	 * -1 for negative x, +1 for positive x, and 0 for Y.
	 */
	public int getSignX() {
		return (axis != 0) ? 0 : negative ? -1 : +1;
	}
	
	/**
	 * @return the sign of this direction along the Y axis, which is
	 * -1 for negative y, +1 for positive y, and 0 for X.
	 */
	public int getSignY() {
		return (axis != 1) ? 0 : negative ? -1 : +1;
	}
	
	/**
	 * @return the step function of this direction along the X axis, which is
	 * +1 for positive x, and 0 for negative x and for Y.
	 */
	public int getStepX() {
		return (axis != 0) ? 0 : negative ? 0 : +1;
	}
	
	/**
	 * @return the step function of this direction along the Y axis, which is
	 * +1 for positive y, and 0 for negative y and for X.
	 */
	public int getStepY() {
		return (axis != 1) ? 0 : negative ? 0 : +1;
	}
	
	/**
	 * @return the abs function of this direction along the X axis, which is
	 * +1 for positive and negative x, and 0 for Y.
	 */
	public int getAbsX() {
		return (axis == 0 ? 1 : 0);
	}
	
	/**
	 * @return the abs function of this direction along the X axis, which is
	 * +1 for positive and negative y, and 0 for X.
	 */
	public int getAbsY() {
		return (axis == 1 ? 1 : 0);
	}
	
	/**
	 * Returns the signed amount in this direction, e.g. the x value for
	 * the positive x direction, -x for the negative x direction, and so on.
	 * 
	 * Mathematically speaking, this function returns the dot product of the
	 * specified vector with the unit vector for this direction.
	 * 
	 * @param x the amount for the positive x direction
	 * @param y the amount for the positive y direction
	 * @return the signed amount in this direction
	 */
	public int select(int x, int y) {
		int unsigned = (axis == 0) ? x : y;
		return (negative ? -unsigned : unsigned);
	}

	/**
	 * See {@link #select(int, int)}.
	 * 
	 * @param v the vector that contains the (x, y) coordinates.
	 * @return the signed amount in this direction
	 */
	public int select(ReadableVector2i v) {
		int unsigned = (axis == 0) ? v.getX() : v.getY();
		return (negative ? -unsigned : unsigned);
	}

	/**
	 * Returns the signed amount in this direction, e.g. the x value for
	 * the positive x direction, -x for the negative x direction, and so on.
	 * 
	 * Mathematically speaking, this function returns the dot product of the
	 * specified vector with the unit vector for this direction.
	 * 
	 * @param x the amount for the positive x direction
	 * @param y the amount for the positive y direction
	 * @return the signed amount in this direction
	 */
	public float select(float x, float y) {
		float unsigned = (axis == 0) ? x : y;
		return (negative ? -unsigned : unsigned);
	}

	/**
	 * Returns the signed amount in this direction, e.g. the x value for
	 * the positive x direction, -x for the negative x direction, and so on.
	 * 
	 * Mathematically speaking, this function returns the dot product of the
	 * specified vector with the unit vector for this direction.
	 * 
	 * @param x the amount for the positive x direction
	 * @param y the amount for the positive y direction
	 * @return the signed amount in this direction
	 */
	public double select(double x, double y) {
		double unsigned = (axis == 0) ? x : y;
		return (negative ? -unsigned : unsigned);
	}

	/**
	 * See {@link #select(double, double)}.
	 * 
	 * @param v the vector that contains the (x, y) coordinates.
	 * @return the signed amount in this direction
	 */
	public double select(ReadableVector2d v) {
		double unsigned = (axis == 0) ? v.getX() : v.getY();
		return (negative ? -unsigned : unsigned);
	}
	
	/**
	 * Returns the amount along the axis of this direction, e.g. the
	 * x value for positive or negative x directions, and so on.
	 * 
	 * @param x the amount for the x direction
	 * @param y the amount for the y direction
	 * @return the amount along the axis of this direction
	 */
	public int selectByAxis(int x, int y) {
		return (axis == 0) ? x : y;
	}

	/**
	 * See {@link #selectByAxis(int, int)}.
	 * 
	 * @param v the vector that contains the (x, y) coordinates.
	 * @return the amount along the axis of this direction
	 */
	public int selectByAxis(ReadableVector2i v) {
		return (axis == 0) ? v.getX() : v.getY();
	}
	
	/**
	 * Returns the amount along the axis of this direction, e.g. the
	 * x value for positive or negative x directions, and so on.
	 * 
	 * @param x the amount for the x direction
	 * @param y the amount for the y direction
	 * @return the amount along the axis of this direction
	 */
	public float selectByAxis(float x, float y) {
		return (axis == 0) ? x : y;
	}

	/**
	 * Returns the amount along the axis of this direction, e.g. the
	 * x value for positive or negative x directions, and so on.
	 * 
	 * @param x the amount for the x direction
	 * @param y the amount for the y direction
	 * @return the amount along the axis of this direction
	 */
	public double selectByAxis(double x, double y) {
		return (axis == 0) ? x : y;
	}

	/**
	 * See {@link #selectByAxis(double, double)}.
	 * 
	 * @param v the vector that contains the (x, y) coordinates.
	 * @return the amount along the axis of this direction
	 */
	public double selectByAxis(ReadableVector2d v) {
		return (axis == 0) ? v.getX() : v.getY();
	}
	
}

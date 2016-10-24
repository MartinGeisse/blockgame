/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.engine.gfx;

import org.lwjgl.opengl.GL11;

/**
 * Represents a color with alpha channel. The individual color channels are
 * represented either by integer values in the range 0..255 or by floating-point
 * values in the range 0.0..1.0
 */
public final class RgbaColor {

	/**
	 * the r
	 */
	private final int r;

	/**
	 * the g
	 */
	private final int g;

	/**
	 * the b
	 */
	private final int b;
	
	/**
	 * the a
	 */
	private final int a;

	/**
	 * Constructor for an alpha channel of 255 / 1.0
	 * @param r the R channel
	 * @param g the G channel
	 * @param b the B channel
	 */
	public RgbaColor(int r, int g, int b) {
		this(r, g, b, 255);
	}
	
	/**
	 * Constructor.
	 * @param r the R channel
	 * @param g the G channel
	 * @param b the B channel
	 * @param a the A channel
	 */
	public RgbaColor(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	/**
	 * Getter method for the r.
	 * @return the r
	 */
	public int getR() {
		return r;
	}

	/**
	 * Getter method for the g.
	 * @return the g
	 */
	public int getG() {
		return g;
	}

	/**
	 * Getter method for the b.
	 * @return the b
	 */
	public int getB() {
		return b;
	}

	/**
	 * Getter method for the a.
	 * @return the a
	 */
	public int getA() {
		return a;
	}

	/**
	 * Configures this color using glColor().
	 */
	public void glColor() {
		GL11.glColor4ub((byte)r, (byte)g, (byte)b, (byte)a);
	}
	
}

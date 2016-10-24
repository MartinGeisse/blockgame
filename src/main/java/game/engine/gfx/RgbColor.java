/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.engine.gfx;

import org.lwjgl.opengl.GL11;

/**
 * Represents a color without alpha channel. The individual color channels are
 * represented either by integer values in the range 0..255 or by floating-point
 * values in the range 0..1.
 */
public final class RgbColor {

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
	 * Constructor.
	 * @param r the R channel
	 * @param g the G channel
	 * @param b the B channel
	 */
	public RgbColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
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
	 * Configures this color using glColor().
	 */
	public void glColor() {
		GL11.glColor3ub((byte)r, (byte)g, (byte)b);
	}

}

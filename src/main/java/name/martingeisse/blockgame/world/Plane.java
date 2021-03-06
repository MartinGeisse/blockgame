/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package name.martingeisse.blockgame.world;

import name.martingeisse.blockgame.system.Texture;
import org.lwjgl.opengl.GL11;

/**
 * A single world plane.
 */
public final class Plane {

	private final int width;
	private final int height;
	private final byte[] data;
	private Player player;

	/**
	 * Constructor.
	 *
	 * @param width  the map width
	 * @param height the map height
	 */
	public Plane(int width, int height) {
		this.width = width;
		this.height = height;
		this.data = new byte[width * height];
	}

	/**
	 * Getter method for the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Getter method for the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns a single map block.
	 *
	 * @param x the x position
	 * @param y the y position
	 * @return the block
	 */
	public int getBlock(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return 0;
		} else {
			return data[y * width + x] & 0xff;
		}
	}

	/**
	 * Changes a single map block.
	 *
	 * @param x     the x position
	 * @param y     the y position
	 * @param value the value to set, in the range 0..255
	 */
	public void setBlock(int x, int y, int value) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			throw new IllegalArgumentException("invalid position (" + x + ", " + y + ") for map size (" + width + " x " + height + ")");
		}
		if (value < 0 || value > 255) {
			throw new IllegalArgumentException("invalid block value: " + value);
		}
		data[y * width + x] = (byte) value;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Draws this plane.
	 */
	void drawInternal(TextureProvider textureProvider, Texture playerTexture) {
		if (textureProvider == null) {
			return;
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor3ub((byte) 255, (byte) 255, (byte) 255);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Texture texture = textureProvider.getBlockTexture(getBlock(x, y));
				if (texture == null) {
					continue;
				}
				texture.glBindTexture();
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0.0f, 1.0f);
				GL11.glVertex2d(x - 0.5, y - 0.5);
				GL11.glTexCoord2f(1.0f, 1.0f);
				GL11.glVertex2d(x + 0.5, y - 0.5);
				GL11.glTexCoord2f(1.0f, 0.0f);
				GL11.glVertex2d(x + 0.5, y + 0.5);
				GL11.glTexCoord2f(0.0f, 0.0f);
				GL11.glVertex2d(x - 0.5, y + 0.5);
				GL11.glEnd();
			}
		}
		player.drawInternal(playerTexture);
	}

}

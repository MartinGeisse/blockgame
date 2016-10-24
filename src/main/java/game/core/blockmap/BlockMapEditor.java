/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.core.blockmap;

/**
 * This class can be used as a helper to fill a block map with content.
 */
public class BlockMapEditor {

	/**
	 * the map
	 */
	private final BlockMapBehavior map;

	/**
	 * the currentBlockValue
	 */
	private int currentBlockValue;

	/**
	 * Constructor.
	 * @param map the map to edit
	 */
	public BlockMapEditor(BlockMapBehavior map) {
		this.map = map;
	}

	/**
	 * This function mirrors the {@link BlockMapBehavior#setBlock(int, int, int)} method.
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param value the value to set, in the range 0..255
	 * @return this
	 */
	public BlockMapEditor setBlock(int x, int y, int value) {
		map.setBlock(x, y, value);
		return this;
	}

	/**
	 * Changes the current block value.
	 * 
	 * @param value the new value to use
	 * @return this
	 */
	public BlockMapEditor withBlock(int value) {
		this.currentBlockValue = value;
		return this;
	}

	/**
	 * Draws a block at the specified position using the current block value.
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @return this
	 */
	public BlockMapEditor setBlock(int x, int y) {
		map.setBlock(x, y, currentBlockValue);
		return this;
	}

	/**
	 * Draws a horizontal line, starting at the specified point and extending to the right.
	 * 
	 * @param x the starting x position
	 * @param y the starting y position
	 * @param length the length of the line
	 * @return this
	 */
	public BlockMapEditor hline(int x, int y, int length) {
		if (length < 0) {
			throw new IllegalArgumentException("length is negative: " + length);
		}
		for (int i = 0; i < length; i++) {
			setBlock(x + i, y);
		}
		return this;
	}

	/**
	 * Draws a vertical line, starting at the specified point and extending downwards.
	 * 
	 * @param x the starting x position
	 * @param y the starting y position
	 * @param length the length of the line
	 * @return this
	 */
	public BlockMapEditor vline(int x, int y, int length) {
		if (length < 0) {
			throw new IllegalArgumentException("length is negative: " + length);
		}
		for (int i = 0; i < length; i++) {
			setBlock(x, y + i);
		}
		return this;
	}

	/**
	 * Draws a filled rectangle, starting at the specified point and extending to the right and downwards.
	 * 
	 * @param x the starting x position
	 * @param y the starting y position
	 * @param w the width of the rectangle
	 * @param h the height of the rectangle
	 * @return this
	 */
	public BlockMapEditor fillRect(int x, int y, int w, int h) {
		if (w < 0 || h < 0) {
			throw new IllegalArgumentException("invalid rectangle size: " + w + " x " + h);
		}
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				setBlock(x + i, y + j);
			}
		}
		return this;
	}

}

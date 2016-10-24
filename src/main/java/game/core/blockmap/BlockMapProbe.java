/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.core.blockmap;

import game.core.geometry.ReadablePosition;

/**
 * This class is able to sample a block map.
 */
public class BlockMapProbe {

	/**
	 * the blockMap
	 */
	private final BlockMapBehavior blockMap;

	/**
	 * the leftExtent
	 */
	private final float leftExtent;

	/**
	 * the rightExtent
	 */
	private final float rightExtent;

	/**
	 * the topExtent
	 */
	private final float topExtent;

	/**
	 * the bottomExtent
	 */
	private final float bottomExtent;

	/**
	 * Constructor.
	 * @param blockMap the block map
	 * @param leftExtent the extent to the left
	 * @param rightExtent the extent to the right
	 * @param topExtent the extent to the top
	 * @param bottomExtent the extent to the bottom
	 */
	public BlockMapProbe(BlockMapBehavior blockMap, float leftExtent, float rightExtent, float topExtent, float bottomExtent) {
		this.blockMap = blockMap;
		this.leftExtent = leftExtent;
		this.rightExtent = rightExtent;
		this.topExtent = topExtent;
		this.bottomExtent = bottomExtent;
	}

	/**
	 * Getter method for the blockMap.
	 * @return the blockMap
	 */
	public final BlockMapBehavior getBlockMap() {
		return blockMap;
	}

	/**
	 * Getter method for the leftExtent.
	 * @return the leftExtent
	 */
	public final float getLeftExtent() {
		return leftExtent;
	}

	/**
	 * Getter method for the rightExtent.
	 * @return the rightExtent
	 */
	public final float getRightExtent() {
		return rightExtent;
	}

	/**
	 * Getter method for the topExtent.
	 * @return the topExtent
	 */
	public final float getTopExtent() {
		return topExtent;
	}

	/**
	 * Getter method for the bottomExtent.
	 * @return the bottomExtent
	 */
	public final float getBottomExtent() {
		return bottomExtent;
	}

	/**
	 * Applies an {@link IsolatedBlockHandler} to each block touched by this probe. The results
	 * from the handler are ignored.
	 * 
	 * @param position the probe position
	 * @param handler the handler to call for each block
	 */
	public final void foreachBlock(ReadablePosition position, IsolatedBlockHandler<?> handler) {
		int x1 = (int)(position.getX() - leftExtent);
		int x2 = (int)(position.getX() + rightExtent);
		int y1 = (int)(position.getY() - topExtent);
		int y2 = (int)(position.getY() + bottomExtent);
		for (int x = x1; x <= x2; x++) {
			for (int y = y1; y <= y2; y++) {
				handler.handle(blockMap, x, y, blockMap.getBlock(x, y));
			}
		}
	}

	/**
	 * A function that handles each touched block in isolation, optionally returning a result per block.
	 *
	 * @param <T> the result type
	 */
	@FunctionalInterface
	public interface IsolatedBlockHandler<T> {

		/**
		 * Handles a single block.
		 * 
		 * @param blockMap the block map
		 * @param x the x position of the block
		 * @param y the y position of the block
		 * @param blockValue the block value
		 * @return the result for this block
		 */
		public T handle(BlockMapBehavior blockMap, int x, int y, int blockValue);

	}

	/**
	 * Applies a {@link ReducingBlockHandler} to each block touched by this probe,
	 * accumulating a result value of type T.
	 * 
	 * @param position the probe position
	 * @param initialValue the initially "accumulated" value (for 0 touched blocks)
	 * @param handler the handler to call for each block
	 * @return the accumulated value
	 */
	public final <T> T reduceBlocks(ReadablePosition position, T initialValue, ReducingBlockHandler<T> handler) {
		int x1 = (int)Math.floor(position.getX() - leftExtent);
		int x2 = (int)Math.ceil(position.getX() + rightExtent);
		int y1 = (int)Math.floor(position.getY() - topExtent);
		int y2 = (int)Math.ceil(position.getY() + bottomExtent);
		T value = initialValue;
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				value = handler.handle(blockMap, x, y, blockMap.getBlock(x, y), value);
			}
		}
		return value;
	}

	/**
	 * A function that reduces blocks to a result value.
	 *
	 * @param <T> the type of the resulting value
	 */
	@FunctionalInterface
	public interface ReducingBlockHandler<T> {

		/**
		 * Handles a single block.
		 * 
		 * @param blockMap the block map
		 * @param x the x position of the block
		 * @param y the y position of the block
		 * @param blockValue the block value
		 * @param previousResult the previously accumulated result
		 * @return the new result
		 */
		public T handle(BlockMapBehavior blockMap, int x, int y, int blockValue, T previousResult);

	}

	/**
	 * Checks if any touched block is solid according to the specified solidity
	 * flags, assuming the probe is at the specified position.
	 * 
	 * This method does not take shortcuts. That is, even if a solid block is found (and the result is
	 * therefore true), then the remaining blocks are still checked to trigger all side effects in the
	 * solidity function.
	 * 
	 * @param position the probe position
	 * @param solidity defines the solidity of blocks
	 * @return true if any block is solid, false if none is
	 */
	public boolean checkAny(ReadablePosition position, IsolatedBlockHandler<Boolean> solidity) {
		return reduceBlocks(position, false, (map, x, y, block, result) -> (result || solidity.handle(map, x, y, block)));
	}

	/**
	 * Checks if all touched blocks are solid according to the specified solidity
	 * flags, assuming the probe is at the specified position.
	 * 
	 * This method does not take shortcuts. That is, even if a non-solid block is found (and the result is
	 * therefore false), then the remaining blocks are still checked to trigger all side effects in the
	 * solidity function.
	 * 
	 * @param position the probe position
	 * @param solidity defines the solidity of blocks
	 * @return true if all blocks are solid, false if any is nonsolid
	 */
	public boolean checkAll(ReadablePosition position, IsolatedBlockHandler<Boolean> solidity) {
		return reduceBlocks(position, true, (map, x, y, block, result) -> (result && solidity.handle(map, x, y, block)));
	}

}

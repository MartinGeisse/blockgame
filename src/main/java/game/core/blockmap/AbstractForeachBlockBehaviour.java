/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.core.blockmap;

import game.core.AbstractBehavior;
import game.core.GameObject;
import game.core.blockmap.BlockMapProbe.IsolatedBlockHandler;
import game.core.geometry.MutablePosition;
import game.core.movement.PositionBehavior;

/**
 * Base class to implement a behavior that samples all touched blocks in
 * a block map, e.g. to collect bonus objects. You should restrict the
 * number of behaviors of this kind as each one samples the blocks over
 * and over again.
 */
public abstract class AbstractForeachBlockBehaviour extends AbstractBehavior implements IsolatedBlockHandler<Void> {

	/**
	 * the blockMapProbe
	 */
	private final BlockMapProbe blockMapProbe;

	/**
	 * Constructor.
	 * @param blockMapProbe the block map probe
	 */
	public AbstractForeachBlockBehaviour(BlockMapProbe blockMapProbe) {
		this.blockMapProbe = blockMapProbe;
	}
	
	/* (non-Javadoc)
	 * @see game.core.AbstractBehavior#handleStep(game.core.GameObject)
	 */
	@Override
	public void handleStep(GameObject target) {
		MutablePosition position = target.getBehavior(PositionBehavior.class).getMutablePosition();
		blockMapProbe.foreachBlock(position, this);
	}

}

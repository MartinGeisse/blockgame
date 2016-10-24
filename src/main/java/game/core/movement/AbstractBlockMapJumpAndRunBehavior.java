/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.core.movement;

import game.core.GameObject;
import game.core.blockmap.BlockMapProbe;
import game.core.blockmap.BlockMapProbe.IsolatedBlockHandler;
import game.core.geometry.MutablePosition;
import game.core.geometry.Position;

/**
 * Jump'n'run behavior based on a block map.
 */
public abstract class AbstractBlockMapJumpAndRunBehavior extends AbstractJumpAndRunBehavior {

	/**
	 * the blockMapProbe
	 */
	private final BlockMapProbe blockMapProbe;
	
	/**
	 * the blockSolidity
	 */
	private final IsolatedBlockHandler<Boolean> blockSolidity;
	

	/**
	 * Constructor.
	 * @param blockMapProbe the probe used for collision detection
	 * @param blockSolidity specifies which blocks are solid
	 */
	public AbstractBlockMapJumpAndRunBehavior(BlockMapProbe blockMapProbe, final IsolatedBlockHandler<Boolean> blockSolidity) {
		this.blockMapProbe = blockMapProbe;
		this.blockSolidity = blockSolidity;
	}

	/* (non-Javadoc)
	 * @see game.core.movement.AbstractJumpAndRunBehavior#adjustHorizontalMovement(game.core.GameObject, float)
	 */
	@Override
	protected float adjustHorizontalMovement(GameObject target, float dx) {
		if (!isBlocked(target, dx, 0)) {
			return dx;
		}
		float dxTotal = 0;
		for (int i = 0; i < 3; i++) {
			dx = dx / 2.0f;
			if (!isBlocked(target, dxTotal + dx, 0)) {
				dxTotal += dx;
			}
		}
		return dxTotal;
	}

	/* (non-Javadoc)
	 * @see game.core.movement.AbstractJumpAndRunBehavior#adjustVerticalMovement(game.core.GameObject, float)
	 */
	@Override
	protected float adjustVerticalMovement(GameObject target, float dy) {
		if (!isBlocked(target, 0, dy)) {
			return dy;
		}
		float dyTotal = 0;
		for (int i = 0; i < 3; i++) {
			dy = dy / 2.0f;
			if (!isBlocked(target, 0, dyTotal + dy)) {
				dyTotal += dy;
			}
		}
		return dyTotal;
	}
	
	/**
	 * 
	 */
	private boolean isBlocked(GameObject target, float dx, float dy) {
		MutablePosition position = target.getBehavior(PositionBehavior.class).getMutablePosition();
		Position newPosition = new Position(position.getX() + dx, position.getY() + dy);
		return blockMapProbe.checkAny(newPosition, blockSolidity);
	}

}

/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.core.movement;

import org.lwjgl.opengl.GL11;

import game.core.AbstractBehavior;
import game.core.GameObject;
import game.core.geometry.MutablePosition;
import game.core.geometry.ReadablePosition;

/**
 * Objects with this behavior have a position in the world and can be moved.
 */
public final class PositionBehavior extends AbstractBehavior {

	/**
	 * the mutablePosition
	 */
	private final MutablePosition mutablePosition = new MutablePosition(0, 0);

	/**
	 * Constructor.
	 */
	public PositionBehavior() {
	}
	
	/**
	 * Constructor.
	 * @param initialPosition the initial position
	 */
	public PositionBehavior(ReadablePosition initialPosition) {
		mutablePosition.copyFrom(initialPosition);
	}
	
	
	/**
	 * Getter method for the mutablePosition.
	 * @return the mutablePosition
	 */
	public MutablePosition getMutablePosition() {
		return mutablePosition;
	}
	
	/* (non-Javadoc)
	 * @see game.core.AbstractBehavior#prepareRenderState(game.core.GameObject)
	 */
	@Override
	public void prepareRenderState(GameObject target) {
		GL11.glTranslatef(mutablePosition.getX(), mutablePosition.getY(), 0.0f);
	}
	
}

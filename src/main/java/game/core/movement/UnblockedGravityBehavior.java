/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.core.movement;

import game.core.AbstractBehavior;
import game.core.GameObject;
import game.core.geometry.MutablePosition;

/**
 * Makes an object fall down with gravity but does not perform collision detection.
 * Can be used for effects.
 */
public class UnblockedGravityBehavior extends AbstractBehavior {

	/**
	 * the MAX_VERTICAL_SPEED
	 */
	private static final float MAX_VERTICAL_SPEED = 0.8f;

	/**
	 * the dx
	 */
	private float dx;

	/**
	 * the dy
	 */
	private float dy;

	/**
	 * the gravity
	 */
	private float gravity;

	/**
	 * Getter method for the dx.
	 * @return the dx
	 */
	public float getDx() {
		return dx;
	}

	/**
	 * Setter method for the dx.
	 * @param dx the dx to set
	 */
	public void setDx(float dx) {
		this.dx = dx;
	}

	/**
	 * Getter method for the dy.
	 * @return the dy
	 */
	public float getDy() {
		return dy;
	}

	/**
	 * Setter method for the dy.
	 * @param dy the dy to set
	 */
	public void setDy(float dy) {
		this.dy = dy;
	}

	/**
	 * Getter method for the gravity.
	 * @return the gravity
	 */
	public float getGravity() {
		return gravity;
	}

	/**
	 * Setter method for the gravity.
	 * @param gravity the gravity to set
	 */
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	/* (non-Javadoc)
	 * @see game.core.AbstractBehavior#handleStep(game.core.GameObject)
	 */
	@Override
	public void handleStep(GameObject target) {
		dy += gravity;
		if (dy > MAX_VERTICAL_SPEED) {
			dy = MAX_VERTICAL_SPEED;
		}
		MutablePosition mutablePosition = target.getBehavior(PositionBehavior.class).getMutablePosition();
		mutablePosition.setX(mutablePosition.getX() + dx);
		mutablePosition.setY(mutablePosition.getY() + dy);
	}

}

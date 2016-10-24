/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.core.movement;

import game.core.AbstractBehavior;
import game.core.GameObject;
import game.core.LeftRight;
import game.core.geometry.MutablePosition;

import org.lwjgl.input.Keyboard;

/**
 * Can be combined with a {@link PositionBehavior} to move the object using the arrow keys.
 */
public final class CursorMoveBehavior extends AbstractBehavior {

	/**
	 * the speed
	 */
	private float speed;

	/**
	 * Constructor.
	 */
	public CursorMoveBehavior() {
		this(1.0f);
	}

	/**
	 * Constructor.
	 * @param speed the movement speed (units per frame)
	 */
	public CursorMoveBehavior(float speed) {
		this.speed = speed;
	}

	/**
	 * Getter method for the speed.
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * Setter method for the speed.
	 * @param speed the speed to set
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	/* (non-Javadoc)
	 * @see game.core.AbstractBehavior#handleStep(game.core.GameObject)
	 */
	@Override
	public void handleStep(GameObject target) {
		handleKey(target, Keyboard.KEY_UP, 0, -speed);
		handleKey(target, Keyboard.KEY_DOWN, 0, speed);
		if (handleKey(target, Keyboard.KEY_LEFT, -speed, 0)) {
			handleLeftRight(target, LeftRight.LEFT);
		}
		if (handleKey(target, Keyboard.KEY_RIGHT, speed, 0)) {
			handleLeftRight(target, LeftRight.RIGHT);
		}
	}

	/**
	 * 
	 */
	private boolean handleKey(GameObject target, int key, float dx, float dy) {
		if (Keyboard.isKeyDown(key)) {
			MutablePosition mutablePosition = target.getBehavior(PositionBehavior.class).getMutablePosition();
			mutablePosition.setX(mutablePosition.getX() + dx);
			mutablePosition.setY(mutablePosition.getY() + dy);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 */
	private void handleLeftRight(GameObject target, LeftRight orientation) {
		LeftRightOrientationBehavior behavior = target.getBehavior(LeftRightOrientationBehavior.class);
		if (behavior != null) {
			behavior.setOrientation(orientation);
		}
	}
	
}

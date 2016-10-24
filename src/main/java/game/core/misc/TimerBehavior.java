/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.core.misc;

import game.core.AbstractBehavior;
import game.core.GameObject;

/**
 * Fires a callback method after a specified number of frames.
 */
public abstract class TimerBehavior extends AbstractBehavior {

	/**
	 * the remainingFrames
	 */
	private int remainingFrames;

	/**
	 * Constructor.
	 * @param remainingFrames the remaining number of frames
	 */
	public TimerBehavior(int remainingFrames) {
		this.remainingFrames = remainingFrames;
	}

	/* (non-Javadoc)
	 * @see game.core.AbstractBehavior#handleStep(game.core.GameObject)
	 */
	@Override
	public void handleStep(GameObject target) {
		remainingFrames--;
		if (remainingFrames <= 0) {
			onExpire(target);
			target.detachBehavior(this);
		}
	}
	
	/**
	 * Called when the timer expires.
	 */
	protected abstract void onExpire(GameObject target);
	
}

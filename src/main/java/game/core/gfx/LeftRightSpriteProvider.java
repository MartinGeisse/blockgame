/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.core.gfx;

import game.core.GameObject;
import game.core.LeftRight;
import game.core.movement.LeftRightOrientationBehavior;
import game.engine.gfx.Sprite;
import game.engine.gfx.SpriteProvider;

/**
 * A sprite provider that delegates to either one of two providers
 * based on the {@link LeftRightOrientationBehavior} of the game
 * object.
 */
public final class LeftRightSpriteProvider implements SpriteProvider {

	/**
	 * the leftProvider
	 */
	private final SpriteProvider leftProvider;

	/**
	 * the rightProvider
	 */
	private final SpriteProvider rightProvider;

	/**
	 * Constructor.
	 * @param leftProvider the left provider
	 * @param rightProvider the right provider
	 */
	public LeftRightSpriteProvider(SpriteProvider leftProvider, SpriteProvider rightProvider) {
		this.leftProvider = leftProvider;
		this.rightProvider = rightProvider;
	}

	/**
	 * Getter method for the leftProvider.
	 * @return the leftProvider
	 */
	public SpriteProvider getLeftProvider() {
		return leftProvider;
	}

	/**
	 * Getter method for the rightProvider.
	 * @return the rightProvider
	 */
	public SpriteProvider getRightProvider() {
		return rightProvider;
	}

	/* (non-Javadoc)
	 * @see game.core.gfx.SpriteProvider#provideSprite(game.core.GameObject)
	 */
	@Override
	public Sprite provideSprite(GameObject object) {
		LeftRightOrientationBehavior behavior = object.getBehavior(LeftRightOrientationBehavior.class);
		if (behavior == null || behavior.getOrientation() == null) {
			return null;
		} else if (behavior.getOrientation() == LeftRight.LEFT) {
			return leftProvider.provideSprite(object);
		} else {
			return rightProvider.provideSprite(object);
		}
	}

}

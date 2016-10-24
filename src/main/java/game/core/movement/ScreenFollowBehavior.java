/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.core.movement;

import game.core.AbstractBehavior;
import game.core.Game;
import game.core.GameObject;
import game.core.geometry.MutablePosition;

/**
 * Makes the screen follow the target object.
 */
public class ScreenFollowBehavior extends AbstractBehavior {

	/**
	 * the SLACK
	 */
	private static final float SLACK = 5.0f;
	
	/**
	 * the game
	 */
	private final Game game;

	/**
	 * Constructor.
	 * @param game the game
	 */
	public ScreenFollowBehavior(Game game) {
		this.game = game;
	}

	/* (non-Javadoc)
	 * @see game.core.AbstractBehavior#handleStep(game.core.GameObject)
	 */
	@Override
	public void handleStep(GameObject target) {
		MutablePosition position = target.getBehavior(PositionBehavior.class).getMutablePosition();
		game.setScreenX(limitBaseSlack(game.getScreenX(), position.getX() - game.getScreenWidthUnits() / 2, SLACK));
		game.setScreenY(limitBaseSlack(game.getScreenY(), position.getY() - game.getScreenHeightUnits() / 2, SLACK));
	}

	private float limitBaseSlack(float value, float base, float slack) {
		return limitMinMax(value, base - slack, base + slack);
	}

	private float limitMinMax(float value, float min, float max) {
		return (value < min ? min : value > max ? max : value);
	}

}

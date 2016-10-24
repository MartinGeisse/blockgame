/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.engine.game;

import game.engine.frame.AbstractFrameHandler;
import game.engine.frame.BreakFrameLoopException;

/**
 * The main game-related frame handler. This just acts as a proxy for the {@link Game}.
 */
public final class FrameHandler extends AbstractFrameHandler {

	/**
	 * the game
	 */
	private final Game game;

	/**
	 * Constructor.
	 * @param game the game
	 */
	public FrameHandler(Game game) {
		if (game == null) {
			throw new IllegalArgumentException("game cannot be null");
		}
		this.game = game;
	}

	/* (non-Javadoc)
	 * @see game.engine.frame.AbstractFrameHandler#draw()
	 */
	@Override
	public void draw() {
		game.draw();
	}

	/* (non-Javadoc)
	 * @see game.engine.frame.AbstractFrameHandler#handleStep()
	 */
	@Override
	public void handleStep() throws BreakFrameLoopException {
		game.handleTick();
	}

}

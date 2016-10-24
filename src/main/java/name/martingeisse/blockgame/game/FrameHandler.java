/**
 * Copyright (c) 2011 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.blockgame.game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

/**
 * Represents the actions that get repeated every frame.
 */
public final class FrameHandler {

	private final Game game;

	public FrameHandler(Game game) {
		this.game = game;
	}

	/**
	 * Handles a step. This method performs the game logic.
	 *
	 * @throws BreakFrameLoopException if this handler wants to break the frame loop
	 */
	public void handleStep() throws BreakFrameLoopException {
		if (Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			throw new BreakFrameLoopException();
		}
		game.handleStep();
	}

	/**
	 * Draws the screen contents using OpenGL.
	 */
	public void draw() {
		game.draw();
	}

}

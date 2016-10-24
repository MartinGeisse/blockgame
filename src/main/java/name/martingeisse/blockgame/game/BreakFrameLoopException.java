/**
 * Copyright (c) 2011 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.blockgame.game;

/**
 * The frame handler can throw this exception to stop the frame loop.
 */
public final class BreakFrameLoopException extends RuntimeException {

	/**
	 * Constructor.
	 */
	public BreakFrameLoopException() {
	}
	
}

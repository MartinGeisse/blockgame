/**
 * Copyright (c) 2011 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package game.engine.frame;

/**
 * Represents an action that gets repeated every frame.
 * 
 * A frame handler can have child handlers. The children's
 * methods are simply called after the parent's method; this
 * allows to combine handlers simple by returning other handlers
 * as children.
 */
public interface IFrameHandler {

	/**
	 * Called just before calling {@link #handleStep()} on all handlers.
	 * This method should prepare data that this or other handlers might
	 * require in their handleStep() method.
	 */
	public void onBeforeHandleStep();
	
	/**
	 * Handles a game step. This method performs the game logic.
	 * @throws BreakFrameLoopException if this handler wants to break the frame loop
	 */
	public void handleStep() throws BreakFrameLoopException;
	
	/**
	 * Called just after calling {@link #handleStep()} on all handlers.
	 * This method should discard unused data.
	 */
	public void onAfterHandleStep();
	
	/**
	 * Called just before calling {@link #draw()} on all handlers.
	 * This method should prepare data that this or other handlers might
	 * require in their draw() method.
	 */
	public void onBeforeDraw();
	
	/**
	 * Draws the screen contents using OpenGL.
	 */
	public void draw();

	/**
	 * Called just after calling {@link #draw()} on all handlers.
	 * This method should discard unused data.
	 */
	public void onAfterDraw();
	
}

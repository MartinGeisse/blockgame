/**
 * Copyright (c) 2010 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package game.engine.frame;


/**
 * Base implementation of {@link IFrameHandler}.
 */
public abstract class AbstractFrameHandler implements IFrameHandler {

	/* (non-Javadoc)
	 * @see name.martingeisse.stackd.frame.IFrameHandler#onBeforeHandleStep()
	 */
	@Override
	public void onBeforeHandleStep() {
	}

	/* (non-Javadoc)
	 * @see name.martingeisse.stackd.frame.IFrameHandler#handleStep()
	 */
	@Override
	public void handleStep() throws BreakFrameLoopException {
	}

	/* (non-Javadoc)
	 * @see name.martingeisse.stackd.frame.IFrameHandler#onAfterHandleStep()
	 */
	@Override
	public void onAfterHandleStep() {
	}

	/* (non-Javadoc)
	 * @see game.engine.frame.IFrameHandler#onBeforeDraw()
	 */
	@Override
	public void onBeforeDraw() {
	}
	
	/* (non-Javadoc)
	 * @see game.engine.frame.IFrameHandler#draw()
	 */
	@Override
	public void draw() {
	}
	
	/* (non-Javadoc)
	 * @see game.engine.frame.IFrameHandler#onAfterDraw()
	 */
	@Override
	public void onAfterDraw() {
	}

}

/**
 * Copyright (c) 2010 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package game.engine.frame.handlers;

import game.engine.frame.BreakFrameLoopException;
import game.engine.frame.IFrameHandler;

/**
 * This is a simple handler that contains another wrapped handler
 * and allows to swap that wrapped handler during runtime.
 * All handler methods are simply forwarded to the wrapped handler,
 * or skipped when the wrapped handler was set to null.
 */
public final class SwappableHandler implements IFrameHandler {

	/**
	 * the wrappedHandler
	 */
	private IFrameHandler wrappedHandler;
	
	/**
	 * Constructor.
	 */
	public SwappableHandler() {
	}

	/**
	 * Constructor.
	 * @param wrappedHandler the wrapped handler
	 */
	public SwappableHandler(IFrameHandler wrappedHandler) {
		this.wrappedHandler = wrappedHandler;
	}

	/**
	 * Getter method for the wrappedHandler.
	 * @return the wrappedHandler
	 */
	public IFrameHandler getWrappedHandler() {
		return wrappedHandler;
	}
	
	/**
	 * Setter method for the wrappedHandler.
	 * @param wrappedHandler the wrappedHandler to set
	 */
	public void setWrappedHandler(IFrameHandler wrappedHandler) {
		this.wrappedHandler = wrappedHandler;
	}
	
	/* (non-Javadoc)
	 * @see name.martingeisse.stackd.client.frame.IFrameHandler#onBeforeHandleStep()
	 */
	@Override
	public void onBeforeHandleStep() {
		if (wrappedHandler != null) {
			wrappedHandler.onBeforeHandleStep();
		}
	}

	/* (non-Javadoc)
	 * @see name.martingeisse.stackd.client.frame.IFrameHandler#handleStep()
	 */
	@Override
	public void handleStep() throws BreakFrameLoopException {
		if (wrappedHandler != null) {
			wrappedHandler.handleStep();
		}
	}

	/* (non-Javadoc)
	 * @see name.martingeisse.stackd.client.frame.IFrameHandler#onAfterHandleStep()
	 */
	@Override
	public void onAfterHandleStep() {
		if (wrappedHandler != null) {
			wrappedHandler.onAfterHandleStep();
		}
	}

	/* (non-Javadoc)
	 * @see game.engine.frame.IFrameHandler#onBeforeDraw()
	 */
	@Override
	public void onBeforeDraw() {
		if (wrappedHandler != null) {
			wrappedHandler.onBeforeDraw();
		}
	}

	/* (non-Javadoc)
	 * @see game.engine.frame.IFrameHandler#draw()
	 */
	@Override
	public void draw() {
		if (wrappedHandler != null) {
			wrappedHandler.draw();
		}
	}

	/* (non-Javadoc)
	 * @see game.engine.frame.IFrameHandler#onAfterDraw()
	 */
	@Override
	public void onAfterDraw() {
		if (wrappedHandler != null) {
			wrappedHandler.onAfterDraw();
		}
	}

}

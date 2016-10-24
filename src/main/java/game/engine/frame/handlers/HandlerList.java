/**
 * Copyright (c) 2010 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package game.engine.frame.handlers;

import game.engine.frame.BreakFrameLoopException;
import game.engine.frame.IFrameHandler;

import java.util.ArrayList;

/**
 * This handler is a list of other handlers. Each method call is
 * forwarded to all of them.
 */
public class HandlerList extends ArrayList<IFrameHandler> implements IFrameHandler {

	/**
	 * Constructor.
	 */
	public HandlerList() {
	}

	/* (non-Javadoc)
	 * @see name.martingeisse.stackd.client.frame.IFrameHandler#onBeforeHandleStep()
	 */
	@Override
	public void onBeforeHandleStep() {
		for (IFrameHandler handler : this) {
			handler.onBeforeHandleStep();
		}
	}

	/* (non-Javadoc)
	 * @see name.martingeisse.stackd.client.frame.IFrameHandler#handleStep()
	 */
	@Override
	public void handleStep() throws BreakFrameLoopException {
		for (IFrameHandler handler : this) {
			handler.handleStep();
		}
	}

	/* (non-Javadoc)
	 * @see name.martingeisse.stackd.client.frame.IFrameHandler#onAfterHandleStep()
	 */
	@Override
	public void onAfterHandleStep() {
		for (IFrameHandler handler : this) {
			handler.onAfterHandleStep();
		}
	}

	/* (non-Javadoc)
	 * @see game.engine.frame.IFrameHandler#onBeforeDraw()
	 */
	@Override
	public void onBeforeDraw() {
		for (IFrameHandler handler : this) {
			handler.onBeforeDraw();
		}
	}

	/* (non-Javadoc)
	 * @see game.engine.frame.IFrameHandler#draw()
	 */
	@Override
	public void draw() {
		for (IFrameHandler handler : this) {
			handler.draw();
		}
	}

	/* (non-Javadoc)
	 * @see game.engine.frame.IFrameHandler#onAfterDraw()
	 */
	@Override
	public void onAfterDraw() {
		for (IFrameHandler handler : this) {
			handler.onAfterDraw();
		}
	}

}

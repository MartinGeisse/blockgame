/**
 * Copyright (c) 2011 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package game.engine.frame;

import static org.lwjgl.opengl.GL11.glFlush;
import game.engine.frame.handlers.HandlerList;
import game.engine.frame.handlers.SwappableHandler;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;


/**
 * Performs a loop, typically drawing the world each
 * frame and potentially doing other things. At a lower
 * level, this loop object keeps a hierarchy of handlers that
 * are executed each frame. The hierarchy is anchored in a
 * single root handler of type {@link SwappableHandler} that allows
 * to put an arbitrary application handler -- typically itself
 * a {@link HandlerList} -- in place.
 */
public final class FrameLoop {

	/**
	 * the rootHandler
	 */
	private final SwappableHandler rootHandler;
	
	/**
	 * Constructor.
	 */
	public FrameLoop() {
		this.rootHandler = new SwappableHandler();
	}
	
	/**
	 * Getter method for the rootHandler.
	 * @return the rootHandler
	 */
	public SwappableHandler getRootHandler() {
		return rootHandler;
	}
	
	/**
	 * Executes a single frame, executing all handlers.
	 * @throws BreakFrameLoopException if a handler wants to break the frame loop
	 */
	public void executeFrame() throws BreakFrameLoopException {
		
		// draw all handlers
		try {
			rootHandler.onBeforeDraw();
			rootHandler.draw();
			rootHandler.onAfterDraw();
			glFlush();
			Display.update();
			Display.processMessages();
		} catch (Exception e) {
			throw new RuntimeException("unexpected exception while drawing", e);
		}
		
		// handle inputs and OS messages
		Mouse.poll();
		Keyboard.poll();
		
		// prepare game logic steps
		try {
			rootHandler.onBeforeHandleStep();
			rootHandler.handleStep();
			rootHandler.onAfterHandleStep();
		} catch (BreakFrameLoopException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("unexpected exception during onBeforeHandleStep()", e);
		}
		
	}

	/**
	 * Executes frames using {@link #executeFrame()} endlessly
	 * until one of the handlers throws a {@link BreakFrameLoopException}.
	 * 
	 * @param fixedFrameInterval the fixed minimum length of each frame in
	 * milliseconds, or null to run as many frames as possible
	 */
	public void executeLoop(Integer fixedFrameInterval) {
		FrameTimer frameTimer = (fixedFrameInterval == null ? null : new FrameTimer(fixedFrameInterval));
		try {
			while (true) {
				executeFrame();
				if (frameTimer != null) {
					while (!frameTimer.test()) {
						synchronized(frameTimer) {
							frameTimer.wait();
						}
					}
				}
			}
		} catch (InterruptedException e) {
		} catch (BreakFrameLoopException e) {
		}
	}
	
}

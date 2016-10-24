/**
 * Copyright (c) 2011 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.blockgame.game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.Util;

import static org.lwjgl.opengl.GL11.glFlush;


/**
 *
 */
public final class FrameLoop {

	private final FrameHandler frameHandler;
	
	/**
	 * Constructor.
	 */
	public FrameLoop(FrameHandler frameHandler) {
		this.frameHandler = frameHandler;
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
	/**
	 * Executes a single frame.
	 *
	 * @throws BreakFrameLoopException if a handler wants to break the frame loop
	 */
	private void executeFrame() throws BreakFrameLoopException {
		
		// draw
		frameHandler.draw();
		Util.checkGLError();
		glFlush();
		Display.update();

		// handle inputs and OS messages
		Display.processMessages();
		Mouse.poll();
		Keyboard.poll();
		
		// handle logic
		frameHandler.handleStep();

	}

}

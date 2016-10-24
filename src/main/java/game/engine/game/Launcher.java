/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.engine.game;

import game.engine.resource.DefaultResouceLoader;
import game.engine.resource.DefaultResourceManager;
import game.engine.resource.Resources;
import game.engine.system.LwjglNativeLibraryHelper;

import org.apache.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

/**
 * Launches the game system.
 */
public class Launcher {

	/**
	 * the logger
	 */
	private static Logger logger = Logger.getLogger(Launcher.class);
	
	/**
	 * the screenWidth
	 */
	private int screenWidth = 800;

	/**
	 * the screenHeight
	 */
	private int screenHeight = 600;

	/**
	 * the fullscreen
	 */
	private boolean fullscreen = false;

	/**
	 * Constructor.
	 * @param args command-line arguments
	 */
	public Launcher(String[] args) {
		logger.trace("parsing command line options...");
		for (final String arg : args) {
			if (arg.equals("-fs")) {
				fullscreen = true;
			}
		}
		logger.trace("command line options parsed");
	}
	
	/**
	 * Getter method for the screenWidth.
	 * @return the screenWidth
	 */
	public final int getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Setter method for the screenWidth.
	 * @param screenWidth the screenWidth to set
	 */
	public final void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	/**
	 * Getter method for the screenHeight.
	 * @return the screenHeight
	 */
	public final int getScreenHeight() {
		return screenHeight;
	}

	/**
	 * Setter method for the screenHeight.
	 * @param screenHeight the screenHeight to set
	 */
	public final void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	/**
	 * Getter method for the fullscreen.
	 * @return the fullscreen
	 */
	public final boolean isFullscreen() {
		return fullscreen;
	}

	/**
	 * Setter method for the fullscreen.
	 * @param fullscreen the fullscreen to set
	 */
	public final void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}

	/**
	 * Starts the game system.
	 * 
	 * @throws Exception on errors during initialization
	 */
	public void startup() throws Exception {
		logger.info("GameLauncher.launch() started");

		// include LWJGL native libraries
		LwjglNativeLibraryHelper.prepareNativeLibraries();

		// configure the display
		logger.trace("finding optimal display mode...");
		DisplayMode bestMode = null;
		int bestModeFrequency = -1;
		for (DisplayMode mode : Display.getAvailableDisplayModes()) {
			if (mode.getWidth() == screenWidth && mode.getHeight() == screenHeight && (mode.isFullscreenCapable() || !fullscreen)) {
				if (mode.getFrequency() > bestModeFrequency) {
					bestMode = mode;
					bestModeFrequency = mode.getFrequency();
				}
			}
		}
		if (bestMode == null) {
			bestMode = new DisplayMode(screenWidth, screenHeight);
		}
		logger.trace("setting intended display mode...");
		Display.setDisplayMode(bestMode);
		if (fullscreen) {
			Display.setFullscreen(true);
		}
		logger.trace("switching display mode...");
		Display.create(new PixelFormat(0, 24, 0));
		logger.trace("display initialized");

		// initialize the keyboard
		logger.trace("initializing keyboard...");
		Keyboard.create();
		Keyboard.poll();
		logger.trace("keyboard initialized");
		
		// initialize the mouse
		logger.trace("initializing mouse...");
		Mouse.create();
		Mouse.poll();
		logger.trace("mouse initialized");

		// prepare loading images and sounds
		Resources.setResourceManager(new DefaultResourceManager(new DefaultResouceLoader()));

	}

	/**
	 * Shuts down the game system.
	 */
	public void shutdown() {
		Mouse.destroy();
		Keyboard.destroy();
		Display.destroy();
	}
	
}

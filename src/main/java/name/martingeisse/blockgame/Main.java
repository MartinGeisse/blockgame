/**
 * Copyright (c) 2010 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.blockgame;

import name.martingeisse.blockgame.game.Camera;
import name.martingeisse.blockgame.game.FrameHandler;
import name.martingeisse.blockgame.game.FrameLoop;
import name.martingeisse.blockgame.game.Game;
import name.martingeisse.blockgame.resource.DefaultResouceLoader;
import name.martingeisse.blockgame.resource.DefaultResourceManager;
import name.martingeisse.blockgame.resource.Resources;
import name.martingeisse.blockgame.system.Launcher;
import name.martingeisse.blockgame.world.Plane;

/**
 * The main class.
 */
public class Main {

	/**
	 * The main method.
	 * 
	 * @param args command-line arguments
	 * @throws Exception on errors
	 */
	public static void main(String[] args) throws Exception {

//		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
//		ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");

		Plane plane = new Plane(10, 10);
		plane.setBlock(5, 1, 1);
		Camera camera = new Camera();
		camera.setZoom(2.0f);
		Game game = new Game(plane, camera);

		Launcher launcher = new Launcher(args);
		launcher.startup();
		Resources.setResourceManager(new DefaultResourceManager(new DefaultResouceLoader()));
		FrameLoop frameLoop = new FrameLoop(new FrameHandler(game));
		frameLoop.executeLoop(20);
		launcher.shutdown();
		System.exit(0);

	}

}

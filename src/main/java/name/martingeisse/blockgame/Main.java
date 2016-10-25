/**
 * Copyright (c) 2010 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.blockgame;

import name.martingeisse.blockgame.world.Camera;
import name.martingeisse.blockgame.game.FrameHandler;
import name.martingeisse.blockgame.game.FrameLoop;
import name.martingeisse.blockgame.game.Game;
import name.martingeisse.blockgame.resource.DefaultResouceLoader;
import name.martingeisse.blockgame.resource.DefaultResourceManager;
import name.martingeisse.blockgame.resource.Resources;
import name.martingeisse.blockgame.system.Launcher;
import name.martingeisse.blockgame.world.Plane;
import name.martingeisse.blockgame.world.Player;

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
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				if (x == 0 || x == 9 || y == 0 || y == 9) {
					plane.setBlock(x, y, 4);
				} else if ((x == 4 || x == 5) && (y == 4 || y == 5)) {
					plane.setBlock(x, y, 5);
				} else {
					plane.setBlock(x, y, 1);
				}
			}
		}

		Player player = new Player();
		player.setPositionX(2.0);
		player.setPositionY(2.0);
		plane.setPlayer(player);

		Camera camera = new Camera();
		camera.setZoom(2.0f);
		Game game = new Game(plane, camera);

		Launcher launcher = new Launcher(args);
		launcher.startup();
		Resources.setResourceManager(new DefaultResourceManager(new DefaultResouceLoader()));
		FrameLoop frameLoop = new FrameLoop(new FrameHandler(game));
		try {
			frameLoop.executeLoop(20);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			launcher.shutdown();
			System.exit(0);
		}

	}

}

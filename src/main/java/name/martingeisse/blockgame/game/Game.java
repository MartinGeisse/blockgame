package name.martingeisse.blockgame.game;

import name.martingeisse.blockgame.resource.Resources;
import name.martingeisse.blockgame.world.Plane;
import org.lwjgl.opengl.GL11;

/**
 *
 */
public final class Game {

	private final Plane plane;
	private final Camera camera;

	public Game(Plane plane, Camera camera) {
		this.plane = plane;
		this.camera = camera;
	}

	/**
	 * Handles a step. This method performs the game logic.
	 *
	 * @throws BreakFrameLoopException if this handler wants to break the frame loop
	 */
	public void handleStep() throws BreakFrameLoopException {
	}

	/**
	 * Draws the screen contents using OpenGL.
	 */
	public void draw() {
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		camera.prepare();
		plane.draw(value -> value == 0 ? null : Resources.getTexture("blockmap/tile1.png"));
	}

}

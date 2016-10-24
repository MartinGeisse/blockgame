/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.engine.game;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

/**
 * Implements a swappable scene in the game.
 * 
 * Future note: 3d support could be implemented by making this an interface
 * with Scene2d and Scene3d implementations.
 */
public class Scene {

	/**
	 * the zoom
	 */
	private float zoom = 1.0f;

	/**
	 * the screenX
	 */
	private float screenX = 0.0f;

	/**
	 * the screenY
	 */
	private float screenY = 0.0f;

	/**
	 * the gameObjects
	 */
	private final List<GameObject> gameObjects = new ArrayList<GameObject>();

	/**
	 * Getter method for the zoom.
	 * @return the zoom
	 */
	public float getZoom() {
		return zoom;
	}

	/**
	 * Setter method for the zoom.
	 * @param zoom the zoom to set
	 */
	public void setZoom(float zoom) {
		this.zoom = zoom;
	}

	/**
	 * Getter method for the screenX.
	 * @return the screenX
	 */
	public float getScreenX() {
		return screenX;
	}

	/**
	 * Setter method for the screenX.
	 * @param screenX the screenX to set
	 */
	public void setScreenX(float screenX) {
		this.screenX = screenX;
	}

	/**
	 * Getter method for the screenY.
	 * @return the screenY
	 */
	public float getScreenY() {
		return screenY;
	}

	/**
	 * Setter method for the screenY.
	 * @param screenY the screenY to set
	 */
	public void setScreenY(float screenY) {
		this.screenY = screenY;
	}

	/**
	 * Getter method for the screen width, in units.
	 * @return the screen width, in units
	 */
	public float getScreenWidthUnits() {
		return 30.0f * Display.getWidth() / Display.getHeight() / zoom;
	}

	/**
	 * Getter method for the screen height, in units.
	 * @return the screen height, in units
	 */
	public float getScreenHeightUnits() {
		return 30.0f / zoom;
	}
	
	/**
	 * Creates a new game object in this scene.
	 * @return the game object.
	 */
	public GameObject newGameObject() {
		GameObject o = new GameObject();
		gameObjects.add(o);
		return o;
	}

	/**
	 * Draws the screen contents using OpenGL.
	 */
	public void draw() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, getScreenWidthUnits(), getScreenHeightUnits(), 0, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(-screenX, -screenY, 0.0f);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		for (GameObject gameObject : gameObjects) {
			gameObject.draw();
		}
	}


	/**
	 * Handles a game tick. This method performs the game logic.
	 */
	public void handleTick() {
		// TODO pass a GameTickProcessor that can copy the arrays into a
		// re-used "clone" array without creating new objects
		List<GameObject> gameObjectsListClone = new ArrayList<>(gameObjects);
		for (GameObject gameObject : gameObjectsListClone) {
			gameObject.handleTick();
		}
	}

}

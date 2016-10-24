/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.engine.game;

import jdk.nashorn.api.scripting.JSObject;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Util;

/**
 * This class acts as the main access point for the technical facilities.
 */
public class Game {

	/**
	 * the currentScene
	 */
	private Scene currentScene;

	/**
	 * Creates a new scene.
	 * @return the scene
	 */
	public Scene newScene() {
		return new Scene();
	}
	
	/**
	 * Getter method for the currentScene.
	 * @return the currentScene
	 */
	public Scene getCurrentScene() {
		return currentScene;
	}

	/**
	 * Setter method for the currentScene.
	 * @param currentScene the currentScene to set
	 */
	public void setCurrentScene(Scene currentScene) {
		this.currentScene = currentScene;
	}
	
	/**
	 * Creates a new behavior that uses the specified applicator function.
	 * 
	 * @param applicator the applicator function
	 * @return the behavior
	 */
	public Behavior newBehavior(JSObject applicatorFunction) {
		return new Behavior(new Behavior.ScriptedApplicator(applicatorFunction));
	}

	/**
	 * Creates a new behavior that uses the specified applicator function.
	 * 
	 * @param applicator the applicator function
	 * @return the behavior
	 */
	public Behavior newBehaviorWithApplicator(Behavior.Applicator applicator) {
		return new Behavior(applicator);
	}

	/**
	 * Draws the screen contents.
	 */
	public void draw() {
		if (currentScene != null) {
			currentScene.draw();
		}
		Util.checkGLError();
	}
	
	/**
	 * Handles a tick of game logic.
	 */
	public void handleTick() {
		Keyboard.poll();
		Mouse.poll();
		if (currentScene != null) {
			currentScene.handleTick();
		}
	}
	
}

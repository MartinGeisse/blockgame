/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.engine.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.nashorn.api.scripting.JSObject;

import org.lwjgl.opengl.GL11;

/**
 * An object in the game.
 */
public class GameObject {

	/**
	 * the data
	 */
	private final Map<Behavior, Object> data = new HashMap<>();

	/**
	 * the eventListeners
	 */
	private final List<EventListener> eventListeners = new ArrayList<EventListener>();

	/**
	 * Obtains behavior-specific data in this object.
	 * 
	 * @param key the key behavior
	 * @return the value
	 */
	public Object get(Behavior key) {
		return data.get(key);
	}
	
	/**
	 * Sets behavior-specific data in this object.
	 * 
	 * @param key the key behavior
	 * @param value the value to set
	 */
	public void set(Behavior key, Object value) {
		data.put(key, value);
	}
	
	/**
	 * Adds an event listener.
	 * @param name the event name
	 * @param function the event handler function
	 */
	public void on(String name, JSObject function) {
		addListener(null, name, function);
	}
	
	/**
	 * Adds an event listener.
	 * @param name the event name
	 * @param function the event handler function
	 */
	public void addListener(String name, JSObject function) {
		addListener(null, name, function);
	}

	/**
	 * Adds an event listener.
	 * @param behavior the event behavior
	 * @param name the event name
	 * @param function the event handler function
	 */
	public void on(Behavior behavior, String name, JSObject function) {
		addListener(new ScriptedEventListener(behavior, name, function));
	}
	
	/**
	 * Adds an event listener.
	 * @param behavior the event behavior
	 * @param name the event name
	 * @param function the event handler function
	 */
	public void addListener(Behavior behavior, String name, JSObject function) {
		addListener(new ScriptedEventListener(behavior, name, function));
	}

	/**
	 * Adds an event listener.
	 * @param listener the listener to add
	 */
	public void on(EventListener listener) {
		addListener(listener);
	}
	
	/**
	 * Adds an event listener.
	 * @param listener the listener to add
	 */
	public void addListener(EventListener listener) {
		eventListeners.add(listener);
	}

	/**
	 * Emits a behavior-independent event on this game object. This is
	 * equivalent to calling emit(null, event, payload).
	 * 
	 * @param event the event name
	 * @param payload the payload
	 */
	public void emit(String event, Object payload) {
		emit(null, event, payload);
	}

	/**
	 * Emits a behavior-specific event on this game object (or behavior-independent
	 * if null is passed for the behavior).
	 *  
	 * @param behavior the behavior to which the event applies
	 * @param name the event name
	 * @param payload the payload
	 */
	public void emit(Behavior behavior, String name, Object payload) {
		// TODO pass a GameTickProcessor that can copy the arrays into a
		// re-used "clone" array without creating new objects
		for (EventListener listener : eventListeners) {
			listener.handleEvent(behavior, name, this, payload);
		}
	}

	/**
	 * Draws this object using OpenGL.
	 */
	public void draw() {
		GL11.glPushMatrix();
		emit("beforeDraw", null);
		emit("draw", null);
		GL11.glPopMatrix();
	}

	/**
	 * Handles the game logic for a single game tick for this object.
	 */
	public void handleTick() {
		emit("tick", null);
	}

}

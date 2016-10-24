/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.engine.game;

import jdk.nashorn.api.scripting.JSObject;

/**
 * Implements an event listener written as a Javascript function.
 * That function gets the game object as the first parameter and the
 * event payload as the second.
 */
public final class ScriptedEventListener extends EventListener {

	/**
	 * the function
	 */
	private final JSObject function;

	/**
	 * Constructor.
	 * @param behavior the event behavior
	 * @param name the event name
	 * @param function the function to call
	 */
	public ScriptedEventListener(Behavior behavior, String name, JSObject function) {
		super(behavior, name);
		this.function = function;
	}

	/* (non-Javadoc)
	 * @see game.engine.game.EventListener#handleEvent(game.engine.game.GameObject, jdk.nashorn.api.scripting.Object)
	 */
	@Override
	public void handleEvent(GameObject gameObject, Object payload) {
		function.call(gameObject, payload);
	}

}

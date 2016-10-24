/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.engine.game;


/**
 * Binds an event listener function to an event in a {@link GameObject}.
 */
public abstract class EventListener {

	/**
	 * the behavior
	 */
	private final Behavior behavior;

	/**
	 * the name
	 */
	private final String name;

	/**
	 * Constructor.
	 * @param behavior the behavior
	 * @param name the event name
	 */
	public EventListener(Behavior behavior, String name) {
		this.behavior = behavior;
		this.name = name;
	}

	/**
	 * Getter method for the behavior.
	 * @return the behavior
	 */
	public final Behavior getBehavior() {
		return behavior;
	}

	/**
	 * Getter method for the name.
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Handles an event.
	 * 
	 * @param behavior the event behavior
	 * @param name the event name
	 * @param gameObject the game object that is emitting the event
	 * @param payload the payload
	 */
	public final void handleEvent(Behavior behavior, String name, GameObject gameObject, Object payload) {
		if (behavior == this.behavior && name == this.name) {
			handleEvent(gameObject, payload);
		}
	}
	
	/**
	 * Handles an event with the specified payload that matches this listener.
	 * 
	 * @param gameObject thegame object that is emitting the event
	 * @param payload the payload
	 */
	public abstract void handleEvent(GameObject gameObject, Object payload);
	
}

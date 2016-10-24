/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.engine.gfx;

import game.engine.game.GameObject;

/**
 * Provides a sprite for a game object. 
 */
public interface SpriteProvider {

	/**
	 * Provides a sprite for a game object.
	 * 
	 * @param object the game object
	 * @return the sprite
	 */
	public Sprite provideSprite(GameObject object);
	
}

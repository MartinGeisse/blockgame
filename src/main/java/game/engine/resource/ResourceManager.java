/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.engine.resource;

import game.engine.gfx.Font;
import game.engine.gfx.Texture;

import org.newdawn.slick.openal.Audio;

/**
 * Keeps track of the game resources (graphics files, sounds, ...)
 */
public interface ResourceManager {

	/**
	 * Returns a texture by name.
	 * 
	 * @param name the name of the texture
	 * @return the texture
	 */
	public Texture getTexture(final String name);

	/**
	 * Returns a font by name.
	 * 
	 * @param name the name of the font
	 * @return the font
	 */
	public Font getFont(final String name);

	/**
	 * Returns a sound by name.
	 * 
	 * @param name the name of the sound
	 * @return the sound
	 */
	public Audio getSound(final String name);

}

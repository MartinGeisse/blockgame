/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.engine.resource;

import game.engine.gfx.Font;
import game.engine.gfx.Texture;

import org.newdawn.slick.openal.Audio;

/**
 * Simple helper class to access the {@link ResourceManager}.
 */
public final class Resources {

	/**
	 * the resourceManager
	 */
	private static ResourceManager resourceManager;

	/**
	 * Prevent instantiation.
	 */
	private Resources() {
	}

	/**
	 * Getter method for the resourceManager.
	 * @return the resourceManager
	 */
	public static ResourceManager getResourceManager() {
		return resourceManager;
	}

	/**
	 * Setter method for the resourceManager.
	 * @param resourceManager the resourceManager to set
	 */
	public static void setResourceManager(ResourceManager resourceManager) {
		Resources.resourceManager = resourceManager;
	}
	
	/**
	 * 
	 */
	private static ResourceManager enforceHasResourceManager() {
		if (resourceManager == null) {
			throw new RuntimeException("no resource manager");
		}
		return resourceManager;
	}

	/**
	 * Returns a texture by name.
	 * 
	 * @param name the name of the texture
	 * @return the texture
	 */
	public static Texture getTexture(final String name) {
		 return enforceHasResourceManager().getTexture(name);
	}
	
	/**
	 * Returns an font by name.
	 * 
	 * @param name the name of the font
	 * @return the font
	 */
	public static Font getFont(final String name) {
		return enforceHasResourceManager().getFont(name);
	}

	/**
	 * Returns a sound by name.
	 * 
	 * @param name the name of the sound
	 * @return the sound
	 */
	public static Audio getSound(final String name) {
		return enforceHasResourceManager().getSound(name);
	}

}

/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.engine.resource;

import game.engine.gfx.Font;
import game.engine.gfx.Texture;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;

/**
 * Loads resource files from disk.
 */
public interface ResourceLoader {

	/**
	 * Loads a texture.
	 * 
	 * @param filename the filename of the texture image, relative to the texture folder
	 * @return the texture
	 * @throws IOException on I/O errors
	 */
	public Texture loadTexture(final String filename) throws IOException;

	/**
	 * Loads a font.
	 * 
	 * @param filename the filename of the font image, relative to the font folder
	 * @return the font
	 * @throws IOException on I/O errors
	 */
	public Font loadFont(final String filename) throws IOException;
	
	/**
	 * Loads a sound.
	 * 
	 * @param filename the filename of the sound file, relative to the sound folder
	 * @return the sound
	 * @throws IOException on I/O errors
	 */
	public Audio loadSound(final String filename) throws IOException;

}

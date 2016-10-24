/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.engine.resource;

import game.engine.gfx.FixedWidthFont;
import game.engine.gfx.Font;
import game.engine.gfx.Texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.opengl.InternalTextureLoader;

/**
 * Default implementation of {@link ResourceLoader}.
 */
public final class DefaultResouceLoader implements ResourceLoader {

	/* (non-Javadoc)
	 * @see game.engine.resource.ResourceLoader#loadTexture(java.lang.String)
	 */
	@Override
	public Texture loadTexture(String filename) throws IOException {
		try (InputStream inputStream = new FileInputStream(new File("resource/textures", filename))) {
		    return new Texture(InternalTextureLoader.get().getTexture(inputStream, filename, true, GL11.GL_LINEAR));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see game.engine.resource.ResourceLoader#loadFont(java.lang.String)
	 */
	@Override
	public Font loadFont(String filename) throws IOException {
		return new FixedWidthFont(loadImage(new File("resource/fonts", filename)), 8, 16);
	}

	/**
	 * 
	 */
	private BufferedImage loadImage(final File file) throws IOException {
		try (InputStream inputStream = new FileInputStream(file)) {
			return ImageIO.read(inputStream);
		}
	}

	/* (non-Javadoc)
	 * @see game.engine.resource.ResourceLoader#loadSound(java.lang.String)
	 */
	@Override
	public Audio loadSound(final String filename) throws IOException {
		int lastDotIndex = filename.lastIndexOf('.');
		if (lastDotIndex == -1) {
			throw new RuntimeException("sound file name has no extension: " + filename);
		}
		String extension = filename.substring(lastDotIndex + 1).toLowerCase();
		String format;
		switch (extension) {
		
		case "ogg":
			format = "OGG";
			break;
			
		case "wav":
			format = "WAV";
			break;
			
		default:
			throw new RuntimeException("unknown sound file extension: " + extension);
			
		}
		
		try (InputStream inputStream = org.newdawn.slick.util.ResourceLoader.getResourceAsStream("resource/sounds/" + filename)) {
			return AudioLoader.getAudio(format, inputStream);
		}
	}
	
}

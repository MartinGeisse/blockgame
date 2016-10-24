/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.core.gfx;

import game.core.GameObject;
import game.engine.gfx.Sprite;
import game.engine.gfx.SpriteProvider;
import game.engine.gfx.Texture;
import game.engine.resource.Resources;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains a set of sprites used for an animation. All sprites have the
 * same extents, and they are animated using a fixed rate.
 */
public final class FixedSizeAnimatedSprite implements SpriteProvider {

	/**
	 * the sprites
	 */
	private final Sprite[] sprites;

	/**
	 * the gameFramesPerSpriteFrame
	 */
	private final int gameFramesPerSpriteFrame;

	/**
	 * the currentGameFrame
	 */
	private int currentGameFrame;

	/**
	 * Constructor.
	 * @param filenamePattern the filename pattern, using a $ for the frame number
	 * @param leftExtent the extent to the left
	 * @param rightExtent the extent to the right
	 * @param topExtent the extent to the top
	 * @param bottomExtent the extent to the bottom
	 * @param gameFramesPerSpriteFrame the number of game frames per sprite frame
	 */
	public FixedSizeAnimatedSprite(String filenamePattern, float leftExtent, float rightExtent, float topExtent, float bottomExtent, int gameFramesPerSpriteFrame) {
		this.sprites = loadSprites(filenamePattern, leftExtent, rightExtent, topExtent, bottomExtent);
		this.gameFramesPerSpriteFrame = gameFramesPerSpriteFrame;
	}

	/**
	 * 
	 */
	private static Sprite[] loadSprites(String filenamePattern, float leftExtent, float rightExtent, float topExtent, float bottomExtent) {
		List<Sprite> sprites = new ArrayList<Sprite>();
		while (true) {
			Texture texture;
			try {
				texture = Resources.getTexture(filenamePattern.replace("$", Integer.toString(sprites.size())));
			} catch (Exception e) {
				break;
			}
			sprites.add(new Sprite(texture, leftExtent, rightExtent, topExtent, bottomExtent));
		}
		if (sprites.size() == 0) {
			throw new RuntimeException("no frame found for sprite filename pattern: " + filenamePattern);
		}
		return sprites.toArray(new Sprite[sprites.size()]);
	}

	/**
	 * Getter method for the currentGameFrame.
	 * @return the currentGameFrame
	 */
	public int getCurrentGameFrame() {
		return currentGameFrame;
	}

	/**
	 * Setter method for the currentGameFrame.
	 * @param currentGameFrame the currentGameFrame to set
	 */
	public void setCurrentGameFrame(int currentGameFrame) {
		this.currentGameFrame = currentGameFrame;
	}

	/* (non-Javadoc)
	 * @see game.core.gfx.SpriteProvider#provideSprite(game.core.GameObject)
	 */
	@Override
	public Sprite provideSprite(GameObject object) {
		return sprites[(currentGameFrame / gameFramesPerSpriteFrame) % sprites.length];
	}

}

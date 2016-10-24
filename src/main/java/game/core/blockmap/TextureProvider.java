/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.core.blockmap;

import game.engine.gfx.Texture;

/**
 * Provides textures for the blocks in a block map.
 */
public interface TextureProvider {

	/**
	 * Returns the texture for a block.
	 * 
	 * @param blockValue the block value
	 * @return the texture
	 */
	public Texture getBlockTexture(int blockValue);
	
}

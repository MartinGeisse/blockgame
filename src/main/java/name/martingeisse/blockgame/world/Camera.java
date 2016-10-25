/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package name.martingeisse.blockgame.world;

import name.martingeisse.blockgame.system.Texture;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public final class Camera {

	public static final double PLAYER_FOCUS_BORDER = 6.0;

	private Plane plane;
	private float zoom = 1.0f;
	private float screenX = 0.0f;
	private float screenY = 0.0f;
	private TextureProvider textureProvider;
	private Texture playerTexture;

	public Plane getPlane() {
		return plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	/**
	 * Getter method for the zoom.
	 *
	 * @return the zoom
	 */
	public float getZoom() {
		return zoom;
	}

	/**
	 * Setter method for the zoom.
	 *
	 * @param zoom the zoom to set
	 */
	public void setZoom(float zoom) {
		this.zoom = zoom;
	}

	/**
	 * Getter method for the screenX.
	 *
	 * @return the screenX
	 */
	public float getScreenX() {
		return screenX;
	}

	/**
	 * Setter method for the screenX.
	 *
	 * @param screenX the screenX to set
	 */
	public void setScreenX(float screenX) {
		this.screenX = screenX;
	}

	/**
	 * Getter method for the screenY.
	 *
	 * @return the screenY
	 */
	public float getScreenY() {
		return screenY;
	}

	/**
	 * Setter method for the screenY.
	 *
	 * @param screenY the screenY to set
	 */
	public void setScreenY(float screenY) {
		this.screenY = screenY;
	}

	public TextureProvider getTextureProvider() {
		return textureProvider;
	}

	public void setTextureProvider(TextureProvider textureProvider) {
		this.textureProvider = textureProvider;
	}

	public Texture getPlayerTexture() {
		return playerTexture;
	}

	public void setPlayerTexture(Texture playerTexture) {
		this.playerTexture = playerTexture;
	}

	/**
	 * Getter method for the screen width, in units.
	 *
	 * @return the screen width, in units
	 */
	public float getScreenWidthUnits() {
		return 30.0f * Display.getWidth() / Display.getHeight() / zoom;
	}

	/**
	 * Getter method for the screen height, in units.
	 *
	 * @return the screen height, in units
	 */
	public float getScreenHeightUnits() {
		return 30.0f / zoom;
	}

	/**
	 * Prepare drawing the screen.
	 */
	public void draw() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(-getScreenWidthUnits() / 2.0, getScreenWidthUnits() / 2.0, -getScreenHeightUnits() / 2.0, getScreenHeightUnits() / 2.0, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(-screenX, -screenY, 0.0f);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		plane.drawInternal(textureProvider, playerTexture);
	}

	public void moveToKeepFocusOnPlayer() {
		Player player = plane.getPlayer();
		screenX += getFocusMovementDeltaBothSides(getScreenWidthUnits(), player.getPositionX() - screenX);
		screenY += getFocusMovementDeltaBothSides(getScreenHeightUnits(), player.getPositionY() - screenY);
	}

	private static double getFocusMovementDeltaBothSides(double size, double relativePosition) {
		return getFocusMovementDeltaOneSide(size, relativePosition) - getFocusMovementDeltaOneSide(size, -relativePosition);
	}

	private static double getFocusMovementDeltaOneSide(double size, double relativePosition) {
		double halfSize = size / 2.0;
		if (relativePosition > halfSize) {
			return PLAYER_FOCUS_BORDER / 2.0 + relativePosition - halfSize;
		} else if (relativePosition > halfSize - PLAYER_FOCUS_BORDER) {
			return 1 / (2.0 * PLAYER_FOCUS_BORDER) * sqr(relativePosition + PLAYER_FOCUS_BORDER - halfSize);
		} else {
			return 0;
		}
	}

	private static double sqr(double x) {
		return x * x;
	}

}

package name.martingeisse.blockgame.world;

import name.martingeisse.blockgame.system.Texture;
import org.lwjgl.opengl.GL11;

/**
 *
 */
public final class Player {

	public static final double PLAYER_RADIUS = 0.35;

	private double positionX;
	private double positionY;
	private double velocityX;
	private double velocityY;

	public void performMouseMovement(int mouseDx, int mouseDy) {
		positionX += mouseDx;
		positionY += mouseDy;
	}

	public double getPositionX() {
		return positionX;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public double getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	void drawInternal(Texture playerTexture) {
		playerTexture.glBindTexture();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex2d(positionX - PLAYER_RADIUS, positionY - PLAYER_RADIUS);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex2d(positionX + PLAYER_RADIUS, positionY - PLAYER_RADIUS);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex2d(positionX + PLAYER_RADIUS, positionY + PLAYER_RADIUS);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex2d(positionX - PLAYER_RADIUS, positionY + PLAYER_RADIUS);
		GL11.glEnd();
	}

}

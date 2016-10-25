package name.martingeisse.blockgame.world;

import name.martingeisse.blockgame.system.Texture;
import org.lwjgl.opengl.GL11;

/**
 *
 */
public final class Player {

	public static final double PLAYER_RADIUS = 0.35;
	public static final double MOUSE_SENSITIVITY = 0.02;
	public static final double FRICTION = 0.15;

	private double positionX;
	private double positionY;
	private double velocityX;
	private double velocityY;

	public void performMouseMovement(int mouseDx, int mouseDy) {
		velocityX += mouseDx * MOUSE_SENSITIVITY;
		velocityX *= (1 - FRICTION);
		velocityY += mouseDy * MOUSE_SENSITIVITY;
		velocityY *= (1 - FRICTION);
		positionX += velocityX;
		positionY += velocityY;
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

package name.martingeisse.blockgame.world;

import name.martingeisse.blockgame.system.Texture;
import name.martingeisse.blockgame.world.collision.Collision;
import name.martingeisse.blockgame.world.collision.CollisionUtil;
import org.lwjgl.opengl.GL11;

/**
 *
 */
public final class Player {

	public static final double PLAYER_RADIUS = 0.35;
	public static final double MOUSE_SENSITIVITY = 0.02;
	public static final double FRICTION = 0.15;
	public static final double COLLISION_FRICTION = 0.7;

	private double positionX;
	private double positionY;
	private double velocityX;
	private double velocityY;

	public void performMouseMovement(int mouseDx, int mouseDy, CollisionUtil.BlockMapCollider blockMapCollider) {

		// handle acceleration and friction
		velocityX += mouseDx * MOUSE_SENSITIVITY;
		velocityX *= (1 - FRICTION);
		velocityY += mouseDy * MOUSE_SENSITIVITY;
		velocityY *= (1 - FRICTION);

		// actually move, checking for collisions
		Collision collision = CollisionUtil.checkSphereBlockCollision(positionX, positionY, velocityX, velocityY, PLAYER_RADIUS, blockMapCollider);
		if (collision == null) {
			positionX += velocityX;
			positionY += velocityY;
		} else {

			// move the first part
			positionX += velocityX * collision.getMovementFraction();
			positionY += velocityY * collision.getMovementFraction();

			// reflect movement
			double temp = (2 - COLLISION_FRICTION) * (velocityX * collision.getSurfaceNormalX() + velocityY * collision.getSurfaceNormalY());
			velocityX -= temp * collision.getSurfaceNormalX();
			velocityY -= temp * collision.getSurfaceNormalY();

			// move the second part TODO should check again for collisions
			positionX += velocityX * (1 - collision.getMovementFraction());
			positionY += velocityY * (1 - collision.getMovementFraction());

		}

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

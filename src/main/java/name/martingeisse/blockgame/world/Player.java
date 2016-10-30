package name.martingeisse.blockgame.world;

import name.martingeisse.blockgame.resource.Resources;
import name.martingeisse.blockgame.system.Texture;
import name.martingeisse.blockgame.world.collision.Collision;
import name.martingeisse.blockgame.world.collision.CollisionUtil;
import org.lwjgl.opengl.GL11;

/**
 *
 */
public final class Player {

	public static final double FAST_COLLISION_MIN_SPEED = 0.7;

	private static final boolean[] blockSolid = {
			true,
			false,
			false,
			false,
			true,
			true,
	};

	private static final String[] collidionSoundNames = {
		null,
		null,
		null,
		null,
		"wood_and_metal_vol.2/metal6.wav",
		"wood_and_metal_vol.2/wood3.wav",
	};

	public static final double PLAYER_RADIUS = 0.35;
	public static final double MOUSE_SENSITIVITY = 0.02;
	public static final double FRICTION = 0.15;
	public static final double COLLISION_FRICTION = 0.7;

	private double positionX;
	private double positionY;
	private double velocityX;
	private double velocityY;

	private int collisionSoundCooldown = 0;

	public void performMouseMovement(int mouseDx, int mouseDy, Plane plane) {

		// handle acceleration and friction
		velocityX += mouseDx * MOUSE_SENSITIVITY;
		velocityX *= (1 - FRICTION);
		velocityY += mouseDy * MOUSE_SENSITIVITY;
		velocityY *= (1 - FRICTION);

		// actually move, checking for collisions
		performMovement(1.0, plane, 0);

		// play a collision sound only once every few frames at most
		if (collisionSoundCooldown > 0) {
			collisionSoundCooldown--;
		}

	}

	private void performMovement(double remainingFraction, Plane plane, int recursionDepth) {
		double deltaX = velocityX * remainingFraction;
		double deltaY = velocityY * remainingFraction;
		CollisionUtil.BlockMapCollider blockMapCollider = (x, y) -> {
			int block = plane.getBlock(x, y);
			return block < 0 || block >= blockSolid.length || blockSolid[block];
		};
		Collision collision = CollisionUtil.checkSphereBlockCollision(positionX, positionY, deltaX, deltaY, PLAYER_RADIUS, blockMapCollider);
		if (collision == null) {
			positionX += deltaX;
			positionY += deltaY;
		} else {

			// move the first part
			positionX += deltaX * collision.getMovementFraction();
			positionY += deltaY * collision.getMovementFraction();

			// Determine velocity perpendicular to the surface, to trigger effects on "fast" collisions. This is simply
			// the negated dot product of the velocity and the surface normal.
			double perpendicularVelocity = -(velocityX * collision.getSurfaceNormalX() + velocityY * collision.getSurfaceNormalY());

			// reflect movement
			double temp = (2 - COLLISION_FRICTION) * (velocityX * collision.getSurfaceNormalX() + velocityY * collision.getSurfaceNormalY());
			velocityX -= temp * collision.getSurfaceNormalX();
			velocityY -= temp * collision.getSurfaceNormalY();

			// move the second part, checking for collisions again
			if (recursionDepth < 10) {
				performMovement(remainingFraction * (1 - collision.getMovementFraction()), plane, recursionDepth + 1);
			}

			// play a collision sound
			if (collisionSoundCooldown == 0) {
				int blockType = plane.getBlock(collision.getBlockX(), collision.getBlockY());
				if (blockType >= 0 || blockType < collidionSoundNames.length && collidionSoundNames[blockType] != null) {
					Resources.getSound(collidionSoundNames[blockType]).playAsSoundEffect(1.0f, 1.0f, false);
					collisionSoundCooldown = 10;
				}
			}

			// trigger effects for "fast" collisions
			if (perpendicularVelocity > FAST_COLLISION_MIN_SPEED) {
				int blockX = collision.getBlockX();
				int blockY = collision.getBlockY();
				int blockType = plane.getBlock(blockX, blockY);
				int behindBlockX = collision.getBlockX() - (int)collision.getSurfaceNormalX();
				int behindBlockY = collision.getBlockY() - (int)collision.getSurfaceNormalY();
				int behindBlockType = plane.getBlock(behindBlockX, behindBlockY);
				if (blockType == 5 && behindBlockType == 1) {
					plane.setBlock(blockX, blockY, 1);
					plane.setBlock(behindBlockX, behindBlockY, 5);
				}
			}

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

package name.martingeisse.blockgame.world.collision;

/**
 * A collision object describes the way two game objects collide. It consists of:
 * - the movement fraction coefficient. This tells the fraction (0..1) of the intended movement that could be
 *   performed before a collision occurred
 * - the surface normal vector (unit length), pointing from the first colliding object towards the second one
 */
public class Collision {

	private final double movementFraction;
	private final double surfaceNormalX;
	private final double surfaceNormalY;

	public Collision(double movementFraction, double surfaceNormalX, double surfaceNormalY) {
		this.movementFraction = movementFraction;
		this.surfaceNormalX = surfaceNormalX;
		this.surfaceNormalY = surfaceNormalY;
	}

	public double getMovementFraction() {
		return movementFraction;
	}

	public double getSurfaceNormalX() {
		return surfaceNormalX;
	}

	public double getSurfaceNormalY() {
		return surfaceNormalY;
	}

}

package name.martingeisse.blockgame.world.collision;

import java.util.function.Predicate;

/**
 *
 */
public final class CollisionUtil {

	/**
	 * Prevent instantiation.
	 */
	private CollisionUtil() {
	}

	/**
	 * Checks whether a moving sphere collides with a block from the block map (non-moving).
	 *
	 * @param x0 the movement start point X for the sphere
	 * @param y0 the movement start point Y for the sphere
	 * @param dx the X movement amount for the sphere
	 * @param dy the Y movement amount for the sphere
	 * @param radius the sphere radius
	 * @param blockMapCollider the collider for the block map
	 * @return information about a collision, or null if no collision occurred
	 */
	public static Collision checkSphereBlockCollision(double x0, double y0, double dx, double dy, double radius, BlockMapCollider blockMapCollider) {
		if (dx < 0 && x0 + dx < radius) {
			return new Collision((radius - x0) / dx, -1, 0);
		}
		return null;
	}

	public static interface BlockMapCollider {
		public boolean isSolid(int x, int y);
	}

}

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

		// Check for the nearest collisions with a block side.
		Collision sideCollision = checkSphereSideCollision(x0, y0, dx, dy, radius, blockMapCollider);

		// Check for the nearest collision with a block corner. Given a corner with at least one solid block, this may
		// yield any solid block with that corner: Either there is only one solid block -- then that block is the one
		// whose corner the player collided with. Or there are multiple solid blocks, then the player cannot collide
		// with its corner because he'll collide with one of the sides first, so the corner has a greater distance.
		// Also, the collision normal is computed as the vector from the player center to the corner. This may be
		// the inverse of the actual normal if that collision happens "from inside" a solid block, but again, the player
		// would collide with a side or other corner first.
		Collision cornerCollision = checkSphereCornerCollision(x0, y0, dx, dy, radius, blockMapCollider);

		// choose the nearer collision of those two
		return chooseNearer(sideCollision, cornerCollision);

	}

	private static Collision checkSphereSideCollision(double x0, double y0, double dx, double dy, double radius, BlockMapCollider blockMapCollider) {

		// TODO remove
		if (dx < 0 && x0 + dx < radius) {
			return new Collision((radius - x0) / dx, -1, 0);
		}
		return null;

	}

	private static Collision checkSphereCornerCollision(double x0, double y0, double dx, double dy, double radius, BlockMapCollider blockMapCollider) {
		double stepFraction = 0.1;
		int stepCount = 10;
		double stepDx = dx * stepFraction;
		double stepDy = dy * stepFraction;
		for (int i=0; i<stepCount; i++) {
			double x = x0 + i * stepDx;
			double y = y0 + i * stepDy;
			// The relevant corner changes when the player crosses a block center. Block centers have integer coordinates,
			// so we need floor/ceil rounding here, not round-to-nearest.
			int ix = (int)Math.floor(x);
			int iy = (int)Math.floor(y);
			// TODO find collision
			// TODO refine step size to find it more exactly
		}
		return null;
	}

	private static Collision chooseNearer(Collision a, Collision b) {
		if (a == null) {
			return b;
		}
		if (b == null) {
			return a;
		}
		return (a.getMovementFraction() < b.getMovementFraction() ? a : b);
	}

	public static interface BlockMapCollider {
		public boolean isSolid(int x, int y);
	}

}

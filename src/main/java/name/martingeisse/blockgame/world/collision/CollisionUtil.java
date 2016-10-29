package name.martingeisse.blockgame.world.collision;

import java.util.Random;

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

		Collision eastCollision = null;
		for (double dx2 = (1.5 - (x0 + radius) % 1.0) % 1.0; dx2 <= dx; dx2++) {
			double dy2 = dy * dx2 / dx;
			int x2 = (int)Math.round(x0 + dx2 + 0.5);
			int y2 = (int)Math.round(y0 + dy2);
			if (blockMapCollider.isSolid(x2, y2)) {
				eastCollision = new Collision(dx2 / dx, -1.0, 0.0);
				break;
			}
		}

		Collision westCollision = null;
		for (double dx2 = (1.5 - (x0 - radius) % 1.0) % 1.0 - 1.0; dx2 >= dx; dx2--) {
			double dy2 = dy * dx2 / dx;
			int x2 = (int)Math.round(x0 + dx2 - 0.5);
			int y2 = (int)Math.round(y0 + dy2);
			if (blockMapCollider.isSolid(x2, y2)) {
				westCollision = new Collision(dx2 / dx, +1.0, 0.0);
				break;
			}
		}

		Collision northCollision = null;
		for (double dy2 = (1.5 - (y0 + radius) % 1.0) % 1.0; dy2 <= dy; dy2++) {
			double dx2 = dx * dy2 / dy;
			int x2 = (int)Math.round(x0 + dx2);
			int y2 = (int)Math.round(y0 + dy2 + 0.5);
			if (blockMapCollider.isSolid(x2, y2)) {
				northCollision = new Collision(dy2 / dy, 0.0, -1.0);
				break;
			}
		}

		Collision southCollision = null;
		for (double dy2 = (1.5 - (y0 - radius) % 1.0) % 1.0 - 1.0; dy2 >= dy; dy2--) {
			double dx2 = dx * dy2 / dy;
			int x2 = (int)Math.round(x0 + dx2);
			int y2 = (int)Math.round(y0 + dy2 - 0.5);
			if (blockMapCollider.isSolid(x2, y2)) {
				southCollision = new Collision(dy2 / dy, 0.0, +1.0);
				break;
			}
		}

		return chooseNearer(chooseNearer(westCollision, eastCollision), chooseNearer(northCollision, southCollision));
	}

	private static Collision checkSphereCornerCollision(double x0, double y0, double dx, double dy, double radius, BlockMapCollider blockMapCollider) {
		double movementLength = Math.sqrt(dx * dx + dy * dy);
		int stepCount = (int)(movementLength / 0.1) + 1;
		double stepDx = dx / stepCount;
		double stepDy = dy / stepCount;
		for (int i=1; i<=stepCount; i++) {
			double x = x0 + i * stepDx;
			double y = y0 + i * stepDy;
			// The relevant corner changes when the player crosses a block center. Block centers have integer coordinates,
			// so we need floor/ceil rounding here, not round-to-nearest.
			int ix = (int)Math.floor(x);
			int iy = (int)Math.floor(y);
			double cornerX = ix + 0.5;
			double cornerY = iy + 0.5;
			double normalX = cornerX - x;
			double normalY = cornerY - y;
			double normalNormSquared = normalX * normalX + normalY * normalY;
			if (normalNormSquared < radius * radius) {
				double normalNorm = Math.sqrt(normalNormSquared);
				int cx = 0, cy = 0;
				boolean found = false;
				if (blockMapCollider.isSolid(ix, iy)) {
					cx = ix;
					cy = iy;
					found = true;
				} else if (blockMapCollider.isSolid(ix + 1, iy)) {
					cx = ix + 1;
					cy = iy;
					found = true;
				} else if (blockMapCollider.isSolid(ix, iy + 1)) {
					cx = ix;
					cy = iy + 1;
					found = true;
				} else if (blockMapCollider.isSolid(ix + 1, iy + 1)) {
					cx = ix + 1;
					cy = iy + 1;
					found = true;
				}
				if (found) {
					return new Collision(((double)(i - 1)) / stepCount, normalX / normalNorm, normalY / normalNorm);
				}
			}
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

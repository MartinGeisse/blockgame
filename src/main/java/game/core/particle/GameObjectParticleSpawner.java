/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.core.particle;

import game.core.Behavior;
import game.core.GameObject;
import game.core.Region;
import game.core.geometry.MutablePosition;
import game.core.geometry.Position;
import game.core.geometry.ReadablePosition;
import game.core.gfx.DrawSpriteBehavior;
import game.core.misc.TimerBehavior;
import game.core.movement.PositionBehavior;
import game.core.movement.UnblockedGravityBehavior;
import game.engine.gfx.SpriteProvider;

import java.util.function.Consumer;

/**
 * Spawns particle effects by creating a new game object for each particle.
 */
public class GameObjectParticleSpawner  {

	/**
	 * the region
	 */
	private final Region region;

	/**
	 * Constructor.
	 * @param region the region to spawn particles in
	 */
	public GameObjectParticleSpawner(Region region) {
		this.region = region;
	}

	/**
	 * Spawns a particle.
	 * 
	 * @param x the initial x position
	 * @param y the initial y position
	 * @param dx the x velocity
	 * @param dy the initial y velocity
	 * @param gravity the gravity
	 * @param spriteProvider the sprite provider
	 * @param particleBehaviors additional behaviors
	 */
	public void spawnParticle(float x, float y, float dx, float dy, float gravity, SpriteProvider spriteProvider, int ttl, Consumer<ReadablePosition> disappearCallback, Behavior... particleBehaviors) {
		GameObject particle = new GameObject();
		particle.attachBehavior(new PositionBehavior(new Position(x, y)));
		particle.attachBehavior(new DrawSpriteBehavior(spriteProvider));		
		particle.attachBehavior(new TimerBehavior(ttl) {
			@Override
			protected void onExpire(GameObject target) {
				region.getGameObjects().remove(target);
				if (disappearCallback != null) {
					MutablePosition position = target.getBehavior(PositionBehavior.class).getMutablePosition();
					disappearCallback.accept(position);
				}
			}
		});
		region.getGameObjects().add(particle);
		
		MutablePosition mutablePosition = particle.getBehavior(PositionBehavior.class).getMutablePosition();
		mutablePosition.setX(x);
		mutablePosition.setY(y);
		
		UnblockedGravityBehavior movement = new UnblockedGravityBehavior();
		movement.setDx(dx);
		movement.setDy(dy);
		movement.setGravity(gravity);
		particle.attachBehavior(movement);
		
		for (Behavior behavior : particleBehaviors) {
			particle.attachBehavior(behavior);
		}

	}
	
}

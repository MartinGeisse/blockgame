/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.engine.core;

import game.engine.game.Behavior;
import game.engine.game.Behavior.Applicator;
import game.engine.game.EventListener;
import game.engine.game.GameObject;
import game.engine.gfx.Sprite;
import game.engine.gfx.SpriteProvider;

import javax.script.ScriptEngine;

import jdk.nashorn.api.scripting.JSObject;

import org.lwjgl.opengl.GL11;

/**
 * Implements the "core" module.
 */
public final class CoreModule {

	/**
	 * the rectangle
	 */
	public final Behavior position;

	/**
	 * the rectangle
	 */
	public final Behavior rectangle;

	/**
	 * the leftRight
	 */
	public final Behavior leftRight;

	/**
	 * the sprite
	 */
	public final Behavior sprite;

	/**
	 * Constructor.
	 * 
	 * @param scriptEngine the Nashorn engine
	 */
	public CoreModule(ScriptEngine scriptEngine) {
		try {

			final EventListener positionTransformListener = new EventListener(null, "beforeDraw") {
				@Override
				public void handleEvent(GameObject gameObject, Object payload) {
					PositionData data = (PositionData)gameObject.get(CoreModule.this.position);
					GL11.glTranslatef(data.x, data.y, 0.0f);
				}
			};

			position = new Behavior(new Applicator() {
				@Override
				public void apply(GameObject target, Object rawParameters, Behavior behavior) {
					PositionData data = new PositionData();
					if (rawParameters != null) {
						JSObject parameters = (JSObject)jdk.nashorn.api.scripting.ScriptUtils.wrap(rawParameters);
						if (parameters.getMember("x") instanceof Number) {
							data.x = ((Number)parameters.getMember("x")).floatValue();
						}
						if (parameters.getMember("y") instanceof Number) {
							data.y = ((Number)parameters.getMember("y")).floatValue();
						}
					}
					target.set(CoreModule.this.position, data);
					target.addListener(positionTransformListener);
				}
			});

			final EventListener drawRectangleListener = new EventListener(null, "draw") {
				@Override
				public void handleEvent(GameObject gameObject, Object payload) {
					GL11.glColor4ub((byte)255, (byte)255, (byte)255, (byte)255);
					GL11.glBegin(GL11.GL_QUADS);
					GL11.glVertex2f(1.0f, 1.0f);
					GL11.glVertex2f(1.0f, -1.0f);
					GL11.glVertex2f(-1.0f, -1.0f);
					GL11.glVertex2f(-1.0f, 1.0f);
					GL11.glEnd();
				}
			};

			rectangle = new Behavior(new Applicator() {
				@Override
				public void apply(GameObject target, Object parameters, Behavior behavior) {
					target.addListener(drawRectangleListener);
				}
			});

			leftRight = new Behavior(new Applicator() {
				@Override
				public void apply(GameObject target, Object parameters, Behavior behavior) {
					target.set(leftRight, "left");
				}
			});
			
			final EventListener drawSpriteListener = new EventListener(null, "draw") {
				@Override
				public void handleEvent(GameObject gameObject, Object payload) {
					SpriteProvider spriteProvider = (SpriteProvider)gameObject.get(sprite);
					if (spriteProvider != null) {
						Sprite sprite = spriteProvider.provideSprite(gameObject);
						if (sprite != null) {
							sprite.draw();
						}
					}
				}
			};
			
			sprite = new Behavior(new Applicator() {
				@Override
				public void apply(GameObject target, Object parameters, Behavior behavior) {
					if (parameters instanceof SpriteProvider) {
						target.set(sprite, parameters);
					} else {
						throw new RuntimeException("invalid sprite provider");
					}
					target.addListener(drawSpriteListener);
				}
			}); 

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Contains the per-object data for the position behavior.
	 */
	public static final class PositionData {
		public float x, y;
	}

}

/**
 * Copyright (c) 2010 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package game.martin;

import game.core.AbstractBehavior;
import game.core.GameObject;
import game.core.blockmap.AbstractForeachBlockBehaviour;
import game.core.blockmap.BlockMapBehavior;
import game.core.blockmap.BlockMapEditor;
import game.core.blockmap.BlockMapProbe;
import game.core.blockmap.BlockMapProbe.IsolatedBlockHandler;
import game.core.blockmap.TextureProvider;
import game.core.geometry.Position;
import game.core.gfx.DrawSpriteBehavior;
import game.core.gfx.FixedSizeAnimatedSprite;
import game.core.gfx.LeftRightSpriteProvider;
import game.core.movement.AbstractBlockMapJumpAndRunBehavior;
import game.core.movement.LeftRightOrientationBehavior;
import game.core.movement.PositionBehavior;
import game.core.movement.ScreenFollowBehavior;
import game.core.particle.GameObjectParticleSpawner;
import game.engine.gfx.Sprite;
import game.engine.gfx.SpriteProvider;
import game.engine.gfx.Texture;
import game.engine.resource.Resources;
import game.engine.system.GameLauncher;

/**
 * The main class.
 */
public class CopyOfMain {

	/**
	 * the coinSprite
	 */
	private static Sprite coinSprite;

	/**
	 * the sparkSprite
	 */
	private static Sprite sparkSprite;

	/**
	 * the playerRunLeft
	 */
	private static FixedSizeAnimatedSprite playerRunLeft;
	
	/**
	 * the playerRunRight
	 */
	private static FixedSizeAnimatedSprite playerRunRight;

	/**
	 * the particleSpawner
	 */
	private static GameObjectParticleSpawner particleSpawner;

	/**
	 * The main method.
	 * @param args command-line arguments
	 * @throws Exception on errors
	 */
	public static void main(String[] args) throws Exception {

		GameLauncher launcher = new GameLauncher(args);
		launcher.launch();

		Sprite playerLeft = new Sprite(Resources.getTexture("sprites/player-left.png"), 0.6f, 0.6f, 0.6f, 0.6f);
		Sprite playerRight = new Sprite(Resources.getTexture("sprites/player-right.png"), 0.6f, 0.6f, 0.6f, 0.6f);
		SpriteProvider playerStandStillSpriteProvider = new LeftRightSpriteProvider(playerLeft, playerRight);
		playerRunLeft = new FixedSizeAnimatedSprite("sprites/player-run-left-$.png", 0.6f, 0.6f, 0.6f, 0.6f, 4);
		playerRunRight = new FixedSizeAnimatedSprite("sprites/player-run-right-$.png", 0.6f, 0.6f, 0.6f, 0.6f, 4);
		SpriteProvider playerRunSpriteProvider = new LeftRightSpriteProvider(playerRunLeft, playerRunRight);
		SpriteProvider playerSpriteProvider =
			((player) -> player.getBehavior(PlayerJumpAndRunBehavior.class).isRunning() ? playerRunSpriteProvider.provideSprite(player) : playerStandStillSpriteProvider.provideSprite(player));

		coinSprite = new Sprite(Resources.getTexture("sprites/coin.png"), 0.5f, 0.5f, 0.5f, 0.5f);
		sparkSprite = new Sprite(Resources.getTexture("sprites/spark.png"), 0.2f, 0.2f, 0.2f, 0.2f);
		particleSpawner = new GameObjectParticleSpawner(launcher.getInitialRegion());

		TextureProvider blockMapTextureProvider = new TextureProvider() {
			@Override
			public Texture getBlockTexture(int blockValue) {
				switch (blockValue) {

				case 0:
					return null;

				case 1:
					return Resources.getTexture("blockmap/ground.png");

				case 2:
					return Resources.getTexture("blockmap/deep-ground.png");

				case 3:
					return Resources.getTexture("blockmap/coin.png");

				case 4:
					return Resources.getTexture("blockmap/coinbox.png");

				case 5:
					return Resources.getTexture("blockmap/emptybox.png");

				default:
					return Resources.getTexture("blockmap/tile1.png");

				}
			}
		};
		BlockMapBehavior blockMapBehavior = new BlockMapBehavior(100, 30);
		blockMapBehavior.setTextureProvider(blockMapTextureProvider);
		drawMap(new BlockMapEditor(blockMapBehavior));
		GameObject blockMap = new GameObject();
		blockMap.attachBehavior(blockMapBehavior);
		launcher.getInitialRegion().getGameObjects().add(blockMap);

		IsolatedBlockHandler<Boolean> solidity = new Solidity();
		BlockMapProbe blockMapProbe = new BlockMapProbe(blockMapBehavior, 0.35f, 0.35f, 0.4f, 0.45f);
		GameObject player = new GameObject();
		player.attachBehavior(new PositionBehavior(new Position(1.0f, 1.0f)));
		player.attachBehavior(new LeftRightOrientationBehavior());
		player.attachBehavior(new DrawSpriteBehavior(playerSpriteProvider));
		player.attachBehavior(new PlayerJumpAndRunBehavior(blockMapProbe, solidity));
		player.attachBehavior(new AbstractForeachBlockBehaviour(blockMapProbe) {
			@Override
			public Void handle(BlockMapBehavior blockMap, int x, int y, int blockValue) {
				if (blockValue == 3) {
					Resources.getSound("coin.wav").playAsSoundEffect(1.0f, 0.5f, false);
					blockMap.setBlock(x, y, 0);
				}
				return null;
			}
		});
		player.attachBehavior(new ScreenFollowBehavior(launcher.getGame()));
		player.attachBehavior(new AbstractBehavior() {
			@Override
			public void handleStep(GameObject target) {
				playerRunLeft.setCurrentGameFrame(playerRunLeft.getCurrentGameFrame() + 1);
				playerRunRight.setCurrentGameFrame(playerRunRight.getCurrentGameFrame() + 1);
			};
		});
		launcher.getInitialRegion().getGameObjects().add(player);
		launcher.getGame().setZoom(1.2f);

		launcher.loop(20);
		launcher.cleanup();
		System.exit(0);

	}

	/**
	 * 
	 */
	private static void drawMap(BlockMapEditor map) {
		map.withBlock(1).hline(0, 15, 100);
		map.withBlock(2).fillRect(0, 16, 100, 14);
		map.withBlock(2).vline(10, 13, 4);
		map.withBlock(0).vline(20, 15, 2);
		map.withBlock(1).setBlock(20, 17);
		map.withBlock(1).setBlock(10, 12);
		map.withBlock(1).setBlock(5, 11);
		map.withBlock(3).hline(15, 14, 5);
		map.withBlock(4).setBlock(15, 11);
		map.withBlock(4).setBlock(17, 11);
		map.withBlock(4).setBlock(19, 11);
	}

	/**
	 *
	 */
	private static class Solidity implements IsolatedBlockHandler<Boolean> {

		/* (non-Javadoc)
		 * @see game.core.blockmap.BlockMapProbe.IsolatedBlockHandler#handle(game.core.blockmap.BlockMapBehavior, int, int, int)
		 */
		@Override
		public Boolean handle(BlockMapBehavior map, int x, int y, int block) {
			if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight()) {
				return true;
			}
			if (block == 0 || block == 3) {
				return false;
			}
			if (block == 4) {
				map.setBlock(x, y, 5);
				Resources.getSound("coin.wav").playAsSoundEffect(1.0f, 0.5f, false);
				particleSpawner.spawnParticle(x + 0.5f, y + 0.5f, 0, -1.0f, 0.1f, coinSprite, 17, position -> spawnSparks(position.getX(), position.getY()));
			}
			return true;
		}

		private void spawnSparks(float x, float y) {
			float u = 0.3f, d = 0.21f;
			spawnSpark(x, y, +u, 0);
			spawnSpark(x, y, +d, +d);
			spawnSpark(x, y, +0, +u);
			spawnSpark(x, y, -d, +d);
			spawnSpark(x, y, -u, 0);
			spawnSpark(x, y, -d, -d);
			spawnSpark(x, y, +0, -u);
			spawnSpark(x, y, +d, -d);
		}

		private void spawnSpark(float x, float y, float dx, float dy) {
			particleSpawner.spawnParticle(x, y, dx, dy, 0, sparkSprite, 4, null);
		}

	};

	/**
	 *
	 */
	private static class PlayerJumpAndRunBehavior extends AbstractBlockMapJumpAndRunBehavior {

		/**
		 * Constructor.
		 * @param blockMapProbe the block map probe
		 * @param blockSolidity the solidity function
		 */
		public PlayerJumpAndRunBehavior(BlockMapProbe blockMapProbe, IsolatedBlockHandler<Boolean> blockSolidity) {
			super(blockMapProbe, blockSolidity);

		}

		/* (non-Javadoc)
		 * @see game.core.movement.AbstractJumpAndRunBehavior#getGravity(game.core.GameObject)
		 */
		@Override
		protected float getGravity(GameObject target) {
			return 0.04f;
		}

	}
}

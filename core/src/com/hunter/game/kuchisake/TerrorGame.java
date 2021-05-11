package com.hunter.game.kuchisake;

import java.util.concurrent.locks.ReentrantLock;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.hunter.game.kuchisake.objects.Kuchisake;
import com.hunter.game.kuchisake.screen.StandardRoom;
import com.hunter.game.kuchisake.startMenu.SceneMenu;

public class TerrorGame extends Game{
	
	public SpriteBatch batch;
	
	public static final float SCALE = 100f;
	public static final float WIDTH = 1920;
	public static final float HEIGHT = 1080;
	
	private AssetManager assetManager;

	Kuchisake kuchisakeOnna;
	boolean hasEncountered = false;
	
	World world;
	
	final float TIME_STEP = 1 / 60f;
	final int VELOCITY_ITERATIONS = 6;
	final int POSITION_ITERATIONS = 2;
	
	final short PLAYER_BIT = 1;
	final short KUCHISAKE_BIT = 2;
	
	private int playerLine = 0;
	private int playerColumn = 1;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		assetManager.load("ButtonAssets/controles_rascunho.png", Texture.class);
		assetManager.load("ButtonAssets/Logo_rascunho.png", Texture.class);
		assetManager.load("ButtonAssets/start_rascunho.png", Texture.class);
		assetManager.finishLoading();
//		this.setScreen(new Sala01(this, 1000));
		this.setScreen(new SceneMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		
	}
	
	public synchronized void worldStep() {
		kuchisakeOnna.setIsWaiting(false);
		notify();
		world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
	}
	
	public float getTimeStep() {
		return TIME_STEP;
	}
	
	public void incrementPlayerLine(int lnIncrement) {
		playerLine += lnIncrement;
	}
	
	public void setPlayerColumn(int column) {
		playerColumn = column;
	}
	
	public synchronized int getPlayerLine() {
		kuchisakeOnna.setIsWaiting(false);
		notify();
		return playerLine;
	}
	
	public synchronized int getPlayerColumn() {
		kuchisakeOnna.setIsWaiting(false);
		notify();
		return playerColumn;
	}
	
	public AssetManager getAssetManager() {
		return assetManager;
	}

	public void createVillain(){
		world = new World(new Vector2(0, 0), true);
		this.getAssetManager().load("CharactersAssets/muie_sprites.png", Texture.class);
		assetManager.finishLoading();
		kuchisakeOnna = new Kuchisake(1750, this, world);

		//Thread run aqui
		kuchisakeOnna.start();
	}
	
	public World getWorld() {
		return world;
	}

	public Kuchisake getKuchisakeOnna() {
		return kuchisakeOnna;
	}
	
	public short getPlayerBit() {
        return PLAYER_BIT;
    }
	
	public short getKuchisakeBit() {
        return KUCHISAKE_BIT;
    }
}

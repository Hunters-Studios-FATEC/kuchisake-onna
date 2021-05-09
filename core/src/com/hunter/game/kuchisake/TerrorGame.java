package com.hunter.game.kuchisake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	
	public AssetManager getAssetManager() {
		return assetManager;
	}

	public void createVillain(){
		this.getAssetManager().load("CharactersAssets/muie_sprites.png", Texture.class);
		assetManager.finishLoading();
		kuchisakeOnna = new Kuchisake(100, this);

		//Thread run aqui
		kuchisakeOnna.start();
	}

	public Kuchisake getKuchisakeOnna() {
		return kuchisakeOnna;
	}
}

package com.hunter.game.kuchisake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hunter.game.kuchisake.screen.Sala01;
import com.hunter.game.kuchisake.screen.StandardRoom;
import com.hunter.game.kuchisake.startMenu.SceneMenu;

public class TerrorGame extends Game{
	
	public SpriteBatch batch;
	
	public static final float SCALE = 100f;
	public static final float WIDTH = 1920;
	public static final float HEIGHT = 1080;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
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
}

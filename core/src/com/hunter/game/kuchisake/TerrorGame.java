package com.hunter.game.kuchisake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hunter.game.kuchisake.screen.Screen;

public class TerrorGame extends Game{
	
	public SpriteBatch batch;
	
	public static final float SCALE = 100f;
	public static final float WIDTH = 1920;
	public static final float HEIGHT = 1080;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new Screen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		
	}
}

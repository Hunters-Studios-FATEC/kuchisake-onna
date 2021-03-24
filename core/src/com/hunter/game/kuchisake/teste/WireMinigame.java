package com.hunter.game.kuchisake.teste;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;

public class WireMinigame {
	
	float w = Gdx.graphics.getWidth();
	float h = Gdx.graphics.getHeight();
	
	FitViewport viewport;
	
	public Stage stage;
	
	Background background;
	WireActor wireActor;
	WireActor2 wireActor2;
	WallActor wallActor;
	
	public WireMinigame(SpriteBatch spriteBatch) {
		viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT * (h / w) / TerrorGame.SCALE, 
								   new OrthographicCamera());
		viewport.apply();
		
		stage = new Stage(viewport, spriteBatch);
		Gdx.input.setInputProcessor(stage);
		
		background = new Background(0, 0);
		wireActor = new WireActor(2, 2);
		wireActor2 = new WireActor2(4, 2);
		wallActor = new WallActor();
		
		stage.addActor(background);
		stage.addActor(wireActor);
		stage.addActor(wireActor2);
		stage.addActor(wallActor);
		
	}
}

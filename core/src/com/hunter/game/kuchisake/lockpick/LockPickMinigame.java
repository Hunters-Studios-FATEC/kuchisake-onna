package com.hunter.game.kuchisake.lockpick;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;

public class LockPickMinigame {
	
	FitViewport viewport;
	
	//float w = Gdx.graphics.getWidth();
	//float h = Gdx.graphics.getHeight();
	
	public Stage stage;
	
	Background background;
	WallActor wallActor;
	FinishChecker finishChecker;
	KeyActor keyActor;
	Correct correctActor;

	boolean isfinished = false;

	TextureAtlas textureAtlas;
	
	public LockPickMinigame(SpriteBatch spriteBatch, TextureAtlas textureAtlas) {
		viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE, 
				   new OrthographicCamera());
		viewport.apply();

		this.textureAtlas = textureAtlas;

		stage = new Stage(viewport, spriteBatch);
		
		background = new Background(0, 0, textureAtlas);
//		wallActor = new WallActor();
		finishChecker = new FinishChecker(15.35f, 5.9f, textureAtlas);
//		keyActor = new KeyActor(3.35f, 4.9f);
		correctActor = new Correct(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, textureAtlas);
		
//		keyActor.addWalls(wallActor);
		
		correctActor.setVisible(false);
		
//		stage.setKeyboardFocus(keyActor);
	}

	public void startMinigame(){
		Gdx.input.setInputProcessor(stage);

		wallActor = new WallActor(textureAtlas);
		keyActor = new KeyActor(3.35f, 4.9f, this, textureAtlas);
		keyActor.addWalls(wallActor);

		stage.addActor(background);
		stage.addActor(wallActor);
		stage.addActor(finishChecker);
		stage.addActor(keyActor);
		stage.addActor(correctActor);

		stage.setKeyboardFocus(keyActor);

		//keyActor.addInitialNearWalls();
	}

	public void closeMinigame() {
		stage.clear();
	}

	public void setIsfinished(boolean isfinished) {
		this.isfinished = isfinished;
	}

	public boolean getIsFinished() {
		return isfinished;
	}

	public Stage getStage(){
		return stage;
	}
}

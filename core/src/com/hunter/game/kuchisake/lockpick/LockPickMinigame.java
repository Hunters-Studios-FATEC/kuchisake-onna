package com.hunter.game.kuchisake.lockpick;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;

public class LockPickMinigame {
	
	FitViewport viewport;

	public Stage stage;
	
	Background background;
	WallActor wallActor;
	FinishChecker finishChecker;
	KeyActor keyActor;
	Correct correctActor;

	boolean isfinished = false;

	TextureAtlas textureAtlas;
	
	Actor description;
	Sprite descSprite;
	
	public LockPickMinigame(SpriteBatch spriteBatch, TextureAtlas textureAtlas, TextureAtlas descriptionAtlas) {
		viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE, 
				   new OrthographicCamera());
		viewport.apply();

		this.textureAtlas = textureAtlas;

		stage = new Stage(viewport, spriteBatch);
		
		background = new Background(0, 0, textureAtlas);
		
		finishChecker = new FinishChecker(15.35f, 7.9f, textureAtlas);
		
		correctActor = new Correct(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2 + 2, textureAtlas);
		correctActor.setVisible(false);
		
		descSprite = new Sprite(descriptionAtlas.findRegion("lockpick"));
		descSprite.setSize(17.64f, 5.88f);
		descSprite.setPosition(viewport.getWorldWidth() / 2 - descSprite.getWidth() / 2, 
				viewport.getWorldHeight() / 2 - descSprite.getHeight() / 2 - 3);
		
		description = new Actor() {
			@Override
			public void draw(Batch batch, float parentAlpha) {
				descSprite.draw(batch);
			}
		};
	}

	public void startMinigame(){
		Gdx.input.setInputProcessor(stage);

		wallActor = new WallActor(textureAtlas);
		keyActor = new KeyActor(3.35f, 6.9f, this, textureAtlas);
		keyActor.addWalls(wallActor);

		stage.addActor(background);
		stage.addActor(description);
		stage.addActor(wallActor);
		stage.addActor(finishChecker);
		stage.addActor(keyActor);
		stage.addActor(correctActor);

		stage.setKeyboardFocus(keyActor);

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

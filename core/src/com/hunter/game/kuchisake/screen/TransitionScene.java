package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;

public class TransitionScene {
	
	FitViewport viewport;
	
	Stage stage;
	Actor transitionActor;
	AlphaAction alphaAction;
	
	TextureAtlas textureAtlas;
	Sprite sprite;
	
	boolean canFadeIn = false;
	
	public TransitionScene(TerrorGame game) {
		viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE);
		
		stage = new Stage(viewport, game.batch);
		alphaAction = new AlphaAction();
		
		textureAtlas = game.getAssetManager().get("MinigameAssets/MinigameObjects.atlas", TextureAtlas.class);
		
		sprite = new Sprite(textureAtlas.findRegion("black_rectangle"));
		sprite.setBounds(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
		
		transitionActor = new Actor() {
			@Override
			public void draw(Batch batch, float parentAlpha) {
				sprite.setAlpha(getColor().a);
				sprite.draw(batch);
			}
		};
		
		transitionActor.setColor(0, 0, 0, 1);
		
		stage.addActor(transitionActor);
		
		if(game.getMinigameManager().getGeradorCompleted()) {
			alphaAction.setAlpha(0);
		}
		else {
			alphaAction.setAlpha(0.8f);
		}
		
		alphaAction.setDuration(0.2f);
		
		transitionActor.addAction(alphaAction);
	}
	
	public void updateTransition() {
		stage.act();
		stage.draw();
	}
	
	public void fadeIn() {
		if(!canFadeIn) {
			alphaAction.reset();
			
			alphaAction.setAlpha(1);
			alphaAction.setDuration(0.2f);
			
			transitionActor.addAction(alphaAction);
			
			canFadeIn = true;
		}
	}
	
	public Actor getActor() {
		return transitionActor;
	}
	
	public void dispose() {
		stage.dispose();
	}
}

package com.hunter.game.kuchisake.hide;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;

public class Hide {
	
	FitViewport viewport;
	
	public Stage stage;
	
	Array<Circle> circleArray;
	Array<CircleOverlay> circleOverlayArray;
	
	Background background;

	boolean isfinished = false;
	
	public Hide(SpriteBatch batch) {
		viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE, 
								   new OrthographicCamera());
		
		stage = new Stage(viewport, batch);
		
		Gdx.input.setInputProcessor(stage);
		
		background = new Background(0, 0);
		
		//circle = new Circle(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0, 5f);
		//circleOverlay = new CircleOverlay(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 5f);
		
		circleArray = new Array<Circle>();
		circleOverlayArray = new Array<CircleOverlay>();
		
		//System.out.println(stage.getActors().size);
		
		stage.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Input.Keys.W) {
					for(int i = 1; i <= (stage.getActors().size - 1) / 2; i++) {
						if(((Circle) stage.getActors().get(i)).getNValue() == 0 && 
						  (!((Circle) stage.getActors().get(i)).getKeyPressed() &&
						  !((Circle) stage.getActors().get(i)).getTriggeredMissedKeyEvent())) {
							
							((Circle) stage.getActors().get(i)).hitKey();
							((CircleOverlay) stage.getActors().get(i + (stage.getActors().size - 1) / 2)).hitKey();
							
							break;
						}
					}
				}
				if(keycode == Input.Keys.A) {
					for(int i = 1; i <= (stage.getActors().size - 1) / 2; i++) {
						if(((Circle) stage.getActors().get(i)).getNValue() == 1 &&
						   (!((Circle) stage.getActors().get(i)).getKeyPressed() &&
						   !((Circle) stage.getActors().get(i)).getTriggeredMissedKeyEvent())) {
							
							((Circle) stage.getActors().get(i)).hitKey();
							((CircleOverlay) stage.getActors().get(i + (stage.getActors().size - 1) / 2)).hitKey();
							
							break;
						}
					}
				}
				if(keycode == Input.Keys.S) {
					for(int i = 1; i <= (stage.getActors().size - 1) / 2; i++) {
						if(((Circle) stage.getActors().get(i)).getNValue() == 2 &&
						   (!((Circle) stage.getActors().get(i)).getKeyPressed() &&
						   !((Circle) stage.getActors().get(i)).getTriggeredMissedKeyEvent())) {
							
							((Circle) stage.getActors().get(i)).hitKey();
							((CircleOverlay) stage.getActors().get(i + (stage.getActors().size - 1) / 2)).hitKey();
							
							break;
						}
					}
				}
				if(keycode == Input.Keys.D) {
					for(int i = 1; i <= (stage.getActors().size - 1) / 2; i++) {
						if(((Circle) stage.getActors().get(i)).getNValue() == 3 &&
						   (!((Circle) stage.getActors().get(i)).getKeyPressed() &&
						   !((Circle) stage.getActors().get(i)).getTriggeredMissedKeyEvent())) {
							
							((Circle) stage.getActors().get(i)).hitKey();
							((CircleOverlay) stage.getActors().get(i + (stage.getActors().size - 1) / 2)).hitKey();
							
							break;
						}
					}
				}
				
				return true;
			}
		});
	}
	
	void createHitCircles() {
		Random random = new Random();
		
		float randomTime = 0;
		float randomX = 0;
		float randomY = 0;
		
		for(int i = 0; i < (6 + random.nextInt(6)); i++) {
			randomTime += 3f * random.nextFloat();
			
			Circle circle;
			CircleOverlay circleOverlay;
			
			randomX = 1.28f * 2 + random.nextFloat() * (viewport.getWorldWidth() - 1.28f * 4);
			randomY = 1.28f * 2 + random.nextFloat() * (viewport.getWorldHeight() - 1.28f * 4);
			
			circle = new Circle(randomX, randomY, random.nextInt(4), randomTime, i + 1);
			
			//circle = new Circle(randomX, randomY, 0, randomTime, i + 1);
			
			circleArray.add(circle);
			
			circleOverlay = new CircleOverlay(randomX, randomY, randomTime);
			
			circleOverlayArray.add(circleOverlay);
		}
	}

	public void startMinigame(){
		stage.addActor(background);

		createHitCircles();

		//System.out.println(circleArray.get(0).startTime);

		for(Circle circle : circleArray) {
			stage.addActor(circle);
		}

		for(CircleOverlay circleOverlay : circleOverlayArray) {
			stage.addActor(circleOverlay);
		}
	}

	public void closeMinigame() {
		stage.clear();
	}

	public boolean getIsFinished() {
		return isfinished;
	}

	public Stage getStage(){
		return stage;
	}
}

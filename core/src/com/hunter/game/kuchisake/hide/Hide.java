package com.hunter.game.kuchisake.hide;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.screen.GameOver;

public class Hide {
	
	FitViewport viewport;
	
	public Stage stage;
	
	Array<Circle> circleArray;
	Array<CircleOverlay> circleOverlayArray;
	
	Background background;

	boolean isFinished = false;
	boolean canShowResults = false;

	SpriteBatch hideBatch;
	
	TextureAtlas textureAtlas;
	
	int count = 0;
	
	float playSoundTimer = 0;
	
	TerrorGame game;
	
	Sound openDoor;
	Sound closeDoor;
	Sound foundPlayer;
	
	boolean playAudio2 = true;
	boolean gotCaught = false;
	boolean showGameOverScreen = false;
	
	float minScore;
	float score;
	
	float maxTime;
	float scorePercent;
	int minNumber;
	
	Sprite descSprite;
	Actor description;
	
	AlphaAction alphaAction;
	
	public Hide(SpriteBatch batch, TerrorGame game, TextureAtlas textureAtlas, TextureAtlas descriptionAtlas) {
		viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE, 
								   new OrthographicCamera());

		hideBatch = batch;

		stage = new Stage(viewport, hideBatch);
		
		this.textureAtlas = textureAtlas;
		
		this.game = game;
		
		openDoor = game.getAssetManager().get("Audio/Sfx/porta abrindo 3.ogg", Sound.class);
		closeDoor = game.getAssetManager().get("Audio/Sfx/porta fechando 3.ogg", Sound.class);
		foundPlayer = game.getAssetManager().get("Audio/Sfx/Achei voce.ogg", Sound.class);
		
		descSprite = new Sprite(descriptionAtlas.findRegion("esconde"));
		descSprite.setSize(17.64f, 5.88f);
		descSprite.setPosition(viewport.getWorldWidth() / 2 - descSprite.getWidth() / 2, 
				viewport.getWorldHeight() / 2 - descSprite.getHeight() / 2);
	}
	
	void createHitCircles() {
		Random random = new Random();
		
		float randomTime = 0;
		float randomX = 0;
		float randomY = 0;
		
		for(int i = 0; i < (minNumber + random.nextInt(6)); i++) {
			randomTime += 0.2f + maxTime * random.nextFloat();
			
			Circle circle;
			CircleOverlay circleOverlay;
			
			randomX = 1.28f * 2 + random.nextFloat() * (viewport.getWorldWidth() - 1.28f * 4);
			randomY = 1.28f * 2 + random.nextFloat() * (viewport.getWorldHeight() - 1.28f * 4);
			
			circle = new Circle(randomX, randomY, random.nextInt(4), randomTime, i + 1, textureAtlas, this);
			
			//circle = new Circle(randomX, randomY, 0, randomTime, i + 1);
			
			circleArray.add(circle);
			
			circleOverlay = new CircleOverlay(randomX, randomY, randomTime, textureAtlas, this);
			
			circleOverlayArray.add(circleOverlay);
		}
		
		minScore = (1000 * circleArray.size) * scorePercent;
	}
	
	public void addAllCircles() {
		count = 0;
		playSoundTimer = 0;
		score = 0;
		
		playAudio2 = true;
		gotCaught = false;
		
		isFinished = false;
		canShowResults = false;

		circleArray = new Array<Circle>();
		circleOverlayArray = new Array<CircleOverlay>();

		createHitCircles();

		for(Circle circle : circleArray) {
			stage.addActor(circle);
		}

		for(CircleOverlay circleOverlay : circleOverlayArray) {
			stage.addActor(circleOverlay);
		}

		stage.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Input.Keys.UP) {
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
				if(keycode == Input.Keys.LEFT) {
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
				if(keycode == Input.Keys.DOWN) {
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
				if(keycode == Input.Keys.RIGHT) {
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
	
	void showDescription() {
		stage = new Stage(viewport, hideBatch);

		background = new Background(0, 0, textureAtlas);

		Gdx.input.setInputProcessor(stage);
		
		description = new Actor() {
			@Override
			public void draw(Batch batch, float parentAlpha) {
				descSprite.setAlpha(getColor().a);
				descSprite.draw(batch);
			}
		};
		
		description.setColor(0, 0, 0, 1f);
		
		alphaAction = new AlphaAction();
		alphaAction.setAlpha(0);
		alphaAction.setDuration(5f);
		
		description.addAction(alphaAction);

		stage.addActor(background);
		stage.addActor(description);
	}

	public void startMinigame() {
		showDescription();
	}
	
	public void verifyConclusion() {
		if(count == (stage.getActors().size - 1) / 2) {
			isFinished = true;
			
			System.out.println(score + " - " + minScore + " - " + count);
		}
	}
	
	public boolean showResults(float delta) {
		if(!canShowResults) {
			canShowResults = true;
			
			game.getRunTheme().stop();
			
			background.setSolidColor();
			
			openDoor.setVolume(openDoor.play(), 0.5f);
		}
		else {
			playSoundTimer += delta;
			
			if(playSoundTimer > 3f && playAudio2) {
				playAudio2 = false;
				
				if(score >= minScore) {
					closeDoor.setVolume(closeDoor.play(), 0.5f);
				}
				else {
					gotCaught = true;
					foundPlayer.setVolume(foundPlayer.play(), 0.5f);
				}
			}
			else if(playSoundTimer > 5.5f) {
				if(!gotCaught){
					background.fadeOut();
				}
				
				if(playSoundTimer > 5.75f) {
					if(gotCaught) {
						showGameOverScreen = true;
					}
					else {
						System.out.println("TESTE");
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public boolean getShowGameOverScreen() {
		return showGameOverScreen;
	}
	
	public void setLevel(int level) {
		switch(level) {
			case 1: {
				maxTime = 3f;
				minNumber = 6;
				scorePercent = 0.35f;
				break;
			}
			case 2: {
				maxTime = 2f;
				minNumber = 12;
				scorePercent = 0.6f;
				break;
			}
			case 3: {
				maxTime = 1f;
				minNumber = 18;
				scorePercent = 0.8f;
				break;
			}
		}
	}
	
	public void incrementCount() {
		count++;
	}
	
	public void incrementScore(float points) {
		score += points;
	}


	public void closeMinigame() {
		stage.clear();
	}

	public boolean getIsFinished() {
		return isFinished;
	}

	public Stage getStage(){
		return stage;
	}
}

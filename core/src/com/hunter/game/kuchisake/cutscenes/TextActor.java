package com.hunter.game.kuchisake.cutscenes;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.screen.Saguao;
import com.hunter.game.kuchisake.startMenu.SceneMenu;

public class TextActor extends Actor{
	
	TextureRegion text;
	Sprite textSprite;
	
	TerrorGame game;
	
	TextureAtlas textureAtlas;
	
	int index = 1;
	
	float posX;
	float posY;
	
	AlphaAction alphaAction;
	
	float timeCount;
	
	Music cutsceneTheme;
	Sound watashi;
	
	boolean isCut1;
	
	public TextActor(TerrorGame game, float posX, float posY, Music cutsceneTheme, boolean isCut1) {
		this.game = game;
		this.isCut1 = isCut1;
		
		if(isCut1) {
			this.textureAtlas = game.getAssetManager().get("Cutscenes/Cutscene1Objects.atlas");
			text = textureAtlas.findRegion("cut1 line" + Integer.toString(index));
		}
		else {
			this.textureAtlas = game.getAssetManager().get("Cutscenes/Cutscene2Objects.atlas");
			text = textureAtlas.findRegion("cut2 line" + Integer.toString(index));
		}
		
		this.posX = posX;
		this.posY = posY;
		
		textSprite = new Sprite(text);
		textSprite.setSize(17.64f, 5.88f);
		textSprite.setPosition(posX - textSprite.getWidth() / 2, posY - textSprite.getHeight() / 2);
		
		setColor(0, 0, 0, 0);
		
		watashi = game.getAssetManager().get("Audio/Sfx/watashi wa kirei.ogg");
		
		alphaAction = new AlphaAction();
		
		fadeInText();
		
		this.cutsceneTheme = cutsceneTheme;
	}
	
	void changeText() {
		if(isCut1) {
			text = textureAtlas.findRegion("cut1 line" + Integer.toString(++index));
		}
		else {
			text = textureAtlas.findRegion("cut2 line" + Integer.toString(++index));
		}
		
		textSprite.setRegion(text);
		textSprite.setPosition(posX - textSprite.getWidth() / 2, posY - textSprite.getHeight() / 2);
		
		if(index == 6 && isCut1) {
			watashi.play(0.5f);
		}
		else if(index == 4 && !isCut1) {
			watashi.play(0.5f);
		}
	}
	
	void fadeInText() {
		alphaAction.reset();
		
		alphaAction.setAlpha(1f);
		alphaAction.setDuration(0.5f);
		
		addAction(alphaAction);
	}
	
	void fadeOutText() {
		alphaAction.reset();
		
		alphaAction.setAlpha(0);
		alphaAction.setDuration(0.5f);
		
		addAction(alphaAction);
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		textSprite.setAlpha(getColor().a);
		textSprite.draw(batch);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(getColor().a == 1f) {
			timeCount += delta;
			
			if(timeCount >= 5f) {
				fadeOutText();
				timeCount = 0;
			}
		}
		else if(getColor().a == 0) {
			if(isCut1) {
				if(index < 6) {
					changeText();
					fadeInText();
				}
				else {
					game.createMinigameManager();
			        game.createInventoryManager();
			        
			        game.createVillain(3, 0);
			        
			        game.addMusic();
			        
			        cutsceneTheme.stop();
					
			        game.getAssetManager().unload("Cutscenes/Cutscene1Objects.atlas");
			        
					game.setScreen(new Saguao(game, 1750, false));
				}
			}
			else {
				if(index < 4) {
					changeText();
					fadeInText();
				}
				else {
		        	
					game.getAssetManager().load("Cutscenes/fundo_cutscene.png", Texture.class);
		            game.getAssetManager().load("Cutscenes/creditos.png", Texture.class);
		            
		        	game.getAssetManager().finishLoading();
			        
			        game.setScreen(new Creditos(game, cutsceneTheme));
				}
			}
		}
	}
}

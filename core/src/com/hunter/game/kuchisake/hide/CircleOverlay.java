package com.hunter.game.kuchisake.hide;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SizeToAction;
import com.hunter.game.kuchisake.TerrorGame;

public class CircleOverlay extends Actor{
	
	float timeCount = 0;
	float startTime = 0;
	
	TextureRegion texture;
	Sprite sprite;
	
	float previousWidth = 0;
	float previousHeight = 0;
	
	boolean keyPressed = false;
	boolean triggeredMissedKeyEvent = false;
	boolean startedActions = false;
	
	AlphaAction alphaAction;
	SizeToAction sizeToAction;
	MoveByAction moveByAction;
	
	Hide hideMinigame;
	
	public CircleOverlay(float x, float y, float time, TextureAtlas textureAtlas, Hide hideMinigame) {
		startTime = time;
		
		texture = textureAtlas.findRegion("hitcircleoverlay");
		
		sprite = new Sprite(texture);
		sprite.setBounds(x, y, sprite.getWidth() * 4 / TerrorGame.SCALE, sprite.getHeight() * 4 / TerrorGame.SCALE);
		sprite.setPosition(x - (0.12f + (sprite.getWidth() - (1.28f + 0.24f)) / 2), 
						   y - (0.12f + (sprite.getHeight() - (1.28f + 0.24f)) / 2));
		
		setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		
		previousWidth = getWidth();
		previousHeight = getHeight();
		
		setColor(0, 0, 0, 0);
		
		this.hideMinigame = hideMinigame;
	}
	
	void addInitialActions() {
		if(timeCount >= startTime && !startedActions) {
			alphaAction = new AlphaAction();
			alphaAction.setAlpha(0.75f);
			alphaAction.setDuration(0.75f);
			
			addAction(alphaAction);
			
			sizeToAction = new SizeToAction();
			sizeToAction.setSize(1.28f + 0.24f, 1.28f + 0.24f);
			sizeToAction.setDuration(1.75f);
			
			addAction(sizeToAction);
			
			moveByAction = new MoveByAction();
			
			startedActions = true;
		}
	}
	
	public void hitKey() {
		if(getColor().a == 0.75f && !keyPressed) {
			if(getActions().indexOf(sizeToAction, true) > -1) {
				removeAction(getActions().get(getActions().indexOf(sizeToAction, true)));
			}
			
			alphaAction.reset();
			alphaAction.setAlpha(0);
			alphaAction.setDuration(0.2f);
			
			addAction(alphaAction);
			
			sizeToAction.reset();
			sizeToAction.setSize(1.28f * 4, 1.28f * 4);
			sizeToAction.setDuration(0.2f);
			
			addAction(sizeToAction);
			
			keyPressed = true;
			
			hideMinigame.incrementScore(1000 * (1.28f + 0.24f) / getWidth());
		}
	}
	
	void missKey() {
		if((getWidth() == 1.28f + 0.24f && !keyPressed) && !triggeredMissedKeyEvent) {	
			alphaAction.reset();
			alphaAction.setAlpha(0);
			alphaAction.setDuration(0.2f);
			
			addAction(alphaAction);
			
			moveByAction.setAmountY(-0.5f);
			moveByAction.setDuration(0.2f);
			
			addAction(moveByAction);
			
			triggeredMissedKeyEvent = true;
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		setPosition(getX() + (previousWidth - getWidth()) / 2 , getY() + (previousHeight - getHeight()) / 2);
		
		previousWidth = getWidth();
		previousHeight = getHeight();
		
		sprite.setPosition(getX(), getY());
		sprite.setAlpha(getColor().a);
		sprite.setSize(getWidth(), getHeight());
		sprite.draw(batch);
		
		//System.out.println(getColor().a);
		
		//System.out.println(getActions());
	}
	
	@Override
	public void act(float delta) {
		timeCount += delta;
		
		addInitialActions();
		missKey();
		
		super.act(delta);
	}
}

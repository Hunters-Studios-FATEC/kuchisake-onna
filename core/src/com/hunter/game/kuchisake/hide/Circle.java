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

public class Circle extends Actor{
	
	int nValue = 0;
	int stageIndex = 0;
	
	float timeCount = 0;
	float startTime = 0;
	
	TextureRegion texture;
	
	Sprite circleSprite;
	Sprite arrowSprite;
	
	float previousWidth = 0;
	float previousHeight = 0;
	
	boolean keyPressed = false;
	boolean triggeredMissedKeyEvent = false;
	boolean startedActions = false;
	
	AlphaAction alphaAction;
	SizeToAction sizeToAction;
	MoveByAction moveByAction;
	
	Hide hideMinigame;
	
	boolean canIncrementCount = true;
	
	public Circle(float x, float y, int randomN, float time, int stgIndex, TextureAtlas textureAtlas, Hide hideMinigame) {
		nValue = randomN;
		
		startTime = time;
		
		stageIndex = stgIndex;
		
		texture = textureAtlas.findRegion("circle");
		
		circleSprite = new Sprite(texture);
		circleSprite.setBounds(x, y, circleSprite.getWidth() / TerrorGame.SCALE, circleSprite.getHeight() / TerrorGame.SCALE);
		
		setBounds(x, y, circleSprite.getWidth(), circleSprite.getHeight());
		
		texture = textureAtlas.findRegion("red_arrow");
		
		arrowSprite = new Sprite(texture);
		arrowSprite.setBounds(x, y, circleSprite.getWidth() / 2, circleSprite.getHeight() / 2);
		arrowSprite.setPosition(x + circleSprite.getWidth() / 2 - arrowSprite.getWidth() / 2, 
								y + circleSprite.getHeight() / 2 - arrowSprite.getHeight() / 2);
		
		arrowSprite.setOrigin(arrowSprite.getWidth() / 2, arrowSprite.getHeight() / 2);
		
		for(int i = 0; i < nValue; i++) {
			arrowSprite.rotate90(false);
		}
		
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
			moveByAction = new MoveByAction();
			
			startedActions = true;
		}
	}
	
	public void hitKey() {
		if(getColor().a == 0.75f && !keyPressed) {
			alphaAction.reset();
			alphaAction.setAlpha(0);
			alphaAction.setDuration(0.2f);
			
			addAction(alphaAction);
			
			sizeToAction.setSize(1.28f * 2f, 1.28f * 2f);
			sizeToAction.setDuration(0.2f);
			
			addAction(sizeToAction);
			
			keyPressed = true;
			
			hideMinigame.incrementHitCount();
		}
	}
	
	void missKey() {
		if((getStage().getActors().get(stageIndex + (getStage().getActors().size - 1) / 2).getWidth() == 1.28f + 0.24f && 
		    !keyPressed) && !triggeredMissedKeyEvent) {	
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
	
	public int getNValue() {
		return nValue;
	}
	
	public boolean getKeyPressed() {
		return keyPressed;
	}
	
	public boolean getTriggeredMissedKeyEvent() {
		return triggeredMissedKeyEvent;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		setPosition(getX() + (previousWidth - getWidth()) / 2 , getY() + (previousHeight - getHeight()) / 2);
		
		circleSprite.setSize(getWidth(), getHeight());
		arrowSprite.setSize(arrowSprite.getWidth() + (getWidth() - previousWidth) / 2, 
							arrowSprite.getHeight() + (getHeight() - previousHeight) / 2);
		
		previousWidth = getWidth();
		previousHeight = getHeight();
		
		circleSprite.setPosition(getX(), getY());
		
		arrowSprite.setPosition(circleSprite.getX() + circleSprite.getWidth() / 2 - arrowSprite.getWidth() / 2, 
								circleSprite.getY() + circleSprite.getHeight() / 2 - arrowSprite.getHeight() / 2);
		
		circleSprite.setAlpha(getColor().a);
		arrowSprite.setAlpha(getColor().a);
		
		circleSprite.draw(batch);
		arrowSprite.draw(batch);
	}
	
	@Override
	public void act(float delta) {
		timeCount += delta;
		
		addInitialActions();
		missKey();
		
		super.act(delta);
		
		if((getActions().size == 0 && (keyPressed || triggeredMissedKeyEvent)) && canIncrementCount) {
			hideMinigame.incrementCount();
			canIncrementCount = false;
		}
	}
}

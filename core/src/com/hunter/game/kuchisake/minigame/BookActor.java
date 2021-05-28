package com.hunter.game.kuchisake.minigame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.hunter.game.kuchisake.TerrorGame;

public class BookActor extends Actor{
	
	TextureRegion texture;
	
	Sprite sprite;
	
	boolean stoppedDragging = false;
	boolean isDragging = false;
	
	float initialXPos;
	float initialYPos;
	
	int value;
	
	public BookActor(String img, float x, float y, int v, TextureAtlas textureAtlas) {
		// TODO Auto-generated constructor stub	
		texture = textureAtlas.findRegion(img);
		
		sprite = new Sprite(texture);
		sprite.setBounds(x, y, sprite.getWidth() / (TerrorGame.SCALE * 2), sprite.getHeight() / (TerrorGame.SCALE * 2));
		
		setBounds(x, y, sprite.getWidth(), sprite.getHeight());
		
		setTouchable(Touchable.enabled);
		
		initialXPos = x;
		initialYPos = y;
		
		value = v;

		
		addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				toFront();
				return true;
			}
			
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				if(isDragging) {
					stoppedDragging = true;
					isDragging = false;
					
				}
			}
			
			public void touchDragged(InputEvent event, float x, float y, int pointer){
				isDragging = true;
				
				moveBy(x - getWidth() / 2, y - getHeight() / 2);
			}
		});
		
	}
	
	public int getValue() {
		return value;
	}
	
	public boolean getIsDragging() {
		return isDragging;
	}
	
	boolean getStoppedDragging() {
		return stoppedDragging;
	}
	
	void setStoppedDragging(boolean state) {
		stoppedDragging = state;
	}
	
	void setPos(float x, float y) {
		MoveToAction setPosAction = new MoveToAction();
		setPosAction.setPosition(x, y);
		addAction(setPosAction);
	}
	
	float[] getInitialPos() {
		float[] position = {initialXPos, initialYPos};
		return position;
	}
	
	@Override
    public void draw(Batch batch, float alpha){
		sprite.setPosition(getX(), getY());
		sprite.draw(batch);
		
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}
}

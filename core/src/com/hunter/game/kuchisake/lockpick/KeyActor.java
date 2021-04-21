package com.hunter.game.kuchisake.lockpick;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.hunter.game.kuchisake.TerrorGame;

public class KeyActor extends Actor{
	
	Texture texture;
	Sprite sprite;

	LockPickMinigame minigame;

	float moveX = 0;
	float moveY = 0;
	
	float previousYPos = 0;
	
	Array<Sprite> nearWallsArray;



	public KeyActor(float x, float y, LockPickMinigame minigame) {

		this.minigame = minigame;
		texture = new Texture("orange_square.png");
		
		sprite = new Sprite(texture);
		sprite.setBounds(x, y, sprite.getWidth() / TerrorGame.SCALE, sprite.getHeight() / TerrorGame.SCALE);
		sprite.setPosition(x + 0.5f / 2 - sprite.getWidth() / 2, y + 0.5f / 2 - sprite.getHeight() / 2);
		
		setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		
		nearWallsArray = new Array<Sprite>();
		
		addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Input.Keys.W) {
					moveY += 2.0f;
				}
				if(keycode == Input.Keys.S) {
					moveY += -2.0f;
				}
				if(keycode == Input.Keys.D) {
					moveX += 2.0f;
				}
				if(keycode == Input.Keys.A) {
					moveX += -2.0f;
				}
				
				return true;
			}
			
			@Override
			public boolean keyUp(InputEvent event, int keycode) {
				if(keycode == Input.Keys.W) {
					moveY -= 2.0f;
				}
				if(keycode == Input.Keys.S) {
					moveY -= -2.0f;
				}
				if(keycode == Input.Keys.D) {
					moveX -= 2.0f;
				}
				if(keycode == Input.Keys.A) {
					moveX -= -2.0f;
				}
				
				return true;
			}
		});
	}
	
	void verifyPos(float xPos, float yPos){
		if(moveX != 0 || moveY != 0) {
			for(Sprite wall : nearWallsArray) {
				if(((getX() + getWidth() > wall.getX() && getX() + getWidth() < wall.getX() + getWidth()) && moveX > 0) && 
				    (getY() + getHeight() > wall.getY() && getY() < wall.getY() + wall.getHeight())) {
					if(moveY != 0) {
						if(getY() + getHeight() > wall.getY() && getY() + getHeight() < wall.getY() + getHeight()) {
							if(previousYPos + getHeight() <= wall.getY()) {
								setPosition(getX(), wall.getY() - getHeight());
							}
						}
						else if(getY() < wall.getY() + wall.getHeight() && getY() > wall.getY() + wall.getHeight() - getHeight()) {
							if(previousYPos >= wall.getY() + wall.getHeight()) {
								setPosition(getX(), wall.getY() + wall.getHeight());
							}
						}
						
						if((getX() + getWidth() > wall.getX() && getX() + getWidth() < wall.getX() + getWidth()) && 
						   (getY() + getHeight() > wall.getY() && getY() < wall.getY() + wall.getHeight())) {
							setPosition(wall.getX() - getWidth(), getY());
						}
					}
					else {
						setPosition(wall.getX() - getWidth(), getY());
					}
				}
				else if(((getX() < wall.getX() + wall.getWidth() && 
						  getX() > wall.getX() + wall.getWidth() - getWidth()) && moveX < 0) && 
						 (getY() + getHeight() > wall.getY() && getY() < wall.getY() + wall.getHeight())) {
					if(moveY != 0) {
						if(getY() + getHeight() > wall.getY() && getY() + getHeight() < wall.getY() + getHeight()) {
							if(previousYPos + getHeight() <= wall.getY()) {
								setPosition(getX(), wall.getY() - getHeight());
							}
						}
						else if(getY() < wall.getY() + wall.getHeight() && getY() > wall.getY() + wall.getHeight() - getHeight()) {
							if(previousYPos >= wall.getY() + wall.getHeight()) {
								setPosition(getX(), wall.getY() + wall.getHeight());
							}
						}
						
						if((getX() < wall.getX() + wall.getWidth() && getX() > wall.getX() + wall.getWidth() - getWidth()) && 
						   (getY() + getHeight() > wall.getY() && getY() < wall.getY() + wall.getHeight())) {
							setPosition(wall.getX() + wall.getWidth(), getY());
						}
					}
					else {
						setPosition(wall.getX() + wall.getWidth(), getY());
					}
				}
				else if(((getY() + getHeight() > wall.getY() && getY() + getHeight() < wall.getY() + getHeight()) && moveY > 0) && 
					(getX() + getWidth() > wall.getX() && getX() < wall.getX() + wall.getWidth())) {
					setPosition(getX(), wall.getY() - getHeight());
				}
				else if(((getY() < wall.getY() + wall.getHeight() && 
						  getY() > wall.getY() + wall.getHeight() - getHeight()) && moveY < 0) && 
						 (getX() + getWidth() > wall.getX() && getX() < wall.getX() + wall.getWidth())) {
					setPosition(getX(), wall.getY() + wall.getHeight());
				}
			}
		}		
	}
	
	void verifyFinishCheckerPos() {
		if((getX() + getWidth() > getStage().getActors().get(2).getX() && 
		   getX() < getStage().getActors().get(2).getX() + getStage().getActors().get(2).getWidth()) &&
		   (getY() + getHeight() > getStage().getActors().get(2).getY() && 
		   getY() < getStage().getActors().get(2).getY() + getStage().getActors().get(2).getHeight())) {
			getStage().getActors().get(4).setVisible(true);
			minigame.setIsfinished(true);
		}
	}
	
	public void addWalls(WallActor wallActor) {
		nearWallsArray.addAll(wallActor.getSpriteArray());
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.setPosition(getX(), getY());
		sprite.draw(batch);
	}
	
	@Override
	public void act(float delta) {
		previousYPos = getY();
		moveBy(moveX * delta, moveY * delta);
		verifyPos(getX(), getY());
		verifyFinishCheckerPos();
		
		//System.out.println(nearWallsArray.size);
	}
	
}

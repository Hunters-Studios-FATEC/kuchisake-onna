package com.hunter.game.kuchisake.lockpick;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.hunter.game.kuchisake.TerrorGame;

public class KeyActor extends Actor{
	
	TextureRegion texture;
	Sprite sprite;
	
	TextureRegion squareTexture;
	Sprite squareSprite;

	LockPickMinigame minigame;

	float moveX = 0;
	float moveY = 0;
	
	float previousYPos = 0;
	
	Array<Sprite> nearWallsArray;
	
	boolean growSquare = false;

	public KeyActor(float x, float y, LockPickMinigame minigame, TextureAtlas textureAtlas) {
		this.minigame = minigame;
		texture = textureAtlas.findRegion("gazua");
		squareTexture = textureAtlas.findRegion("orange_square");
		
		sprite = new Sprite(texture);
		sprite.setBounds(x, y, 36 / TerrorGame.SCALE, 48 / TerrorGame.SCALE);
		sprite.setPosition(x + 0.5f / 2 - sprite.getWidth() / 2, y + 0.5f / 2 - sprite.getHeight() / 2);
		
		setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		
		nearWallsArray = new Array<Sprite>();
		
		squareSprite = new Sprite(squareTexture);
		squareSprite.setBounds(sprite.getX(), sprite.getY(), 48 / TerrorGame.SCALE, 48 / TerrorGame.SCALE);
		squareSprite.setOriginCenter();
		
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
		if((getX() + getWidth() > getStage().getActors().get(3).getX() && 
		   getX() < getStage().getActors().get(3).getX() + getStage().getActors().get(3).getWidth()) &&
		   (getY() + getHeight() > getStage().getActors().get(3).getY() && 
		   getY() < getStage().getActors().get(3).getY() + getStage().getActors().get(3).getHeight())) {
			getStage().getActors().get(5).setVisible(true);
			minigame.setIsfinished(true);
		}
	}
	
	public void addWalls(WallActor wallActor) {
		nearWallsArray.addAll(wallActor.getSpriteArray());
	}
	
	void changeSquareSize(float delta) {
		if(growSquare) {
			if(squareSprite.getWidth() < 48f / TerrorGame.SCALE) {
				squareSprite.setScale(squareSprite.getWidth() + (48f / TerrorGame.SCALE * delta));
			}
			else {
				growSquare = false;
			}
		}
		else {
			if(squareSprite.getWidth() > 0f / TerrorGame.SCALE) {
				squareSprite.setScale(squareSprite.getWidth() - (48f / TerrorGame.SCALE * delta));
			}
			else {
				growSquare = true;
			}
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.setPosition(getX(), getY());
		squareSprite.setPosition(sprite.getX(), sprite.getY());
		squareSprite.draw(batch);
		sprite.draw(batch);
	}
	
	@Override
	public void act(float delta) {
		previousYPos = getY();
		moveBy(moveX * delta, moveY * delta);
		verifyPos(getX(), getY());
		verifyFinishCheckerPos();
		changeSquareSize(delta);
		
		//System.out.println(nearWallsArray.size);
	}
	
}

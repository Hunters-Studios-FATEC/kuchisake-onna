package com.hunter.game.kuchisake.teste;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.hunter.game.kuchisake.TerrorGame;

public class WireActor2 extends Actor{
	
	Array<Sprite> spriteArray;
	
	public WireActor2(float x, float y) {
		spriteArray = new Array<Sprite>();
		
		addSprite("red_square.png", x, y, 0);
		
		setBounds(x, y, spriteArray.get(0).getWidth(), spriteArray.get(0).getHeight());
		
		addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				resetSpriteArray(4, 2);
			}
			
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				//moveBy(x, y);
				
				if(event.getStageX() >= spriteArray.get(spriteArray.size - 1).getX() + getWidth()) {
					if((spriteArray.size > 1 && 
					   spriteArray.get(spriteArray.size - 2).getX() != spriteArray.get(spriteArray.size - 1).getX() + getWidth()) || 
					   spriteArray.size == 1) {
						if(!isPosWall(0, event)) {
							addSprite("red_square.png", spriteArray.get(spriteArray.size - 1).getX() + getWidth(), 
									  spriteArray.get(spriteArray.size - 1).getY(), 0);
						}
					}
					//System.out.print("mouse x: " + event.getStageX() + " | ");
				}
				else if(event.getStageX() <= spriteArray.get(spriteArray.size - 1).getX()) {
					if((spriteArray.size > 1 && 
					   spriteArray.get(spriteArray.size - 2).getX() != spriteArray.get(spriteArray.size - 1).getX() - getWidth()) || 
					   spriteArray.size == 1) {
						if(!isPosWall(1, event)) {
							addSprite("red_square.png", spriteArray.get(spriteArray.size - 1).getX() - getWidth(), 
									  spriteArray.get(spriteArray.size - 1).getY(), 0);
						}
					}
					//System.out.print("mouse x: " + event.getStageX() + " | ");
				}
				else if(event.getStageY() >= spriteArray.get(spriteArray.size - 1).getY() + getHeight()) {
					if((spriteArray.size > 1 && 
					   spriteArray.get(spriteArray.size - 2).getY() != spriteArray.get(spriteArray.size - 1).getY() + getHeight()) || 
					   spriteArray.size == 1) {
						if(!isPosWall(2, event)) {
							addSprite("red_square.png", spriteArray.get(spriteArray.size - 1).getX(), 
									  spriteArray.get(spriteArray.size - 1).getY() + getHeight(), 90);
						}					
					}
					//System.out.print("mouse x: " + event.getStageX() + " | ");
				}
				else if(event.getStageY() <= spriteArray.get(spriteArray.size - 1).getY()) {
					if((spriteArray.size > 1 && 
					   spriteArray.get(spriteArray.size - 2).getY() != spriteArray.get(spriteArray.size - 1).getY() - getHeight()) || 
					   spriteArray.size == 1) {
						if(!isPosWall(3, event)) {
							addSprite("red_square.png", spriteArray.get(spriteArray.size - 1).getX(), 
									  spriteArray.get(spriteArray.size - 1).getY() - getHeight(), 90);
						}	
					}
					//System.out.print("mouse x: " + event.getStageX() + " | ");
				}
			}
		});
		
	}
	
	void addSprite(String filePath, float x, float y, float rotation) {
		Texture texture = new Texture(filePath);
		
		Sprite sprite = new Sprite(texture);
		sprite.setBounds(x, y, sprite.getWidth() / TerrorGame.SCALE, sprite.getHeight() / TerrorGame.SCALE);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setRotation(rotation);
		
		spriteArray.add(sprite);
	}
	
	void resetSpriteArray(float x, float y) {
		spriteArray.clear();
		
		addSprite("red_square.png", x, y, 0);
	}
	
	boolean isPosWall(int n, InputEvent event) {
		boolean result = false;
		
		switch(n) {
			case 0:{
				for(Sprite sprite: ((WallActor) event.getStage().getActors().get(3)).getSpriteArray()) {
					if(spriteArray.get(spriteArray.size - 1).getX() + getWidth() == sprite.getX() && 
					   (sprite.getY() <= spriteArray.get(spriteArray.size - 1).getY() && 
			            spriteArray.get(spriteArray.size - 1).getY() <= sprite.getY() + sprite.getHeight() - getHeight())) {
						result = true;
						break;
					}
				}
				break;
			}
			case 1:{
				for(Sprite sprite: ((WallActor) event.getStage().getActors().get(3)).getSpriteArray()) {
					if(spriteArray.get(spriteArray.size - 1).getX() - getWidth() == sprite.getX() &&
					   (sprite.getY() <= spriteArray.get(spriteArray.size - 1).getY() && 
				        spriteArray.get(spriteArray.size - 1).getY() <= sprite.getY() + sprite.getHeight() - getHeight())) {
						result = true;
						break;
					}
				}
				break;
			}
			case 2:{
				for(Sprite sprite: ((WallActor) event.getStage().getActors().get(3)).getSpriteArray()) {
					if(spriteArray.get(spriteArray.size - 1).getY() + getHeight() == sprite.getY() && 
					   (sprite.getX() <= spriteArray.get(spriteArray.size - 1).getX() && 
				        spriteArray.get(spriteArray.size - 1).getX() <= sprite.getX() + sprite.getWidth() - getWidth())) {
						result = true;
						break;
					}
				}
				break;
			}
			case 3:{
				for(Sprite sprite: ((WallActor) event.getStage().getActors().get(3)).getSpriteArray()) {
					if(spriteArray.get(spriteArray.size - 1).getY() - getHeight() == sprite.getY() && 
					   (sprite.getX() <= spriteArray.get(spriteArray.size - 1).getX() && 
					    spriteArray.get(spriteArray.size - 1).getX() <= sprite.getX() + sprite.getWidth() - getWidth())) {
						result = true;
						break;
					}
				}
				break;
			}
		}
		
		return result;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		for(Sprite sprite : spriteArray) {
			sprite.draw(batch);
		}
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}
}

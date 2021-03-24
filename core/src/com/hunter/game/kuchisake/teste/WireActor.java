package com.hunter.game.kuchisake.teste;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.hunter.game.kuchisake.TerrorGame;

public class WireActor extends Actor{
	
	Texture texture;
	
	Sprite sprite;
	
	public WireActor(float x, float y) {
		texture = new Texture("red_square.png");
		
		sprite = new Sprite(texture);
		sprite.setBounds(x, y, sprite.getWidth() / TerrorGame.SCALE, sprite.getHeight() / TerrorGame.SCALE);
		
		setBounds(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2, sprite.getWidth(), sprite.getHeight());
		
		setTouchable(Touchable.enabled);
		
		setOrigin(getWidth() / 2, getHeight() / 2);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		
		addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				//System.out.println("down");
				
				//setRotation(getRotation() + 15);
				//System.out.println(getRotation());
				
				return true;
			}
			
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				//System.out.println("up");				
				
			}
			
			public void touchDragged(InputEvent event, float x, float y, int pointer){			
				//rotateBy(getAngle(getX(), getY(), x, y));
				//sizeBy(getLineDistance(getX(), getY(), x, y), 0);
				
				if(event.getStageX() > getX()) {
					setSize(getLineDistance(getX(), getY(), event.getStageX(), event.getStageY()) + getOriginX() / 2, getHeight());
					setRotation(getAngle(getX(), getY(), event.getStageX(), event.getStageY() - getOriginY() / 2));
				}
				
				//System.out.println(event.getStageX());
			}
		});
		
		//System.out.println(getOriginX());
		//System.out.println(getOriginY());
	}
	
	float getAngle(float x1, float y1, float x2, float y2) {	
		float width = x2 - x1;
		float height = y2 - y1;
		
		float angle = (float) Math.toDegrees(Math.atan(height / width));
		
		return angle;
	}
	
	float getLineDistance(float x1, float y1, float x2, float y2) {
		float xLine = x2 - x1;
		float yLine = y2 - y1;
		
		float lineDistance = (float) Math.sqrt(Math.pow(xLine, 2) + Math.pow(yLine, 2));
		
		return lineDistance;
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.setPosition(getX(), getY());
		sprite.setSize(getWidth(), getHeight());
		sprite.setRotation(getRotation());
		sprite.draw(batch);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}
}

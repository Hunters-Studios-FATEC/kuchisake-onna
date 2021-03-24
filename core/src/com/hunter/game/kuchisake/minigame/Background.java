package com.hunter.game.kuchisake.minigame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.hunter.game.kuchisake.TerrorGame;

public class Background extends Actor{
	
	Texture texture;
	
	Sprite sprite;
	
	public Background(float x, float y) {
		texture = new Texture("black_rectangle.png");
		
		sprite = new Sprite(texture);
		sprite.setBounds(x, y, sprite.getWidth() / TerrorGame.SCALE, sprite.getHeight() / TerrorGame.SCALE);
		sprite.setAlpha(0.5f);
		
		setBounds(x, y, sprite.getWidth(), sprite.getHeight());
		
		//texture.dispose();
		
		addListener(new InputListener() {
			@Override
			public boolean mouseMoved(InputEvent event, float x, float y) {
				//System.out.println(x);
				//System.out.println(y);
				
				return true;
			}
		});
	}

	
	@Override
    public void draw(Batch batch, float alpha){
		sprite.setPosition(getX(), getY());
		sprite.draw(batch);
		
	}
	
}

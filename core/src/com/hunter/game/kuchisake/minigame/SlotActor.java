package com.hunter.game.kuchisake.minigame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.hunter.game.kuchisake.TerrorGame;

public class SlotActor extends Actor{
	
	Texture texture;
	
	Sprite sprite;
	
	public SlotActor(String img, float x, float y) {
		texture = new Texture(img);
		
		sprite = new Sprite(texture);
		sprite.setBounds(x, y, sprite.getWidth() / (TerrorGame.SCALE * 5), sprite.getHeight() / (TerrorGame.SCALE * 5));
		
		setBounds(sprite.getX() - sprite.getWidth() / 2, sprite.getY() - sprite.getHeight() / 2, 
				  sprite.getWidth(), sprite.getHeight());
		
		//texture.dispose();
	}
	
	@Override
    public void draw(Batch batch, float alpha){
		sprite.setPosition(getX(), getY());
		sprite.draw(batch);
		
	}
}

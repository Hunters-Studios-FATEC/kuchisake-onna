package com.hunter.game.kuchisake.lockpick;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.hunter.game.kuchisake.TerrorGame;

public class Correct extends Actor{
	Texture texture;
	
	Sprite sprite;
	
	public Correct(float x, float y) {
		texture = new Texture("correto.png");
		
		sprite = new Sprite(texture);
		sprite.setBounds(x, y, sprite.getWidth() / (TerrorGame.SCALE * 3), sprite.getHeight() / (TerrorGame.SCALE * 3));
		sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
		
		setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		
	}

	
	@Override
    public void draw(Batch batch, float alpha){
		sprite.draw(batch);
		
	}
}

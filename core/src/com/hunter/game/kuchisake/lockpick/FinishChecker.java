package com.hunter.game.kuchisake.lockpick;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.hunter.game.kuchisake.TerrorGame;

public class FinishChecker extends Actor{
	
	TextureRegion texture;
	Sprite sprite;
	
	public FinishChecker(float x, float y, TextureAtlas textureAtlas) {
		texture = textureAtlas.findRegion("fechadura");
		
		sprite = new Sprite(texture);
		sprite.setBounds(x, y, 36 / TerrorGame.SCALE, 48 / TerrorGame.SCALE);
		sprite.setPosition(x + 0.5f / 2 - sprite.getWidth() / 2, y + 0.5f / 2 - sprite.getHeight() / 2);
		
		setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.draw(batch);
	}
}

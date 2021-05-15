package com.hunter.game.kuchisake.WireMinigame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.hunter.game.kuchisake.TerrorGame;

public class SlotWire extends Actor{

	TextureRegion texture;

	Sprite sprite;


	public SlotWire(float x, float y, String img, TextureAtlas textureAtlas) {
		texture = textureAtlas.findRegion(img);

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

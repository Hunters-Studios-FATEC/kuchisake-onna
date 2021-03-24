package com.hunter.game.kuchisake.teste;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.hunter.game.kuchisake.TerrorGame;

public class WallActor extends Actor{
	
	Array<Sprite> spriteArray = new Array<Sprite>();
	
	public WallActor() {
		createSprite(4, 1, 100, 100);
		createSprite(3, 2, 100, 200);
		createSprite(5, 2, 100, 100);
		createSprite(4, 4, 600, 100);
		createSprite(6, 2, 300, 100);
		createSprite(10, 2, 100, 200);
		createSprite(8, 1, 100, 100);
		createSprite(9, 0, 800, 100);
		createSprite(11, 2, 600, 100);
		createSprite(17, 1, 100, 100);
	}
	
	void createSprite(float x, float y, float width, float height) {
		Texture texture = new Texture("gray_square.png");
		Sprite sprite = new Sprite(texture);
		
		sprite.setBounds(x, y, width / TerrorGame.SCALE, height / TerrorGame.SCALE);
		
		spriteArray.add(sprite);
	}
	
	public Array<Sprite> getSpriteArray() {
		return spriteArray;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		for(Sprite sprite : spriteArray) {
			sprite.draw(batch);
		}	
	}
}

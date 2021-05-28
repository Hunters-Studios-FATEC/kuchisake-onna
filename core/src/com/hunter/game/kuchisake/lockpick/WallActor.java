package com.hunter.game.kuchisake.lockpick;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.hunter.game.kuchisake.TerrorGame;

public class WallActor extends Actor{
	
	Array<Sprite> spriteArray;

	TextureRegion textureRegion;

	public WallActor(TextureAtlas textureAtlas) {
		spriteArray = new Array<Sprite>();

		textureRegion = textureAtlas.findRegion("dark_gray_square");
		
		createSprite(2.85f, 6.4f, 50, 50 * 3); // parede 0
		createSprite(3.35f, 7.4f, 50 * 7, 50); // parede 1
		createSprite(3.35f, 6.4f, 50 * 9, 50); // parede 2
		createSprite(6.85f, 7.4f, 50, 50 * 5); // parede 5
		createSprite(7.35f, 9.4f, 50 * 4, 50); // parede 6
		createSprite(7.85f, 4.9f, 50, 50 * 5); // parede 3
		createSprite(7.85f, 7.9f, 50, 50 * 2); // parede 4
		createSprite(8.35f, 6.9f, 50 * 5, 50); // parede 14
		createSprite(8.35f, 7.9f, 50 * 8, 50); // parede 11
		createSprite(8.85f, 8.9f, 50 * 6, 50); // parede 8
		createSprite(11.35f, 9.4f, 50 * 9, 50); // parede 10
		createSprite(12.35f, 7.9f, 50, 50 * 2); // parede 12
		createSprite(12.85f, 8.4f, 50 * 6, 50); // parede 13
		createSprite(11.35f, 5.9f, 50, 50 * 4); // parede 15
		createSprite(8.85f, 5.9f, 50 * 5, 50); // parede 16
		createSprite(8.35f, 4.9f, 50 * 8, 50); // parede 17
		createSprite(12.35f, 4.9f, 50, 50 * 5); // parede 18
		createSprite(12.85f, 6.9f, 50 * 2, 50); // parede 19
		createSprite(13.35f, 7.4f, 50 * 5, 50); // parede 9
		createSprite(15.85f, 7.4f, 50, 50 * 5); // parede 7
		
	}
	
	void createSprite(float x, float y, float width, float height) {

		Sprite sprite = new Sprite(textureRegion);
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

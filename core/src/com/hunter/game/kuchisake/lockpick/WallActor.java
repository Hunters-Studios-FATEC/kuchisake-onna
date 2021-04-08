package com.hunter.game.kuchisake.lockpick;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.hunter.game.kuchisake.TerrorGame;

public class WallActor extends Actor{
	
	Array<Sprite> spriteArray;
	//Array<Array<Sprite>> adjWallsArray;
	
	public WallActor() {
		spriteArray = new Array<Sprite>();
		//adjWallsArray = new Array<Array<Sprite>>();
		
		createSprite(2.85f, 4.4f, 50, 50 * 3); // parede 0
		createSprite(3.35f, 5.4f, 50 * 7, 50); // parede 1
		createSprite(3.35f, 4.4f, 50 * 9, 50); // parede 2
		createSprite(6.85f, 5.4f, 50, 50 * 5); // parede 5
		createSprite(7.35f, 7.4f, 50 * 4, 50); // parede 6
		createSprite(7.85f, 2.9f, 50, 50 * 5); // parede 3
		createSprite(7.85f, 5.9f, 50, 50 * 2); // parede 4
		createSprite(8.35f, 4.9f, 50 * 5, 50); // parede 14
		createSprite(8.35f, 5.9f, 50 * 8, 50); // parede 11
		createSprite(8.85f, 6.9f, 50 * 6, 50); // parede 8
		createSprite(11.35f, 7.4f, 50 * 9, 50); // parede 10
		createSprite(12.35f, 5.9f, 50, 50 * 2); // parede 12
		createSprite(12.85f, 6.4f, 50 * 6, 50); // parede 13
		createSprite(11.35f, 3.9f, 50, 50 * 4); // parede 15
		createSprite(8.85f, 3.9f, 50 * 5, 50); // parede 16
		createSprite(8.35f, 2.9f, 50 * 8, 50); // parede 17
		createSprite(12.35f, 2.9f, 50, 50 * 4); // parede 18
		createSprite(12.35f, 4.9f, 50 * 3, 50); // parede 19
		createSprite(13.35f, 5.4f, 50 * 5, 50); // parede 9
		createSprite(15.85f, 5.4f, 50, 50 * 5); // parede 7
		
		/*addAdjacentWalls(getSprite(1), getSprite(2));
		addAdjacentWalls(getSprite(0), getSprite(3));
		addAdjacentWalls(getSprite(0), getSprite(4));
		addAdjacentWalls(getSprite(1), getSprite(1));
		addAdjacentWalls(getSprite(2), getSprite(5));
		addAdjacentWalls(getSprite(4), getSprite(4));*/
	}
	
	void createSprite(float x, float y, float width, float height) {
		Texture texture = new Texture("dark_gray_square.png");
		
		Sprite sprite = new Sprite(texture);
		sprite.setBounds(x, y, width / TerrorGame.SCALE, height / TerrorGame.SCALE);
		
		spriteArray.add(sprite);
	}
	
	/*void addAdjacentWalls(Sprite adjWall1, Sprite adjWall2){
		Array<Sprite> adjacentWalls = new Array<Sprite>();
		
		adjacentWalls.add(adjWall1, adjWall2);
		
		adjWallsArray.add(adjacentWalls);
	}*/
	
	public Array<Sprite> getSpriteArray() {
		return spriteArray;
	}
	
	/*public Array<Array<Sprite>> getAdjWallsArray() {
		return adjWallsArray;
	}*/
	
	/*Sprite getSprite(int index) {
		return spriteArray.get(index);
	}*/
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		for(Sprite sprite : spriteArray) {
			sprite.draw(batch);
		}
	}
}

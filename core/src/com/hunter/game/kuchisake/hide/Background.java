package com.hunter.game.kuchisake.hide;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.hunter.game.kuchisake.TerrorGame;


public class Background extends Actor{
	
	TextureRegion texture;
	Sprite sprite;
	
	AlphaAction alphaAction;
	
	public Background(float x, float y, TextureAtlas textureAtlas) {
		texture = textureAtlas.findRegion("black_rectangle");
		
		sprite = new Sprite(texture);
		setColor(0, 0, 0, 0.5f);
		
		sprite.setBounds(x, y, sprite.getWidth() / TerrorGame.SCALE, sprite.getHeight() / TerrorGame.SCALE);
		
		alphaAction = new AlphaAction();
	}
	
	public void setSolidColor() {
		alphaAction.setAlpha(1f);
		alphaAction.setDuration(0.2f);
		addAction(alphaAction);
	}
	
	public void fadeOut() {
		alphaAction.reset();
		alphaAction.setAlpha(0);
		alphaAction.setDuration(0.2f);
		addAction(alphaAction);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.setAlpha(getColor().a);
		sprite.draw(batch);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}
}

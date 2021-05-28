package com.hunter.game.kuchisake.minigameGerador;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.hunter.game.kuchisake.TerrorGame;

public class Background extends Actor{
	TextureRegion texture;
	
	Sprite sprite;
	
	public Background(float x, float y, TextureAtlas textureAtlas) {
		texture = textureAtlas.findRegion("black_rectangle");
		
		sprite = new Sprite(texture);
		sprite.setBounds(x, y, sprite.getWidth() / TerrorGame.SCALE, sprite.getHeight() / TerrorGame.SCALE);
		sprite.setAlpha(0.5f);
		
		setBounds(x, y, sprite.getWidth(), sprite.getHeight());
		
	}
	
	
	@Override
    public void draw(Batch batch, float alpha){
		sprite.setPosition(getX(), getY());
		sprite.draw(batch);
		
	}
}

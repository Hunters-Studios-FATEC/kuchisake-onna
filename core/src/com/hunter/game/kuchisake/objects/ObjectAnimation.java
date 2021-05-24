package com.hunter.game.kuchisake.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.hunter.game.kuchisake.screen.StandardRoom;

public class ObjectAnimation extends Thread {
	
	Animation<TextureRegion> animation;
	
	Sprite sprite;
	
	float frameChangeTimer = 0;
	float frameCount;
	
	public ObjectAnimation(float frameDuration, TextureRegion[] frames) {
		animation = new Animation<TextureRegion>(frameDuration, addFrames(frames));
		
		frameCount = frames.length;
	}
	
	Array<TextureRegion> addFrames(TextureRegion[] frames) {
		Array<TextureRegion> framesArray = new Array<TextureRegion>();
		
		for(TextureRegion frame : frames) {
			framesArray.add(frame);
		}
		
		return framesArray;
	}
	
	public TextureRegion changeFrame(float delta){
        TextureRegion textureRegion;
        textureRegion = animation.getKeyFrame(frameChangeTimer, true);
        
        frameChangeTimer += delta;
        
        if(frameChangeTimer >= animation.getFrameDuration() * frameCount) {
        	frameChangeTimer -= animation.getFrameDuration() * frameCount;
        }
        
        return textureRegion;
    }
}

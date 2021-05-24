package com.hunter.game.kuchisake.WireMinigame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;

public class WireMinigame {
	
	//float w = Gdx.graphics.getWidth();
	//float h = Gdx.graphics.getHeight();
	
	FitViewport viewport;
	
	public Stage stage;

	TextureRegion descTexture;
	Sprite descSprite;
	Actor description;

	Background background;
	WireActor wireActor1;
	WireActor wireActor2;
	WireActor wireActor3;
	SlotWire wireSlot1;
	SlotWire wireSlot2;
	SlotWire wireSlot3;
	Correct correct;

	boolean isFinished = false;

	TextureAtlas textureAtlas;
	TextureAtlas descriptionAtlas;

	public WireMinigame(SpriteBatch spriteBatch, TextureAtlas textureAtlas, final TextureAtlas descriptionAtlas) {
		viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE, 
								   new OrthographicCamera());
		viewport.apply();

		this.textureAtlas = textureAtlas;
		this.descriptionAtlas = descriptionAtlas;

		stage = new Stage(viewport, spriteBatch);

		descTexture = descriptionAtlas.findRegion("fios");
		descSprite = new Sprite(descTexture);
		descSprite.setSize( 17.64f, 5.88f);
		descSprite.setPosition((viewport.getWorldWidth() / 2) - (descSprite.getWidth() / 2), (viewport.getWorldHeight() / 2) - (descSprite.getHeight()/2) + 3);
		description = new Actor(){
			@Override
			public void draw(Batch batch, float parentAlpha) {
				descSprite.draw(batch);
			}
		};
		background = new Background(0, 0, textureAtlas);
		wireActor1 = new WireActor(1, 5, "red_square", 6, textureAtlas);
		wireActor2 = new WireActor(1, 3, "green_square", 4, textureAtlas);
		wireActor3 = new WireActor(1, 1, "blue_square", 5, textureAtlas);

		wireSlot1 = new SlotWire(18,5,"green_square", textureAtlas);
		wireSlot2 = new SlotWire(18,3,"blue_square", textureAtlas);
		wireSlot3 = new SlotWire(18,1,"red_square", textureAtlas);

		correct = new Correct(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, textureAtlas);

		correct.setVisible(false);
	}

	public void startMinigame(){
		Gdx.input.setInputProcessor(stage);

		wireActor1 = new WireActor(1, 5, "red_square", 6, textureAtlas);
		wireActor2 = new WireActor(1, 3, "green_square", 4, textureAtlas);
		wireActor3 = new WireActor(1, 1, "blue_square", 5, textureAtlas);

		stage.addActor(background);
		stage.addActor(wireActor1);
		stage.addActor(wireActor2);
		stage.addActor(wireActor3);
		stage.addActor(wireSlot1);
		stage.addActor(wireSlot2);
		stage.addActor(wireSlot3);
		stage.addActor(correct);
		stage.addActor(description);
	}

	public void verifyWires(){
		int accept_count = 0;

		for(int i = 1; i < 4; i++){
			if(!((WireActor) stage.getActors().get(i)).getAccept_input()){
				accept_count++;
			}
		}

		if (accept_count == 3){
			correct.setVisible(true);
			isFinished = true;
		}
	}


	public void closeMinigame() {
		stage.clear();
	}

	public boolean getIsFinished() {
		return isFinished;
	}

	public Stage getStage(){
		return stage;
	}


}

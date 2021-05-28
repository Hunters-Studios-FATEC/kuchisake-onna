package com.hunter.game.kuchisake.minigame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;

public class MinigameBook {
	
	float w = Gdx.graphics.getWidth();
	float h = Gdx.graphics.getHeight();
	
	FitViewport viewport;
	
	public Stage stage;
	
	int password = 0;
	boolean isFinished = false;
	
	Background background;
	
	SlotActor slotActor1;
	SlotActor slotActor2;
	SlotActor slotActor3;
	
	BookActor bookActor1;
	BookActor bookActor2;
	BookActor bookActor3;
	
	Correct correctActor;

	TextureAtlas textureAtlas;
	
	Actor description;
	Sprite descSprite;
	
	public MinigameBook(SpriteBatch spriteBatch, TextureAtlas textureAtlas, TextureAtlas descriptionAtlas) {
		viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE,
								   new OrthographicCamera());
		viewport.apply();

		this.textureAtlas = textureAtlas;
		stage = new Stage(viewport, spriteBatch);
		
		background = new Background(0, 0, textureAtlas);
		
		correctActor = new Correct(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, textureAtlas);
		correctActor.setVisible(false);
		
		slotActor1 = new SlotActor("espaco_vazio", viewport.getWorldWidth() / 2 - 3, viewport.getWorldHeight() / 2, textureAtlas);
		slotActor2 = new SlotActor("espaco_vazio", viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, textureAtlas);
		slotActor3 = new SlotActor("espaco_vazio", viewport.getWorldWidth() / 2 + 3, viewport.getWorldHeight() / 2, textureAtlas);
		
		bookActor1 = new BookActor("livro1", 0, 0, 1, textureAtlas);
		bookActor2 = new BookActor("livro2", 0, 2, 2, textureAtlas);
		bookActor3 = new BookActor("livro3", 0, 4, 3, textureAtlas);
		
		descSprite = new Sprite(descriptionAtlas.findRegion("estante"));
		descSprite.setSize(17.64f, 5.88f);
		descSprite.setPosition(viewport.getWorldWidth() / 2 - descSprite.getWidth() / 2, 
				viewport.getWorldHeight() / 2 - descSprite.getHeight() / 2 - 3);
		
		description = new Actor() {
			@Override
			public void draw(Batch batch, float parentAlpha) {
				descSprite.draw(batch);
			}
		};
		
	}
	
	public void startMinigame() {
		password = 0;

		Gdx.input.setInputProcessor(stage);

		bookActor1 = new BookActor("livro1", 0, 0, 1, textureAtlas);
		bookActor2 = new BookActor("livro2", 0, 2, 2, textureAtlas);
		bookActor3 = new BookActor("livro3", 0, 4, 3, textureAtlas);

		stage.addActor(background);
		
		stage.addActor(description);
		
		stage.addActor(correctActor);
		
		stage.addActor(slotActor1);
		stage.addActor(slotActor2);
		stage.addActor(slotActor3);
		
		stage.addActor(bookActor1);
		stage.addActor(bookActor2);
		stage.addActor(bookActor3);
	}
	
	public void closeMinigame() {
		stage.clear();
	}
	
	int slicePassword(int slice) {
		// 2 : Casa da centena
		// 3 : Casa da dezena
		// 4 : Casa da unidade
		
		int newPassword = 0;
		
		switch(slice) {
			case 2:{
				newPassword = (password - (password % 100));
				break;
			}
			case 3:{
				newPassword = ((password % 100) / 10) * 10;
				break;
			}
			case 4:{
				newPassword = (password % 100) % 10;
				break;
			}
		}
		
		return newPassword;
	}
	
	public boolean getIsFinished() {
		return isFinished;
	}
	
	public void verifyActorPos() {
		if(!isFinished) {
			BookActor actor = (BookActor) stage.getActors().get(8);
			
			if(actor.getStoppedDragging()) {
				actor.setStoppedDragging(false);
				
				float posX = actor.getX() + actor.getWidth() / 2;
				float posY = actor.getY() + actor.getHeight() / 2;
				
				for(int i = 3; i < 6; i++) {
					SlotActor sltActor = (SlotActor) stage.getActors().get(i);
					
					// 215 / 100 = 2
					// 215 % 100 = 15;
					// 15 / 10 = 1;
					// 15 % 10 = 5;
					
					//530
					
					int slotIndex = i - 1;
					
					if((posX > sltActor.getX() && posX < (sltActor.getX() + sltActor.getWidth())) &&
					   (posY > sltActor.getY() && posY < (sltActor.getY() + sltActor.getHeight()))) {
						int passwordSlice = slicePassword(slotIndex);
						
						if(passwordSlice / (Math.pow(10, 2 + (2 - slotIndex))) != actor.getValue() && passwordSlice > 0){
							int bookActorValue = (int) (passwordSlice / (Math.pow(10, 2 + (2 - slotIndex))));
							
							password -= passwordSlice;
							
							int bookActorIndex = (((BookActor) stage.getActors().get(6)).getValue() == bookActorValue)? 6 : 7;
							
							((BookActor) stage.getActors().get(bookActorIndex)).setPos(
									((BookActor) stage.getActors().get(bookActorIndex)).getInitialPos()[0], 
									((BookActor) stage.getActors().get(bookActorIndex)).getInitialPos()[1]);
							
						}
						else if(passwordSlice / (Math.pow(10, 2 + (2 - slotIndex))) == actor.getValue()) {
							password -= passwordSlice;
						}
						
						actor.setPos(sltActor.getX(), sltActor.getY());
						password += actor.getValue() * (Math.pow(10, 2 + (2 - slotIndex)));
					}
					else {
						if(slicePassword(slotIndex) / (Math.pow(10, 2 + (2 - slotIndex))) == actor.getValue()){				
							password -= slicePassword(slotIndex);
						}
					}
				}
				
				if(password == 123) {
					correctActor.setVisible(true);
					correctActor.toFront();
					isFinished = true;
				}
			}
		}
	}

	public Stage getStage(){
		return stage;
	}
}

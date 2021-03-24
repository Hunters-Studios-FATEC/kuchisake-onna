package com.hunter.game.kuchisake.minigame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	
	public MinigameBook(SpriteBatch spriteBatch) {	
		viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT * (h / w) / TerrorGame.SCALE, 
								   new OrthographicCamera());
		viewport.apply();
		
		//camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
		//camera.update();
		
		stage = new Stage(viewport, spriteBatch);
		Gdx.input.setInputProcessor(stage);
		
		background = new Background(0, 0);
		
		correctActor = new Correct(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2);
		correctActor.setVisible(false);
		
		slotActor1 = new SlotActor("espaco_vazio.png", viewport.getWorldWidth() / 2 - 3, viewport.getWorldHeight() / 2);
		slotActor2 = new SlotActor("espaco_vazio.png", viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2);
		slotActor3 = new SlotActor("espaco_vazio.png", viewport.getWorldWidth() / 2 + 3, viewport.getWorldHeight() / 2);
		
		bookActor1 = new BookActor("livro1.png", 0, 0, 1);
		bookActor2 = new BookActor("livro2.png", 0, 2, 2);
		bookActor3 = new BookActor("livro3.png", 0, 4, 3);
		
		/*stage.addActor(background);
		
		stage.addActor(correctActor);
		
		stage.addActor(slotActor1);
		stage.addActor(slotActor2);
		stage.addActor(slotActor3);
		
		stage.addActor(bookActor1);
		stage.addActor(bookActor2);
		stage.addActor(bookActor3);*/
		
	}
	
	public void startMinigame() {
		stage.addActor(background);
		
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
		
		//System.out.println(newPassword);
		
		return newPassword;
	}
	
	public boolean getIsFinished() {
		return isFinished;
	}
	
	public void verifyActorPos() {
		if(!isFinished) {
			BookActor actor = (BookActor) stage.getActors().get(7);
			
			if(actor.getStoppedDragging()) {
				actor.setStoppedDragging(false);
				
				float posX = actor.getX() + actor.getWidth() / 2;
				float posY = actor.getY() + actor.getHeight() / 2;
				
				for(int i = 2; i < 5; i++) {
					SlotActor sltActor = (SlotActor) stage.getActors().get(i);
					
					// 215 / 100 = 2
					// 215 % 100 = 15;
					// 15 / 10 = 1;
					// 15 % 10 = 5;
					
					//530
					
					if((posX > sltActor.getX() && posX < (sltActor.getX() + sltActor.getWidth())) &&
					   (posY > sltActor.getY() && posY < (sltActor.getY() + sltActor.getHeight()))) {
						int passwordSlice = slicePassword(i);
						
						if(passwordSlice / (Math.pow(10, 2 + (2 - i))) != actor.getValue() && passwordSlice > 0){
							int bookActorValue = (int) (passwordSlice / (Math.pow(10, 2 + (2 - i))));
							
							password -= passwordSlice;
							
							int bookActorIndex = (((BookActor) stage.getActors().get(5)).getValue() == bookActorValue)? 5 : 6;
							
							((BookActor) stage.getActors().get(bookActorIndex)).setPos(
									((BookActor) stage.getActors().get(bookActorIndex)).getInitialPos()[0], 
									((BookActor) stage.getActors().get(bookActorIndex)).getInitialPos()[1]);
							
						}
						else if(passwordSlice / (Math.pow(10, 2 + (2 - i))) == actor.getValue()) {
							password -= passwordSlice;
						}
						
						actor.setPos(sltActor.getX(), sltActor.getY());
						password += actor.getValue() * (Math.pow(10, 2 + (2 - i)));
						//System.out.println(password);
					}
					else {
						if(slicePassword(i) / (Math.pow(10, 2 + (2 - i))) == actor.getValue()){				
							password -= slicePassword(i);
							//System.out.println(password);
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
}

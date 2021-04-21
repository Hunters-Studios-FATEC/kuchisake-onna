package com.hunter.game.kuchisake.WireMinigame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;

public class WireMinigame {
	
	//float w = Gdx.graphics.getWidth();
	//float h = Gdx.graphics.getHeight();
	
	FitViewport viewport;
	
	public Stage stage;
	
	Background background;
	WireActor wireActor1;
	WireActor wireActor2;
	WireActor wireActor3;
	SlotWire wireSlot1;
	SlotWire wireSlot2;
	SlotWire wireSlot3;
	Correct correct;

	boolean isFinished = false;

	public WireMinigame(SpriteBatch spriteBatch) {
		viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE, 
								   new OrthographicCamera());
		viewport.apply();
		
		stage = new Stage(viewport, spriteBatch);
		
		background = new Background(0, 0);
		wireActor1 = new WireActor(1, 5, "red_square.png", 6);
		wireActor2 = new WireActor(1, 3, "green_square.png", 4);
		wireActor3 = new WireActor(1, 1, "blue_square.png", 5);

		wireSlot1 = new SlotWire(18,5,"green_wire_slot.png");
		wireSlot2 = new SlotWire(18,3,"blue_wire_slot.png");
		wireSlot3 = new SlotWire(18,1,"red_wire_slot.png");

		correct = new Correct(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2);

		correct.setVisible(false);
	}

	public void startMinigame(){
		Gdx.input.setInputProcessor(stage);

		wireActor1 = new WireActor(1, 5, "red_square.png", 6);
		wireActor2 = new WireActor(1, 3, "green_square.png", 4);
		wireActor3 = new WireActor(1, 1, "blue_square.png", 5);

		stage.addActor(background);
		stage.addActor(wireActor1);
		stage.addActor(wireActor2);
		stage.addActor(wireActor3);
		stage.addActor(wireSlot1);
		stage.addActor(wireSlot2);
		stage.addActor(wireSlot3);
		stage.addActor(correct);
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

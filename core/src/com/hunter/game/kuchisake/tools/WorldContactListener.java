package com.hunter.game.kuchisake.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.hunter.game.kuchisake.objects.Player;
import com.hunter.game.kuchisake.screen.Screen;

public class WorldContactListener implements ContactListener{

	Screen screen;
	MinigameManager minigameManager;
	Player player;

	public WorldContactListener(MinigameManager minigameManager){
		this.minigameManager = minigameManager;
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		if("player sensor".equals(fixA.getUserData()) || "player sensor".equals(fixB.getUserData())) {
			Fixture sensor = ("player sensor".equals(fixA.getUserData()))? fixA : fixB;
			Fixture object = (sensor.equals(fixA))? fixB: fixA;
			
			minigameManager.setCanStartMinigame(true);

			if (object.getUserData().equals("esconde")) {
				player.setminigameID(0);
			} else if (object.getUserData().equals("lockpick")){
					player.setminigameID(1);
			} else if(object.getUserData().equals("bookshelf")){
				player.setminigameID(2);
			} else if (object.getUserData().equals("fios")){
				player.setminigameID(3);
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		if("player sensor".equals(fixA.getUserData()) || "player sensor".equals(fixB.getUserData())) {
			Fixture sensor = ("player sensor".equals(fixA.getUserData()))? fixA : fixB;
			Fixture object = (sensor.equals(fixA))? fixB: fixA;
			
			minigameManager.setCanStartMinigame(false);
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
}

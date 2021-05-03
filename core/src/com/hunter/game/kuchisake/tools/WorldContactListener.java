package com.hunter.game.kuchisake.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.hunter.game.kuchisake.objects.Player;
import com.hunter.game.kuchisake.screen.StandardRoom;

public class WorldContactListener implements ContactListener{

	MinigameManager minigameManager;
	Player player;
	StandardRoom standardRoom;

	public WorldContactListener(MinigameManager minigameManager, Player player, StandardRoom standardRoom){
		// parametro player criado e atribuido a variavel global player.
		this.minigameManager = minigameManager;
		this.player = player;
		this.standardRoom = standardRoom;
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		if("player sensor".equals(fixA.getUserData()) || "player sensor".equals(fixB.getUserData())) {
			Fixture sensor = ("player sensor".equals(fixA.getUserData())) ? fixA : fixB;
			Fixture object = (sensor.equals(fixA)) ? fixB : fixA;

			if (object.getUserData().equals("esconde")) {
				minigameManager.setCanStartMinigame(true);
				System.out.println("ESCONDE");
				player.setminigameID(0);
			} else if (object.getUserData().equals("lockpick")) {
				minigameManager.setCanStartMinigame(true);
				System.out.println("LOCKPICK");
				player.setminigameID(1);
			} else if (object.getUserData().equals("bookshelf")) {
				minigameManager.setCanStartMinigame(true);
				System.out.println("BOOKSHELF");
				player.setminigameID(2);
			} else if (object.getUserData().equals("fios")) {
				minigameManager.setCanStartMinigame(true);
				System.out.println("FIOS");
				player.setminigameID(3);
			} else if (object.getUserData().equals("gerador")) {
				minigameManager.setCanStartMinigame(true);
				System.out.println("GERADOR");
				player.setminigameID(4);
			} else if (object.getUserData().toString().contains("door")) {
				player.setTouchingDoor(true);
				System.out.println("porrtaaaa");
				if (object.getUserData().toString().contains("Up")){
					int roomN = Integer.parseInt(object.getUserData().toString().substring("doorUp".length()));
					standardRoom.setChangeRoom("doorUp", roomN);
				}
				if (object.getUserData().toString().contains("Down")){
					int roomN = Integer.parseInt(object.getUserData().toString().substring("doorDown".length()));
					standardRoom.setChangeRoom("doorDown", roomN);
				}
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
			player.setTouchingDoor(false);
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

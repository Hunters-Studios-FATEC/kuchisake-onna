package com.hunter.game.kuchisake.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.objects.Player;
import com.hunter.game.kuchisake.screen.StandardRoom;

public class WorldContactListener implements ContactListener{

	MinigameManager minigameManager;
	Player player;
	StandardRoom standardRoom;
	InventoryManager inventoryManager;

	public WorldContactListener(MinigameManager minigameManager, Player player, StandardRoom standardRoom, InventoryManager inventoryManager){
		// parametro player criado e atribuido a variavel global player.
		this.minigameManager = minigameManager;
		this.player = player;
		this.standardRoom = standardRoom;
		this.inventoryManager = inventoryManager;
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
				if (inventoryManager.getItemBackpack().contains("gazua", false)){
					minigameManager.setCanStartMinigame(true);
					System.out.println("LOCKPICK");
					player.setminigameID(1);
				} else {
					minigameManager.wrongSound.play(0.5f);
				}
			} else if (object.getUserData().equals("bookshelf")) {
				if (inventoryManager.getItemBackpack().contains("livro1", false) &&
				inventoryManager.getItemBackpack().contains("livro2", false) &&
				inventoryManager.getItemBackpack().contains("livro3", false)){
					minigameManager.setCanStartMinigame(true);
					System.out.println("BOOKSHELF");
					player.setminigameID(2);
				} else {
					minigameManager.wrongSound.play(0.5f);
				}

			} else if (object.getUserData().equals("fios") ) {
				if (inventoryManager.getItemBackpack().contains("fiosItem", false)){
					minigameManager.setCanStartMinigame(true);
					System.out.println("FIOS");
					player.setminigameID(3);
				} else{
					minigameManager.wrongSound.play(0.5f);
				}

			} else if (object.getUserData().equals("gerador")) {
				if (minigameManager.getWireCompleted()){
					minigameManager.setCanStartMinigame(true);
					System.out.println("GERADOR");
					player.setminigameID(4);
				} else {
					minigameManager.wrongSound.play(0.5f);
				}
			} else if (object.getUserData().toString().contains("door")) {
				player.setTouchingDoor(true);
				if (object.getUserData().toString().contains("Up")){
					int roomN = Integer.parseInt(object.getUserData().toString().substring("doorUp".length()));
					standardRoom.setChangeRoom("doorUp", roomN);
				}
				else if (object.getUserData().toString().contains("Down")){
					int roomN = Integer.parseInt(object.getUserData().toString().substring("doorDown".length()));
					standardRoom.setChangeRoom("doorDown", roomN);
				}
			} else if (object.getUserData().equals("objetoMundo")){
				player.setCanInteractWorld(true);
			} else if (object.getUserData().equals("fiosItem")){
				inventoryManager.setCanCollectItem(true);
				player.setItemName("fiosItem");
			} else if (object.getUserData().equals("chaveServico")){
				inventoryManager.setCanCollectItem(true);
				player.setItemName("chaveServico");
			} else if (object.getUserData().equals("gazua")){
				inventoryManager.setCanCollectItem(true);
				player.setItemName("gazua");
			} else if (object.getUserData().equals("livro1")){
				inventoryManager.setCanCollectItem(true);
				player.setItemName("livro1");
			} else if (object.getUserData().equals("livro2")){
				inventoryManager.setCanCollectItem(true);
				player.setItemName("livro2");
			} else if (object.getUserData().equals("livro3")){
				inventoryManager.setCanCollectItem(true);
				player.setItemName("livro3");
			} else if (object.getUserData().equals("extintor")){
				inventoryManager.setCanCollectItem(true);
				player.setItemName("extintor");
			} else if (object.getUserData().equals("mask1")){
				inventoryManager.setCanCollectItem(true);
				player.setItemName("mask1");
			} else if (object.getUserData().equals("mask2")) {
				inventoryManager.setCanCollectItem(true);
				player.setItemName("mask2");
			} else if (object.getUserData().equals("mask3")) {
				inventoryManager.setCanCollectItem(true);
				player.setItemName("mask3");
			} else if (object.getUserData().equals("mask4")) {
				inventoryManager.setCanCollectItem(true);
				player.setItemName("mask4");
			} else if (object.getUserData().equals("chaveBiblio")){
				inventoryManager.setCanCollectItem(true);
				player.setItemName("chaveBiblio");
			} else if (object.getUserData().equals("cachorro")){
				inventoryManager.setCanCollectItem(true);
				player.setItemName("cachorro");
			} else if (object.getUserData().equals("chavePrincipal")){
				inventoryManager.setCanCollectItem(true);
				player.setItemName("chavePrincipal");
			} else if (object.getUserData().equals("chavePorao")){
				inventoryManager.setCanCollectItem(true);
				player.setItemName("chavePorao");
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
			player.setCanInteractWorld(false);
			inventoryManager.setCanCollectItem(false);

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

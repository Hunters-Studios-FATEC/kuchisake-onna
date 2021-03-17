package com.hunter.game.kuchisake.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.hunter.game.kuchisake.screen.Screen;

public class WorldContactListener implements ContactListener{

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		if("player sensor".equals(fixA.getUserData()) || "player sensor".equals(fixB.getUserData())) {
			Fixture sensor = ("player sensor".equals(fixA.getUserData()))? fixA : fixB;
			Fixture object = (sensor.equals(fixA))? fixB: fixA;
			
			Screen.setCanStartMinigame(true);
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
			
			Screen.setCanStartMinigame(false);
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

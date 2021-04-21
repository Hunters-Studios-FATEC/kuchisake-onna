package com.hunter.game.kuchisake.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.screen.Screen;
import com.hunter.game.kuchisake.tools.MinigameManager;

public class Player {
    Body player;
    BodyDef bodyDef;

    FixtureDef fixtureDef;
    Fixture fixture;

    PolygonShape polygonShape;

    final float MAX_VELOCITY = 3.5f;
    int minigameID = -1;

    EdgeShape collisionSensor;

    MinigameManager minigameManager;

    public Player(World world, MinigameManager minigameManager) {
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        polygonShape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(1000 / TerrorGame.SCALE, 352 / TerrorGame.SCALE);
        player = world.createBody(bodyDef);

        polygonShape.setAsBox(128 / TerrorGame.SCALE, 128 / TerrorGame.SCALE);
        fixtureDef.shape = polygonShape;

        fixtureDef.filter.categoryBits = Collisions.PLAYER_BIT;
        fixtureDef.filter.maskBits = Collisions.GROUND_BIT;

        fixture = player.createFixture(fixtureDef);

        fixtureDef = new FixtureDef();

        collisionSensor = new EdgeShape();
        collisionSensor.set(new Vector2(-64 / TerrorGame.SCALE, 0), new Vector2(64 / TerrorGame.SCALE, 0));

        fixtureDef.shape = collisionSensor;
        fixtureDef.isSensor = true;
        
        //definido o categoryBits (identificador da fixture do sensor)
        fixtureDef.filter.categoryBits = Collisions.PLAYER_BIT;
        //definido o maskBits (identificador das colisoes que esse sensor detecta)
        fixtureDef.filter.maskBits = Collisions.HIDE_BIT + Collisions.LOCKPICK_BIT + Collisions.SHELF_BIT + Collisions.WIRE_BIT + Collisions.GERADOR_BIT;

        fixture = player.createFixture(fixtureDef);
        fixture.setUserData("player sensor");
        
        //parametro minigameManager criado e atribuido a variavel global minigameManager.
        this.minigameManager = minigameManager;
    }

    public void handleInput() {
        if (!minigameManager.getIsMinigameActive()){
            if (Gdx.input.isKeyPressed(Input.Keys.D) && player.getLinearVelocity().x < MAX_VELOCITY) {
                player.applyLinearImpulse(new Vector2(0.5f, 0), player.getWorldCenter(), true);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.A) && player.getLinearVelocity().x > -MAX_VELOCITY) {
                player.applyLinearImpulse(new Vector2(-0.5f, 0), player.getWorldCenter(), true);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E) && minigameManager.getCanStartMinigame()) {
            if (minigameManager.getActors(minigameID).size == 0) {
                minigameManager.setMinigameActive(true);
                minigameManager.startMinigame(minigameID);
            } else {
                minigameManager.closeMinigame(minigameID);
            }
        }
    }

    public void setminigameID(int minigameID) {
        this.minigameID = minigameID;
    }

    public Body getBody() {
        return player;
    }

    public PolygonShape getPolygonShape() {
        return polygonShape;
    }
}

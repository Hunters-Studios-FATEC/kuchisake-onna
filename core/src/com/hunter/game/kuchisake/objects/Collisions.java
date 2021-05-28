package com.hunter.game.kuchisake.objects;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.hunter.game.kuchisake.TerrorGame;

import javax.swing.Spring;

public class Collisions {

    final short GROUND_BIT = 4;
    final short SHELF_BIT = 8;
    final short HIDE_BIT = 16;
    final short WIRE_BIT = 32;
    final short LOCKPICK_BIT = 64;
    final short GERADOR_BIT = 128;
    final short PORTA_BIT = 256;
    final short ITEM_BIT = 512;
    final short INTERACTIBLE_BIT = 1024;

    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    MapProperties mapProperties;

    Body shelf;
    Body body1;
    Body body2;
    Body body3;
    Body body4;

    Body invisible_wall;
    World world;
    
    TerrorGame game;

    public Collisions(World world, String mapa_path, TerrorGame game) {
        this.world = world;
        this.game = game;

        TmxMapLoader mapLoader = new TmxMapLoader();
        map = game.getAssetManager().get(mapa_path, TiledMap.class);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / TerrorGame.SCALE);
        
        mapProperties = map.getProperties();

    }

    public void CreateCollisions(float posX,float posY, String collision_tag, float sprite_width, short category_bits){
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape porygonShape = new PolygonShape();
        Body body;

        //Pos Y padrao - 425
        bodyDef.position.set(posX / TerrorGame.SCALE, (posY + 128)  / TerrorGame.SCALE);
        porygonShape.setAsBox(sprite_width / TerrorGame.SCALE, 128 / TerrorGame.SCALE);
        body = world.createBody(bodyDef);
        fixtureDef.shape = porygonShape;
        fixtureDef.filter.categoryBits = category_bits;
        fixtureDef.filter.maskBits = game.getPlayerBit();
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(collision_tag);
        
        porygonShape.dispose();
    }

    public TiledMap getMap() {
        return map;
    }

    public OrthogonalTiledMapRenderer getMapRenderer() {
        return mapRenderer;
    }
    
    public MapProperties getMapProperties() {
    	return mapProperties;
    }

    public short getGroundBit() {
        return GROUND_BIT;
    }

    public short getShelfBit() {
        return SHELF_BIT;
    }

    public short getHideBit() {
        return HIDE_BIT;
    }

    public short getWireBit() {
        return WIRE_BIT;
    }

    public short getLockpickBit() {
        return LOCKPICK_BIT;
    }

    public short getGeradorBit() {
        return GERADOR_BIT;
    }

    public short getPortaBit() {
        return PORTA_BIT;
    }

    public short getITEM_BIT() {
        return ITEM_BIT;
    }

    public short getINTERACTIBLE_BIT() {
        return INTERACTIBLE_BIT;
    }
}

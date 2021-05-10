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

    //PolygonShape polygonShape = new PolygonShape();
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

        /*for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / TerrorGame.SCALE,
                    (rect.getY() + rect.getHeight() / 2) / TerrorGame.SCALE);
            invisible_wall = world.createBody(bodyDef);

            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(rect.getWidth() / 2 / TerrorGame.SCALE, rect.getHeight() / 2 / TerrorGame.SCALE);
            fixtureDef.shape = polygonShape;

            fixtureDef.filter.categoryBits = GROUND_BIT;
            fixtureDef.filter.maskBits = PLAYER_BIT;
            invisible_wall.createFixture(fixtureDef);
            
            polygonShape.dispose();
        }*/

//        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
//            Rectangle rect = ((RectangleMapObject) object).getRectangle();
//
//            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / TerrorGame.SCALE,
//                    (rect.getY() + rect.getHeight() / 2) / TerrorGame.SCALE);
//            shelf = world.createBody(bodyDef);
//
//            polygonShape.setAsBox(rect.getWidth() / 2 / TerrorGame.SCALE, rect.getHeight() / 2 / TerrorGame.SCALE);
//            fixtureDef.shape = polygonShape;
//
//            fixtureDef.filter.categoryBits = SHELF_BIT;
//            fixtureDef.filter.maskBits = PLAYER_BIT;
//
//            Fixture fixture = shelf.createFixture(fixtureDef);
//            fixture.setUserData("bookshelf");
//        }
//
//        bodyDef.position.set(600 / TerrorGame.SCALE, 410 / TerrorGame.SCALE);
//        body1 = world.createBody(bodyDef);
//        PolygonShape porygonShape1 = new PolygonShape();
//        porygonShape1.setAsBox(80 / TerrorGame.SCALE, 200 / TerrorGame.SCALE);
//        fixtureDef.shape = porygonShape1;
//        fixtureDef.filter.categoryBits = HIDE_BIT;
//        fixtureDef.filter.maskBits = PLAYER_BIT;
//        Fixture fixture1 = body1.createFixture(fixtureDef);
//        fixture1.setUserData("esconde");
//
//        bodyDef.position.set(900 / TerrorGame.SCALE, 410 / TerrorGame.SCALE);
//        body2 = world.createBody(bodyDef);
//        PolygonShape porygonShape2 = new PolygonShape();
//        porygonShape2.setAsBox(80 / TerrorGame.SCALE, 200 / TerrorGame.SCALE);
//        fixtureDef.shape = porygonShape2;
//        fixtureDef.filter.categoryBits = LOCKPICK_BIT;
//        fixtureDef.filter.maskBits = PLAYER_BIT;
//        Fixture fixture2 = body2.createFixture(fixtureDef);
//        fixture2.setUserData("lockpick");
//
//        bodyDef.position.set(1200 / TerrorGame.SCALE, 410 / TerrorGame.SCALE);
//        body3 = world.createBody(bodyDef);
//        PolygonShape porygonShape3 = new PolygonShape();
//        porygonShape3.setAsBox(80 / TerrorGame.SCALE, 200 / TerrorGame.SCALE);
//        fixtureDef.shape = porygonShape3;
//        fixtureDef.filter.categoryBits = WIRE_BIT;
//        fixtureDef.filter.maskBits = PLAYER_BIT;
//        Fixture fixture3 = body3.createFixture(fixtureDef);
//        fixture3.setUserData("fios");
//
//        bodyDef.position.set(1800 / TerrorGame.SCALE, 410 / TerrorGame.SCALE);
//        body4 = world.createBody(bodyDef);
//        PolygonShape porygonShape4 = new PolygonShape();
//        porygonShape4.setAsBox(80 / TerrorGame.SCALE, 200 / TerrorGame.SCALE);
//        fixtureDef.shape = porygonShape4;
//        fixtureDef.filter.categoryBits = GERADOR_BIT;
//        fixtureDef.filter.maskBits = PLAYER_BIT;
//        Fixture fixture4 = body4.createFixture(fixtureDef);
//        fixture4.setUserData("gerador");
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

    /*public PolygonShape getPolygonShape() {
        return polygonShape;
    }*/

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
}

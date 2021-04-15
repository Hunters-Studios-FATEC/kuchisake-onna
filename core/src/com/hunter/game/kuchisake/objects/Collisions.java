package com.hunter.game.kuchisake.objects;

import com.badlogic.gdx.maps.MapObject;
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

public class Collisions {

    static final int GROUND_BIT = 1;
    static final int PLAYER_BIT = 2;
    static final int SHELF_BIT = 4;

    PolygonShape polygonShape = new PolygonShape();
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    Body ground;
    Body shelf;

    public Collisions(World world) {
        FixtureDef fixtureDef = new FixtureDef();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        fixtureDef.filter.categoryBits = GROUND_BIT;
        fixtureDef.filter.maskBits = PLAYER_BIT;

        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("mapa_teste.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / TerrorGame.SCALE);

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / TerrorGame.SCALE,
                    (rect.getY() + rect.getHeight() / 2) / TerrorGame.SCALE);
            ground = world.createBody(bodyDef);

            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(rect.getWidth() / 2 / TerrorGame.SCALE, rect.getHeight() / 2 / TerrorGame.SCALE);
            fixtureDef.shape = polygonShape;

            ground.createFixture(fixtureDef);
        }

        fixtureDef.filter.categoryBits = SHELF_BIT;
        fixtureDef.filter.maskBits = PLAYER_BIT;

        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / TerrorGame.SCALE,
                    (rect.getY() + rect.getHeight() / 2) / TerrorGame.SCALE);
            shelf = world.createBody(bodyDef);

            polygonShape.setAsBox(rect.getWidth() / 2 / TerrorGame.SCALE, rect.getHeight() / 2 / TerrorGame.SCALE);
            fixtureDef.shape = polygonShape;

            Fixture fixture = shelf.createFixture(fixtureDef);
            fixture.setUserData("bookshelf");
        }
    }

    public PolygonShape getPolygonShape() {
        return polygonShape;
    }

    public TiledMap getMap() {
        return map;
    }

    public OrthogonalTiledMapRenderer getMapRenderer() {
        return mapRenderer;
    }
}

package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.WireMinigame.WireMinigame;
import com.hunter.game.kuchisake.hide.Hide;
import com.hunter.game.kuchisake.lockpick.LockPickMinigame;
import com.hunter.game.kuchisake.minigame.MinigameBook;
//import com.hunter.game.kuchisake.teste.WireMinigame;
import com.hunter.game.kuchisake.tools.WorldContactListener;

public class Screen implements com.badlogic.gdx.Screen {
	
	TerrorGame game;
	
	//float w = Gdx.graphics.getWidth();
	//float h = Gdx.graphics.getHeight();
	
	OrthographicCamera camera;
	FitViewport viewport;
	
	World world;
	
	final float TIME_STEP = 1 / 60f;
	final int VELOCITY_ITERATIONS = 6;
	final int POSITION_ITERATIONS = 2;
	float accumulator = 0;
	
	Body player;
	Body ground;
	
	BodyDef bodyDef;
	
	FixtureDef fixtureDef;
	Fixture fixture;
	PolygonShape polygonShape;
	
	TmxMapLoader mapLoader;
	TiledMap map;
	OrthogonalTiledMapRenderer mapRenderer;
	
	Box2DDebugRenderer debugRenderer;
	
	final float MAX_VELOCITY = 3.5f;
	
	//MinigameBook minigameScreen;
	
	float clearStageTimer = 0;
	
	EdgeShape collisionSensor;
	
	final int PLAYER_BIT = 1;
	final int GROUND_BIT = 2;
	final int SHELF_BIT = 4;
	
	static boolean canStartMinigame = false;
	
	//WireMinigame wireMinigame;
	//LockPickMinigame lockPickMinigame;
	//Hide hideMinigame;
	WireMinigame wireMinigame;
	
	public Screen(TerrorGame game) {
		this.game = game;
		
		camera = new OrthographicCamera();
		
		viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE, camera);
		viewport.apply();
		
		camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
		camera.update();
		
		world = new World(new Vector2(0, -10), true);
		
		world.setContactListener(new WorldContactListener());
		
		bodyDef = new BodyDef();
		fixtureDef = new FixtureDef();
		polygonShape = new PolygonShape();
		
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(1000 / TerrorGame.SCALE, 352 / TerrorGame.SCALE);
		player = world.createBody(bodyDef);
		
		polygonShape.setAsBox(128 / TerrorGame.SCALE, 128 / TerrorGame.SCALE);
		fixtureDef.shape = polygonShape;
		
		fixtureDef.filter.categoryBits = PLAYER_BIT;
		fixtureDef.filter.maskBits = GROUND_BIT;
		
		fixture = player.createFixture(fixtureDef);
		
		fixtureDef = new FixtureDef();
		
		collisionSensor = new EdgeShape();
		collisionSensor.set(new Vector2(-64 / TerrorGame.SCALE, 0), new Vector2(64 / TerrorGame.SCALE, 0));
		
		fixtureDef.shape = collisionSensor;
		fixtureDef.isSensor = true;
		
		fixture = player.createFixture(fixtureDef);
		fixture.setUserData("player sensor");
		
		mapLoader = new TmxMapLoader();
		map = mapLoader.load("mapa_teste.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / TerrorGame.SCALE);
		
		fixtureDef = new FixtureDef();
		
		bodyDef.type = BodyType.StaticBody;
		
		fixtureDef.filter.categoryBits = GROUND_BIT;
		fixtureDef.filter.maskBits = PLAYER_BIT;
		
		for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / TerrorGame.SCALE, 
								 (rect.getY() + rect.getHeight() / 2) / TerrorGame.SCALE);
			ground = world.createBody(bodyDef);
			
			polygonShape.setAsBox(rect.getWidth() / 2 / TerrorGame.SCALE, rect.getHeight() / 2 / TerrorGame.SCALE);
			fixtureDef.shape = polygonShape;
			
			ground.createFixture(fixtureDef);
		}
		
		fixtureDef.filter.categoryBits = SHELF_BIT;
		fixtureDef.filter.maskBits = PLAYER_BIT;
		
		for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / TerrorGame.SCALE, 
								 (rect.getY() + rect.getHeight() / 2) / TerrorGame.SCALE);
			ground = world.createBody(bodyDef);
			
			polygonShape.setAsBox(rect.getWidth() / 2 / TerrorGame.SCALE, rect.getHeight() / 2 / TerrorGame.SCALE);
			fixtureDef.shape = polygonShape;
			
			fixture = ground.createFixture(fixtureDef);
			fixture.setUserData("bookshelf");
		}
		
		debugRenderer = new Box2DDebugRenderer();
		
		//minigameScreen = new MinigameBook(game.batch);
		
		//wireMinigame = new WireMinigame(game.batch);
		
		//lockPickMinigame = new LockPickMinigame(game.batch);
		
		//hideMinigame = new Hide(game.batch);
		
		wireMinigame = new WireMinigame(game.batch);
	}
	
	void stepWorld(float dt) {
		accumulator += (dt < 0.25f)? dt : 0.25f;
		
		while(accumulator >= TIME_STEP) {
			//handleInput();
			
			camera.position.x = player.getPosition().x;
			camera.update();
			
			world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
			accumulator -= TIME_STEP;
		}
	}
	
	void handleInput() {
		if(Gdx.input.isKeyPressed(Input.Keys.D) && player.getLinearVelocity().x < MAX_VELOCITY) {
			player.applyLinearImpulse(new Vector2(0.5f, 0), player.getWorldCenter(), true);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.A) && player.getLinearVelocity().x > -MAX_VELOCITY) {
			player.applyLinearImpulse(new Vector2(-0.5f, 0), player.getWorldCenter(), true);
		}
		
		/*if(Gdx.input.isKeyJustPressed(Input.Keys.W) && canStartMinigame) {
			if(minigameScreen.stage.getActors().size == 0) {
				minigameScreen.startMinigame();
			}
			else {
				minigameScreen.closeMinigame();
			}
		}*/	
	}
	
	void minigameUpdate(float dt) {
		/*game.batch.setProjectionMatrix(minigameScreen.stage.getCamera().combined);
		
		minigameScreen.verifyActorPos();
		
		minigameScreen.stage.act(dt);
		minigameScreen.stage.draw();
		
		if(minigameScreen.getIsFinished()) {
			clearStageTimer += dt;
			
			if(clearStageTimer > 1.5) {
				minigameScreen.stage.clear();
				canStartMinigame = false;
			}
		}*/
		
		/*game.batch.setProjectionMatrix(wireMinigame.stage.getCamera().combined);
		
		wireMinigame.stage.act(dt);
		wireMinigame.stage.draw();*/
		
		/*game.batch.setProjectionMatrix(lockPickMinigame.stage.getCamera().combined);
		
		lockPickMinigame.stage.act(dt);
		lockPickMinigame.stage.draw();*/
		
		/*game.batch.setProjectionMatrix(hideMinigame.stage.getCamera().combined);
		
		hideMinigame.stage.act(dt);
		hideMinigame.stage.draw();*/
		
		game.batch.setProjectionMatrix(wireMinigame.stage.getCamera().combined);
		
		wireMinigame.stage.act(dt);
		wireMinigame.stage.draw();
	}
	
	public static void setCanStartMinigame(boolean state) {
		canStartMinigame = state;
		//System.out.println(canStartMinigame);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		stepWorld(delta);
		mapRenderer.setView(camera);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		mapRenderer.render();
		
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		//game.batch.draw(img, 0, 0);
		game.batch.end();
		
		debugRenderer.render(world, camera.combined);
		
		/*if(minigameScreen.stage.getActors().size > 0) {
			minigameUpdate(delta);
		}*/
		
		minigameUpdate(delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		viewport.update(width, height);
		//minigameScreen.stage.getViewport().update(width, height);
		//wireMinigame.stage.getViewport().update(width, height);
		//lockPickMinigame.stage.getViewport().update(width, height);
		//hideMinigame.stage.getViewport().update(width, height);
		wireMinigame.stage.getViewport().update(width, height);
		
		game.batch.setProjectionMatrix(camera.combined);
		//game.batch.setProjectionMatrix(minigameScreen.stage.getCamera().combined);
		//game.batch.setProjectionMatrix(wireMinigame.stage.getCamera().combined);
		//game.batch.setProjectionMatrix(lockPickMinigame.stage.getCamera().combined);
		//game.batch.setProjectionMatrix(hideMinigame.stage.getCamera().combined);
		game.batch.setProjectionMatrix(wireMinigame.stage.getCamera().combined);
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		game.batch.dispose();
		world.dispose();
		polygonShape.dispose();
		debugRenderer.dispose();
		//minigameScreen.stage.dispose();
	}
	
}

package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.CpuSpriteBatch;
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
import com.hunter.game.kuchisake.objects.Collisions;
import com.hunter.game.kuchisake.objects.Player;
import com.hunter.game.kuchisake.tools.MinigameManager;
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
	
	Box2DDebugRenderer debugRenderer;

	Player player;
	Collisions collisions;
	OrthogonalTiledMapRenderer mapRenderer;

	MinigameManager minigameManager;

	
	public Screen(TerrorGame game) {
		this.game = game;

		camera = new OrthographicCamera();
		
		viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE, camera);
		viewport.apply();
		
		camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
		camera.update();
		
		world = new World(new Vector2(0, -10), true);
		
		world.setContactListener(new WorldContactListener());

		debugRenderer = new Box2DDebugRenderer();

		collisions = new Collisions(world);
		player = new Player(world);

		mapRenderer = collisions.getMapRenderer();

		minigameManager = new MinigameManager(game.batch);
	}


	
	void stepWorld(float dt) {
		accumulator += (dt < 0.25f)? dt : 0.25f;
		
		while(accumulator >= TIME_STEP) {
			player.handleInput();
			
			camera.position.x = player.getBody().getPosition().x;
			camera.update();
			world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
			accumulator -= TIME_STEP;
		}
	}


	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		stepWorld(delta);
		collisions.getMapRenderer().setView(camera);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		collisions.getMapRenderer().render();
		
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.batch.end();
		
		debugRenderer.render(world, camera.combined);
		
		minigameManager.minigameUpdate(delta, 2);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		viewport.update(width, height);
		game.batch.setProjectionMatrix(camera.combined);
		minigameManager.minigameResize(width, height, 2);
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
		player.getPolygonShape().dispose();
		collisions.getMap().dispose();
		collisions.getPolygonShape().dispose();
		debugRenderer.dispose();
		minigameManager.minigameDispose();
	}
	
}

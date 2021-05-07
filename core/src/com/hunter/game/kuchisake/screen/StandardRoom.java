package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.objects.Collisions;
import com.hunter.game.kuchisake.objects.Player;
import com.hunter.game.kuchisake.tools.InventoryManager;
import com.hunter.game.kuchisake.tools.MinigameManager;
import com.hunter.game.kuchisake.tools.WorldContactListener;

import org.omg.CORBA.PUBLIC_MEMBER;

import javax.swing.Spring;

//import com.hunter.game.kuchisake.teste.WireMinigame;

public class StandardRoom implements com.badlogic.gdx.Screen {
	
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
	int maxMinigameID = 5;

	InventoryManager inventoryManager;

	String direction;
	int doorNum;

	Texture portaFechada;
	Texture portaAberta;

	float doorAnimationTimer = 0f;

	public StandardRoom(TerrorGame game, String fundo_sala, float playerDoorPosX) {
		this.game = game;

		camera = new OrthographicCamera();
		
		viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE, camera);
		viewport.apply();
		
		camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
		camera.update();
		
		world = new World(new Vector2(0, 0), true);
		
		minigameManager = new MinigameManager(game.batch, player);

		inventoryManager = new InventoryManager(game);
		
		collisions = new Collisions(world, fundo_sala, game);
		player = new Player(world, minigameManager, inventoryManager, collisions, this, playerDoorPosX, 5, 160, game);
		
		world.setContactListener(new WorldContactListener(minigameManager, player, this));

		debugRenderer = new Box2DDebugRenderer();

		mapRenderer = collisions.getMapRenderer();

//		portaFechada = game.getAssetManager().get("PortasEEscadas/porta1.png", Texture.class);
//		portaAberta = game.getAssetManager().get("PortasEEscadas/porta2.png", Texture.class);
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

	public void setChangeRoom(String up_or_down, int room_num){
		direction = up_or_down;
		doorNum = room_num;
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
		
//		game.batch.setProjectionMatrix(camera.combined);
//		game.batch.begin();
//		game.batch.end();
//
//		debugRenderer.render(world, camera.combined);
//
//		for (int i = 0; i < maxMinigameID; i++) {
//				minigameManager.minigameUpdate(delta, i);
//		}
//
//		inventoryManager.inventoryUpdate(delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		viewport.update(width, height);
		game.batch.setProjectionMatrix(camera.combined);

		/*for (int i = 0; i < maxMinigameID; i++) {
			minigameManager.minigameResize(width, height, i);
		}*/

		//inventoryManager.inventoryResize(width, height);
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
		//game.batch.dispose();
		world.dispose();
		//player.getPolygonShape().dispose();
		//collisions.getPolygonShape().dispose();
		mapRenderer.dispose();
		debugRenderer.dispose();
		//minigameManager.minigameDispose();
		inventoryManager.inventoryDispose();
	}
}

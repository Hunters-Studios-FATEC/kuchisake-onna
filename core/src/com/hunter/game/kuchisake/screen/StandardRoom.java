package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.objects.Collisions;
import com.hunter.game.kuchisake.objects.Player;
import com.hunter.game.kuchisake.tools.InventoryManager;
import com.hunter.game.kuchisake.tools.MinigameManager;
import com.hunter.game.kuchisake.tools.WorldContactListener;

//import com.hunter.game.kuchisake.teste.WireMinigame;

public class StandardRoom implements com.badlogic.gdx.Screen {
	
	TerrorGame game;
	
	//float w = Gdx.graphics.getWidth();
	//float h = Gdx.graphics.getHeight();
	
	OrthographicCamera camera;
	FitViewport viewport;
	
	World world;
	
	float accumulator = 0;
	
	Box2DDebugRenderer debugRenderer;

	Player player;
	Collisions collisions;
	OrthogonalTiledMapRenderer mapRenderer;
	
	MapProperties mapProperties;
	
	int tilesNumberX;
	float tileWidth;
	float mapWidth;

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
		
		//world = new World(new Vector2(0, 0), true);
		world = game.getWorld();

		inventoryManager = new InventoryManager(game);
		
		collisions = new Collisions(world, fundo_sala, game);
		player = new Player(world, game.getMinigameManager(), inventoryManager, collisions, this, playerDoorPosX, game);
		
		world.setContactListener(new WorldContactListener(game.getMinigameManager(), player, this));

		debugRenderer = new Box2DDebugRenderer();

		mapRenderer = collisions.getMapRenderer();
		
		mapProperties = collisions.getMapProperties();
		
		tilesNumberX = mapProperties.get("width", Integer.class);
		tileWidth = mapProperties.get("tilewidth", Integer.class) / TerrorGame.SCALE;
		mapWidth = tilesNumberX * tileWidth;

//		portaFechada = game.getAssetManager().get("PortasEEscadas/porta1.png", Texture.class);
//		portaAberta = game.getAssetManager().get("PortasEEscadas/porta2.png", Texture.class);
		
		//game.getKuchisakeOnna().setStandardRoom(this);
	}

	void stepWorld(float dt) {
		accumulator += (dt < 0.25f)? dt : 0.25f;
		
		while(accumulator >= game.getTimeStep()) {
			player.handleInput();
			
			game.setPlayerXPos(player.getBody().getPosition().x);
			
			/*synchronized (this) {
				world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
				notify();
			}*/
			game.worldStep();
			
			updateCamera();
			
			accumulator -= game.getTimeStep();
		}
	}

	public void setChangeRoom(String up_or_down, int room_num){
		direction = up_or_down;
		doorNum = room_num;
	}
	
	void updateCamera() {
		float playerXPos = player.getBody().getPosition().x;
		
		if(playerXPos > viewport.getWorldWidth() / 2 && playerXPos < mapWidth - viewport.getWorldWidth() / 2) {
			camera.position.x = playerXPos;
		}
		else {
			if(playerXPos <= viewport.getWorldWidth() / 2) {
				camera.position.x = viewport.getWorldWidth() / 2;
			}
			else {
				camera.position.x = mapWidth - viewport.getWorldWidth() / 2;
			}
		}
		
		camera.update();
	}
	
	float getMapWidth() {
		return mapWidth;
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
		
		if(game.getKuchisakeOnna().getBody().getPosition().y == 2.88f + 4.25f) {
			game.getKuchisakeOnna().getSprite().setSize(128 * 3.75f / TerrorGame.SCALE, 128 * 3.75f / TerrorGame.SCALE);
		}
		else {
			game.getKuchisakeOnna().getSprite().setSize(128 * 5.5f / TerrorGame.SCALE, 128 * 5.5f / TerrorGame.SCALE);
		}

		boolean isPlayerInSaguao = ((game.getPlayerLine() == 0 && game.getPlayerColumn() == 1) ||
								    (game.getPlayerLine() == 1 && game.getPlayerColumn() == 2));

		boolean isKuchisakeInSaguao = ((game.getKuchisakeOnna().getCurrentLine() == 0 && game.getKuchisakeOnna().getCurrentColumn() == 1) || 
									   (game.getKuchisakeOnna().getCurrentLine() == 1 && game.getKuchisakeOnna().getCurrentColumn() == 2));

		if((isPlayerInSaguao && isKuchisakeInSaguao) ||
				game.getPlayerLine() == game.getKuchisakeOnna().getCurrentLine() && game.getPlayerColumn() == game.getKuchisakeOnna().getCurrentColumn()) {
			game.getKuchisakeOnna().getSprite().setAlpha(1);
			game.setHasEncountered(true);
		}
		else {
			game.getKuchisakeOnna().getSprite().setAlpha(0);
		}

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
		//world.dispose();
		
		Array<Body> bodies = new Array<Body>(world.getBodyCount());
		world.getBodies(bodies);
		
		for(Body body: bodies) {
			if(!body.equals(game.getKuchisakeOnna().getBody())) {
				world.destroyBody(body);
			}
		}
		
		//player.getPolygonShape().dispose();
		//collisions.getPolygonShape().dispose();
		mapRenderer.dispose();
		debugRenderer.dispose();
		//minigameManager.minigameDispose();
		inventoryManager.inventoryDispose();
	}
}

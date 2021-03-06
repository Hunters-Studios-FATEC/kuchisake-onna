package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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


public class StandardRoom implements com.badlogic.gdx.Screen {
	
	TerrorGame game;
	
	OrthographicCamera camera;
	FitViewport viewport;
	
	World world;
	
	float accumulator = 0;

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
	
	float doorAnimationTimer = 0;
	
	TransitionScene transitionScene;
	
	boolean canSwitchAssets = false;
	
	Music mansionTheme;
	Music runTheme;

	Sound portaSound;
	
	boolean collidedWithKuchisake = false;

	public StandardRoom(TerrorGame game, String fundo_sala, float playerDoorPosX) {
		this.game = game;

		camera = new OrthographicCamera();
		
		viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE, camera);
		viewport.apply();
		
		camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
		camera.update();
		
		world = game.getWorld();

		inventoryManager = game.getInventoryManager();
		
		collisions = new Collisions(world, fundo_sala, game);
		player = new Player(world, game.getMinigameManager(), inventoryManager, collisions, this, playerDoorPosX, game);
		
		world.setContactListener(new WorldContactListener(game.getMinigameManager(), player, this, inventoryManager));

		mapRenderer = collisions.getMapRenderer();
		
		mapProperties = collisions.getMapProperties();
		
		tilesNumberX = mapProperties.get("width", Integer.class);
		tileWidth = mapProperties.get("tilewidth", Integer.class) / TerrorGame.SCALE;
		mapWidth = tilesNumberX * tileWidth;

		portaSound = game.getAssetManager().get("Audio/Sfx/porta fechando 3.ogg");

		transitionScene = new TransitionScene(game);
		
		mansionTheme = game.getMansionTheme();
		runTheme = game.getRunTheme();
		
		mansionTheme.setVolume(0.5f);
		mansionTheme.setLooping(true);
		
		runTheme.setVolume(0.5f);
		runTheme.setLooping(true);

		game.getKuchisakeOnna().setCanSetVolume(true);
	}

	void stepWorld(float dt) {
		accumulator += (dt < 0.25f)? dt : 0.25f;
		
		if(canSwitchAssets && accumulator < game.getTimeStep()) {
			player.walkInput();
			
			game.setPlayerXPos(player.getBody().getPosition().x);
			
			game.worldStep();
		}
		
		while(accumulator >= game.getTimeStep()) {
			player.walkInput();
			
			game.setPlayerXPos(player.getBody().getPosition().x);

			game.worldStep();
			
			accumulator -= game.getTimeStep();
		}
		
		if(!canSwitchAssets) {
			player.handleInput();
		}
	}

	public void setChangeRoom(String up_or_down, int room_num){
		direction = up_or_down;
		doorNum = room_num;
	}
	
	public boolean getCanSwitchAssets() {
		return canSwitchAssets;
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
	
	void playMusic(int id) {
		if(game.getCanPlayMusic()) {
			switch(id) {
				case 0:
					if(runTheme.isPlaying()) {
						runTheme.stop();
					}
					
					if(!mansionTheme.isPlaying()) {
						mansionTheme.play();	
					}
					
					break;
				case 1: 
					if(mansionTheme.isPlaying()) {
						mansionTheme.stop();
					}
					
					if(!runTheme.isPlaying()) {
						runTheme.play();
						game.getKuchisakeOnna().playFoundAudio();
					}
					
					break;
			}
			
			game.setCanPlayMusic(false);
		}
	}
	
	public void verifyKuchisakeCollision() {
		if(game.getPlayerLine() == game.getKuchisakeOnna().getCurrentLine() 
				&& game.getPlayerColumn() == game.getKuchisakeOnna().getCurrentColumn()) {
			collidedWithKuchisake = true;
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		if((game.getMinigameManager().getGeradorCompleted() && transitionScene.getActor().getColor().a == 0) || 
				(!game.getMinigameManager().getGeradorCompleted() && transitionScene.getActor().getColor().a == 0.8f)) {
			stepWorld(delta);
		}
		
		updateCamera();
		
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

			if (game.getPlayerLine() == game.getKuchisakeOnna().getCurrentLine() && game.getPlayerColumn() == game.getKuchisakeOnna().getCurrentColumn()){
				game.setHasEncountered(true);
				
				game.setCanPlayMusic(true);
				
				playMusic(1);
			}

		}
		else {
			game.getKuchisakeOnna().getSprite().setAlpha(0);
		}
		
		playMusic(0);
		
		if(game.getMinigameManager().getGeradorCompleted() && 
				(transitionScene.getActor().getActions().size == 0 && !canSwitchAssets)) {
			transitionScene.getActor().setColor(0, 0, 0, 0);
		}
		
		if(collidedWithKuchisake) {
			runTheme.stop();
			game.getKuchisakeOnna().stopFoundAudio();

			game.setScreen(new GameOver(game));
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		viewport.update(width, height);
		game.batch.setProjectionMatrix(camera.combined);

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

		Array<Body> bodies = new Array<Body>(world.getBodyCount());
		world.getBodies(bodies);
		
		for(Body body: bodies) {
			if(!body.equals(game.getKuchisakeOnna().getBody())) {
				world.destroyBody(body);
			}
		}
		
		transitionScene.dispose();
		
		mapRenderer.dispose();
		inventoryManager.inventoryDispose();
	}
}

package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;

public class Saguao extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion corrimao;
    TextureRegion portaFechada;
    TextureRegion portaAberta;

    Sprite corri;
    Sprite porta1;
    Sprite porta2;

    boolean isSecondFloor = false;

    public Saguao(TerrorGame game, float playerDoorPosX, boolean secondFloor) {
        super(game, "Tilesets/saguao_segundo.tmx", playerDoorPosX);
        collisions.CreateCollisions(1750, 160,"doorUp2",320, collisions.getPortaBit());
        collisions.CreateCollisions(1750, 585,"doorDown1",320, collisions.getPortaBit());
        collisions.CreateCollisions(420 + 280f / 2f, 585, "doorUp2", 140, collisions.getPortaBit());
        collisions.CreateCollisions(2800 + 280f / 2f, 585, "doorUp3", 140, collisions.getPortaBit());

        textureAtlas = game.getAssetManager().get("ScenaryAssets/saguao/SaguaoObjects.atlas", TextureAtlas.class);
        corrimao = textureAtlas.findRegion("corrimao");
        portaFechada = textureAtlas.findRegion("porta1");
        portaAberta = textureAtlas.findRegion("porta2");

        corri = new Sprite(corrimao);
        porta1 = new Sprite(portaFechada);
        porta2 = new Sprite(portaFechada);

        corri.setSize(corri.getWidth() / TerrorGame.SCALE, corri.getHeight() / TerrorGame.SCALE);
        corri.setPosition(0, 10.46f - corri.getHeight());
        
        porta1.setSize(porta1.getWidth() / TerrorGame.SCALE, porta1.getHeight() / TerrorGame.SCALE);
        porta1.setPosition(140 * 3 / TerrorGame.SCALE, 585 / TerrorGame.SCALE);
        
        porta2.setSize(porta2.getWidth() / TerrorGame.SCALE, porta2.getHeight() / TerrorGame.SCALE);
        porta2.setPosition((3500 - 140 * 3) / TerrorGame.SCALE - porta2.getWidth(), 585 / TerrorGame.SCALE);
        
        isSecondFloor = secondFloor;
        
        if(isSecondFloor) {
        	player.setSizeAndPosition(3.25f, 4.25f);
        }

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);
        game.getKuchisakeOnna().KuchisakeUpdate(delta);

        game.batch.begin();
        porta1.draw(game.batch);
        porta2.draw(game.batch);
        
        if (isSecondFloor){

            if (game.getKuchisakeOnna().getBody().getPosition().y == 2.88f + 4.25f){
                game.getKuchisakeOnna().draw(game.batch);
            }

            player.draw(game.batch);

            corri.draw(game.batch);

            if (game.getKuchisakeOnna().getBody().getPosition().y == 2.88f){
            	game.getKuchisakeOnna().draw(game.batch);
            }
        }else {
            if (game.getKuchisakeOnna().getBody().getPosition().y == 2.88f + 4.25f){
            	game.getKuchisakeOnna().draw(game.batch);
            }

            corri.draw(game.batch);

            if (game.getKuchisakeOnna().getBody().getPosition().y == 2.88f){
            	game.getKuchisakeOnna().draw(game.batch);
            }

            player.draw(game.batch);
        }
        
        game.batch.end();

        debugRenderer.render(world, camera.combined);
        inventoryManager.inventoryUpdate(delta);

		/*for (int i = 0; i < maxMinigameID; i++) {
				minigameManager.minigameUpdate(delta, i);
		}*/

        //inventoryManager.inventoryUpdate(delta);
        if (player.getCanChangeRoom()){
        	if(!isSecondFloor) {
        		if (direction == "doorUp" && doorNum == 2){
                    isSecondFloor = true;
                    player.setSizeAndPosition(3.25f, 4.25f);
//                    System.out.println("subir");
                    player.setCanChangeRoom(false);
                    
                    game.incrementPlayerLine(1);
                    game.setPlayerColumn(2);
                }
        	}
        	else {
        		 if (direction == "doorDown" && doorNum == 1){
                     isSecondFloor = false;
                     player.setSizeAndPosition(5,-4.25f);
//                     System.out.println("descer");
                     player.setCanChangeRoom(false);
                     
                     game.incrementPlayerLine(-1);
                     game.setPlayerColumn(1);
                 }
                 
                 if (direction == "doorUp" && doorNum == 2){
                     doorAnimationTimer += delta;
                     if(doorAnimationTimer > 1.5f){
                         dispose();

                         game.getAssetManager().unload("Tilesets/saguao_segundo.tmx");
                         game.getAssetManager().unload("ScenaryAssets/saguao/SaguaoObjects.atlas");
                         
                         game.getAssetManager().load("Tilesets/sala1.tmx", TiledMap.class);
                         game.getAssetManager().load("ScenaryAssets/sala_1/Sala1Objects.atlas", TextureAtlas.class);

                         game.getAssetManager().finishLoading();
                         
                         game.incrementPlayerLine(1);
                         game.setPlayerColumn(2);

                         game.setScreen(new Sala1(game, 483));
                     }
                 }
                 else if (direction == "doorUp" && doorNum == 3){
                     doorAnimationTimer += delta;
                     if(doorAnimationTimer > 1.5f){
                         dispose();

                         game.getAssetManager().unload("Tilesets/saguao_segundo.tmx");
                         game.getAssetManager().unload("ScenaryAssets/saguao/SaguaoObjects.atlas");
                         
                         game.getAssetManager().load("Tilesets/sala2.tmx", TiledMap.class);
                         game.getAssetManager().load("ScenaryAssets/sala_2/Sala2Objects.atlas", TextureAtlas.class);

                         game.getAssetManager().finishLoading();
                         
                         game.incrementPlayerLine(1);
                         game.setPlayerColumn(3);

                         game.setScreen(new Sala2(game, 2891));
                     }
                 }
            }
        }
        
        if(!isSecondFloor) {
        	if(player.getBody().getPosition().x < 0) {
            	dispose();

                game.getAssetManager().unload("Tilesets/saguao_segundo.tmx");
                game.getAssetManager().unload("ScenaryAssets/saguao/SaguaoObjects.atlas");
                
                game.getAssetManager().load("Tilesets/sala1.tmx", TiledMap.class);
                game.getAssetManager().load("ScenaryAssets/sala_1/Sala1Objects.atlas", TextureAtlas.class);

                game.getAssetManager().finishLoading();
                game.setPlayerColumn(0);
                
                game.setScreen(new SalaEstar(game, 3500 - 128));
            }
            else if(player.getBody().getPosition().x > mapWidth) {
            	dispose();

                game.getAssetManager().unload("Tilesets/saguao_segundo.tmx");
                game.getAssetManager().unload("ScenaryAssets/saguao/SaguaoObjects.atlas");
                
                game.getAssetManager().load("Tilesets/sala1.tmx", TiledMap.class);
                game.getAssetManager().load("ScenaryAssets/sala_1/Sala1Objects.atlas", TextureAtlas.class);

                game.getAssetManager().finishLoading();
                game.setPlayerColumn(2);
                
                game.setScreen(new Cozinha(game, 128));
            }
        }
        else {
        	if(player.getBody().getPosition().x < 0) {
            	dispose();

                game.getAssetManager().unload("Tilesets/saguao_segundo.tmx");
                game.getAssetManager().unload("ScenaryAssets/saguao/SaguaoObjects.atlas");
                
                game.getAssetManager().load("Tilesets/corredor.tmx", TiledMap.class);
                game.getAssetManager().load("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);

                game.getAssetManager().finishLoading();
                game.setPlayerColumn(1);
                
                game.setScreen(new CorredorQuarto(game, 3500 - 128));
            }
            else if(player.getBody().getPosition().x > mapWidth) {
            	dispose();

                game.getAssetManager().unload("Tilesets/saguao_segundo.tmx");
                game.getAssetManager().unload("ScenaryAssets/saguao/SaguaoObjects.atlas");
                
                game.getAssetManager().load("Tilesets/corredor.tmx", TiledMap.class);
                game.getAssetManager().load("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);

                game.getAssetManager().finishLoading();
                game.setPlayerColumn(3);
                
                game.setScreen(new CorredorSalas(game, 128));
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

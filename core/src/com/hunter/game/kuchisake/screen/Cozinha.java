package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;

import javax.xml.stream.FactoryConfigurationError;

public class Cozinha extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;

    Sprite porta1;
    Sprite porta2;

    boolean isSecondFloor = false;

    public Cozinha(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/sala1.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(483, 160,"doorUp4", 203, collisions.getPortaBit());
        collisions.CreateCollisions(3143, 160,"doorUp5", 203, collisions.getPortaBit());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/sala_1/Sala1Objects.atlas", TextureAtlas.class);
        portaFechada = textureAtlas.findRegion("porta1");
        portaAberta = textureAtlas.findRegion("porta2");
        
        porta1 = new Sprite(portaFechada);
        porta2 = new Sprite(portaFechada);
        
        porta1.setSize(porta1.getWidth() * 1.45f / TerrorGame.SCALE, porta1.getHeight() * 1.45f / TerrorGame.SCALE);
        porta1.setPosition(280 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        
        porta2.setSize(porta2.getWidth() * 1.45f / TerrorGame.SCALE, porta2.getHeight() * 1.45f / TerrorGame.SCALE);
        porta2.setPosition(2940 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);

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
        game.getKuchisakeOnna().draw(game.batch);
        player.draw(game.batch);
        
        game.batch.end();

        debugRenderer.render(world, camera.combined);
        inventoryManager.inventoryUpdate(delta);

		/*for (int i = 0; i < maxMinigameID; i++) {
				minigameManager.minigameUpdate(delta, i);
		}*/

        //inventoryManager.inventoryUpdate(delta);
        if (player.getCanChangeRoom()){
            if (direction == "doorUp" && doorNum == 4){
                doorAnimationTimer += delta;
                if(doorAnimationTimer > 1.5f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/sala1.tmx");
                    game.getAssetManager().unload("ScenaryAssets/sala_1/Sala1Objects.atlas");
                    
                    game.getAssetManager().load("Tilesets/sala2.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/sala_2/Sala2Objects.atlas", TextureAtlas.class);

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(1);
                    game.setPlayerColumn(4);
                    
                    game.setScreen(new AreaServico(game, 2891));
                }

            }
            else if (direction == "doorUp" && doorNum == 5){
                doorAnimationTimer += delta;
                if(doorAnimationTimer > 1.5f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/sala1.tmx");
                    game.getAssetManager().unload("ScenaryAssets/sala_1/Sala1Objects.atlas");
                    
                    game.getAssetManager().load("Tilesets/corredor.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(1);
                    game.setPlayerColumn(5);
                    
                    game.setScreen(new CorredorServico(game, 128));
                }

            }

        }
        
        if(player.getBody().getPosition().x < 0) {
        	dispose();

            game.getAssetManager().unload("Tilesets/sala1.tmx");
            game.getAssetManager().unload("ScenaryAssets/sala_1/Sala1Objects.atlas");
            
            game.getAssetManager().load("Tilesets/saguao_segundo.tmx", TiledMap.class);
            game.getAssetManager().load("ScenaryAssets/saguao/SaguaoObjects.atlas", TextureAtlas.class);

            game.getAssetManager().finishLoading();
            game.setPlayerColumn(1);
            
            game.setScreen(new Saguao(game, 3500 - 128, false));
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

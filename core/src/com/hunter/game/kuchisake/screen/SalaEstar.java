package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;

import javax.xml.stream.FactoryConfigurationError;

public class SalaEstar extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;

    Sprite porta;

    public SalaEstar(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/sala1.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(483, 160,"doorUp0", 203, collisions.getPortaBit());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/sala_1/Sala1Objects.atlas", TextureAtlas.class);
        portaFechada = textureAtlas.findRegion("porta1");
        portaAberta = textureAtlas.findRegion("porta2");
        
        porta = new Sprite(portaFechada);
        
        porta.setSize(porta.getWidth() * 1.45f / TerrorGame.SCALE, porta.getHeight() * 1.45f / TerrorGame.SCALE);
        porta.setPosition(280 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);
        game.getKuchisakeOnna().KuchisakeUpdate(delta);
        player.playerUpdate(delta);

        game.batch.begin();
        
        porta.draw(game.batch);
        game.getKuchisakeOnna().draw(game.batch);
        player.draw(game.batch);
        
        game.batch.end();

        debugRenderer.render(world, camera.combined);
        inventoryManager.inventoryUpdate(delta);
        transitionScene.updateTransition();

		/*for (int i = 0; i < maxMinigameID; i++) {
				minigameManager.minigameUpdate(delta, i);
		}*/

        //inventoryManager.inventoryUpdate(delta);
        
        if (player.getCanChangeRoom()){
            if (direction == "doorUp" && doorNum == 0){
                doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                	game.getAssetManager().load("Tilesets/corredor.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);
                    
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 1.5f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/sala1.tmx");
                    game.getAssetManager().unload("ScenaryAssets/sala_1/Sala1Objects.atlas");

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(1);
                    game.setPlayerColumn(0);
                    game.setScreen(new CorredorBiblioteca(game, 2891));
                }

            }
        }

        if(player.getBody().getPosition().x > mapWidth) {
        	doorAnimationTimer += delta;
        	transitionScene.fadeIn();
            
            if(!canSwitchAssets) {
            	game.getAssetManager().load("Tilesets/saguao_segundo.tmx", TiledMap.class);
                game.getAssetManager().load("ScenaryAssets/saguao/SaguaoObjects.atlas", TextureAtlas.class);
                
                canSwitchAssets = true;
            }
            
            if(doorAnimationTimer > 1.5f){
            	dispose();

                game.getAssetManager().unload("Tilesets/sala1.tmx");
                game.getAssetManager().unload("ScenaryAssets/sala_1/Sala1Objects.atlas");

                game.getAssetManager().finishLoading();
                game.setPlayerColumn(1);
                game.setScreen(new Saguao(game, 128, false));
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

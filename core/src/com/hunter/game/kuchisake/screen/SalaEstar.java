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
        super(game, "Tilesets/salaEstar.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(483, 160,"doorUp0", 203, collisions.getPortaBit());

        if (!game.getInventoryManager().getItemBackpack().contains("livro1", false)) {
            collisions.CreateCollisions(2700, 160, "livro1", 203, collisions.getITEM_BIT());
        }

        textureAtlas = game.getAssetManager().get("ScenaryAssets/salaEstar/SalaEstarObjects.atlas", TextureAtlas.class);
        portaFechada = textureAtlas.findRegion("porta corredor 1");
        portaAberta = textureAtlas.findRegion("porta corredor 2");
        
        porta = new Sprite(portaFechada);
        
        porta.setSize(porta.getWidth() / TerrorGame.SCALE, porta.getHeight() / TerrorGame.SCALE);
        porta.setPosition(280 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        porta.setAlpha(0.5f);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);
        game.getKuchisakeOnna().KuchisakeUpdate(delta);
        player.playerUpdate(delta);

        game.batch.begin();

        game.getKuchisakeOnna().draw(game.batch);
        player.draw(game.batch);
        porta.draw(game.batch);
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

                    game.getAssetManager().unload("Tilesets/salaEstar.tmx");
                    game.getAssetManager().unload("ScenaryAssets/salaEstar/SalaEstarObjects.atlas");

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

                game.getAssetManager().unload("Tilesets/salaEstar.tmx");
                game.getAssetManager().unload("ScenaryAssets/salaEstar/SalaEstarObjects.atlas");

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

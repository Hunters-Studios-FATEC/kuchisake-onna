package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;

import javax.xml.stream.FactoryConfigurationError;

public class Quarto extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;

    Sprite porta;

    boolean isSecondFloor = false;

    public Quarto(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/quarto.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(2810, 160,"doorDown1", 230, collisions.getPortaBit());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/quarto/QuartoObjects.atlas", TextureAtlas.class);
        portaFechada = textureAtlas.findRegion("portaCorredor1");
        portaAberta = textureAtlas.findRegion("portaCorredor2");
        
        porta = new Sprite(portaFechada);
        
        porta.setSize(porta.getWidth() / TerrorGame.SCALE, porta.getHeight() / TerrorGame.SCALE);
        porta.setPosition((3500 - 460 * 2) / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        porta.setAlpha(0.5f);
        
        if(game.getHasEncountered()) {
        	game.getMinigameManager().setMinigameActive(true);
        	game.getMinigameManager().showDescription(0);
        }

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);
        game.getKuchisakeOnna().KuchisakeUpdate(delta);

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
        
        game.getMinigameManager().minigameUpdate(delta, 0);
        
        if (player.getCanChangeRoom()){
        	if (direction == "doorDown" && doorNum == 1){
                doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                	game.getAssetManager().load("Tilesets/corredor.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);
                    
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 1.5f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/quarto.tmx");
                    game.getAssetManager().unload("ScenaryAssets/quarto/QuartoObjects.atlas");

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(-1);
                    game.setPlayerColumn(1);
                    
                    game.setScreen(new CorredorQuarto(game, 1980));
                }
            }
        }


    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        
        game.getMinigameManager().minigameResize(width, height, 0);
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

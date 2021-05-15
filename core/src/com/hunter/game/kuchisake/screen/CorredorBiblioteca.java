package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;

import javax.xml.stream.FactoryConfigurationError;

public class CorredorBiblioteca extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion estatua;

    Sprite porta1;
    Sprite porta2;
    Sprite porta3;

    boolean isSecondFloor = false;

    public CorredorBiblioteca(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/corredor.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(2891, 160,"doorDown0", 203, collisions.getPortaBit());
        collisions.CreateCollisions(971, 160,"doorUp0", 203, collisions.getPortaBit());
        collisions.CreateCollisions(1900+230, 160,"doorUp-1", 203, collisions.getPortaBit());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);
        portaFechada = textureAtlas.findRegion("portaCorredor1");
        portaAberta = textureAtlas.findRegion("portaCorredor2");
        estatua = textureAtlas.findRegion("estatua");

        porta1 = new Sprite(portaFechada);
        porta2 = new Sprite(portaFechada);
        porta3 = new Sprite(portaFechada);
        
        porta1.setSize(porta1.getWidth() / TerrorGame.SCALE, porta1.getHeight() / TerrorGame.SCALE);
        porta1.setPosition((3500 - 812) / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        porta1.setAlpha(0.5f);

        porta2.setSize(porta2.getWidth() / TerrorGame.SCALE, porta2.getHeight() / TerrorGame.SCALE);
        porta2.setPosition(741 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);

        porta3.setSize(porta3.getWidth() / TerrorGame.SCALE, porta3.getHeight() / TerrorGame.SCALE);
        porta3.setPosition(1900 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);
        game.getKuchisakeOnna().KuchisakeUpdate(delta);
        game.batch.begin();

        porta3.draw(game.batch);
        porta2.draw(game.batch);
        game.getKuchisakeOnna().draw(game.batch);
        player.draw(game.batch);
        porta1.draw(game.batch);

        game.batch.end();

        debugRenderer.render(world, camera.combined);
        inventoryManager.inventoryUpdate(delta);
        transitionScene.updateTransition();

		/*for (int i = 0; i < maxMinigameID; i++) {
				minigameManager.minigameUpdate(delta, i);
		}*/

        //inventoryManager.inventoryUpdate(delta);
        if (player.getCanChangeRoom()){
            if (direction == "doorDown" && doorNum == 0){
                doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                    game.getAssetManager().load("Tilesets/sala1.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/sala_1/Sala1Objects.atlas", TextureAtlas.class);
                    
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 1.5f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/corredor.tmx");
                    game.getAssetManager().unload("ScenaryAssets/corredor/CorredorObjects.atlas");

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(-1);
                    game.setPlayerColumn(0);
                    
                    game.setScreen(new SalaEstar(game, 483));
                }

            } else if (direction == "doorUp" && doorNum == 0){
                doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                    game.getAssetManager().load("Tilesets/sala1.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/quarto/QuartoObjects.atlas", TextureAtlas.class);
                    
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 1.5f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/corredor.tmx");
                    game.getAssetManager().unload("ScenaryAssets/corredor/CorredorObjects.atlas");

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(1);
                    game.setPlayerColumn(0);
                    
                    game.setScreen(new Biblioteca(game, 971+230));
                }
            } else if (direction == "doorUp" && doorNum == -1){
                doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                    game.getAssetManager().load("Tilesets/quarto.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/quarto/QuartoObjects.atlas", TextureAtlas.class);
                    
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 1.5f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/corredor.tmx");
                    game.getAssetManager().unload("ScenaryAssets/corredor/CorredorObjects.atlas");

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(1);
                    game.setPlayerColumn(-1);
                    
                    game.setHasEncountered(false);
                    
                    game.setScreen(new SalaSegura(game, 1900+230));
                }
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

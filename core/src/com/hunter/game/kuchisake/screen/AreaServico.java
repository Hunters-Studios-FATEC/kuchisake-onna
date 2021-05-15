package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.tools.MinigameManager;

import javax.xml.stream.FactoryConfigurationError;

public class AreaServico extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    MinigameManager minigameManager;

    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion caixaEnergia;

    Sprite porta;
    Sprite quadroEnergia;

    boolean isSecondFloor = false;

    public AreaServico(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/sala2.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(2891, 160,"doorDown2", 203, collisions.getPortaBit());
        collisions.CreateCollisions(203, 160, "fios", 203, collisions.getWireBit());
        collisions.CreateCollisions(1750, 160, "gerador", 203, collisions.getGeradorBit());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/sala_2/Sala2Objects.atlas", TextureAtlas.class);
        portaFechada = textureAtlas.findRegion("porta1");
        portaAberta = textureAtlas.findRegion("porta2");
        caixaEnergia = textureAtlas.findRegion("quadro_forca");
        
        porta = new Sprite(portaFechada);
        quadroEnergia = new Sprite(caixaEnergia);
        
        porta.setSize(porta.getWidth() * 1.45f / TerrorGame.SCALE, porta.getHeight() * 1.45f / TerrorGame.SCALE);
        porta.setPosition((3500 - 812) / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        porta.setAlpha(0.5f);
        
        quadroEnergia.setSize(quadroEnergia.getWidth() / TerrorGame.SCALE, quadroEnergia.getHeight() / TerrorGame.SCALE);
        quadroEnergia.setPosition(51 / TerrorGame.SCALE, 476 / TerrorGame.SCALE);

        minigameManager = game.getMinigameManager();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);
        game.getKuchisakeOnna().KuchisakeUpdate(delta);

        game.batch.begin();
        
        quadroEnergia.draw(game.batch);
        game.getKuchisakeOnna().draw(game.batch);
        player.draw(game.batch);
        porta.draw(game.batch);
        
        game.batch.end();

        debugRenderer.render(world, camera.combined);
        inventoryManager.inventoryUpdate(delta);
        transitionScene.updateTransition();



        minigameManager.minigameUpdate(delta, 3);
        minigameManager.minigameUpdate(delta, 4);


        //inventoryManager.inventoryUpdate(delta);
        if (player.getCanChangeRoom()){
            if (direction == "doorDown" && doorNum == 2){
                doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                	game.getAssetManager().load("Tilesets/sala1.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/sala_1/Sala1Objects.atlas", TextureAtlas.class);
                    
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 1.5f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/sala2.tmx");
                    game.getAssetManager().unload("ScenaryAssets/sala_2/Sala2Objects.atlas");
                    game.getAssetManager().unload("Audio/Sfx/minigame complete 6.ogg");
                    game.getAssetManager().unload("Audio/Sfx/wrongBuzzer.wav");
                    
                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(-1);
                    game.setPlayerColumn(2);
                    
                    game.setScreen(new Cozinha(game, 483));
                }

            }
        }


    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        minigameManager.minigameResize(width, height, 3);
        minigameManager.minigameResize(width, height, 4);
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
        minigameManager.minigameDispose(3);
        minigameManager.minigameDispose(4);
    }
}

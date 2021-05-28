package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.objects.ObjectAnimation;
import com.hunter.game.kuchisake.tools.MinigameManager;

import javax.xml.stream.FactoryConfigurationError;

public class AreaServico extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    MinigameManager minigameManager;

    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion caixaEnergia;
    TextureRegion gerador;
    TextureRegion lustreCorredor1;
    TextureRegion lustreCorredor2;
    TextureRegion lustreCorredor3;
    TextureRegion lustreCorredor4;

    Sprite porta;
    Sprite quadroEnergia;
    Sprite geradorSprite;
    Sprite lustreSprite1;
    Sprite lustreSprite2;
    
    ObjectAnimation lustreAnimation;

    public AreaServico(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/area_servico.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(1950, 160,"doorDown2", 203, collisions.getPortaBit());
        collisions.CreateCollisions(203, 160, "fios", 203, collisions.getWireBit());
        collisions.CreateCollisions(2962, 160, "gerador", 380, collisions.getGeradorBit());
        collisions.CreateCollisions(-128, 160, "zap zap", 128, collisions.getGroundBit());
        collisions.CreateCollisions(3628, 160, "zap zap", 128, collisions.getGroundBit());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/areaServico/AreaServicoObjects.atlas", TextureAtlas.class);
        portaFechada = textureAtlas.findRegion("portaCorredor1");
        portaAberta = textureAtlas.findRegion("portaCorredor2");
        caixaEnergia = textureAtlas.findRegion("quadro_forca");
        gerador = textureAtlas.findRegion("gerador");
        lustreCorredor1 = textureAtlas.findRegion("lustre corredor1");
        lustreCorredor2 = textureAtlas.findRegion("lustre corredor2");
        lustreCorredor3 = textureAtlas.findRegion("lustre corredor3");
        lustreCorredor4 = textureAtlas.findRegion("lustre corredor4");
        
        porta = new Sprite(portaFechada);
        quadroEnergia = new Sprite(caixaEnergia);
        geradorSprite = new Sprite(gerador);
        lustreSprite1 = new Sprite(lustreCorredor1);
        lustreSprite2 = new Sprite(lustreCorredor1);
        
        porta.setSize(porta.getWidth() / TerrorGame.SCALE, porta.getHeight() / TerrorGame.SCALE);
        porta.setPosition(1747 / TerrorGame.SCALE, 175 / TerrorGame.SCALE);
        
        quadroEnergia.setSize(quadroEnergia.getWidth() / TerrorGame.SCALE, quadroEnergia.getHeight() / TerrorGame.SCALE);
        quadroEnergia.setPosition(51 / TerrorGame.SCALE, 476 / TerrorGame.SCALE);
        
        geradorSprite.setSize(geradorSprite.getWidth() / TerrorGame.SCALE, geradorSprite.getHeight() / TerrorGame.SCALE);
        geradorSprite.setPosition(2582 / TerrorGame.SCALE, 175 / TerrorGame.SCALE);
        
        lustreSprite1.setSize(lustreSprite1.getWidth() / TerrorGame.SCALE, lustreSprite1.getHeight() / TerrorGame.SCALE);
        lustreSprite1.setPosition(1347 / TerrorGame.SCALE, 700 / TerrorGame.SCALE);
        
        lustreSprite2.setSize(lustreSprite2.getWidth() / TerrorGame.SCALE, lustreSprite2.getHeight() / TerrorGame.SCALE);
        lustreSprite2.setPosition(2377 / TerrorGame.SCALE, 700 / TerrorGame.SCALE);
        
        lustreAnimation = new ObjectAnimation(0.2f, 
        		new TextureRegion[] {lustreCorredor1, lustreCorredor2, lustreCorredor3, lustreCorredor4});

        minigameManager = game.getMinigameManager();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);
        game.getKuchisakeOnna().KuchisakeUpdate(delta);
        
        if(game.getMinigameManager().getGeradorCompleted()) {
        	lustreSprite1.setRegion(lustreAnimation.changeFrame(delta));
        	lustreSprite2.setRegion(lustreAnimation.changeFrame(delta));
        }
        
        if(!lustreSprite2.isFlipX()) {
        	lustreSprite2.flip(true, false);
        }

        game.batch.begin();
        
        porta.draw(game.batch);
        lustreSprite1.draw(game.batch);
        lustreSprite2.draw(game.batch);
        geradorSprite.draw(game.batch);
        quadroEnergia.draw(game.batch);
        game.getKuchisakeOnna().draw(game.batch);
        player.draw(game.batch);
        
        game.batch.end();
        transitionScene.updateTransition();
        inventoryManager.inventoryUpdate(delta);

        minigameManager.minigameUpdate(delta, 3);
        minigameManager.minigameUpdate(delta, 4);

        if (player.getCanChangeRoom()){
            if (direction == "doorDown" && doorNum == 2){
                doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                	game.getAssetManager().load("Tilesets/cozinha.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/cozinha/CozinhaObjects.atlas", TextureAtlas.class);
                    portaSound.play(0.5f);
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 2f){

                    dispose();
                    game.getAssetManager().unload("Tilesets/area_servico.tmx");
                    game.getAssetManager().unload("ScenaryAssets/areaServico/AreaServicoObjects.atlas");
                    game.getAssetManager().unload("Audio/Sfx/minigame complete 6.ogg");
                    game.getAssetManager().unload("Audio/Sfx/wrongBuzzer.wav");
                    
                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(-1);
                    game.setPlayerColumn(2);
                    
                    game.setScreen(new Cozinha(game, 600));
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

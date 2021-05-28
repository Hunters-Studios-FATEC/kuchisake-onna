package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.cutscenes.Cutscene2;
import com.hunter.game.kuchisake.objects.ObjectAnimation;
import com.hunter.game.kuchisake.tools.MinigameManager;

import javax.xml.stream.FactoryConfigurationError;

public class Cozinha extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion portaPrincipal;
    TextureRegion lustre1;
    TextureRegion lustre2;
    TextureRegion lustre3;
    TextureRegion lustre4;

    Sprite portaPrinc;
    Sprite porta1;
    Sprite porta2;
    Sprite lustre;

    Sound portraTrancada;
    
    ObjectAnimation lustreAnimation;

    public Cozinha(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/cozinha.tmx", playerDoorPosX);

        if(game.getHasEncountered() &&
                (game.getKuchisakeOnna().getCurrentLine() != game.getPlayerLine() ||
                game.getKuchisakeOnna().getCurrentColumn() != game.getPlayerColumn())) {
            game.getMinigameManager().setMinigameActive(true);
            game.getMinigameManager().startMinigame(0);
        }

        collisions.CreateCollisions(600, 160,"doorUp4", 203, collisions.getPortaBit());
        collisions.CreateCollisions(3143, 160,"doorUp5", 203, collisions.getPortaBit());
        collisions.CreateCollisions(1750, 160, "doorDown-2", 203, collisions.getPortaBit());
        collisions.CreateCollisions(3628, 160, "zap zap", 128, collisions.getGroundBit());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/cozinha/CozinhaObjects.atlas", TextureAtlas.class);
        portaFechada = textureAtlas.findRegion("portaCorredor1");
        portaAberta = textureAtlas.findRegion("portaCorredor2");
        portaPrincipal = textureAtlas.findRegion("saida1");
        lustre1 = textureAtlas.findRegion("lustre1");
        lustre2 = textureAtlas.findRegion("lustre2");
        lustre3 = textureAtlas.findRegion("lustre3");
        lustre4 = textureAtlas.findRegion("lustre4");

        portaPrinc = new Sprite(portaPrincipal);
        porta1 = new Sprite(portaFechada);
        porta2 = new Sprite(portaFechada);
        lustre = new Sprite(lustre1);

        portaPrinc.setSize(portaPrinc.getWidth() / TerrorGame.SCALE, portaPrinc.getHeight() / TerrorGame.SCALE);
        portaPrinc.setPosition((1750 / TerrorGame.SCALE) - (portaPrinc.getWidth() / 2), 160 / TerrorGame.SCALE);
        portaPrinc.setAlpha(0.5f);

        porta1.setSize(porta1.getWidth()/ TerrorGame.SCALE, porta1.getHeight() / TerrorGame.SCALE);
        porta1.setPosition(397 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        porta1.setAlpha(0.5f);
        
        porta2.setSize(porta2.getWidth()/ TerrorGame.SCALE, porta2.getHeight() / TerrorGame.SCALE);
        porta2.setPosition(2940 / TerrorGame.SCALE, 175 / TerrorGame.SCALE);
        
        lustre.setSize(lustre.getWidth() / TerrorGame.SCALE, lustre.getHeight() / TerrorGame.SCALE);
        lustre.setPosition(1750 / TerrorGame.SCALE - lustre.getWidth() / 2, viewport.getWorldHeight() - lustre.getHeight());
        
        lustreAnimation = new ObjectAnimation(0.2f, new TextureRegion[] {lustre1, lustre2, lustre3, lustre4});

        portraTrancada = game.getAssetManager().get("Audio/Sfx/porta trancada.ogg");
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);
        game.getKuchisakeOnna().KuchisakeUpdate(delta);

        game.batch.begin();
        
        porta2.draw(game.batch);
        game.getKuchisakeOnna().draw(game.batch);
        player.draw(game.batch);
        
        if(game.getMinigameManager().getGeradorCompleted()) {
        	lustre.setRegion(lustreAnimation.changeFrame(delta));
        }
        
        lustre.draw(game.batch);
        portaPrinc.draw(game.batch);
        porta1.draw(game.batch);
        
        game.batch.end();

        transitionScene.updateTransition();
        inventoryManager.inventoryUpdate(delta);

        game.getMinigameManager().minigameUpdate(delta, 0);

        if (player.getCanChangeRoom()){
            if (direction == "doorUp" && doorNum == 4){
                if (game.getInventoryManager().getItemBackpack().contains("chaveServico", false)) {
                    doorAnimationTimer += delta;
                    transitionScene.fadeIn();
                    
                    if(!canSwitchAssets) {
                    	game.getAssetManager().load("Tilesets/area_servico.tmx", TiledMap.class);
                        game.getAssetManager().load("ScenaryAssets/areaServico/AreaServicoObjects.atlas", TextureAtlas.class);
                        game.getAssetManager().load("Audio/Sfx/minigame complete 6.ogg", Sound.class);
                        game.getAssetManager().load("Audio/Sfx/wrongBuzzer.wav", Sound.class);
                        portaSound.play(0.5f);
                        canSwitchAssets = true;
                    }
                    
                    if (doorAnimationTimer > 2f) {
                        dispose();

                        game.getAssetManager().unload("Tilesets/cozinha.tmx");
                        game.getAssetManager().unload("ScenaryAssets/cozinha/CozinhaObjects.atlas");

                        game.getAssetManager().finishLoading();
                        game.incrementPlayerLine(1);
                        game.setPlayerColumn(4);

                        game.setScreen(new AreaServico(game, 1950));
                    }
                    } else {
                    portraTrancada.setVolume(portraTrancada.play(), 0.5f);
                    player.setCanChangeRoom(false);
                }

            }
            else if (direction == "doorUp" && doorNum == 5){
                doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                	game.getAssetManager().load("Tilesets/corredor.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);
                    game.getAssetManager().load("Audio/Sfx/porta trancada.ogg", Sound.class);
                    portaSound.play(0.5f);
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 2f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/cozinha.tmx");
                    game.getAssetManager().unload("ScenaryAssets/cozinha/CozinhaObjects.atlas");

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(1);
                    game.setPlayerColumn(5);
                    
                    game.setScreen(new CorredorServico(game, 600));
                }

            } else if (direction == "doorDown" && doorNum == -2){
                if (game.getInventoryManager().getItemBackpack().contains("chavePrincipal", false)){
                    doorAnimationTimer += delta;
                    transitionScene.fadeIn();

                    if(!canSwitchAssets) {
                        mansionTheme.stop();
                        runTheme.stop();
                        portaSound.play(0.5f);
                        canSwitchAssets = true;
                    }

                    if(doorAnimationTimer > 3f){
                        dispose();

                        game.getAssetManager().unload("Tilesets/cozinha.tmx");
                        game.getAssetManager().unload("ScenaryAssets/cozinha/CozinhaObjects.atlas");
                        game.getAssetManager().finishLoading();
                        game.setScreen(new Cutscene2(game));
                    }
                } else {
                    portraTrancada.setVolume(portraTrancada.play(), 0.5f);
                    player.setCanChangeRoom(false);
                }
            }
        }
        
        if(player.getBody().getPosition().x < 0) {
        	doorAnimationTimer += delta;
            transitionScene.fadeIn();
            
            if(!canSwitchAssets) {
            	game.getAssetManager().load("Tilesets/saguao_segundo.tmx", TiledMap.class);
                game.getAssetManager().load("ScenaryAssets/saguao/SaguaoObjects.atlas", TextureAtlas.class);
                
                canSwitchAssets = true;
            }
            
            if(doorAnimationTimer > 1.5f){
            	dispose();

                game.getAssetManager().unload("Tilesets/cozinha.tmx");
                game.getAssetManager().unload("ScenaryAssets/cozinha/CozinhaObjects.atlas");

                game.getAssetManager().finishLoading();
                game.setPlayerColumn(1);
                
                game.setScreen(new Saguao(game, 3500 - 128, false));
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

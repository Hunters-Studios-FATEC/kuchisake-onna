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

public class CorredorQuarto extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion estatua;
    TextureRegion lustreCorredor1;
    TextureRegion lustreCorredor2;
    TextureRegion lustreCorredor3;
    TextureRegion lustreCorredor4;

    Sprite porta;
    Sprite estatua1;
    Sprite estatua2;
    Sprite lustreSprite1;
    Sprite lustreSprite2;

    Sound portraTrancada;
    MinigameManager minigameManager;
    
    ObjectAnimation lustreAnimation;

    public CorredorQuarto(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/corredor.tmx", playerDoorPosX);

        minigameManager = game.getMinigameManager();
        
        collisions.CreateCollisions(1750, 160,"doorUp1", 230, collisions.getPortaBit());
        collisions.CreateCollisions(1750, 160, "lockpick", 230, collisions.getLockpickBit());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);
        portaFechada = textureAtlas.findRegion("portaCorredor1");
        portaAberta = textureAtlas.findRegion("portaCorredor2");
        estatua = textureAtlas.findRegion("estatua");
        lustreCorredor1 = textureAtlas.findRegion("lustre corredor1");
        lustreCorredor2 = textureAtlas.findRegion("lustre corredor2");
        lustreCorredor3 = textureAtlas.findRegion("lustre corredor3");
        lustreCorredor4 = textureAtlas.findRegion("lustre corredor4");
        
        porta = new Sprite(portaFechada);
        estatua1 = new Sprite(estatua);
        estatua2 = new Sprite(estatua);
        lustreSprite1 = new Sprite(lustreCorredor1);
        lustreSprite2 = new Sprite(lustreCorredor1);
        
        porta.setSize(porta.getWidth() / TerrorGame.SCALE, porta.getHeight() / TerrorGame.SCALE);
        porta.setPosition(1750 / TerrorGame.SCALE - porta.getWidth() / 2, 160 / TerrorGame.SCALE);
        
        estatua1.setSize(estatua1.getWidth() / TerrorGame.SCALE, estatua1.getHeight() / TerrorGame.SCALE);
        estatua1.setPosition(492 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        
        estatua2.setSize(estatua2.getWidth() / TerrorGame.SCALE, estatua2.getHeight() / TerrorGame.SCALE);
        estatua2.setPosition((3500 - 782) / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        
        lustreSprite1.setSize(lustreSprite1.getWidth() / TerrorGame.SCALE, lustreSprite1.getHeight() / TerrorGame.SCALE);
        lustreSprite1.setPosition(1147 / TerrorGame.SCALE, 700 / TerrorGame.SCALE);
        
        lustreSprite2.setSize(lustreSprite2.getWidth() / TerrorGame.SCALE, lustreSprite2.getHeight() / TerrorGame.SCALE);
        lustreSprite2.setPosition(2177 / TerrorGame.SCALE, 700 / TerrorGame.SCALE);
        
        lustreAnimation = new ObjectAnimation(0.2f, 
        		new TextureRegion[] {lustreCorredor1, lustreCorredor2, lustreCorredor3, lustreCorredor4});

        portraTrancada = game.getAssetManager().get("Audio/Sfx/porta trancada.ogg");
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
        
        estatua1.draw(game.batch);
        estatua2.draw(game.batch);
        porta.draw(game.batch);
        
        lustreSprite1.draw(game.batch);
        lustreSprite2.draw(game.batch);
        
        game.getKuchisakeOnna().draw(game.batch);
        player.draw(game.batch);
        
        game.batch.end();

        debugRenderer.render(world, camera.combined);
        inventoryManager.inventoryUpdate(delta);
        minigameManager.minigameUpdate(delta, 1);
        transitionScene.updateTransition();

		/*for (int i = 0; i < maxMinigameID; i++) {
				minigameManager.minigameUpdate(delta, i);
		}*/

        //inventoryManager.inventoryUpdate(delta);
       /* if (player.getCanChangeRoom()){
            if (direction == "doorUp" && doorNum == 1){
                isSecondFloor = true;
                player.setSizeAndPosition(3.25f, 4.25f);
//                System.out.println("subir");
                player.setCanChangeRoom(false);
            }

            if (direction == "doorDown" && doorNum == 1){
                isSecondFloor = false;
                player.setSizeAndPosition(5,-4.25f);
//                System.out.println("descer");
                player.setCanChangeRoom(false);
            }
        }*/
        
        if (player.getCanChangeRoom()){
        	if (direction == "doorUp" && doorNum == 1){
        	    if (game.getMinigameManager().getLockCompleted()){
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
                        game.setPlayerColumn(1);

                        game.setScreen(new Quarto(game, 2810));
                    }
                } else {
                    portraTrancada.setVolume(portraTrancada.play(), 0.5f);
                    player.setCanChangeRoom(false);
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
            
        	if(doorAnimationTimer > 1.5f) {
        		dispose();

                game.getAssetManager().unload("Tilesets/corredor.tmx");
                game.getAssetManager().unload("ScenaryAssets/corredor/CorredorObjects.atlas");

                game.getAssetManager().finishLoading();
                game.setPlayerColumn(2);
                
                game.setScreen(new Saguao(game, 128, true));
        	}
        }

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        minigameManager.minigameResize(width, height, 1);
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
        minigameManager.minigameDispose(1);
    }
}

package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.objects.ObjectAnimation;

import javax.xml.stream.FactoryConfigurationError;

public class CorredorBiblioteca extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion estatua;
    TextureRegion lustreCorredor1;
    TextureRegion lustreCorredor2;
    TextureRegion lustreCorredor3;
    TextureRegion lustreCorredor4;

    Sprite porta1;
    Sprite porta2;
    Sprite porta3;
    Sprite lustreSprite1;
    Sprite lustreSprite2;

    boolean isSecondFloor = false;

    Sound portraTrancada;
    
    ObjectAnimation lustreAnimation;

    public CorredorBiblioteca(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/corredor.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(2900, 160,"doorDown0", 203, collisions.getPortaBit());
        collisions.CreateCollisions(600, 160,"doorUp0", 203, collisions.getPortaBit());
        collisions.CreateCollisions(1750, 160,"doorUp-1", 203, collisions.getPortaBit());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);
        portaFechada = textureAtlas.findRegion("portaCorredor1");
        portaAberta = textureAtlas.findRegion("portaCorredor2");
        estatua = textureAtlas.findRegion("estatua");
        lustreCorredor1 = textureAtlas.findRegion("lustre corredor1");
        lustreCorredor2 = textureAtlas.findRegion("lustre corredor2");
        lustreCorredor3 = textureAtlas.findRegion("lustre corredor3");
        lustreCorredor4 = textureAtlas.findRegion("lustre corredor4");

        porta1 = new Sprite(portaFechada);
        porta2 = new Sprite(portaFechada);
        porta3 = new Sprite(portaFechada);
        lustreSprite1 = new Sprite(lustreCorredor1);
        lustreSprite2 = new Sprite(lustreCorredor1);
        
        porta1.setSize(porta1.getWidth() / TerrorGame.SCALE, porta1.getHeight() / TerrorGame.SCALE);
        porta1.setPosition(2697 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);

        porta2.setSize(porta2.getWidth() / TerrorGame.SCALE, porta2.getHeight() / TerrorGame.SCALE);
        porta2.setPosition(397 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);

        porta3.setSize(porta3.getWidth() / TerrorGame.SCALE, porta3.getHeight() / TerrorGame.SCALE);
        porta3.setPosition(1547 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        
        lustreSprite1.setSize(lustreSprite1.getWidth() / TerrorGame.SCALE, lustreSprite1.getHeight() / TerrorGame.SCALE);
        lustreSprite1.setPosition(1057 / TerrorGame.SCALE, 700 / TerrorGame.SCALE);
        
        lustreSprite2.setSize(lustreSprite2.getWidth() / TerrorGame.SCALE, lustreSprite2.getHeight() / TerrorGame.SCALE);
        lustreSprite2.setPosition(2297 / TerrorGame.SCALE, 700 / TerrorGame.SCALE);
        
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

        porta3.draw(game.batch);
        porta2.draw(game.batch);
        porta1.draw(game.batch);
        
        lustreSprite1.draw(game.batch);
        lustreSprite2.draw(game.batch);
        
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
            if (direction == "doorDown" && doorNum == 0){
                doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                    game.getAssetManager().load("Tilesets/salaEstar.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/salaEstar/SalaEstarObjects.atlas", TextureAtlas.class);
                    portaSound.play(0.5f);
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 2f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/corredor.tmx");
                    game.getAssetManager().unload("ScenaryAssets/corredor/CorredorObjects.atlas");
                    game.getAssetManager().unload("Audio/Sfx/porta trancada.ogg");

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(-1);
                    game.setPlayerColumn(0);
                    
                    game.setScreen(new SalaEstar(game, 483));
                }

            } else if (direction == "doorUp" && doorNum == 0){
                if (inventoryManager.getItemBackpack().contains("chaveBiblio", false)){
                    doorAnimationTimer += delta;
                    transitionScene.fadeIn();

                    if(!canSwitchAssets) {
                        game.getAssetManager().load("Tilesets/biblioteca.tmx", TiledMap.class);
                        game.getAssetManager().load("ScenaryAssets/biblioteca/BibliotecaObjects.atlas", TextureAtlas.class);
                        portaSound.play(0.5f);
                        canSwitchAssets = true;
                    }

                    if(doorAnimationTimer > 2f) {
                        dispose();

                        game.getAssetManager().unload("Tilesets/corredor.tmx");
                        game.getAssetManager().unload("ScenaryAssets/corredor/CorredorObjects.atlas");
                        game.getAssetManager().unload("Audio/Sfx/porta trancada.ogg");

                        game.getAssetManager().finishLoading();
                        game.incrementPlayerLine(1);
                        game.setPlayerColumn(0);

                        game.setScreen(new Biblioteca(game, 971 + 230));
                    }
                } else {
                    portraTrancada.setVolume(portraTrancada.play(), 0.5f);
                    player.setCanChangeRoom(false);
                }
            } else if (direction == "doorUp" && doorNum == -1){
                doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                    game.getAssetManager().load("Tilesets/sala_descanso.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/salaDescanso/SalaDescansoObjects.atlas", TextureAtlas.class);
                    portaSound.play(0.5f);
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 2f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/corredor.tmx");
                    game.getAssetManager().unload("ScenaryAssets/corredor/CorredorObjects.atlas");
                    game.getAssetManager().unload("Audio/Sfx/porta trancada.ogg");

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(1);
                    game.setPlayerColumn(-1);
                    
                    game.setHasEncountered(false);
                    game.setCanPlayMusic(true);
                    
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

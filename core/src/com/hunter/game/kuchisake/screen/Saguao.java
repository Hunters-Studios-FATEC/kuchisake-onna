package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.objects.ObjectAnimation;

public class Saguao extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion corrimao;
    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion lustre1;
    TextureRegion lustre2;
    TextureRegion lustre3;
    TextureRegion lustre4;

    Sprite corri;
    Sprite porta1;
    Sprite porta2;
    Sprite lustre;

    boolean isSecondFloor = false;
    
    ObjectAnimation lustreAnimation;

    public Saguao(TerrorGame game, float playerDoorPosX, boolean secondFloor) {
        super(game, "Tilesets/saguao_segundo.tmx", playerDoorPosX);
        collisions.CreateCollisions(1750, 160,"doorUp2",320, collisions.getPortaBit());
        collisions.CreateCollisions(1750, 585,"doorDown1",320, collisions.getPortaBit());
        collisions.CreateCollisions(420 + 280f / 2f, 585, "doorUp2", 140, collisions.getPortaBit());
        collisions.CreateCollisions(2800 + 280f / 2f, 585, "doorUp3", 140, collisions.getPortaBit());

        textureAtlas = game.getAssetManager().get("ScenaryAssets/saguao/SaguaoObjects.atlas", TextureAtlas.class);
        corrimao = textureAtlas.findRegion("corrimao");
        portaFechada = textureAtlas.findRegion("porta1");
        portaAberta = textureAtlas.findRegion("porta2");
        lustre1 = textureAtlas.findRegion("lustre1");
        lustre2 = textureAtlas.findRegion("lustre2");
        lustre3 = textureAtlas.findRegion("lustre3");
        lustre4 = textureAtlas.findRegion("lustre4");

        corri = new Sprite(corrimao);
        porta1 = new Sprite(portaFechada);
        porta2 = new Sprite(portaFechada);
        lustre = new Sprite(lustre1);

        corri.setSize(corri.getWidth() / TerrorGame.SCALE, corri.getHeight() / TerrorGame.SCALE);
        corri.setPosition(0, 10.46f - corri.getHeight());
        
        porta1.setSize(porta1.getWidth() / TerrorGame.SCALE, porta1.getHeight() / TerrorGame.SCALE);
        porta1.setPosition(140 * 3 / TerrorGame.SCALE, 585 / TerrorGame.SCALE);
        
        porta2.setSize(porta2.getWidth() / TerrorGame.SCALE, porta2.getHeight() / TerrorGame.SCALE);
        porta2.setPosition((3500 - 140 * 3) / TerrorGame.SCALE - porta2.getWidth(), 585 / TerrorGame.SCALE);
        
        lustre.setSize(lustre.getWidth() / TerrorGame.SCALE, lustre.getHeight() / TerrorGame.SCALE);
        lustre.setPosition(1750 / TerrorGame.SCALE - lustre.getWidth() / 2, viewport.getWorldHeight() - lustre.getHeight());
        
        isSecondFloor = secondFloor;
        
        if(isSecondFloor) {
        	player.setSizeAndPosition(3.25f, 4.25f);
        }
        
        lustreAnimation = new ObjectAnimation(0.2f, new TextureRegion[] {lustre1, lustre2, lustre3, lustre4});
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
        
        if (isSecondFloor){

            if (game.getKuchisakeOnna().getBody().getPosition().y == 2.88f + 4.25f){
                game.getKuchisakeOnna().draw(game.batch);
            }

            player.draw(game.batch);

            corri.draw(game.batch);

            if (game.getKuchisakeOnna().getBody().getPosition().y == 2.88f){
            	game.getKuchisakeOnna().draw(game.batch);
            }
        }else {
            if (game.getKuchisakeOnna().getBody().getPosition().y == 2.88f + 4.25f){
            	game.getKuchisakeOnna().draw(game.batch);
            }

            corri.draw(game.batch);

            if (game.getKuchisakeOnna().getBody().getPosition().y == 2.88f){
            	game.getKuchisakeOnna().draw(game.batch);
            }

            player.draw(game.batch);
        }
        
        if(game.getMinigameManager().getGeradorCompleted()) {
        	lustre.setRegion(lustreAnimation.changeFrame(delta));
        }
        
        lustre.draw(game.batch);
        
        game.batch.end();

        transitionScene.updateTransition();
        inventoryManager.inventoryUpdate(delta);

        if (player.getCanChangeRoom()){
        	if(!isSecondFloor) {
        		if (direction == "doorUp" && doorNum == 2){
                    isSecondFloor = true;
                    player.setSizeAndPosition(3.25f, 4.25f);
                    player.setCanChangeRoom(false);
                    
                    game.incrementPlayerLine(1);
                    game.setPlayerColumn(2);
                }
        	}
        	else {
        		 if (direction == "doorDown" && doorNum == 1){
                     isSecondFloor = false;
                     player.setSizeAndPosition(5,-4.25f);
                     player.setCanChangeRoom(false);
                     
                     game.incrementPlayerLine(-1);
                     game.setPlayerColumn(1);
                 }
                 
                 if (direction == "doorUp" && doorNum == 2){
                     doorAnimationTimer += delta;
                     transitionScene.fadeIn();
                     
                     if(!canSwitchAssets) {
                         game.getAssetManager().load("Tilesets/sala1.tmx", TiledMap.class);
                         game.getAssetManager().load("ScenaryAssets/sala_1/Sala1Objects.atlas", TextureAtlas.class);
                         portaSound.play(0.5f);
                         canSwitchAssets = true;
                     }
                     
                     if(doorAnimationTimer > 2f){
                    	 dispose();
                    	 
                    	 game.getAssetManager().unload("Tilesets/saguao_segundo.tmx");
                         game.getAssetManager().unload("ScenaryAssets/saguao/SaguaoObjects.atlas");
                         
                         game.getAssetManager().finishLoading();
                         
                         game.incrementPlayerLine(1);
                         game.setPlayerColumn(2);

                         game.setScreen(new Sala1(game, 483));
                     }
                 }
                 else if (direction == "doorUp" && doorNum == 3){
                     doorAnimationTimer += delta;
                     transitionScene.fadeIn();
                     
                     if(!canSwitchAssets) {     
                         game.getAssetManager().load("Tilesets/sala2.tmx", TiledMap.class);
                         game.getAssetManager().load("ScenaryAssets/sala_2/Sala2Objects.atlas", TextureAtlas.class);
                         portaSound.play(0.5f);
                         canSwitchAssets = true;
                     }
                     
                     if(doorAnimationTimer > 2f){
                     	 dispose();
                     	 
                    	 game.getAssetManager().unload("Tilesets/saguao_segundo.tmx");
                         game.getAssetManager().unload("ScenaryAssets/saguao/SaguaoObjects.atlas");
                         
                         game.getAssetManager().finishLoading();
                         
                         game.incrementPlayerLine(1);
                         game.setPlayerColumn(3);

                         game.setScreen(new Sala2(game, 2891));
                     }
                 }
            }
        }
        
        if(!isSecondFloor) {
        	if(player.getBody().getPosition().x < 0) {
        		doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                    game.getAssetManager().load("Tilesets/salaEstar.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/salaEstar/SalaEstarObjects.atlas", TextureAtlas.class);
                    
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 2f){
                	dispose();
                	
                	game.getAssetManager().unload("Tilesets/saguao_segundo.tmx");
                    game.getAssetManager().unload("ScenaryAssets/saguao/SaguaoObjects.atlas");
                	
                	game.getAssetManager().finishLoading();
                	
                    game.setPlayerColumn(0);
                    
                    game.setScreen(new SalaEstar(game, 3500 - 128));
                }
            }
            else if(player.getBody().getPosition().x > mapWidth) {
            	doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                    game.getAssetManager().load("Tilesets/cozinha.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/cozinha/CozinhaObjects.atlas", TextureAtlas.class);
                    game.getAssetManager().load("Audio/Sfx/porta trancada.ogg", Sound.class);
                    
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 2f) {
                	dispose();
                	
                	game.getAssetManager().unload("Tilesets/saguao_segundo.tmx");
                    game.getAssetManager().unload("ScenaryAssets/saguao/SaguaoObjects.atlas");

                    game.getAssetManager().finishLoading();
                    
                    game.setPlayerColumn(2);
                    
                    game.setScreen(new Cozinha(game, 128));
                }
            }
        }
        else {
        	if(player.getBody().getPosition().x < 0) {
        		doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {   
                    game.getAssetManager().load("Tilesets/corredor.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);
                    game.getAssetManager().load("Audio/Sfx/porta trancada.ogg", Sound.class);
                    
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 2f) {
                	dispose();
                	
                	game.getAssetManager().unload("Tilesets/saguao_segundo.tmx");
                    game.getAssetManager().unload("ScenaryAssets/saguao/SaguaoObjects.atlas");

                    game.getAssetManager().finishLoading();
                    game.setPlayerColumn(1);
                    
                    game.setScreen(new CorredorQuarto(game, 3500 - 128));
                }
            }
            else if(player.getBody().getPosition().x > mapWidth) {
            	doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                    game.getAssetManager().load("Tilesets/corredor.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);
                    
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 2f) {
                	dispose();
                	
                	game.getAssetManager().unload("Tilesets/saguao_segundo.tmx");
                    game.getAssetManager().unload("ScenaryAssets/saguao/SaguaoObjects.atlas");

                    game.getAssetManager().finishLoading();
                    game.setPlayerColumn(3);
                    
                    game.setScreen(new CorredorSalas(game, 128));
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

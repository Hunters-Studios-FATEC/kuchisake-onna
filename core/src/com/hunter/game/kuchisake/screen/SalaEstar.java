package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.objects.ObjectAnimation;

import javax.xml.stream.FactoryConfigurationError;

public class SalaEstar extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion livro;
    TextureRegion lustre1;
    TextureRegion lustre2;
    TextureRegion lustre3;
    TextureRegion lustre4;
    TextureRegion lareiraAcesa1;
    TextureRegion lareiraAcesa2;
    TextureRegion lareiraAcesa3;
    TextureRegion lareiraApagada;

    Sprite porta;
    Sprite livroSprite;
    Sprite lustre;
    Sprite lareira;

    boolean isLareiraAcessa = true;
    
    ObjectAnimation lustreAnimation;
    ObjectAnimation lareiraAnimation;

    public SalaEstar(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/salaEstar.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(483, 160,"doorUp0", 203, collisions.getPortaBit());

        //colisao da lareira
        collisions.CreateCollisions(1750, 160, "objetoMundo", 203, collisions.getINTERACTIBLE_BIT());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/salaEstar/SalaEstarObjects.atlas", TextureAtlas.class);

        if (!game.getInventoryManager().getItemBackpack().contains("livro1", false)) {
            collisions.CreateCollisions(2710, 160, "livro1", 203, collisions.getITEM_BIT());
            
            livro = textureAtlas.findRegion("livro1");
            
            livroSprite = new Sprite(livro);
            livroSprite.setSize(livroSprite.getWidth() / (TerrorGame.SCALE * 2), livroSprite.getHeight() / (TerrorGame.SCALE * 2));
            livroSprite.setPosition(2710 / TerrorGame.SCALE - livroSprite.getWidth() / 2, 346 / TerrorGame.SCALE);
        }

        if (!game.getInventoryManager().getItemBackpack().contains("mask1", false) && !isLareiraAcessa) {
            collisions.CreateCollisions(1750, 160, "mask1", 203, collisions.getITEM_BIT());
        }

        portaFechada = textureAtlas.findRegion("portaCorredor1");
        portaAberta = textureAtlas.findRegion("portaCorredor2");
        lustre1 = textureAtlas.findRegion("lustre1");
        lustre2 = textureAtlas.findRegion("lustre2");
        lustre3 = textureAtlas.findRegion("lustre3");
        lustre4 = textureAtlas.findRegion("lustre4");
        lareiraAcesa1 = textureAtlas.findRegion("lareira acesa1");
        lareiraAcesa2 = textureAtlas.findRegion("lareira acesa2");
        lareiraAcesa3 = textureAtlas.findRegion("lareira acesa3");
        lareiraApagada = textureAtlas.findRegion("lareira apagada");
        
        porta = new Sprite(portaFechada);
        lustre = new Sprite(lustre1);
        lareira = new Sprite(lareiraApagada);
        
        porta.setSize(porta.getWidth() / TerrorGame.SCALE, porta.getHeight() / TerrorGame.SCALE);
        porta.setPosition(280 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        porta.setAlpha(0.5f);
        
        lustre.setSize(lustre.getWidth() / TerrorGame.SCALE, lustre.getHeight() / TerrorGame.SCALE);
        lustre.setPosition(1750 / TerrorGame.SCALE - lustre.getWidth() / 2, viewport.getWorldHeight() - lustre.getHeight());
        
        lareira.setSize(lareira.getWidth() / TerrorGame.SCALE, lareira.getHeight() / TerrorGame.SCALE);
        lareira.setPosition(1750 / TerrorGame.SCALE - lareira.getWidth() / 2, 160 / TerrorGame.SCALE);
        
        lustreAnimation = new ObjectAnimation(0.2f, new TextureRegion[] {lustre1, lustre2, lustre3, lustre4});
        lareiraAnimation = new ObjectAnimation(0.2f, new TextureRegion[] {lareiraAcesa1, lareiraAcesa2, lareiraAcesa3});
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);
        game.getKuchisakeOnna().KuchisakeUpdate(delta);
        player.playerUpdate(delta);

        game.batch.begin();
        
        lareira.setRegion(lareiraAnimation.changeFrame(delta));
        lareira.draw(game.batch);
        
        if (!game.getInventoryManager().getItemBackpack().contains("livro1", false)) {
        	livroSprite.draw(game.batch);
        }

        game.getKuchisakeOnna().draw(game.batch);
        player.draw(game.batch);
        porta.draw(game.batch);
        
        if(game.getMinigameManager().getGeradorCompleted()) {
        	lustre.setRegion(lustreAnimation.changeFrame(delta));
        }
        
        lustre.draw(game.batch);
        
        game.batch.end();

        debugRenderer.render(world, camera.combined);
        inventoryManager.inventoryUpdate(delta);
        transitionScene.updateTransition();

		/*for (int i = 0; i < maxMinigameID; i++) {
				minigameManager.minigameUpdate(delta, i);
		}*/

        //inventoryManager.inventoryUpdate(delta);

        if (player.getChangeObjectVisual() && inventoryManager.getItemBackpack().contains("extintor", false) && !game.getInventoryManager().getItemBackpack().contains("mask1", false)){
            isLareiraAcessa = false;
            collisions.CreateCollisions(1750, 160, "mask1", 203, collisions.getITEM_BIT());
        }

        if (player.getCanChangeRoom()){
            if (direction == "doorUp" && doorNum == 0){
                doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                	game.getAssetManager().load("Tilesets/corredor.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);
                    game.getAssetManager().load("Audio/Sfx/porta trancada.ogg", Sound.class);
                    
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

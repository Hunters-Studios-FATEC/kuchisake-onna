package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.objects.ObjectAnimation;
import com.hunter.game.kuchisake.tools.SpriteItem;

import javax.xml.stream.FactoryConfigurationError;

public class Porao extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;
    
    TextureRegion lampada1;
    TextureRegion lampada2;
    TextureRegion lampada3;
    TextureRegion lampada4;
    TextureRegion escada;
    TextureRegion cach;
    TextureRegion maskara3;

    Sprite cachorro;
    Sprite mask3;
    Sprite lampada;
    Sprite escadaSprite;

    SpriteItem dogeFlutuar;
    SpriteItem maskFlutuar;

    boolean isSecondFloor = false;
    
    ObjectAnimation lampadaAnimation;

    public Porao(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/porao.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(550, 160,"doorDown5", 500, collisions.getPortaBit());
        textureAtlas = game.getAssetManager().get("ScenaryAssets/porao/PoraoObjects.atlas", TextureAtlas.class);

        if (!game.getInventoryManager().getItemBackpack().contains("mask3", false)){
            collisions.CreateCollisions(2700, 160, "mask3", 230, collisions.getITEM_BIT());

            maskara3 = textureAtlas.findRegion("mask3");
            mask3 = new Sprite(maskara3);
            mask3.setSize(mask3.getWidth() / TerrorGame.SCALE, mask3.getHeight() / TerrorGame.SCALE);
            mask3.setPosition(2700 / TerrorGame.SCALE - (mask3.getWidth() / 2), 160 / TerrorGame.SCALE);

            maskFlutuar = new SpriteItem(50f, mask3);
        }

        if (!game.getInventoryManager().getItemBackpack().contains("cachorro", false)){
            collisions.CreateCollisions(1750, 160, "cachorro", 230, collisions.getITEM_BIT());

            cach = textureAtlas.findRegion("cachorro");
            cachorro = new Sprite(cach);
            cachorro.setSize(cachorro.getWidth() / TerrorGame.SCALE, cachorro.getHeight() / TerrorGame.SCALE);
            cachorro.setPosition(1750 / TerrorGame.SCALE - (cachorro.getWidth() / 2), 160 / TerrorGame.SCALE);

            dogeFlutuar = new SpriteItem(50f, cachorro);
        }
        
        lampada1 = textureAtlas.findRegion("lampada1");
        lampada2 = textureAtlas.findRegion("lampada2");
        lampada3 = textureAtlas.findRegion("lampada3");
        lampada4 = textureAtlas.findRegion("lampada4");
        escada = textureAtlas.findRegion("escada");
        
        lampada = new Sprite(lampada1);
        escadaSprite = new Sprite(escada);
        
        lampada.setSize(lampada.getWidth() / TerrorGame.SCALE, lampada.getHeight() / TerrorGame.SCALE);
        lampada.setPosition(2685 / TerrorGame.SCALE - lampada.getWidth() / 2, 1050 / TerrorGame.SCALE - lampada.getHeight());
        
        escadaSprite.setSize(escadaSprite.getWidth() / TerrorGame.SCALE, escadaSprite.getHeight() / TerrorGame.SCALE);
        escadaSprite.setPosition(0, 160 / TerrorGame.SCALE);
        
        lampadaAnimation = new ObjectAnimation(0.2f, new TextureRegion[] {lampada1, lampada2, lampada3, lampada4});

        if(game.getHasEncountered() &&
                (game.getKuchisakeOnna().getCurrentLine() != game.getPlayerLine() ||
                        game.getKuchisakeOnna().getCurrentColumn() != game.getPlayerColumn())) {
            game.getMinigameManager().setMinigameActive(true);
            game.getMinigameManager().startMinigame(0);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);
        game.getKuchisakeOnna().KuchisakeUpdate(delta);

        game.batch.begin();
        
        escadaSprite.draw(game.batch);
        
        if(game.getMinigameManager().getGeradorCompleted()) {
        	lampada.setRegion(lampadaAnimation.changeFrame(delta));
        }
        
        lampada.draw(game.batch);
        if (!game.getInventoryManager().getItemBackpack().contains("mask3", false)){
            mask3.draw(game.batch);
            maskFlutuar.flutar(delta);
        }

        if (!game.getInventoryManager().getItemBackpack().contains("cachorro", false)){
            cachorro.draw(game.batch);
            dogeFlutuar.flutar(delta);
        }

        game.getKuchisakeOnna().draw(game.batch);
        player.draw(game.batch);
        
        game.batch.end();

//        debugRenderer.render(world, camera.combined);
        inventoryManager.inventoryUpdate(delta);
        transitionScene.updateTransition();

		/*for (int i = 0; i < maxMinigameID; i++) {
				minigameManager.minigameUpdate(delta, i);
		}*/

        //inventoryManager.inventoryUpdate(delta);

        game.getMinigameManager().minigameUpdate(delta, 0);

        if (player.getCanChangeRoom()){
            if (direction == "doorDown" && doorNum == 5){
                doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                	game.getAssetManager().load("Tilesets/corredor.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);
                    portaSound.play(0.5f);
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 2f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/porao.tmx");
                    game.getAssetManager().unload("ScenaryAssets/porao/PoraoObjects.atlas");

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(-1);
                    game.setPlayerColumn(5);
                    
                    game.setScreen(new CorredorServico(game, 2900));
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

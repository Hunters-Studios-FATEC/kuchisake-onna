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

public class Sala3 extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion gazua;
    TextureRegion lustre1;
    TextureRegion lustre2;
    TextureRegion lustre3;
    TextureRegion lustre4;

    Sprite porta;
    Sprite gazuaSprite;
    Sprite lustre;

    SpriteItem gazuaItem;
    boolean isSecondFloor = false;
    
    ObjectAnimation lustreAnimation;

    public Sala3(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/sala3.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(483, 160,"doorDown3", 203, collisions.getPortaBit());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/sala_3/Sala3Objects.atlas", TextureAtlas.class);

        if (!game.getInventoryManager().getItemBackpack().contains("gazua", false)) {
            collisions.CreateCollisions(1530, 160, "gazua", 203, collisions.getITEM_BIT());
            
            gazua = textureAtlas.findRegion("gazua");
            
            gazuaSprite = new Sprite(gazua);
            gazuaSprite.setSize(gazuaSprite.getWidth() / (TerrorGame.SCALE * 5), gazuaSprite.getHeight() / (TerrorGame.SCALE * 5));
            gazuaSprite.setPosition(1530 / TerrorGame.SCALE - gazuaSprite.getWidth() / 2, 
            		355 / TerrorGame.SCALE - gazuaSprite.getHeight() *  (48f / 699f));
            gazuaItem = new SpriteItem(50f, gazuaSprite);
        }
        
        portaFechada = textureAtlas.findRegion("portaCorredor1");
        portaAberta = textureAtlas.findRegion("portaCorredor2");
        lustre1 = textureAtlas.findRegion("lustre1");
        lustre2 = textureAtlas.findRegion("lustre2");
        lustre3 = textureAtlas.findRegion("lustre3");
        lustre4 = textureAtlas.findRegion("lustre4");
        
        porta = new Sprite(portaFechada);
        lustre = new Sprite(lustre1);
        
        porta.setSize(porta.getWidth() / TerrorGame.SCALE, porta.getHeight() / TerrorGame.SCALE);
        porta.setPosition(280 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        porta.setAlpha(0.5f);
        
        lustre.setSize(lustre.getWidth() / TerrorGame.SCALE, lustre.getHeight() / TerrorGame.SCALE);
        lustre.setPosition(1750 / TerrorGame.SCALE - lustre.getWidth() / 2, viewport.getWorldHeight() - lustre.getHeight());
        
        lustreAnimation = new ObjectAnimation(0.2f, new TextureRegion[] {lustre1, lustre2, lustre3, lustre4});
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);
        game.getKuchisakeOnna().KuchisakeUpdate(delta);

        game.batch.begin();
        
        if (!game.getInventoryManager().getItemBackpack().contains("gazua", false)) {
        	gazuaSprite.draw(game.batch);
        	gazuaItem.flutar(delta);
        }
        
        game.getKuchisakeOnna().draw(game.batch);
        player.draw(game.batch);
        porta.draw(game.batch);
        
        if(game.getMinigameManager().getGeradorCompleted()) {
        	lustre.setRegion(lustreAnimation.changeFrame(delta));
        }
        
        lustre.draw(game.batch);
        
        game.batch.end();

//        debugRenderer.render(world, camera.combined);
        inventoryManager.inventoryUpdate(delta);
        transitionScene.updateTransition();

		/*for (int i = 0; i < maxMinigameID; i++) {
				minigameManager.minigameUpdate(delta, i);
		}*/

        //inventoryManager.inventoryUpdate(delta);
        if (player.getCanChangeRoom()){
        	if (direction == "doorDown" && doorNum == 3){
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

                    game.getAssetManager().unload("Tilesets/sala3.tmx");
                    game.getAssetManager().unload("ScenaryAssets/sala_3/Sala3Objects.atlas");

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(-1);
                    game.setPlayerColumn(3);
                    
                    game.setScreen(new CorredorSalas(game, 971));
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

package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.objects.ObjectAnimation;

import javax.xml.stream.FactoryConfigurationError;

public class Sala2 extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion fios;
    TextureRegion lustre1;
    TextureRegion lustre2;
    TextureRegion lustre3;
    TextureRegion lustre4;

    Sprite porta;
    Sprite fiosSprite;
    Sprite lustre;

    boolean isSecondFloor = false;
    
    ObjectAnimation lustreAnimation;

    public Sala2(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/sala2.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(2891, 160,"doorDown2", 203, collisions.getPortaBit());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/sala_2/Sala2Objects.atlas", TextureAtlas.class);

        if (!game.getInventoryManager().getItemBackpack().contains("fiosItem", false)) {
            collisions.CreateCollisions(1720, 160, "fiosItem", 203, collisions.getITEM_BIT());
            
            fios = textureAtlas.findRegion("fiosItem");
            fiosSprite = new Sprite(fios);
            fiosSprite.setSize(fiosSprite.getWidth() / (TerrorGame.SCALE * 2), fiosSprite.getHeight() / (TerrorGame.SCALE * 2));
            fiosSprite.setPosition(1720 / TerrorGame.SCALE - fiosSprite.getWidth() / 2, 155 / TerrorGame.SCALE);
        }

        portaFechada = textureAtlas.findRegion("porta1");
        portaAberta = textureAtlas.findRegion("porta2");
        lustre1 = textureAtlas.findRegion("lustre1");
        lustre2 = textureAtlas.findRegion("lustre2");
        lustre3 = textureAtlas.findRegion("lustre3");
        lustre4 = textureAtlas.findRegion("lustre4");
        
        porta = new Sprite(portaFechada);
        lustre = new Sprite(lustre1);
        
        porta.setSize(porta.getWidth() * 1.45f / TerrorGame.SCALE, porta.getHeight() * 1.45f / TerrorGame.SCALE);
        porta.setPosition((3500 - 812) / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
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
        
        if (!game.getInventoryManager().getItemBackpack().contains("fiosItem", false)) {
        	fiosSprite.draw(game.batch);
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
        if (player.getCanChangeRoom()){
            if (direction == "doorDown" && doorNum == 2){
                doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                	game.getAssetManager().load("Tilesets/saguao_segundo.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/saguao/SaguaoObjects.atlas", TextureAtlas.class);
                    
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 1.5f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/sala2.tmx");
                    game.getAssetManager().unload("ScenaryAssets/sala_2/Sala2Objects.atlas");

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(-1);
                    game.setPlayerColumn(2);
                    
                    game.setScreen(new Saguao(game, 2800 + 280f / 2f, true));
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

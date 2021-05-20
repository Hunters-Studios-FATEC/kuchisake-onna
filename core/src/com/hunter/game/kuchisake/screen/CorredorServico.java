package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.objects.ObjectAnimation;
import com.hunter.game.kuchisake.tools.SpriteItem;

import javax.xml.stream.FactoryConfigurationError;

public class CorredorServico extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion estatua;
    TextureRegion extintor;
    TextureRegion lustreCorredor1;
    TextureRegion lustreCorredor2;
    TextureRegion lustreCorredor3;
    TextureRegion lustreCorredor4;

    Sprite estatua1;
    Sprite estatua2;
    Sprite porta1;
    Sprite porta2;
    Sprite extintorSprite;
    Sprite lustreSprite1;
    Sprite lustreSprite2;

    SpriteItem extintorFlutuar;

    Sound portraTrancada;
    
    ObjectAnimation lustreAnimation;

    public CorredorServico(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/corredor.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(600, 160,"doorDown2", 230, collisions.getPortaBit());
        collisions.CreateCollisions(2900, 160,"doorUp6", 230, collisions.getPortaBit());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);

        if (!game.getInventoryManager().getItemBackpack().contains("extintor", false)) {
            collisions.CreateCollisions(1750, 160, "extintor", 203, collisions.getITEM_BIT());
            
            extintor = textureAtlas.findRegion("extintor");
            
            extintorSprite = new Sprite(extintor);
            extintorSprite.setSize(extintorSprite.getWidth() / (TerrorGame.SCALE * 1.4f), extintorSprite.getHeight() / (TerrorGame.SCALE * 1.4f));
            extintorSprite.setPosition(1750 / TerrorGame.SCALE - extintorSprite.getWidth() / 2, 160 / TerrorGame.SCALE);
            extintorFlutuar = new SpriteItem(50f, extintorSprite);
        }
        
        portaFechada = textureAtlas.findRegion("portaCorredor1");
        portaAberta = textureAtlas.findRegion("portaCorredor2");
        estatua = textureAtlas.findRegion("estatua");
        lustreCorredor1 = textureAtlas.findRegion("lustre corredor1");
        lustreCorredor2 = textureAtlas.findRegion("lustre corredor2");
        lustreCorredor3 = textureAtlas.findRegion("lustre corredor3");
        lustreCorredor4 = textureAtlas.findRegion("lustre corredor4");
        
        estatua1 = new Sprite(estatua);
        estatua2 = new Sprite(estatua);
        porta1 = new Sprite(portaFechada);
        porta2 = new Sprite(portaFechada);
        lustreSprite1 = new Sprite(lustreCorredor1);
        lustreSprite2 = new Sprite(lustreCorredor1);
        
        estatua1.setSize(estatua1.getWidth() / TerrorGame.SCALE, estatua1.getHeight() / TerrorGame.SCALE);
        estatua1.setPosition(1030 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        
        estatua2.setSize(estatua2.getWidth() / TerrorGame.SCALE, estatua2.getHeight() / TerrorGame.SCALE);
        estatua2.setPosition(2145 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        
        porta1.setSize(porta1.getWidth() / TerrorGame.SCALE, porta1.getHeight() / TerrorGame.SCALE);
        porta1.setPosition(370 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        
        porta2.setSize(porta2.getWidth() / TerrorGame.SCALE, porta2.getHeight() / TerrorGame.SCALE);
        porta2.setPosition(2670 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        
        lustreSprite1.setSize(lustreSprite1.getWidth() / TerrorGame.SCALE, lustreSprite1.getHeight() / TerrorGame.SCALE);
        lustreSprite1.setPosition(70 / TerrorGame.SCALE, 700 / TerrorGame.SCALE);
        
        lustreSprite2.setSize(lustreSprite2.getWidth() / TerrorGame.SCALE, lustreSprite2.getHeight() / TerrorGame.SCALE);
        lustreSprite2.setPosition(3230 / TerrorGame.SCALE, 700 / TerrorGame.SCALE);
        
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
        
        if (!game.getInventoryManager().getItemBackpack().contains("extintor", false)) {
        	extintorSprite.draw(game.batch);
        	extintorFlutuar.flutar(delta);
        }
        
        estatua1.draw(game.batch);
        estatua2.draw(game.batch);
        porta1.draw(game.batch);
        porta2.draw(game.batch);
        
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
            if (direction == "doorDown" && doorNum == 2) {
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
		
		            game.getAssetManager().unload("Tilesets/corredor.tmx");
		            game.getAssetManager().unload("ScenaryAssets/corredor/CorredorObjects.atlas");

		
		            game.getAssetManager().finishLoading();
		            game.incrementPlayerLine(-1);
		            game.setPlayerColumn(2);
		            
		            game.setScreen(new Cozinha(game, 3143));
                }
            }
	        else if (direction == "doorUp" && doorNum == 6) {
	            if (inventoryManager.getItemBackpack().contains("chavePorao", false)) {
                    doorAnimationTimer += delta;
                    transitionScene.fadeIn();

                    if (!canSwitchAssets) {
                        game.getAssetManager().load("Tilesets/porao.tmx", TiledMap.class);
                        game.getAssetManager().load("ScenaryAssets/porao/PoraoObjects.atlas", TextureAtlas.class);
                        portaSound.play(0.5f);
                        canSwitchAssets = true;
                    }

                    if (doorAnimationTimer > 2f) {
                        dispose();

                        game.getAssetManager().unload("Tilesets/corredor.tmx");
                        game.getAssetManager().unload("ScenaryAssets/corredor/CorredorObjects.atlas");
                        game.getAssetManager().unload("Audio/Sfx/porta trancada.ogg");

                        game.getAssetManager().finishLoading();
                        game.incrementPlayerLine(1);
                        game.setPlayerColumn(6);

                        game.setScreen(new Porao(game, 2810));
                    }
                } else {
                    portraTrancada.setVolume(portraTrancada.play(), 0.5f);
                    player.setCanChangeRoom(false);
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

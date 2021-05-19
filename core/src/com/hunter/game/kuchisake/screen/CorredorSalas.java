package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.objects.ObjectAnimation;

import javax.xml.stream.FactoryConfigurationError;

public class CorredorSalas extends StandardRoom implements Screen {

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
    Sprite estatua1;
    Sprite estatua2;
    Sprite estatua3;
    Sprite lustreSprite1;
    Sprite lustreSprite2;
    Sprite lustreSprite3;
    Sprite lustreSprite4;

    //boolean isSecondFloor = false;
    
    ObjectAnimation lustreAnimation;

    public CorredorSalas(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/corredor.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(971, 160,"doorUp4", 230, collisions.getPortaBit());
        collisions.CreateCollisions(2487, 160,"doorUp5", 230, collisions.getPortaBit());
        
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
        estatua1 = new Sprite(estatua);
        estatua2 = new Sprite(estatua);
        estatua3 = new Sprite(estatua);
        lustreSprite1 = new Sprite(lustreCorredor1);
        lustreSprite2 = new Sprite(lustreCorredor1);
        lustreSprite3 = new Sprite(lustreCorredor1);
        lustreSprite4 = new Sprite(lustreCorredor1);
        
        porta1.setSize(porta1.getWidth() / TerrorGame.SCALE, porta1.getHeight() / TerrorGame.SCALE);
        porta1.setPosition(741 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        
        porta2.setSize(porta2.getWidth() / TerrorGame.SCALE, porta2.getHeight() / TerrorGame.SCALE);
        porta2.setPosition(2257 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        
        estatua1.setSize(estatua1.getWidth() / TerrorGame.SCALE, estatua1.getHeight() / TerrorGame.SCALE);
        estatua1.setPosition(91 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        
        estatua2.setSize(estatua2.getWidth() / TerrorGame.SCALE, estatua2.getHeight() / TerrorGame.SCALE);
        estatua2.setPosition(1750 / TerrorGame.SCALE - estatua2.getWidth() / 2, 160 / TerrorGame.SCALE);
        
        estatua3.setSize(estatua3.getWidth() / TerrorGame.SCALE, estatua3.getHeight() / TerrorGame.SCALE);
        estatua3.setPosition(3128 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        
        lustreSprite1.setSize(lustreSprite1.getWidth() / TerrorGame.SCALE, lustreSprite1.getHeight() / TerrorGame.SCALE);
        lustreSprite1.setPosition(441 / TerrorGame.SCALE, 700 / TerrorGame.SCALE);
        
        lustreSprite2.setSize(lustreSprite2.getWidth() / TerrorGame.SCALE, lustreSprite2.getHeight() / TerrorGame.SCALE);
        lustreSprite2.setPosition(1301 / TerrorGame.SCALE, 700 / TerrorGame.SCALE);
        
        lustreSprite3.setSize(lustreSprite3.getWidth() / TerrorGame.SCALE, lustreSprite3.getHeight() / TerrorGame.SCALE);
        lustreSprite3.setPosition(1957 / TerrorGame.SCALE, 700 / TerrorGame.SCALE);
        
        lustreSprite4.setSize(lustreSprite4.getWidth() / TerrorGame.SCALE, lustreSprite4.getHeight() / TerrorGame.SCALE);
        lustreSprite4.setPosition(2817 / TerrorGame.SCALE, 700 / TerrorGame.SCALE);
        
        lustreAnimation = new ObjectAnimation(0.2f, 
        		new TextureRegion[] {lustreCorredor1, lustreCorredor2, lustreCorredor3, lustreCorredor4});
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
        	lustreSprite3.setRegion(lustreAnimation.changeFrame(delta));
        	lustreSprite4.setRegion(lustreAnimation.changeFrame(delta));
        }
        
        if(!lustreSprite2.isFlipX()) {
        	lustreSprite2.flip(true, false);
        }
        
        if(!lustreSprite4.isFlipX()) {
        	lustreSprite4.flip(true, false);
        }

        game.batch.begin();
        
        estatua1.draw(game.batch);
        estatua2.draw(game.batch);
        estatua3.draw(game.batch);
        porta1.draw(game.batch);
        porta2.draw(game.batch);
        
        lustreSprite1.draw(game.batch);
        lustreSprite2.draw(game.batch);
        lustreSprite3.draw(game.batch);
        lustreSprite4.draw(game.batch);
        
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
        /*if (player.getCanChangeRoom()){
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
        	if (direction == "doorUp" && doorNum == 4){
                doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                    game.getAssetManager().load("Tilesets/sala3.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/sala_3/Sala3Objects.atlas", TextureAtlas.class);
                    
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 1.5f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/corredor.tmx");
                    game.getAssetManager().unload("ScenaryAssets/corredor/CorredorObjects.atlas");

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(1);
                    game.setPlayerColumn(4);
                    
                    game.setScreen(new Sala3(game, 483));
                }
            }
        	else if (direction == "doorUp" && doorNum == 5){
                doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                    game.getAssetManager().load("Tilesets/salaVitima1.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/salaVitima1/SalaVitima1Objects.atlas", TextureAtlas.class);
                    
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 1.5f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/corredor.tmx");
                    game.getAssetManager().unload("ScenaryAssets/corredor/CorredorObjects.atlas");

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(1);
                    game.setPlayerColumn(5);
                    
                    game.setScreen(new SalaVitima1(game, 2891));
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

                game.getAssetManager().unload("Tilesets/corredor.tmx");
                game.getAssetManager().unload("ScenaryAssets/corredor/CorredorObjects.atlas");

                game.getAssetManager().finishLoading();
                game.setPlayerColumn(2);
                
                game.setScreen(new Saguao(game, 3500 - 128, true));
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

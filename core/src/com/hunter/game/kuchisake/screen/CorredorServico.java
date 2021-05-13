package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;

import javax.xml.stream.FactoryConfigurationError;

public class CorredorServico extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion estatua;

    Sprite estatua1;
    Sprite estatua2;
    Sprite porta1;
    Sprite porta2;

    public CorredorServico(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/corredor.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(460, 160,"doorDown2", 230, collisions.getPortaBit());
        collisions.CreateCollisions(3040, 160,"doorUp6", 230, collisions.getPortaBit());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);
        portaFechada = textureAtlas.findRegion("portaCorredor1");
        portaAberta = textureAtlas.findRegion("portaCorredor2");
        estatua = textureAtlas.findRegion("estatua");
        
        estatua1 = new Sprite(estatua);
        estatua2 = new Sprite(estatua);
        porta1 = new Sprite(portaFechada);
        porta2 = new Sprite(portaFechada);
        
        estatua1.setSize(estatua1.getWidth() / TerrorGame.SCALE, estatua1.getHeight() / TerrorGame.SCALE);
        estatua1.setPosition(492 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        
        estatua2.setSize(estatua2.getWidth() / TerrorGame.SCALE, estatua2.getHeight() / TerrorGame.SCALE);
        estatua2.setPosition((3500 - 782) / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        
        porta1.setSize(porta1.getWidth() / TerrorGame.SCALE, porta1.getHeight() / TerrorGame.SCALE);
        porta1.setPosition(230 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        
        porta2.setSize(porta2.getWidth() / TerrorGame.SCALE, porta2.getHeight() / TerrorGame.SCALE);
        porta2.setPosition((3500 - 690) / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);
        game.getKuchisakeOnna().KuchisakeUpdate(delta);

        game.batch.begin();
        
        estatua1.draw(game.batch);
        estatua2.draw(game.batch);
        porta1.draw(game.batch);
        porta2.draw(game.batch);
        game.getKuchisakeOnna().draw(game.batch);
        player.draw(game.batch);
        
        game.batch.end();

        debugRenderer.render(world, camera.combined);
        inventoryManager.inventoryUpdate(delta);

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
            	
                if(doorAnimationTimer > 1.5f){
		        	dispose();
		
		            game.getAssetManager().unload("Tilesets/corredor.tmx");
		            game.getAssetManager().unload("ScenaryAssets/corredor/CorredorObjects.atlas");
		            
		            game.getAssetManager().load("Tilesets/sala1.tmx", TiledMap.class);
		            game.getAssetManager().load("ScenaryAssets/sala_1/Sala1Objects.atlas", TextureAtlas.class);
		
		            game.getAssetManager().finishLoading();
		            game.incrementPlayerLine(-1);
		            game.setPlayerColumn(2);
		            
		            game.setScreen(new Cozinha(game, 3143));
                }
            }
	        else if (direction == "doorUp" && doorNum == 6) {
	        	doorAnimationTimer += delta;
	        	
                if(doorAnimationTimer > 1.5f){
		        	dispose();
		
		            game.getAssetManager().unload("Tilesets/corredor.tmx");
		            game.getAssetManager().unload("ScenaryAssets/corredor/CorredorObjects.atlas");
		            
		            game.getAssetManager().load("Tilesets/quarto.tmx", TiledMap.class);
		            game.getAssetManager().load("ScenaryAssets/quarto/QuartoObjects.atlas", TextureAtlas.class);
		
		            game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(1);
                    game.setPlayerColumn(6);
                    
		            game.setScreen(new Porao(game, 2810));
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

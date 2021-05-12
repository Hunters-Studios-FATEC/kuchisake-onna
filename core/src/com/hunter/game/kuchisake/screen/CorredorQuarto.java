package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;

import javax.xml.stream.FactoryConfigurationError;

public class CorredorQuarto extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion estatua;

    Sprite porta;
    Sprite estatua1;
    Sprite estatua2;

    public CorredorQuarto(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/corredor.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(1750, 160,"doorUp1", 230, collisions.getPortaBit());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);
        portaFechada = textureAtlas.findRegion("portaCorredor1");
        portaAberta = textureAtlas.findRegion("portaCorredor2");
        estatua = textureAtlas.findRegion("estatua");
        
        porta = new Sprite(portaFechada);
        estatua1 = new Sprite(estatua);
        estatua2 = new Sprite(estatua);
        
        porta.setSize(porta.getWidth() / TerrorGame.SCALE, porta.getHeight() / TerrorGame.SCALE);
        porta.setPosition(1750 / TerrorGame.SCALE - porta.getWidth() / 2, 160 / TerrorGame.SCALE);
        
        estatua1.setSize(estatua1.getWidth() / TerrorGame.SCALE, estatua1.getHeight() / TerrorGame.SCALE);
        estatua1.setPosition(492 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        
        estatua2.setSize(estatua2.getWidth() / TerrorGame.SCALE, estatua2.getHeight() / TerrorGame.SCALE);
        estatua2.setPosition((3500 - 782) / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
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
        porta.draw(game.batch);
        game.getKuchisakeOnna().draw(game.batch);
        player.draw(game.batch);
        
        game.batch.end();

        debugRenderer.render(world, camera.combined);

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
                doorAnimationTimer += delta;
                if(doorAnimationTimer > 1.5f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/corredor.tmx");
                    game.getAssetManager().unload("ScenaryAssets/corredor/CorredorObjects.atlas");
                    
                    game.getAssetManager().load("Tilesets/quarto.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/quarto/QuartoObjects.atlas", TextureAtlas.class);

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(1);
                    game.setPlayerColumn(1);
                    
                    game.setScreen(new Quarto(game, 2810));
                }
            }
        }
        
        if(player.getBody().getPosition().x > mapWidth) {
        	dispose();

            game.getAssetManager().unload("Tilesets/corredor.tmx");
            game.getAssetManager().unload("ScenaryAssets/corredor/CorredorObjects.atlas");
            
            game.getAssetManager().load("Tilesets/saguao_segundo.tmx", TiledMap.class);
            game.getAssetManager().load("ScenaryAssets/saguao/SaguaoObjects.atlas", TextureAtlas.class);

            game.getAssetManager().finishLoading();
            game.setPlayerColumn(2);
            game.setScreen(new Saguao(game, 128, true));
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

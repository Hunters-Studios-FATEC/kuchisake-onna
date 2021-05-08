package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;

import javax.xml.stream.FactoryConfigurationError;

public class CorredorSalas extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion estatua;

    Sprite porta1;
    Sprite porta2;
    Sprite estatua1;
    Sprite estatua2;
    Sprite estatua3;

    //boolean isSecondFloor = false;

    public CorredorSalas(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/corredor.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(971, 160,"doorUp3", 230, collisions.getPortaBit());
        collisions.CreateCollisions(2487, 160,"doorUp4", 230, collisions.getPortaBit());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);
        portaFechada = textureAtlas.findRegion("portaCorredor1");
        portaAberta = textureAtlas.findRegion("portaCorredor2");
        estatua = textureAtlas.findRegion("estatua");
        
        porta1 = new Sprite(portaFechada);
        porta2 = new Sprite(portaFechada);
        estatua1 = new Sprite(estatua);
        estatua2 = new Sprite(estatua);
        estatua3 = new Sprite(estatua);
        
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
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);

        game.batch.begin();
        
        estatua1.draw(game.batch);
        estatua2.draw(game.batch);
        estatua3.draw(game.batch);
        porta1.draw(game.batch);
        porta2.draw(game.batch);
        player.draw(game.batch);
        
        game.batch.end();

        debugRenderer.render(world, camera.combined);

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
        	if (direction == "doorUp" && doorNum == 3){
                System.out.println("muda porra");
                
                doorAnimationTimer += delta;
                if(doorAnimationTimer > 1.5f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/corredor.tmx");
                    game.getAssetManager().unload("ScenaryAssets/corredor/CorredorObjects.atlas");
                    
                    game.getAssetManager().load("Tilesets/sala1.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/sala_1/Sala1Objects.atlas", TextureAtlas.class);

                    game.getAssetManager().finishLoading();

                    game.setScreen(new Sala3(game, 483));
                }
            }
        	else if (direction == "doorUp" && doorNum == 4){
                System.out.println("muda porra");
                
                doorAnimationTimer += delta;
                if(doorAnimationTimer > 1.5f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/corredor.tmx");
                    game.getAssetManager().unload("ScenaryAssets/corredor/CorredorObjects.atlas");
                    
                    game.getAssetManager().load("Tilesets/sala2.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/sala_2/Sala2Objects.atlas", TextureAtlas.class);

                    game.getAssetManager().finishLoading();

                    game.setScreen(new SalaVitima1(game, 2891));
                }
            }
        }
        
        if(player.getBody().getPosition().x < 0) {
        	dispose();

            game.getAssetManager().unload("Tilesets/corredor.tmx");
            game.getAssetManager().unload("ScenaryAssets/corredor/CorredorObjects.atlas");
            
            game.getAssetManager().load("Tilesets/saguao_segundo.tmx", TiledMap.class);
            game.getAssetManager().load("ScenaryAssets/saguao/SaguaoObjects.atlas", TextureAtlas.class);

            game.getAssetManager().finishLoading();

            game.setScreen(new Saguao(game, 3500 - 128, true));
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

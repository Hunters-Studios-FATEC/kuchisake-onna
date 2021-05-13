package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;

import javax.xml.stream.FactoryConfigurationError;

public class Sala1 extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;

    Sprite porta;

    boolean isSecondFloor = false;

    public Sala1(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/sala1.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(483, 160,"doorDown2", 203, collisions.getPortaBit());
        collisions.CreateCollisions(1750, 160, "chaveServico", 203, collisions.getITEM_BIT());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/sala_1/Sala1Objects.atlas", TextureAtlas.class);
        portaFechada = textureAtlas.findRegion("porta1");
        portaAberta = textureAtlas.findRegion("porta2");
        
        porta = new Sprite(portaFechada);
        
        porta.setSize(porta.getWidth() * 1.45f / TerrorGame.SCALE, porta.getHeight() * 1.45f / TerrorGame.SCALE);
        porta.setPosition(280 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        porta.setAlpha(0.5f);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);
        game.getKuchisakeOnna().KuchisakeUpdate(delta);

        game.batch.begin();
        game.getKuchisakeOnna().draw(game.batch);
        player.draw(game.batch);
        porta.draw(game.batch);
        
        game.batch.end();

        debugRenderer.render(world, camera.combined);
        inventoryManager.inventoryUpdate(delta);

		/*for (int i = 0; i < maxMinigameID; i++) {
				minigameManager.minigameUpdate(delta, i);
		}*/

        //inventoryManager.inventoryUpdate(delta);
        
        if (player.getCanChangeRoom()){
            if (direction == "doorDown" && doorNum == 2){
                doorAnimationTimer += delta;
                if(doorAnimationTimer > 1.5f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/sala1.tmx");
                    game.getAssetManager().unload("ScenaryAssets/sala_1/Sala1Objects.atlas");
                    
                    game.getAssetManager().load("Tilesets/saguao_segundo.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/saguao/SaguaoObjects.atlas", TextureAtlas.class);

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(-1);
                    game.setPlayerColumn(2);
                    
                    game.setScreen(new Saguao(game, 420 + 280f / 2f, true));
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

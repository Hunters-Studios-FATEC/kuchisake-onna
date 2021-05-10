package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;

import javax.xml.stream.FactoryConfigurationError;

public class AreaServico extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion caixaEnergia;

    Sprite porta;
    Sprite quadroEnergia;

    boolean isSecondFloor = false;

    public AreaServico(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/sala2.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(2891, 160,"doorDown2", 203, collisions.getPortaBit());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/sala_2/Sala2Objects.atlas", TextureAtlas.class);
        portaFechada = textureAtlas.findRegion("porta1");
        portaAberta = textureAtlas.findRegion("porta2");
        caixaEnergia = textureAtlas.findRegion("quadro_forca");
        
        porta = new Sprite(portaFechada);
        quadroEnergia = new Sprite(caixaEnergia);
        
        porta.setSize(porta.getWidth() * 1.45f / TerrorGame.SCALE, porta.getHeight() * 1.45f / TerrorGame.SCALE);
        porta.setPosition((3500 - 812) / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        porta.setAlpha(0.5f);
        
        quadroEnergia.setSize(quadroEnergia.getWidth() / TerrorGame.SCALE, quadroEnergia.getHeight() / TerrorGame.SCALE);
        quadroEnergia.setPosition(51 / TerrorGame.SCALE, 476 / TerrorGame.SCALE);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);

        game.batch.begin();
        
        quadroEnergia.draw(game.batch);
        player.draw(game.batch);
        porta.draw(game.batch);
        
        game.batch.end();

        debugRenderer.render(world, camera.combined);

		/*for (int i = 0; i < maxMinigameID; i++) {
				minigameManager.minigameUpdate(delta, i);
		}*/

        //inventoryManager.inventoryUpdate(delta);
        if (player.getCanChangeRoom()){
            if (direction == "doorDown" && doorNum == 2){
                System.out.println("muda porra");
                doorAnimationTimer += delta;
                if(doorAnimationTimer > 1.5f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/sala2.tmx");
                    game.getAssetManager().unload("ScenaryAssets/sala_2/Sala2Objects.atlas");
                    
                    game.getAssetManager().load("Tilesets/sala1.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/sala_1/Sala1Objects.atlas", TextureAtlas.class);

                    game.getAssetManager().finishLoading();

                    game.setScreen(new Cozinha(game, 483));
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
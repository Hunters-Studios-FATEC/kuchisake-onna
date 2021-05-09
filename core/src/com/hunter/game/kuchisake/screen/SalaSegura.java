package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;

public class SalaSegura extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;

    Sprite porta;

    boolean isSecondFloor = false;

    public SalaSegura(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/quarto.tmx", playerDoorPosX);

        collisions.CreateCollisions(1900+230, 160,"doorDown0", 203, collisions.getPortaBit());

        textureAtlas = game.getAssetManager().get("ScenaryAssets/quarto/QuartoObjects.atlas", TextureAtlas.class);
        portaFechada = textureAtlas.findRegion("portaCorredor1");
        portaAberta = textureAtlas.findRegion("portaCorredor2");

        porta = new Sprite(portaFechada);

        porta.setSize(porta.getWidth() / TerrorGame.SCALE, porta.getHeight() / TerrorGame.SCALE);
        porta.setPosition(1900 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        porta.setAlpha(0.5f);
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);

        game.batch.begin();

        player.draw(game.batch);
        porta.draw(game.batch);

        game.batch.end();

        debugRenderer.render(world, camera.combined);

		/*for (int i = 0; i < maxMinigameID; i++) {
				minigameManager.minigameUpdate(delta, i);
		}*/

        //inventoryManager.inventoryUpdate(delta);
        if (player.getCanChangeRoom()){
            if (direction == "doorDown" && doorNum == 0){
                System.out.println("Indo Corredor Biblioteca");

                doorAnimationTimer += delta;
                if(doorAnimationTimer > 1.5f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/quarto.tmx");
                    game.getAssetManager().unload("ScenaryAssets/quarto/QuartoObjects.atlas");

                    game.getAssetManager().load("Tilesets/corredor.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);

                    game.getAssetManager().finishLoading();

                    game.setScreen(new CorredorBiblioteca(game, 1900+230));
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

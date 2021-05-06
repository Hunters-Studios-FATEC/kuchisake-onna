package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;

public class Sala01 extends StandardRoom implements Screen {

    Sprite porta1;


    public Sala01(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/corredor.tmx", playerDoorPosX);
        collisions.CreateCollisions(1750, "doorUp1",280, collisions.getPortaBit());

        porta1 = new Sprite(portaFechada);
        porta1.setSize((porta1.getWidth() / TerrorGame.SCALE) * 1.5f, (porta1.getHeight() / TerrorGame.SCALE) * 1.5f);
        porta1.setPosition(1750 / TerrorGame.SCALE - porta1.getWidth() / 2, 160 / TerrorGame.SCALE);
    }



    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);

        game.batch.begin();
        porta1.draw(game.batch);
        player.draw(game.batch);
        game.batch.end();

		debugRenderer.render(world, camera.combined);

		/*for (int i = 0; i < maxMinigameID; i++) {
				minigameManager.minigameUpdate(delta, i);
		}*/

		//inventoryManager.inventoryUpdate(delta);

        if (player.getCanChangeRoom()){
            if (direction == "doorUp" && doorNum == 1){
                porta1.setTexture(portaAberta);
                System.out.println("muda porra");
                doorAnimationTimer += delta;
                if(doorAnimationTimer > 1.5f){
                    dispose();
                    
                    game.getAssetManager().unload("Tilesets/corredor.tmx");
                	game.getAssetManager().load("Tilesets/saguao.tmx", TiledMap.class);
                	
                	game.getAssetManager().finishLoading();
                	
                    game.setScreen(new Sala02(game, 1750));
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

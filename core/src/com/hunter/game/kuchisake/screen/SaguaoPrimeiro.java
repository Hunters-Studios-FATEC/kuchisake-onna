package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;

public class SaguaoPrimeiro extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    public SaguaoPrimeiro(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/saguao_primeiro.tmx", playerDoorPosX);
        collisions.CreateCollisions(1750,425, "DoorUp1",320, collisions.getPortaBit());
        textureAtlas = new TextureAtlas("ScenaryAssets/SaguaoPrimeiroPack.atlas");

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);

        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        debugRenderer.render(world, camera.combined);

		/*for (int i = 0; i < maxMinigameID; i++) {
				minigameManager.minigameUpdate(delta, i);
		}*/

        //inventoryManager.inventoryUpdate(delta);

        if (player.getCanChangeRoom()){
            if (direction == "doorUp" && doorNum == 1){
                System.out.println("muda porra");
                doorAnimationTimer += delta;
                if(doorAnimationTimer > 1.5f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/corredor.tmx");
                    game.getAssetManager().load("Tilesets/saguao.tmx", TiledMap.class);

                    game.getAssetManager().finishLoading();

                    //game.setScreen(new Sala02(game, 1750));
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

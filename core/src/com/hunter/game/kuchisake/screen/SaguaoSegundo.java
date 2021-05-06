package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;

public class SaguaoSegundo extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion corrimao;
    TextureRegion portaAberta;
    TextureRegion portaFechada;

    Sprite corri;
    Sprite porta1;
    Sprite porta2;

    public SaguaoSegundo(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/saguao_segundo.tmx",3, 160 + 750 - 128, playerDoorPosX);
        collisions.CreateCollisions(1750, "DoorUp1",320, collisions.getPortaBit());

        textureAtlas = game.getAssetManager().get("ScenaryAssets/SaguaoPrimeiroPack.atlas", TextureAtlas.class);
        corrimao = textureAtlas.findRegion("corrimao");
        portaAberta = textureAtlas.findRegion("porta1");
        portaFechada = textureAtlas.findRegion("porta2");

        corri = new Sprite(corrimao);
        porta1 = new Sprite(portaFechada);
        porta2 = new Sprite(portaFechada);

        corri.setSize(corri.getWidth() / TerrorGame.SCALE, corri.getHeight() / TerrorGame.SCALE);
        corri.setPosition(0, 10.46f - corri.getHeight());

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);

        game.batch.begin();
        player.draw(game.batch);
        corri.draw(game.batch);
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

package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;

import javax.xml.stream.FactoryConfigurationError;

public class Saguao extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion corrimao;
    TextureRegion portaAberta;
    TextureRegion portaFechada;

    Sprite corri;
    Sprite porta1;
    Sprite porta2;

    boolean isSecondFloor = false;

    public Saguao(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/saguao_segundo.tmx", playerDoorPosX);
        collisions.CreateCollisions(1750, 160,"doorUp1",320, collisions.getPortaBit());
        collisions.CreateCollisions(1750, 585,"doorDown1",320, collisions.getPortaBit());
//        collisions.CreateCollisions();
//        collisions.CreateCollisions();

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
        if (isSecondFloor){ ;
            player.draw(game.batch);
            corri.draw(game.batch);
        }else {
            corri.draw(game.batch);
            player.draw(game.batch);
        }
        game.batch.end();

        debugRenderer.render(world, camera.combined);

		/*for (int i = 0; i < maxMinigameID; i++) {
				minigameManager.minigameUpdate(delta, i);
		}*/

        //inventoryManager.inventoryUpdate(delta);
        if (player.getCanChangeRoom()){
            if (direction == "doorUp" && doorNum == 1){
                isSecondFloor = true;
                player.setSizeAndPosition(3, 4.25f);
//                System.out.println("subir");
                player.setCanChangeRoom(false);
            }

            if (direction == "doorDown" && doorNum == 1){
                isSecondFloor = false;
                player.setSizeAndPosition(5,-4.25f);
//                System.out.println("descer");
                player.setCanChangeRoom(false);

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

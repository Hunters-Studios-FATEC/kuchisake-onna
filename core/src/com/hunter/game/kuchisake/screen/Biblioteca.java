package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.tools.MinigameManager;

public class Biblioteca extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion estanteTexture;

    Sprite porta1;
    Sprite porta2;

    Sprite estante;

    boolean isSecondFloor = false;
    MinigameManager minigameManager;

    public Biblioteca(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/sala1.tmx", playerDoorPosX);

        minigameManager = game.getMinigameManager();

        collisions.CreateCollisions(971+230f, 160,"doorDown0", 230, collisions.getPortaBit());
        collisions.CreateCollisions(2487+230f, 160,"doorUp0", 203, collisions.getPortaBit());
        collisions.CreateCollisions(2487+230f, 160,"bookshelf", 203, collisions.getShelfBit());

        if (!game.getInventoryManager().getItemBackpack().contains("livro3", false)) {
            collisions.CreateCollisions(875, 160, "livro3", 203, collisions.getITEM_BIT());
        }

        textureAtlas = game.getAssetManager().get("ScenaryAssets/quarto/QuartoObjects.atlas", TextureAtlas.class);
        portaFechada = textureAtlas.findRegion("portaCorredor1");
        portaAberta = textureAtlas.findRegion("portaCorredor2");

        porta1 = new Sprite(portaFechada);
        porta2 = new Sprite(portaFechada);

        porta1.setSize(porta1.getWidth() / TerrorGame.SCALE, porta1.getHeight() / TerrorGame.SCALE);
        porta1.setPosition(971 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        porta1.setAlpha(0.5f);

        porta2.setSize(porta2.getWidth() / TerrorGame.SCALE, porta2.getHeight() / TerrorGame.SCALE);
        porta2.setPosition(2487 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);
        game.getKuchisakeOnna().KuchisakeUpdate(delta);

        game.batch.begin();

        porta2.draw(game.batch);
        game.getKuchisakeOnna().draw(game.batch);
        player.draw(game.batch);
        porta1.draw(game.batch);

        game.batch.end();

        debugRenderer.render(world, camera.combined);
        inventoryManager.inventoryUpdate(delta);
        minigameManager.minigameUpdate(delta, 2);
        transitionScene.updateTransition();

		/*for (int i = 0; i < maxMinigameID; i++) {
				minigameManager.minigameUpdate(delta, i);
		}*/

        //inventoryManager.inventoryUpdate(delta);
        if (player.getCanChangeRoom()){
            if (direction == "doorDown" && doorNum == 0){
                doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                	game.getAssetManager().load("Tilesets/corredor.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);
                    
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 1.5f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/sala1.tmx");
                    game.getAssetManager().unload("ScenaryAssets/quarto/QuartoObjects.atlas");

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(-1);
                    game.setPlayerColumn(0);
                    
                    game.setScreen(new CorredorBiblioteca(game, 971));
                }

            } else if (direction == "doorUp" && doorNum == 0){
                if (game.getMinigameManager().getBookCompleted()){
                    doorAnimationTimer += delta;
                    transitionScene.fadeIn();

                    if(!canSwitchAssets) {
                        game.getAssetManager().load("Tilesets/sala2.tmx", TiledMap.class);
                        game.getAssetManager().load("ScenaryAssets/sala_2/Sala2Objects.atlas", TextureAtlas.class);

                        canSwitchAssets = true;
                    }

                    if(doorAnimationTimer > 1.5f){
                        dispose();

                        game.getAssetManager().unload("Tilesets/sala1.tmx");
                        game.getAssetManager().unload("ScenaryAssets/quarto/QuartoObjects.atlas");

                        game.getAssetManager().finishLoading();
                        game.incrementPlayerLine(1);
                        game.setPlayerColumn(0);

                        game.setScreen(new SalaSecreta(game, 2487+230));
                    }
                }
            }
        }

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        minigameManager.minigameResize(width, height, 2);
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
        minigameManager.minigameDispose(2);
    }
}

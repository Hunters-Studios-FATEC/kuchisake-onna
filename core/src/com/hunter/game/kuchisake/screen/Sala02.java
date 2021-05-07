package com.hunter.game.kuchisake.screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;

public class Sala02 extends StandardRoom {

    Sprite porta1;

    public Sala02(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/saguao.tmx", playerDoorPosX);
        collisions.CreateCollisions(1750,425, "doorDown1",280, collisions.getPortaBit());

        porta1 = new Sprite(portaFechada);
        porta1.setSize((porta1.getWidth() / TerrorGame.SCALE) *1.5f, (porta1.getHeight() / TerrorGame.SCALE) * 1.5f);
        porta1.setPosition(1750 / TerrorGame.SCALE - porta1.getWidth() / 2, 160 / TerrorGame.SCALE);
    }

    @Override
    void stepWorld(float dt) {
        super.stepWorld(dt);
    }

    @Override
    public void show() {
        super.show();
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
            if (direction == "doorDown" && doorNum == 1){
                porta1.setTexture(portaAberta);
                System.out.println("muda porra");
                doorAnimationTimer += delta;
                if(doorAnimationTimer > 1.5f){
                    dispose();
                    
                    game.getAssetManager().unload("Tilesets/saguao.tmx");
                	game.getAssetManager().load("Tilesets/corredor.tmx", TiledMap.class);
                	
                	game.getAssetManager().finishLoading();
                    
                    game.setScreen(new Sala01(game, 1750));
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

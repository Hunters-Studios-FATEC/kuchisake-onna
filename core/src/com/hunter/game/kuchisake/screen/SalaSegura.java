package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.TesteSQLite;
import com.hunter.game.kuchisake.objects.ObjectAnimation;

public class SalaSegura extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion lustre1;
    TextureRegion lustre2;
    TextureRegion lustre3;
    TextureRegion lustre4;

    Sprite porta;
    Sprite lustre;

    boolean isSecondFloor = false;
    
    ObjectAnimation lustreAnimation;
    
    TesteSQLite testeBancoDeDados;

    public SalaSegura(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/sala_descanso.tmx", playerDoorPosX);

        collisions.CreateCollisions(1900+230, 160,"doorDown0", 203, collisions.getPortaBit());

        textureAtlas = game.getAssetManager().get("ScenaryAssets/salaDescanso/SalaDescansoObjects.atlas", TextureAtlas.class);
        portaFechada = textureAtlas.findRegion("portaCorredor1");
        portaAberta = textureAtlas.findRegion("portaCorredor2");
        lustre1 = textureAtlas.findRegion("lustre1");
        lustre2 = textureAtlas.findRegion("lustre2");
        lustre3 = textureAtlas.findRegion("lustre3");
        lustre4 = textureAtlas.findRegion("lustre4");

        porta = new Sprite(portaFechada);
        lustre = new Sprite(lustre1);

        porta.setSize(porta.getWidth() / TerrorGame.SCALE, porta.getHeight() / TerrorGame.SCALE);
        porta.setPosition(1900 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        porta.setAlpha(0.5f);
        
        lustre.setSize(lustre.getWidth() / TerrorGame.SCALE, lustre.getHeight() / TerrorGame.SCALE);
        lustre.setPosition(1750 / TerrorGame.SCALE - lustre.getWidth() / 2, viewport.getWorldHeight() - lustre.getHeight());
        
        lustreAnimation = new ObjectAnimation(0.2f, new TextureRegion[] {lustre1, lustre2, lustre3, lustre4});
        
        testeBancoDeDados = new TesteSQLite();
        
        testeBancoDeDados.connect(game.getInventoryManager().getItemBackpack(), 
        		game.getKuchisakeOnna().getCurrentLine(),
        		game.getKuchisakeOnna().getCurrentColumn(),
        		game.getMinigameManager().getLockCompleted(),
        		game.getMinigameManager().getBookCompleted(),
        		game.getMinigameManager().getWireCompleted(),
        		game.getMinigameManager().getGeradorCompleted(),
        		1,
        		1);
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);
        game.getKuchisakeOnna().KuchisakeUpdate(delta);

        game.batch.begin();
        
        player.draw(game.batch);
        
        porta.draw(game.batch);
        
        if(game.getMinigameManager().getGeradorCompleted()) {
        	lustre.setRegion(lustreAnimation.changeFrame(delta));
        }
        
        lustre.draw(game.batch);

        game.batch.end();

//        debugRenderer.render(world, camera.combined);
        inventoryManager.inventoryUpdate(delta);
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
                    game.getAssetManager().load("Audio/Sfx/porta trancada.ogg", Sound.class);
                    game.getAssetManager().load("ScenaryAssets/corredor/save.png", Texture.class);
                    portaSound.play(0.5f);
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 2f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/sala_descanso.tmx");
                    game.getAssetManager().unload("ScenaryAssets/salaDescanso/SalaDescansoObjects.atlas");

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(-1);
                    game.setPlayerColumn(0);
                    
                    game.setScreen(new CorredorBiblioteca(game, 1750));
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

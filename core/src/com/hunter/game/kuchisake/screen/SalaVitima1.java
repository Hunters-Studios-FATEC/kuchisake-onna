package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.objects.ObjectAnimation;

import javax.xml.stream.FactoryConfigurationError;

public class SalaVitima1 extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion livro;
    TextureRegion lampada1;
    TextureRegion lampada2;
    TextureRegion lampada3;
    TextureRegion lampada4;

    Sprite porta;
    Sprite livroSprite;
    Sprite lampada;

    boolean isSecondFloor = false;
    
    ObjectAnimation lampadaAnimation;

    public SalaVitima1(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/salaVitima1.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(2891, 160,"doorDown3", 203, collisions.getPortaBit());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/salaVitima1/SalaVitima1Objects.atlas", TextureAtlas.class);

        if (!game.getInventoryManager().getItemBackpack().contains("livro2", false)) {
            collisions.CreateCollisions(978, 160, "livro2", 203, collisions.getITEM_BIT());
            
            livro = textureAtlas.findRegion("livro2");
            
            livroSprite = new Sprite(livro);
            livroSprite.setSize(livroSprite.getWidth() / (TerrorGame.SCALE * 2), livroSprite.getHeight() / (TerrorGame.SCALE * 2));
            livroSprite.setPosition(978 / TerrorGame.SCALE - livroSprite.getWidth() / 2 + 0.5f, 365 / TerrorGame.SCALE);
        }

        portaFechada = textureAtlas.findRegion("portaCorredor1");
        portaAberta = textureAtlas.findRegion("portaCorredor2");
        lampada1 = textureAtlas.findRegion("lampada1");
        lampada2 = textureAtlas.findRegion("lampada2");
        lampada3 = textureAtlas.findRegion("lampada3");
        lampada4 = textureAtlas.findRegion("lampada4");
        
        porta = new Sprite(portaFechada);
        lampada = new Sprite(lampada1);
        
        porta.setSize(porta.getWidth() / TerrorGame.SCALE, porta.getHeight() / TerrorGame.SCALE);
        porta.setPosition((3500 - 812) / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        porta.setAlpha(0.5f);
        
        lampada.setSize(lampada.getWidth() / TerrorGame.SCALE, lampada.getHeight() / TerrorGame.SCALE);
        lampada.setPosition(1750 / TerrorGame.SCALE - lampada.getWidth() / 2, viewport.getWorldHeight() - lampada.getHeight());
        
        lampadaAnimation = new ObjectAnimation(0.2f, new TextureRegion[] {lampada1, lampada2, lampada3, lampada4});
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);
        game.getKuchisakeOnna().KuchisakeUpdate(delta);

        game.batch.begin();
        
        if(game.getMinigameManager().getGeradorCompleted()) {
        	lampada.setRegion(lampadaAnimation.changeFrame(delta));
        }
        
        lampada.draw(game.batch);
        
        if (!game.getInventoryManager().getItemBackpack().contains("livro2", false)) {
        	livroSprite.draw(game.batch);
        }
        
        game.getKuchisakeOnna().draw(game.batch);
        player.draw(game.batch);
        porta.draw(game.batch);
        
        game.batch.end();

        debugRenderer.render(world, camera.combined);
        inventoryManager.inventoryUpdate(delta);
        transitionScene.updateTransition();

		/*for (int i = 0; i < maxMinigameID; i++) {
				minigameManager.minigameUpdate(delta, i);
		}*/

        //inventoryManager.inventoryUpdate(delta);
        if (player.getCanChangeRoom()){
        	if (direction == "doorDown" && doorNum == 3){
                doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                	game.getAssetManager().load("Tilesets/corredor.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);
                    
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 1.5f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/salaVitima1.tmx");
                    game.getAssetManager().unload("ScenaryAssets/salaVitima1/SalaVitima1Objects.atlas");

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(-1);
                    game.setPlayerColumn(3);
                    
                    game.setScreen(new CorredorSalas(game, 2487));
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

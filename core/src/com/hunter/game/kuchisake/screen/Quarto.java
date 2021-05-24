package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.objects.ObjectAnimation;
import com.hunter.game.kuchisake.tools.SpriteItem;


public class Quarto extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion chave;
    TextureRegion abajur1;
    TextureRegion abajur2;
    TextureRegion abajur3;
    TextureRegion abajur4;
    TextureRegion lustre1;
    TextureRegion lustre2;
    TextureRegion lustre3;
    TextureRegion lustre4;
    TextureRegion quadro;
    TextureRegion maskara4;

    Sprite mask4;
    Sprite porta;
    Sprite chaveSprite;
    Sprite abajur;
    Sprite lustre;
    Sprite quadroSprite;

    SpriteItem chaveFlutuar;
    SpriteItem maskFlutuar;

    boolean isSecondFloor = false;

    boolean isQuadroSemCachorro = true;
    
    ObjectAnimation abajurAnimation;
    ObjectAnimation lustreAnimation;

    public Quarto(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/quarto.tmx", playerDoorPosX);
        
        collisions.CreateCollisions(2810, 160,"doorDown1", 230, collisions.getPortaBit());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/quarto/QuartoObjects.atlas", TextureAtlas.class);

        if (!game.getInventoryManager().getItemBackpack().contains("chaveBiblio", false)) {
            collisions.CreateCollisions(2177, 160, "chaveBiblio", 230, collisions.getITEM_BIT());
            
            chave = textureAtlas.findRegion("chaveBiblio");
            
            chaveSprite = new Sprite(chave);
            chaveSprite.setSize(chaveSprite.getWidth() / (TerrorGame.SCALE * 2), chaveSprite.getHeight() / (TerrorGame.SCALE * 2));
            chaveSprite.setPosition(2177 / TerrorGame.SCALE - chaveSprite.getWidth() / 2, 470 / TerrorGame.SCALE);

            chaveFlutuar = new SpriteItem(50f, chaveSprite);
        }

        //Colisao do quadro da cabeca de cachorro faltando
        collisions.CreateCollisions(300, 160, "objetoMundo", 203, collisions.getINTERACTIBLE_BIT());

        maskara4 = textureAtlas.findRegion("mask4");
        mask4 = new Sprite(maskara4);
        mask4.setSize(mask4.getWidth() / TerrorGame.SCALE, mask4.getHeight() / TerrorGame.SCALE);
        mask4.setPosition(300 / TerrorGame.SCALE - (mask4.getWidth() / 2), 500 / TerrorGame.SCALE);

        maskFlutuar = new SpriteItem(50f, mask4);
        
        portaFechada = textureAtlas.findRegion("portaCorredor1");
        portaAberta = textureAtlas.findRegion("portaCorredor2");
        abajur1 = textureAtlas.findRegion("abajur1");
        abajur2 = textureAtlas.findRegion("abajur2");
        abajur3 = textureAtlas.findRegion("abajur3");
        abajur4 = textureAtlas.findRegion("abajur4");
        lustre1 = textureAtlas.findRegion("lustre1");
        lustre2 = textureAtlas.findRegion("lustre2");
        lustre3 = textureAtlas.findRegion("lustre3");
        lustre4 = textureAtlas.findRegion("lustre4");
        quadro = textureAtlas.findRegion("caixa cachorro");
        
        porta = new Sprite(portaFechada);
        abajur = new Sprite(abajur1);
        lustre = new Sprite(lustre1);
        quadroSprite = new Sprite(quadro);
        
        porta.setSize(porta.getWidth() / TerrorGame.SCALE, porta.getHeight() / TerrorGame.SCALE);
        porta.setPosition((3500 - 460 * 2) / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        porta.setAlpha(0.5f);
        
        abajur.setSize(abajur.getWidth() / TerrorGame.SCALE, abajur.getHeight() / TerrorGame.SCALE);
        abajur.setPosition(2177 / TerrorGame.SCALE - abajur.getWidth() / 2, 470 / TerrorGame.SCALE);
        
        lustre.setSize(lustre.getWidth() / TerrorGame.SCALE, lustre.getHeight() / TerrorGame.SCALE);
        lustre.setPosition(1750 / TerrorGame.SCALE - lustre.getWidth() / 2, viewport.getWorldHeight() - lustre.getHeight());
        
        quadroSprite.setSize(quadroSprite.getWidth() / TerrorGame.SCALE, quadroSprite.getHeight() / TerrorGame.SCALE);
        quadroSprite.setPosition(162.5f / TerrorGame.SCALE, 500 / TerrorGame.SCALE);
        
        if(game.getHasEncountered() &&
                (game.getKuchisakeOnna().getCurrentLine() != game.getPlayerLine() ||
                game.getKuchisakeOnna().getCurrentColumn() != game.getPlayerColumn())) {
        	game.getMinigameManager().setMinigameActive(true);
        	game.getMinigameManager().startMinigame(0);
        }
        
        abajurAnimation = new ObjectAnimation(0.2f, new TextureRegion[] {abajur1, abajur2, abajur3, abajur4});
        lustreAnimation = new ObjectAnimation(0.2f, new TextureRegion[] {lustre1, lustre2, lustre3, lustre4});
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);
        game.getKuchisakeOnna().KuchisakeUpdate(delta);

        game.batch.begin();
        
        if(game.getMinigameManager().getGeradorCompleted()) {
        	abajur.setRegion(abajurAnimation.changeFrame(delta));
        	lustre.setRegion(lustreAnimation.changeFrame(delta));
        }
        
        abajur.draw(game.batch);
        
        if (!game.getInventoryManager().getItemBackpack().contains("chaveBiblio", false)) {
        	chaveSprite.draw(game.batch);
        	chaveFlutuar.flutar(delta);
        }

        if (isQuadroSemCachorro && !game.getInventoryManager().getItemBackpack().contains("mask4", false)){
            quadroSprite.draw(game.batch);
        } else if (!isQuadroSemCachorro && !game.getInventoryManager().getItemBackpack().contains("mask4", false)){
            mask4.draw(game.batch);
            maskFlutuar.flutar(delta);
        }
        
        game.getKuchisakeOnna().draw(game.batch);
        player.draw(game.batch);
        porta.draw(game.batch);
        
        lustre.draw(game.batch);
        
        game.batch.end();

//        debugRenderer.render(world, camera.combined);
        inventoryManager.inventoryUpdate(delta);
        transitionScene.updateTransition();

		/*for (int i = 0; i < maxMinigameID; i++) {
				minigameManager.minigameUpdate(delta, i);
		}*/

        //inventoryManager.inventoryUpdate(delta);
        
        game.getMinigameManager().minigameUpdate(delta, 0);
        
        if (player.getChangeObjectVisual() &&
                inventoryManager.getItemBackpack().contains("cachorro", false) &&
                !game.getInventoryManager().getItemBackpack().contains("mask4", false)){
            isQuadroSemCachorro = false;
            collisions.CreateCollisions(300, 160, "mask4", 203, collisions.getITEM_BIT());
        }
        
        if (player.getCanChangeRoom()){
        	if (direction == "doorDown" && doorNum == 1){
                doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                	game.getAssetManager().load("Tilesets/corredor.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);
                    portaSound.play(0.5f);
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 2f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/quarto.tmx");
                    game.getAssetManager().unload("ScenaryAssets/quarto/QuartoObjects.atlas");

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(-1);
                    game.setPlayerColumn(1);
                    
                    game.setScreen(new CorredorQuarto(game, 1980));
                }
            }
        }


    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        
        game.getMinigameManager().minigameResize(width, height, 0);
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

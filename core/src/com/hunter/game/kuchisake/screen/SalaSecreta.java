package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.objects.ObjectAnimation;
import com.hunter.game.kuchisake.tools.SpriteItem;

public class SalaSecreta extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion braseiroAceso1;
    TextureRegion braseiroAceso2;
    TextureRegion braseiroAceso3;
    TextureRegion braseiroApagado;
    TextureRegion caixaMascara;
    TextureRegion chavePrinc;
    TextureRegion maskara2;
    TextureRegion chavePorao;

    Sprite porta;
    Sprite braseiro1;
    Sprite braseiro2;
    Sprite caixaMask;
    Sprite chavePrincipal;
    Sprite mask2;
    Sprite chavePor;

    SpriteItem maskFlutuar;
    SpriteItem chaveFlutuar;

    boolean hasTodasPecasMascara = false;
    
    ObjectAnimation braseiroAnimation;

    public SalaSecreta(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/sala_secreta.tmx", playerDoorPosX);

        collisions.CreateCollisions(-128, 160, "zap zap", 128, collisions.getGroundBit());
        collisions.CreateCollisions(3628, 160, "zap zap", 128, collisions.getGroundBit());
        collisions.CreateCollisions(2487+230f, 160,"doorDown0", 203, collisions.getPortaBit());
        textureAtlas = game.getAssetManager().get("ScenaryAssets/salaSecreta/SalaSecretaObjects.atlas", TextureAtlas.class);

        if (!game.getInventoryManager().getItemBackpack().contains("chavePorao", false)){
            chavePorao = textureAtlas.findRegion("chavePorao");
            chavePor = new Sprite(chavePorao);
            chavePor.setPosition(450 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
            chavePor.setSize(chavePor.getWidth() / TerrorGame.SCALE, chavePor.getHeight() / TerrorGame.SCALE);
            collisions.CreateCollisions(450, 160, "chavePorao", 203, collisions.getITEM_BIT());

            chaveFlutuar = new SpriteItem(50f, chavePor);

        }

        if (!game.getInventoryManager().getItemBackpack().contains("mask2", false)){
            maskara2 = textureAtlas.findRegion("mask2");
            mask2 = new Sprite(maskara2);
            mask2.setPosition(2030 / TerrorGame.SCALE, 160/ TerrorGame.SCALE);
            mask2.setSize(mask2.getWidth() / TerrorGame.SCALE, mask2.getHeight() / TerrorGame.SCALE);
            collisions.CreateCollisions(2030, 160, "mask2", 203, collisions.getITEM_BIT());

            maskFlutuar = new SpriteItem(50f, mask2);
        }

        //colisao da caixa ornamentada de mascara
        collisions.CreateCollisions(1750, 160, "objetoMundo", 203, collisions.getINTERACTIBLE_BIT());

        if (!game.getInventoryManager().getItemBackpack().contains("chavePrincipal", false) && hasTodasPecasMascara) {
            collisions.CreateCollisions(1750, 160, "chavePrincipal", 203, collisions.getITEM_BIT());
        }

        portaFechada = textureAtlas.findRegion("portaCorredor1");
        portaAberta = textureAtlas.findRegion("portaCorredor2");
        braseiroAceso1 = textureAtlas.findRegion("braseiro aceso1");
        braseiroAceso2 = textureAtlas.findRegion("braseiro aceso2");
        braseiroAceso3 = textureAtlas.findRegion("braseiro aceso3");
        braseiroApagado = textureAtlas.findRegion("braseiro apagado");
        caixaMascara = textureAtlas.findRegion("caixa mascara");
        chavePrinc = textureAtlas.findRegion("chavePrincipal");

        porta = new Sprite(portaFechada);
        braseiro1 = new Sprite(braseiroApagado);
        braseiro2 = new Sprite(braseiroApagado);
        caixaMask = new Sprite(caixaMascara);
        chavePrincipal = new Sprite(chavePrinc);

        porta.setSize(porta.getWidth() / TerrorGame.SCALE, porta.getHeight() / TerrorGame.SCALE);
        porta.setPosition(2487 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        porta.setAlpha(0.5f);
        
        braseiro1.setSize(braseiro1.getWidth() / TerrorGame.SCALE, braseiro1.getHeight() / TerrorGame.SCALE);
        braseiro1.setPosition(1155 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        
        braseiro2.setSize(braseiro2.getWidth() / TerrorGame.SCALE, braseiro2.getHeight() / TerrorGame.SCALE);
        braseiro2.setPosition(2095 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);

        caixaMask.setSize(caixaMask.getWidth() / TerrorGame.SCALE, caixaMask.getHeight() / TerrorGame.SCALE);
        caixaMask.setPosition((1594 / TerrorGame.SCALE) + (caixaMask.getWidth() / 14), (160+415) / TerrorGame.SCALE);

        chavePrincipal.setSize(chavePrincipal.getWidth() / (TerrorGame.SCALE * 2), chavePrincipal.getHeight() / (TerrorGame.SCALE * 2));
        chavePrincipal.setPosition(1750 / TerrorGame.SCALE - (chavePrincipal.getWidth() / 2), (160 + 415) / TerrorGame.SCALE );

        braseiroAnimation = new ObjectAnimation(0.2f, new TextureRegion[] {braseiroAceso1, braseiroAceso2, braseiroAceso3});
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
        game.getKuchisakeOnna().KuchisakeUpdate(delta);

        game.batch.begin();
        
        braseiro1.setRegion(braseiroAnimation.changeFrame(delta));
        braseiro1.draw(game.batch);
        
        braseiro2.setRegion(braseiroAnimation.changeFrame(delta));
        braseiro2.draw(game.batch);

        if (!game.getInventoryManager().getItemBackpack().contains("chavePorao", false)){
            chavePor.draw(game.batch);
            chaveFlutuar.flutar(delta);
        }

        if (!game.getInventoryManager().getItemBackpack().contains("mask2", false)){
            mask2.draw(game.batch);
            maskFlutuar.flutar(delta);
        }

        if (hasTodasPecasMascara && !game.getInventoryManager().getItemBackpack().contains("chavePrincipal", false)){
            chavePrincipal.draw(game.batch);
        } else if (!game.getInventoryManager().getItemBackpack().contains("chavePrincipal", false)){
            caixaMask.draw(game.batch);
        }

        game.getKuchisakeOnna().draw(game.batch);
        player.draw(game.batch);

        porta.draw(game.batch);
        game.batch.end();

        transitionScene.updateTransition();
        inventoryManager.inventoryUpdate(delta);

        if (player.getChangeObjectVisual() && inventoryManager.getItemBackpack().contains("mask1", false) &&
                inventoryManager.getItemBackpack().contains("mask2", false) &&
                inventoryManager.getItemBackpack().contains("mask3", false) &&
                inventoryManager.getItemBackpack().contains("mask4", false) &&
                !game.getInventoryManager().getItemBackpack().contains("chavePrincipal", false)){
            hasTodasPecasMascara = true;
            collisions.CreateCollisions(1750, 160, "chavePrincipal", 203, collisions.getITEM_BIT());
        }

        if (player.getCanChangeRoom()){
            if (direction == "doorDown" && doorNum == 0){
                doorAnimationTimer += delta;
                transitionScene.fadeIn();
                
                if(!canSwitchAssets) {
                	game.getAssetManager().load("Tilesets/biblioteca.tmx", TiledMap.class);
                    game.getAssetManager().load("ScenaryAssets/biblioteca/BibliotecaObjects.atlas", TextureAtlas.class);
                    portaSound.play(0.5f);
                    canSwitchAssets = true;
                }
                
                if(doorAnimationTimer > 2f){
                    dispose();

                    game.getAssetManager().unload("Tilesets/sala_secreta.tmx");
                    game.getAssetManager().unload("ScenaryAssets/salaSecreta/SalaSecretaObjects.atlas");

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(-1);
                    game.setPlayerColumn(0);
                    
                    game.setScreen(new Biblioteca(game, 2487+230));
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

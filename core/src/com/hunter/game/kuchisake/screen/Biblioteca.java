package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.objects.ObjectAnimation;
import com.hunter.game.kuchisake.tools.MinigameManager;
import com.hunter.game.kuchisake.tools.SpriteItem;

public class Biblioteca extends StandardRoom implements Screen {

    TextureAtlas textureAtlas;

    TextureRegion portaFechada;
    TextureRegion portaAberta;
    TextureRegion estanteTexture;
    TextureRegion lustre1;
    TextureRegion lustre2;
    TextureRegion lustre3;
    TextureRegion lustre4;
    TextureRegion estante;
    TextureRegion livro;

    Sprite livro3;
    Sprite porta1;
    Sprite porta2;
    Sprite estanteSprite1;
    Sprite estanteSprite2;
    Sprite lustre;

    SpriteItem livroFlutua;

    MinigameManager minigameManager;
    
    ObjectAnimation lustreAnimation;

    public Biblioteca(TerrorGame game, float playerDoorPosX) {
        super(game, "Tilesets/biblioteca.tmx", playerDoorPosX);

        minigameManager = game.getMinigameManager();

        collisions.CreateCollisions(971+230f, 160,"doorDown0", 230, collisions.getPortaBit());
        collisions.CreateCollisions(2487+230f, 160,"doorUp0", 203, collisions.getPortaBit());
        collisions.CreateCollisions(2487+230f, 160,"bookshelf", 203, collisions.getShelfBit());
        collisions.CreateCollisions(-128, 160, "zap zap", 128, collisions.getGroundBit());
        collisions.CreateCollisions(3628, 160, "zap zap", 128, collisions.getGroundBit());
        
        textureAtlas = game.getAssetManager().get("ScenaryAssets/biblioteca/BibliotecaObjects.atlas", TextureAtlas.class);

        if (!game.getInventoryManager().getItemBackpack().contains("livro3", false)) {
            collisions.CreateCollisions(750, 160, "livro3", 203, collisions.getITEM_BIT());
            livro = textureAtlas.findRegion("livro3");
            livro3 = new Sprite(livro);
            livro3.setSize(livro3.getWidth() / (TerrorGame.SCALE * 2), livro3.getHeight() / (TerrorGame.SCALE * 2));
            livro3.setPosition((750 / TerrorGame.SCALE) - (livro3.getWidth() / 2), 160 / TerrorGame.SCALE);

            livroFlutua = new SpriteItem(50f, livro3);
        }

        portaFechada = textureAtlas.findRegion("portaCorredor1");
        portaAberta = textureAtlas.findRegion("portaCorredor2");
        lustre1 = textureAtlas.findRegion("lustre1");
        lustre2 = textureAtlas.findRegion("lustre2");
        lustre3 = textureAtlas.findRegion("lustre3");
        lustre4 = textureAtlas.findRegion("lustre4");
        estante = textureAtlas.findRegion("sprite estante");

        porta1 = new Sprite(portaFechada);
        porta2 = new Sprite(portaFechada);
        lustre = new Sprite(lustre1);
        estanteSprite1 = new Sprite(estante);
        estanteSprite2 = new Sprite(estante);

        porta1.setSize(porta1.getWidth() / TerrorGame.SCALE, porta1.getHeight() / TerrorGame.SCALE);
        porta1.setPosition(971 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        porta1.setAlpha(0.5f);

        porta2.setSize(porta2.getWidth() / TerrorGame.SCALE, porta2.getHeight() / TerrorGame.SCALE);
        porta2.setPosition(2487 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        
        lustre.setSize(lustre.getWidth() / TerrorGame.SCALE, lustre.getHeight() / TerrorGame.SCALE);
        lustre.setPosition(1750 / TerrorGame.SCALE - lustre.getWidth() / 2, viewport.getWorldHeight() - lustre.getHeight());
        
        estanteSprite1.setSize(estanteSprite1.getWidth() / TerrorGame.SCALE, estanteSprite1.getHeight() / TerrorGame.SCALE);
        estanteSprite1.setPosition(36 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        
        estanteSprite2.setSize(estanteSprite2.getWidth() / TerrorGame.SCALE, estanteSprite2.getHeight() / TerrorGame.SCALE);
        
        if(!game.getMinigameManager().getBookCompleted()) {
        	estanteSprite2.setPosition(2399 / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        }
        else {
        	estanteSprite2.setPosition((2399 - 993) / TerrorGame.SCALE, 160 / TerrorGame.SCALE);
        }
        
        lustreAnimation = new ObjectAnimation(0.2f, new TextureRegion[] {lustre1, lustre2, lustre3, lustre4});

        if(game.getHasEncountered() &&
                (game.getKuchisakeOnna().getCurrentLine() != game.getPlayerLine() ||
                        game.getKuchisakeOnna().getCurrentColumn() != game.getPlayerColumn())) {
            game.getMinigameManager().setMinigameActive(true);
            game.getMinigameManager().startMinigame(0);
        }

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.setProjectionMatrix(camera.combined);

        player.playerUpdate(delta);
        game.getKuchisakeOnna().KuchisakeUpdate(delta);

        game.batch.begin();

        porta2.draw(game.batch);
        estanteSprite1.draw(game.batch);
        estanteSprite2.draw(game.batch);

        if (!game.getInventoryManager().getItemBackpack().contains("livro3", false)) {
            livro3.draw(game.batch);
            livroFlutua.flutar(delta);
        }

        if (minigameManager.getBookCompleted() && estanteSprite2.getX() > (2399 - 993)/ TerrorGame.SCALE){
            estanteSprite2.translateX((-993 / TerrorGame.SCALE) * delta);
            estanteSprite2.draw(game.batch);
        }

        game.getKuchisakeOnna().draw(game.batch);
        player.draw(game.batch);
        porta1.draw(game.batch);
        
        if(game.getMinigameManager().getGeradorCompleted()) {
        	lustre.setRegion(lustreAnimation.changeFrame(delta));
        }
        
        lustre.draw(game.batch);

        game.batch.end();

        transitionScene.updateTransition();
        minigameManager.minigameUpdate(delta, 2);
        inventoryManager.inventoryUpdate(delta);

        game.getMinigameManager().minigameUpdate(delta, 0);

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

                    game.getAssetManager().unload("Tilesets/biblioteca.tmx");
                    game.getAssetManager().unload("ScenaryAssets/biblioteca/BibliotecaObjects.atlas");

                    game.getAssetManager().finishLoading();
                    game.incrementPlayerLine(-1);
                    game.setPlayerColumn(0);
                    
                    game.setScreen(new CorredorBiblioteca(game, 600));
                }

            } else if (direction == "doorUp" && doorNum == 0){
                if (game.getMinigameManager().getBookCompleted() && estanteSprite2.getX() <= (2399 - 993) / TerrorGame.SCALE){
                    doorAnimationTimer += delta;
                    transitionScene.fadeIn();

                    if(!canSwitchAssets) {
                        game.getAssetManager().load("Tilesets/sala_secreta.tmx", TiledMap.class);
                        game.getAssetManager().load("ScenaryAssets/salaSecreta/SalaSecretaObjects.atlas", TextureAtlas.class);
                        portaSound.play(0.5f);
                        canSwitchAssets = true;
                    }

                    if(doorAnimationTimer > 2f){
                        dispose();

                        game.getAssetManager().unload("Tilesets/biblioteca.tmx");
                        game.getAssetManager().unload("ScenaryAssets/biblioteca/BibliotecaObjects.atlas");

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
        minigameManager.minigameDispose(2);
    }
}

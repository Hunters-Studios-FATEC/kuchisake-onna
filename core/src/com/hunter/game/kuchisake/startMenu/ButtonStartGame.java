package com.hunter.game.kuchisake.startMenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.screen.Saguao;

public class ButtonStartGame extends Actor {
    Texture image;
    Sprite botao;

    float posX;
    float posY;
    
    TerrorGame game;
    SceneMenu menu;

    public ButtonStartGame(String imagem_path, float posX, float posY, TerrorGame terrorGame, SceneMenu menu){
        game = terrorGame;
        
        image = game.getAssetManager().get(imagem_path, Texture.class);
        botao = new Sprite(image);

        this.posX = posX;
        this.posY = posY;

        botao.setSize(botao.getWidth() / TerrorGame.SCALE, botao.getHeight() / TerrorGame.SCALE);
        botao.setPosition(posX - botao.getWidth() / 2, posY);
        setBounds(botao.getX(), botao.getY(), botao.getWidth(), botao.getHeight());

        setTouchable(Touchable.enabled);

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean checaLargura = (event.getStageX() >= botao.getX() && event.getStageX() <= botao.getX() + botao.getWidth());
                boolean checaAltura = (event.getStageY() >= botao.getY() && event.getStageY() <= botao.getY() + botao.getHeight());

                //boolean buttonPressed = Gdx.input.isTouched();

                if (checaLargura && checaAltura){
                    loadScene();
                    System.out.println("INicia porra");
                }

                System.out.println(event.getStageX() + " , " + event.getStageY());
                return true;
            }
        });

    }


    public void loadScene(){
    	game.getAssetManager().unload("ButtonAssets/controles_rascunho.png");
    	game.getAssetManager().unload("ButtonAssets/Logo_rascunho.png");
    	game.getAssetManager().unload("ButtonAssets/start_rascunho.png");
    	
    	game.getAssetManager().setLoader(TiledMap.class, new TmxMapLoader());
    	
    	game.getAssetManager().load("CharactersAssets/sprites_protag_right.png", Texture.class);
    	game.getAssetManager().load("CharactersAssets/sprite_stoped_right.png", Texture.class);
    	game.getAssetManager().load("Tilesets/saguao_segundo.tmx", TiledMap.class);
    	game.getAssetManager().load("ScenaryAssets/SaguaoPrimeiroPack.atlas", TextureAtlas.class);
//    	game.getAssetManager().load("PortasEEscadas/porta1.png", Texture.class);
//    	game.getAssetManager().load("PortasEEscadas/porta2.png", Texture.class);
//    	game.getAssetManager().load("arrow.png", Texture.class);
//    	game.getAssetManager().load("arrow.png", Texture.class);
    	
    	game.getAssetManager().finishLoading();
    	
        game.setScreen(new Saguao(game, 1000 / TerrorGame.SCALE));
    }

    public Sprite getBotaoStart() {
        return botao;
    }

    public void draw(Batch batch, float parentAlpha) {
        botao.draw(batch);
    }
}

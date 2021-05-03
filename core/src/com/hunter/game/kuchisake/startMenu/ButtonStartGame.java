package com.hunter.game.kuchisake.startMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.screen.Sala01;

public class ButtonStartGame  {
    Texture image;
    Sprite botao;

    float posX;
    float posY;
    TerrorGame game;

    public ButtonStartGame(String imagem_path, float posX, float posY, TerrorGame terrorGame){
        image = new Texture(imagem_path);
        botao = new Sprite(image);
        game = terrorGame;

        this.posX = posX;
        this.posY = posY;


        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                boolean checaLargura = (screenX >= botao.getX() && screenX <= botao.getX() + botao.getWidth());
                boolean checaAltura = (screenY >= botao.getY() && screenY <= botao.getY() + botao.getHeight());

                if (checaLargura && checaAltura){
                    loadScene();
                }

                return true;
            }
        });

    }

    public void loadScene(){
        game.setScreen(new Sala01(game, 1000 / TerrorGame.SCALE));
    }

}

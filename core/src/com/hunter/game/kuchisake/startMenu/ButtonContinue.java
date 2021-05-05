package com.hunter.game.kuchisake.startMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.hunter.game.kuchisake.TerrorGame;

public class ButtonContinue extends Actor {
        Texture image;
        Sprite botao;

        float posX;
        float posY;
        TerrorGame game;

        public ButtonContinue(String imagem_path, float posX, float posY, TerrorGame terrorGame){
            image = new Texture(imagem_path);
            botao = new Sprite(image);
            game = terrorGame;

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

                    boolean buttonPressed = Gdx.input.isTouched();

                    if ((checaLargura && checaAltura) && buttonPressed){
                        loadGame();
                        System.out.println("INicia porra");
                    }

                    System.out.println(event.getStageX() + " , " + event.getStageY());
                    return true;
                }
            });

        }


        public void loadGame(){
            System.out.println("Carregando save.");
        }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        botao.draw(batch);
    }

    public Sprite getBotaoContinue() {
        return botao;
    }

}

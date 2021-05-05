package com.hunter.game.kuchisake.startMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.hunter.game.kuchisake.TerrorGame;

public class ButtonControles extends Actor {
    Texture image;
    Sprite botao;

    float posX;
    float posY;
    TerrorGame game;

    boolean controlesCarregados;

    public ButtonControles(String imagem_path, float posX, float posY){
        image = new Texture(imagem_path);
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

                boolean buttonPressed = Gdx.input.isTouched();

                if ((checaLargura && checaAltura) && buttonPressed){
                    loadControlesImage();
                    System.out.println("INicia porra");
                }
                System.out.println(event.getStageX() + " , " + event.getStageY());
                return true;
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && controlesCarregados){
                    retirarImagemControles();
                    controlesCarregados = false;
                }
                return true;
            }
        });

    }

    public void loadControlesImage(){
        System.out.println("Controles aparacendo");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        botao.draw(batch);
    }

    public void retirarImagemControles(){
        System.out.println("Controles saíram");
    }

    public Sprite getBotaoControle() {
        return botao;
    }

}

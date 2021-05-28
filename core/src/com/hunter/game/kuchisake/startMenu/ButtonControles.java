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
    
    Texture controles;
    Sprite controlesSprite;

    float posX;
    float posY;
    TerrorGame game;

    boolean controlesCarregados = false;

    public ButtonControles(String imagem_path, float posX, float posY, TerrorGame terrorGame){
    	game = terrorGame;
    	
        image = game.getAssetManager().get(imagem_path, Texture.class);
        botao = new Sprite(image);

        this.posX = posX;
        this.posY = posY;

        botao.setSize(botao.getWidth() / 42f, botao.getHeight() / 42f);
        botao.setPosition(posX - botao.getWidth() / 2, posY);
        setBounds(botao.getX(), botao.getY(), botao.getWidth(), botao.getHeight());
        
        controles = game.getAssetManager().get("ButtonAssets/controls.png");
        controlesSprite = new Sprite(controles);
        controlesSprite.setSize(controlesSprite.getWidth() / TerrorGame.SCALE, controlesSprite.getHeight() / TerrorGame.SCALE);
        controlesSprite.setPosition(0, 0);

        setTouchable(Touchable.enabled);

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean checaLargura = (event.getStageX() >= botao.getX() && event.getStageX() <= botao.getX() + botao.getWidth());
                boolean checaAltura = (event.getStageY() >= botao.getY() && event.getStageY() <= botao.getY() + botao.getHeight());

                if (checaLargura && checaAltura){
                    loadControlesImage();
                }
                return true;
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && controlesCarregados){
                    retirarImagemControles();
                }
                return true;
            }
        });

    }

    public void loadControlesImage(){
        controlesCarregados = true;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        botao.draw(batch);
        
        if(controlesCarregados) {
        	controlesSprite.draw(batch);
        }
        
    }

    public void retirarImagemControles(){
        controlesCarregados = false;
    }

    public Sprite getBotaoControle() {
        return botao;
    }

}

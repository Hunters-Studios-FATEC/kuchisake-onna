package com.hunter.game.kuchisake.minigameGerador;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BarraTempo extends Actor {
    Sprite barra;
    Texture textureBarra;
    MinigameGerador minigameGerador;
    Arrow setas;

    float timer = 3f;
    float timerCounter = timer;

    float initialWidth;

    public BarraTempo(float x, float y, MinigameGerador minigameGerador, Arrow setas){
        textureBarra = new Texture("red_square.png");
        barra = new Sprite(textureBarra);
        this.minigameGerador = minigameGerador;
        this.setas = setas;

        float posI_x = x;
        float posI_Y = y;

        float barra_width = 1.96f * 4f;
        float barra_height = 0.5f;

        initialWidth = barra_width;

        barra.setBounds(posI_x, posI_Y, barra_width, barra_height);
        barra.setPosition(posI_x - barra.getWidth() / 2, posI_Y - barra.getHeight());
        setBounds(barra.getX(), barra.getY(), barra_width, barra_height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        barra.setSize( (timerCounter / timer) * initialWidth, barra.getHeight());
        barra.draw(batch);
    }

    @Override
    public void act(float delta){
        if (!setas.rowCompleted) {
            if (timerCounter <= 0) {
                timerCounter = 0;
                minigameGerador.verifyTimer();
            } else {
                timerCounter -= delta;
            }
        }
    }
}


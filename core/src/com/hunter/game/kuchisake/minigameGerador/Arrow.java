package com.hunter.game.kuchisake.minigameGerador;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.hunter.game.kuchisake.TerrorGame;


public class Arrow extends Actor {
    Sprite seta;

    Array<Sprite> sequencia;

    Texture texture;


    public Arrow(float x_pos, float y_pos){
        sequencia = new Array<Sprite>();

        add_Arrow(x_pos, y_pos);

        addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.UP) {

                }
                if(keycode == Input.Keys.LEFT) {

                }
                if(keycode == Input.Keys.DOWN) {

                }
                if(keycode == Input.Keys.RIGHT) {

                }

                return true;

            }
        });
    }

    void add_Arrow(float xpos, float ypos) {
        float posI_x = xpos;
        float posI_Y = ypos;

        for (int i = 0; i < 5; i++) {
            texture = new Texture("arrow.png");
            seta = new Sprite(texture);
            seta.setBounds(posI_x, posI_Y, seta.getWidth() / TerrorGame.SCALE, seta.getHeight() / TerrorGame.SCALE);
            seta.setPosition(posI_x + seta.getWidth() * i, posI_Y);
            sequencia.add(seta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        seta.draw(batch);
    }

}

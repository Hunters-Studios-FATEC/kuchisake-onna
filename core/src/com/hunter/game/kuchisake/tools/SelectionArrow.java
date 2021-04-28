package com.hunter.game.kuchisake.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SelectionArrow extends Actor {

    Sprite seta;
    Texture setaTexture;

    public SelectionArrow(float xPos, float yPos,  boolean rigthArrow ){
        float posX = xPos;
        float posY = yPos;

        boolean isRightArrow = rigthArrow;

        float seta_width = 1f; // width e height ja divididos pela escala - 100.
        float seta_height = 1f;

        setaTexture = new Texture("arrow.png");
        seta = new Sprite(setaTexture);

        seta.setBounds(posX, posY, seta_width, seta_height);
        seta.setPosition(posX, posY);

        if (isRightArrow){
            seta.rotate90(true);
        } else {
            seta.rotate90(false);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        seta.draw(batch, parentAlpha);
    }

}

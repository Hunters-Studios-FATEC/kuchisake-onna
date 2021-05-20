package com.hunter.game.kuchisake.tools;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.hunter.game.kuchisake.TerrorGame;

public class SpriteItem {

    float yflutuar;
    Sprite spriteItem;
    float meio;
    boolean limiteSuperior;

    public SpriteItem(float yTranslate, Sprite sprite){
        spriteItem = sprite;
        yflutuar = yTranslate / TerrorGame.SCALE;
        meio = spriteItem.getY();
    }

    public void flutar(float delta){
        if (!limiteSuperior){
            if (spriteItem.getY() <= meio + yflutuar){
                spriteItem.translateY(yflutuar * delta);
            } else {
                limiteSuperior = true;
            }
        } else {
            if (spriteItem.getY() >= meio){
                spriteItem.translateY(-yflutuar * delta);
            } else {
                limiteSuperior = false;
            }
        }
    }
}


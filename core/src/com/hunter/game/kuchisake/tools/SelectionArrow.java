package com.hunter.game.kuchisake.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.hunter.game.kuchisake.TerrorGame;

public class SelectionArrow extends Actor {

    Sprite seta;
    TextureRegion setaTexture;
    boolean isRightArrow;
    InventoryItem inventoryItem;

    public SelectionArrow(float xPos, float yPos, boolean rigthArrow, InventoryItem inventoryIt, TerrorGame game, TextureAtlas textureAtlas){
        float posX = xPos;
        float posY = yPos;

        isRightArrow = rigthArrow;

        float seta_width = 1f; // width e height ja divididos pela escala - 100.
        float seta_height = 1f;

        setaTexture = textureAtlas.findRegion("red_arrow");
        seta = new Sprite(setaTexture);
        this.inventoryItem = inventoryIt;

        seta.setBounds(posX, posY, seta_width, seta_height);
        seta.setPosition(posX, posY);

        setBounds(posX, posY, seta_width, seta_height);

        if (isRightArrow){
            seta.rotate90(true);
        } else {
            seta.rotate90(false);
        }

        setTouchable(Touchable.enabled);

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (isRightArrow){
                    inventoryItem.incrementShowedItem();
                    System.out.println("+1");

                    if (inventoryItem.getShowedItem() > inventoryItem.getMaxItem() - 1){
                        inventoryItem.setShowedItem(0);
                        inventoryItem.changeItem(inventoryItem.getShowedItem());
                    } else {
                        inventoryItem.changeItem(inventoryItem.getShowedItem());
                    }
                }

                if (!isRightArrow){
                    inventoryItem.deincrementShowedItem();
                    System.out.println("-1");

                    if (inventoryItem.getShowedItem() < 0){
                        inventoryItem.setShowedItem(inventoryItem.getMaxItem() - 1);
                        inventoryItem.changeItem(inventoryItem.getShowedItem());
                    } else {
                        inventoryItem.changeItem(inventoryItem.getShowedItem());
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        seta.draw(batch, parentAlpha);
    }

}

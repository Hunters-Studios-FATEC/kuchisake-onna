package com.hunter.game.kuchisake.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.hunter.game.kuchisake.TerrorGame;

public class InventoryItem extends Actor{
    Sprite Item;

    InventoryManager inventoryManager;

    TextureAtlas objectsAtlas;
    TextureAtlas descriptionAtlas;

    int showedItem = 0;
    int maxItem = 0;

    Array<Integer> numberOfItens;
    Array<Sprite> itemSprite;
    Array<Sprite> itemDescription;

    float ITEMBOX_WIDTH = 1.96f;
    float ITEMBOX_HEIGHT = 1.96f;
    float DESCRIPTION_WIDTH = 17.64f;
    float DESCRIPTION_HEIGHT = 5.88f;

    float positionX;
    float positionY;

    Sprite changedSprite;
    Sprite changedDescription;

    public InventoryItem(float posX, float posY, TextureAtlas oAtlas, TextureAtlas dAtlas){
        objectsAtlas = oAtlas;
        descriptionAtlas = dAtlas;

        positionX = posX;
        positionY = posY;

        numberOfItens = new Array<Integer>();
        itemSprite = new Array<Sprite>();
        itemDescription = new Array<Sprite>();
    }

    public void addInventoryItem(String itemRegion){
        TextureRegion tpath = objectsAtlas.findRegion(itemRegion);
        TextureRegion opath = descriptionAtlas.findRegion(itemRegion);
        itemSprite.add(new Sprite(tpath));
        itemDescription.add(new Sprite(opath));

        itemSprite.get(maxItem).setBounds(positionX, positionY, ITEMBOX_WIDTH, ITEMBOX_HEIGHT);
        itemSprite.get(maxItem).setPosition(positionX - ITEMBOX_WIDTH / 2, positionY - ITEMBOX_HEIGHT / 2 + 2.5f);

        itemDescription.get(maxItem).setBounds(positionX, positionY - 2, DESCRIPTION_WIDTH, DESCRIPTION_HEIGHT);
        itemDescription.get(maxItem).setPosition(positionX - DESCRIPTION_WIDTH / 2, (positionY - DESCRIPTION_HEIGHT / 2) - 1);

        incrementMaxItem();

        if (itemSprite.size == 1){
            changedSprite = itemSprite.get(0);
            changedDescription = itemDescription.get(0);
        }
    }

    public void changeItem(int showedItem) {
    	if(itemSprite.size > 0) {
    		changedSprite = itemSprite.get(showedItem);
            changedDescription = itemDescription.get(showedItem);
    	}
    }

    public int getShowedItem() {
        return showedItem;
    }

    public void setShowedItem(int showedItem) {
        this.showedItem = showedItem;
    }

    public int getMaxItem() {
        return maxItem;
    }

    public void incrementMaxItem() {
        maxItem += 1;
    }

    public void incrementShowedItem(){
        showedItem +=1;
    }

    public void deincrementShowedItem(){
        showedItem -= 1;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
    	if(changedSprite != null) {
    		changedSprite.draw(batch);
            changedDescription.draw(batch);
    	}
    }
}

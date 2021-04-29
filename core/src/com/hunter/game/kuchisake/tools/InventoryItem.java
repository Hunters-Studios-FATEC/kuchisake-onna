package com.hunter.game.kuchisake.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class InventoryItem extends Actor{
    Sprite Item;

    InventoryManager inventoryManager;

    Texture itemTexture;

    CharSequence description;

    BitmapFont font;

    int showedItem = 0;
    int maxItem = -1;

    Array<Integer> numberOfItens;
    Array<Sprite> itemSprite;
    Array<CharSequence> itemDescription;

    float ITEMBOX_WIDTH = 1.96f;
    float ITEMBOX_HEIGHT = 1.96f;
    float DESCRIPTION_WIDTH = 1.96f;
    float DESCRIPTION_HEIGHT = 1.96f;

    float positionX;
    float positionY;

    Sprite changedSprite;
    CharSequence changedDescription;

    public InventoryItem(float posX, float posY){
        font = new BitmapFont();
        changedDescription = description;

        positionX = posX;
        positionY = posY;

        numberOfItens = new Array<Integer>();
        itemSprite = new Array<Sprite>();
        itemDescription = new Array<CharSequence>();

        addInventoryItem(maxItem + 1, "blue_square.png", "A blue square"); //teste
        changedSprite = new Sprite(itemSprite.get(0));
        changedDescription = itemDescription.get(0);
        addInventoryItem(maxItem + 1, "green_square.png", "A green square");


    }

    private void addInventoryItem(int maxItem, String texturePath, String description){
        Texture tpath = new Texture(texturePath);
        itemSprite.add(new Sprite(tpath));
        itemDescription.add(description);

        itemSprite.get(maxItem).setBounds(positionX, positionY, ITEMBOX_WIDTH, ITEMBOX_HEIGHT);
        itemSprite.get(maxItem).setPosition(positionX - ITEMBOX_WIDTH / 2, positionY - ITEMBOX_HEIGHT / 2 + 2);


        incrementMaxItem();
        System.out.println(maxItem);
    }

    public void changeItem(int showedItem){
        changedSprite = itemSprite.get(showedItem);
        changedDescription = itemDescription.get(showedItem);
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
        font.getData().setScale(1f);
        font.draw(batch, changedDescription, positionX, positionY - 1);
        changedSprite.draw(batch);
    }
}

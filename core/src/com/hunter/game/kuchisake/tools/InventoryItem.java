package com.hunter.game.kuchisake.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class InventoryItem {
    Sprite Item;

    InventoryManager inventoryManager;

    Texture itemTexture;

    CharSequence description;

    BitmapFont font;

    int maxInventoryItens = 1;

    Array<Integer> numberOfItens;
    Array<Sprite> itemSprite;
    Array<String> itemDescription;


    public InventoryItem(float posX, float posY, InventoryManager inventoryManager, CharSequence ItemDescription){
        font = new BitmapFont();

        // to do
        // Add item Sprite
        // Add font description
        // Add itens to their arrays
        // Add input to change iten sprite and description on the array
        // Add for loop to cycle through


    }
}

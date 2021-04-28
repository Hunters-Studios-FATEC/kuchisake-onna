package com.hunter.game.kuchisake.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.minigameGerador.Arrow;
import com.hunter.game.kuchisake.minigameGerador.Background;

public class InventoryManager {
    public Stage stage;
    SpriteBatch spriteBatch;
    FitViewport viewport;

    Background background;

    SelectionArrow selectionArrowLeft;
    SelectionArrow selectionArrowRight;

    Array<Integer> numberOfItens;
    Array<Sprite> itemSprite;
    Array<String> itemDescription;

    boolean isInventoryOpen = false;

    int maxInventoryItens = 1;

    public InventoryManager(SpriteBatch batch){
        spriteBatch = batch;

        viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE, new OrthographicCamera());

        viewport.apply();

        numberOfItens = new Array<Integer>();
        itemDescription = new Array<String>();
        itemSprite = new Array<Sprite>();

        stage = new Stage(viewport, spriteBatch);
        background = new Background(0, 0);

        selectionArrowLeft = new SelectionArrow((viewport.getWorldWidth() -2)  / 2 - 7, viewport.getWorldHeight() / 2, false);
        selectionArrowRight = new SelectionArrow((viewport.getWorldWidth()) / 2 + 7, viewport.getWorldHeight() / 2, true);


        //Input setas de seleção
    }

    public void openInventory(){
        Gdx.input.setInputProcessor(stage);
//        System.out.println("Inventory is Open");

        stage.addActor(background);
        stage.addActor(selectionArrowLeft);
        stage.addActor(selectionArrowRight);

        //stage.setKeyboardFocus(selectArrows);

    }

    public void inventoryUpdate(float dt){
        if (stage.getActors().size > 0) {
            spriteBatch.setProjectionMatrix(stage.getCamera().combined);
            stage.draw();
        }
    }

    public void closeInventory(){
//        System.out.println("Inventory closed");
        stage.clear();
    }

    public void inventoryDispose(){
        stage.dispose();
    }


    public void setMaxInventorySize(int maxInventorySize) {
        this.maxInventoryItens = maxInventorySize;
    }


    public void setInventoryOpen(boolean inventoryOpen) {
        isInventoryOpen = inventoryOpen;
    }

    public boolean getInventoryOpen() {
        return isInventoryOpen;
    }
}

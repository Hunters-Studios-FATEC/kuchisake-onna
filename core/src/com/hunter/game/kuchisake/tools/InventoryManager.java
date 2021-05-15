package com.hunter.game.kuchisake.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.minigameGerador.Background;

public class InventoryManager {
    public Stage stage;
    SpriteBatch spriteBatch;
    FitViewport viewport;

    Background background;

    SelectionArrow selectionArrowLeft;
    SelectionArrow selectionArrowRight;
    InventoryItem inventoryItem;

    boolean isInventoryOpen = false;
    boolean canCollectItem = false;

    Array<String> itemBackpack;

    TerrorGame game;

    TextureAtlas textureAtlas;
    TextureAtlas objectsAtlas;
    TextureAtlas descriptionAtlas;
    public InventoryManager(TerrorGame game){
        textureAtlas = game.getAssetManager().get("MinigameAssets/MinigameObjects.atlas");
        objectsAtlas = game.getAssetManager().get("Coletaveis/Coletaveis.atlas");
        descriptionAtlas = game.getAssetManager().get("Coletaveis/DescriptionAtlas.atlas");

        itemBackpack = new Array<String>();

        this.game = game;
        
        spriteBatch = game.batch;

        viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE, new OrthographicCamera());

        viewport.apply();

        stage = new Stage(viewport, spriteBatch);
        background = new Background(0, 0, textureAtlas);

        inventoryItem = new InventoryItem(viewport.getWorldWidth()  / 2, viewport.getWorldHeight() / 2, objectsAtlas, descriptionAtlas);

        selectionArrowLeft = new SelectionArrow((viewport.getWorldWidth() - 2)  / 2 - 7, viewport.getWorldHeight() / 2, false, inventoryItem, game, textureAtlas);
        selectionArrowRight = new SelectionArrow((viewport.getWorldWidth()) / 2 + 7, viewport.getWorldHeight() / 2, true, inventoryItem, game, textureAtlas);
    }

    public void openInventory(){
        Gdx.input.setInputProcessor(stage);
//        System.out.println("Inventory is Open");

        stage.addActor(background);
        stage.addActor(selectionArrowLeft);
        stage.addActor(selectionArrowRight);
        stage.addActor(inventoryItem);

    }

    public void addItem(String itemTag){
        inventoryItem.addInventoryItem(itemTag);
        itemBackpack.add(itemTag);
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

    public void inventoryResize(int width, int height) {
        stage.getViewport().update(width, height);
        spriteBatch.setProjectionMatrix(stage.getCamera().combined);
    }

    public Array<String> getItemBackpack() {
        return itemBackpack;
    }

    public void inventoryDispose(){
        stage.dispose();
    }

    public void setInventoryOpen(boolean inventoryOpen) {
        isInventoryOpen = inventoryOpen;
    }

    public boolean getInventoryOpen() {
        return isInventoryOpen;
    }

    public boolean getCanCollectItem() {
        return canCollectItem;
    }

    public void setCanCollectItem(boolean canCollectItem) {
        this.canCollectItem = canCollectItem;
    }


}

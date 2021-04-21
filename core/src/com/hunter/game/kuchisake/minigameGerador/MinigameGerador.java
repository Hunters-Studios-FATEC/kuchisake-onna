package com.hunter.game.kuchisake.minigameGerador;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;

public class MinigameGerador {

    SpriteBatch spriteBatch;
    public Stage stage;
    FitViewport viewport;

    Background background;

    Arrow arrowActor;

    public MinigameGerador(SpriteBatch batch){
        spriteBatch = batch;
        viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE, new OrthographicCamera());

        viewport.apply();

        stage = new Stage(viewport, spriteBatch);
        background = new Background(0, 0);
    }

    public void startMinigame() {
        Gdx.input.setInputProcessor(stage);

        arrowActor = new Arrow(2, 2);

        stage.addActor(background);
        stage.addActor(arrowActor);
    }

    public Stage getStage() {
        return stage;
    }


}

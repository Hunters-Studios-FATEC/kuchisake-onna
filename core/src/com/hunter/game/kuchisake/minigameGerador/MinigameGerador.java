package com.hunter.game.kuchisake.minigameGerador;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;

public class MinigameGerador {
    public Stage stage;
    SpriteBatch spriteBatch;
    FitViewport viewport;

    Background background;

    Arrow arrowActor;

    BarraTempo barraActor;

    boolean isFinished = false;

    int completedCounter = 0;

    final float ARROWS_HEIGHT = 6F;

    public MinigameGerador(SpriteBatch batch){
        spriteBatch = batch;
        viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE, new OrthographicCamera());

        viewport.apply();

        stage = new Stage(viewport, spriteBatch);
        background = new Background(0, 0);
    }

    public void startMinigame() {
        Gdx.input.setInputProcessor(stage);

        arrowActor = new Arrow(viewport.getWorldWidth() / 2, ARROWS_HEIGHT, this);
        barraActor = new BarraTempo(viewport.getWorldWidth() / 2, viewport.getWorldHeight() - 1, this, arrowActor);

        stage.addActor(background);
        stage.addActor(arrowActor);
        stage.addActor(barraActor);

        stage.setKeyboardFocus(arrowActor);
    }

    // Minigame update somente reseta, depois de chamado. Por isso o contador vai ate 2.
    public void minigameUpdate() {
        if (completedCounter < 2){
            completedCounter++;
            closeMinigame();
            startMinigame();
        } else {
            isFinished = true;
        }
    }

    public void verifyTimer(){
        if (barraActor.timerCounter <= 0){
            closeMinigame();
            //Tocar som para atrair mulher.
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void closeMinigame() {
        stage.clear();
    }

    public boolean getIsFinished() {
        return isFinished;
    }


}

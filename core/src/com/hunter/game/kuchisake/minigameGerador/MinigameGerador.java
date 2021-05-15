package com.hunter.game.kuchisake.minigameGerador;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;

public class MinigameGerador {
    public Stage stage;
    SpriteBatch spriteBatch;
    FitViewport viewport;

    Background background;

    Arrow arrowActor;

    Sound correctSound;
    Sound wrongSound;

    TextureRegion descTexture;
    Sprite descSprite;
    Actor description;

    BarraTempo barraActor;

    TextureAtlas textureAtlas;
    TextureAtlas descriptionAtlas;

    boolean isFinished = false;

    int completedCounter = 0;

    final float ARROWS_HEIGHT = 6F;

    public MinigameGerador(SpriteBatch batch, TextureAtlas textureAtlas, TextureAtlas descriptionAtlas){
        spriteBatch = batch;
        viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE, new OrthographicCamera());

        viewport.apply();

        this.textureAtlas = textureAtlas;
        this.descriptionAtlas = descriptionAtlas;

        descTexture = descriptionAtlas.findRegion("gerador");
        descSprite = new Sprite(descTexture);
        descSprite.setSize( 17.64f, 5.88f);
        descSprite.setPosition((viewport.getWorldWidth() / 2) - (descSprite.getWidth() / 2), (viewport.getWorldHeight() / 2) - (descSprite.getHeight()/2) -3);
        description = new Actor(){
            @Override
            public void draw(Batch batch, float parentAlpha) {
                descSprite.draw(batch);
            }
        };

        stage = new Stage(viewport, spriteBatch);
        background = new Background(0, 0, textureAtlas);
    }

    public void startMinigame() {
        Gdx.input.setInputProcessor(stage);

        arrowActor = new Arrow(viewport.getWorldWidth() / 2, ARROWS_HEIGHT, this, textureAtlas);
        barraActor = new BarraTempo(viewport.getWorldWidth() / 2, viewport.getWorldHeight() - 1, this, arrowActor, textureAtlas);

        stage.addActor(background);
        stage.addActor(arrowActor);
        stage.addActor(barraActor);
        stage.addActor(description);

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

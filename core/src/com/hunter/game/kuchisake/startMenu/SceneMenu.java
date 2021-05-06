package com.hunter.game.kuchisake.startMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.minigameGerador.Background;

public class SceneMenu implements Screen {

    ButtonStartGame start;
    ButtonContinue contin;
    ButtonControles controls;

    FitViewport viewport;
    OrthographicCamera orthographicCamera;
    TerrorGame terrorGame;

    Texture logo;
    Sprite logoSprite;

    Stage stage;

    public SceneMenu(TerrorGame terrorGame) {
        this.terrorGame = terrorGame;
        orthographicCamera = new OrthographicCamera();
        viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE, orthographicCamera);
        viewport.apply();

        orthographicCamera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        orthographicCamera.update();

        logo = terrorGame.getAssetManager().get("ButtonAssets/Logo_rascunho.png", Texture.class);
        logoSprite = new Sprite(logo);
        logoSprite.setSize(logoSprite.getWidth() / TerrorGame.SCALE, logoSprite.getHeight() / TerrorGame.SCALE);
        logoSprite.setPosition(viewport.getWorldWidth() / 2 - logoSprite.getWidth() / 2, viewport.getWorldHeight() / 2 + 1);

        this.start = new ButtonStartGame("ButtonAssets/start_rascunho.png", viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2 - 1, terrorGame, this);
        this.controls = new ButtonControles("ButtonAssets/controles_rascunho.png",viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2 - 2.5f, terrorGame);
        this.contin = new ButtonContinue("ButtonAssets/controles_rascunho.png", viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2 - 3.5f, terrorGame);

        stage = new Stage(viewport, terrorGame.batch);

        Gdx.input.setInputProcessor(stage);
        stage.addActor(start);
        stage.addActor(contin);
        stage.addActor(controls);
    }
    
    public Stage getStage() {
    	return stage;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        terrorGame.batch.setProjectionMatrix(orthographicCamera.combined);

        stage.draw();

        terrorGame.batch.begin();
        logoSprite.draw(terrorGame.batch);
        terrorGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        terrorGame.batch.setProjectionMatrix(orthographicCamera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    	stage.clear();
    	dispose();
    }

    @Override
    public void dispose() {
    	stage.dispose();
    }
}

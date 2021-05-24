package com.hunter.game.kuchisake.startMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    Texture fundo;
    Sprite fundoSprite;

    Stage stage;
    
    Music menuTheme;
    
    public SceneMenu(TerrorGame terrorGame) {
        this.terrorGame = terrorGame;
        orthographicCamera = new OrthographicCamera();
        viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE, orthographicCamera);
        viewport.apply();

        orthographicCamera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        orthographicCamera.update();

        fundo = terrorGame.getAssetManager().get("ButtonAssets/fundo_menu.png", Texture.class);
        fundoSprite = new Sprite(fundo);
        fundoSprite.setPosition(0, 0);
        fundoSprite.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        
        menuTheme = terrorGame.getAssetManager().get("Audio/Music/Night Wind.wav");
        menuTheme.setVolume(0.3f);
        menuTheme.setLooping(true);
        menuTheme.play();

        this.start = new ButtonStartGame("ButtonAssets/botao_jogar.png", viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, terrorGame, menuTheme);
        this.contin = new ButtonContinue("ButtonAssets/botao_carregar.png", viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2 - 2.5f, terrorGame);
        this.controls = new ButtonControles("ButtonAssets/botao_controles.png",viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2 - 5f, terrorGame);
        
        stage = new Stage(viewport, terrorGame.batch);

        Gdx.input.setInputProcessor(stage);
        stage.addActor(start);
        stage.addActor(contin);
        stage.addActor(controls);
        
        stage.setKeyboardFocus(controls);
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
        
        terrorGame.batch.begin();
        fundoSprite.draw(terrorGame.batch);
        terrorGame.batch.end();

        stage.draw();
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

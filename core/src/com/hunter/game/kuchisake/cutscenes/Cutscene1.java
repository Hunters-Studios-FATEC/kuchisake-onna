package com.hunter.game.kuchisake.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.minigameGerador.Background;

public class Cutscene1 implements Screen {

    FitViewport viewport;
    OrthographicCamera orthographicCamera;
    TerrorGame terrorGame;

    Texture fundo;
    Sprite fundoSprite;

    Stage stage;
    TextActor textActor;
    
    TextureAtlas textureAtlas;
    
    Music cutsceneTheme;
    
    public Cutscene1(TerrorGame terrorGame) {
        this.terrorGame = terrorGame;
        orthographicCamera = new OrthographicCamera();
        viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE, orthographicCamera);
        viewport.apply();

        orthographicCamera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        orthographicCamera.update();

        fundo = terrorGame.getAssetManager().get("Cutscenes/fundo_cutscene.png", Texture.class);
        fundoSprite = new Sprite(fundo);
        fundoSprite.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        fundoSprite.setPosition(0, 0);
        
        textureAtlas = terrorGame.getAssetManager().get("Cutscenes/Cutscene1Objects.atlas");
        
        cutsceneTheme = terrorGame.getAssetManager().get("Audio/Music/Scary Sorrow.wav");
        cutsceneTheme.setVolume(0.3f);
        cutsceneTheme.setLooping(true);
        cutsceneTheme.play();

        stage = new Stage(viewport, terrorGame.batch);
        
        textActor = new TextActor(terrorGame, viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, cutsceneTheme, true);

        Gdx.input.setInputProcessor(stage);
        
        stage.addActor(textActor);
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
        
        stage.act(delta);
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

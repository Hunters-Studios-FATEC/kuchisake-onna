package com.hunter.game.kuchisake.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.TesteTXTProb;
import com.hunter.game.kuchisake.startMenu.ButtonContinue;
import com.hunter.game.kuchisake.startMenu.ButtonControles;
import com.hunter.game.kuchisake.startMenu.ButtonStartGame;
import com.hunter.game.kuchisake.startMenu.SceneMenu;

public class GameOver implements Screen{
	FitViewport viewport;
    OrthographicCamera orthographicCamera;
    TerrorGame terrorGame;
    
    Texture gameOverText;
    Sprite gameOverSprite;
    
    Sound acabou;
    
    boolean playOnce;
    
    float timeCount;
    
    TesteTXTProb txt;
    
    public GameOver(TerrorGame terrorGame) {
        this.terrorGame = terrorGame;
        orthographicCamera = new OrthographicCamera();
        viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE, orthographicCamera);
        viewport.apply();

        orthographicCamera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        orthographicCamera.update();
        
        gameOverText = terrorGame.getAssetManager().get("ButtonAssets/game_over_text.png");
        
        gameOverSprite = new Sprite(gameOverText);
        gameOverSprite.setSize(17.64f, 5.88f);
        gameOverSprite.setPosition(viewport.getWorldWidth() / 2 - gameOverSprite.getWidth() / 2, 
        		viewport.getWorldHeight() / 2 - gameOverSprite.getHeight() / 2);
        
        acabou = terrorGame.getAssetManager().get("Audio/Sfx/acabou.ogg");
        
        playOnce = true;
        timeCount = 0;
        
        System.out.println("NÚMERO DE VEZES QUE SALVOU O JOGO: " + terrorGame.getSaveCount());
        System.out.println("NÚMERO DE VEZES QUE TENTOU SE ESCONDER DA VILÃ: " + terrorGame.getHideCount());
        
        txt = new TesteTXTProb();
        
        txt.writeTXT(terrorGame.getSaveCount(), terrorGame.getHideCount());
        
        terrorGame.resetVariables();
        
        
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        terrorGame.batch.setProjectionMatrix(orthographicCamera.combined);
        
        if(playOnce) {
        	acabou.play(0.5f);
        	playOnce = false;
        }
        
        terrorGame.batch.begin();
        gameOverSprite.draw(terrorGame.batch);
        terrorGame.batch.end();
        
        timeCount += delta;
        
        if(timeCount >= 5f) {
        	terrorGame.getAssetManager().clear();
        	
        	terrorGame.getAssetManager().load("ButtonAssets/botao_jogar.png", Texture.class);
        	terrorGame.getAssetManager().load("ButtonAssets/botao_carregar.png", Texture.class);
        	terrorGame.getAssetManager().load("ButtonAssets/botao_controles.png", Texture.class);
        	terrorGame.getAssetManager().load("ButtonAssets/fundo_menu.png", Texture.class);
        	terrorGame.getAssetManager().load("ButtonAssets/controls.png", Texture.class);
        	terrorGame.getAssetManager().load("Audio/Music/Night Wind.wav", Music.class);
        	
        	terrorGame.getAssetManager().finishLoading();
        	
        	terrorGame.setScreen(new SceneMenu(terrorGame));
        }
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

    }

    @Override
    public void dispose() {
    	
    }
}

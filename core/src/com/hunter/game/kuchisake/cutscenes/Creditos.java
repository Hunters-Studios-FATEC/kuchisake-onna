package com.hunter.game.kuchisake.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.startMenu.SceneMenu;

public class Creditos implements Screen {
	FitViewport viewport;
	OrthographicCamera orthographicCamera;
	TerrorGame terrorGame;
	
	Texture fundo;
	Sprite fundoSprite;
	
	Stage stage;
	
	Texture texture;
	Sprite spriteCreditos;
	
	Music cutsceneTheme;
	
	Actor creditos;
	
	public Creditos(TerrorGame terrorGame, Music music) {
	    this.terrorGame = terrorGame;
	    
	    cutsceneTheme = music;
	    
	    orthographicCamera = new OrthographicCamera();
	    viewport = new FitViewport(TerrorGame.WIDTH / TerrorGame.SCALE, TerrorGame.HEIGHT / TerrorGame.SCALE, orthographicCamera);
	    viewport.apply();
	
	    orthographicCamera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
	    orthographicCamera.update();
	
	    fundo = terrorGame.getAssetManager().get("Cutscenes/fundo_cutscene.png", Texture.class);
	    fundoSprite = new Sprite(fundo);
	    fundoSprite.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
	    fundoSprite.setPosition(0, 0);
	    
	    texture = terrorGame.getAssetManager().get("Cutscenes/creditos.png");
	    spriteCreditos = new Sprite(texture);
	    spriteCreditos.setSize(spriteCreditos.getWidth() / TerrorGame.SCALE , spriteCreditos.getHeight() / TerrorGame.SCALE);
	    spriteCreditos.setPosition(0, 0 - spriteCreditos.getHeight());
	    
	    
	    creditos = new Actor() {
	    	@Override
	    	public void draw(Batch batch, float parentAlpha) {
	    		spriteCreditos.setPosition(creditos.getX(), creditos.getY());
	    		spriteCreditos.draw(batch);
	    	}
	    	
	    };
	    
	    creditos.setBounds(spriteCreditos.getX(), spriteCreditos.getY(), spriteCreditos.getWidth(), spriteCreditos.getHeight());
	
	    MoveToAction moveToAction = new MoveToAction();
	    
	    moveToAction.setPosition(0, 1080 / TerrorGame.SCALE);
	    moveToAction.setDuration(30f);
	    
	    creditos.addAction(moveToAction);
	    
	    stage = new Stage(viewport, terrorGame.batch);
	    stage.addActor(creditos);
	
	    Gdx.input.setInputProcessor(stage);
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
	    
	    if(creditos.getY() >= 1080 / TerrorGame.SCALE) {
	    	
        	cutsceneTheme.stop();
	    	
			terrorGame.getAssetManager().clear();
	    	
	    	terrorGame.getAssetManager().load("ButtonAssets/fundo_menu.png", Texture.class);
        	terrorGame.getAssetManager().load("ButtonAssets/botao_jogar.png", Texture.class);
        	terrorGame.getAssetManager().load("ButtonAssets/botao_carregar.png", Texture.class);
        	terrorGame.getAssetManager().load("ButtonAssets/botao_controles.png", Texture.class);
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
		stage.clear();
		dispose();
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}
}
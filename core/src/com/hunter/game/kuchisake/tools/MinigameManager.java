package com.hunter.game.kuchisake.tools;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.WireMinigame.WireMinigame;
import com.hunter.game.kuchisake.hide.Hide;
import com.hunter.game.kuchisake.lockpick.LockPickMinigame;
import com.hunter.game.kuchisake.minigame.MinigameBook;
import com.hunter.game.kuchisake.minigameGerador.MinigameGerador;

public class MinigameManager {

    //Screen screen;
    Hide hideMinigame;
    LockPickMinigame lockPickMinigame;
    MinigameBook minigameBook;
    WireMinigame wireMinigame;
    MinigameGerador geradorMinigame;
    SpriteBatch spriteBatch;

    Sound correctSound;
    Sound wrongSound;
    Sound geradorOn;

    boolean lockCompleted;
    boolean wireCompleted;
    boolean bookCompleted;
    boolean geradorCompleted;

    boolean canStartMinigame = false;
    boolean isMinigameActive = false;

    float clearStageTimer = 0f;
    
    TextureAtlas textureAtlas;
    TextureAtlas descriptionAtlas;
    
    TerrorGame game;

    public MinigameManager(SpriteBatch batch, TerrorGame game){
    	//SpriteBatch spriteBatch = batch; -> A variavel global spriteBatch nao estava recebendo o parametro batch.
        spriteBatch = batch;
        
        this.game = game;
        
        textureAtlas = game.getAssetManager().get("MinigameAssets/MinigameObjects.atlas", TextureAtlas.class);
        descriptionAtlas = game.getAssetManager().get("MinigameAssets/MinigameDescription.atlas", TextureAtlas.class);

//        correctSound = game.getAssetManager().get("Audio/Sfx/minigame complete 6.ogg");
        wrongSound = game.getAssetManager().get("Audio/Sfx/wrongBuzzer.wav");
//        geradorOn = game.getAssetManager().get("Audio/Sfx/gerador ligando.ogg");

        hideMinigame = new Hide(batch, game, textureAtlas);
        lockPickMinigame = new LockPickMinigame(batch, textureAtlas);
        minigameBook = new MinigameBook(batch, textureAtlas);
        wireMinigame = new WireMinigame(batch, textureAtlas, descriptionAtlas);
        geradorMinigame = new MinigameGerador(batch, textureAtlas, descriptionAtlas);

//        int hideMinigame_ID = 0;
//        int lockPickMinigame_ID = 1;
//        int minigameBook_ID = 2;
//        int wireMinigame_ID = 3;
//        int minigameGerador_ID = 4;
    }

    public void startMinigame (int id){
        switch (id){
            case 0:
            	hideMinigame.setLevel(game.getLevel());
                hideMinigame.startMinigame();
                game.setIsHiding(true);
                game.setHasEncountered(false);
                break;

            case 1:
                if (!lockCompleted) {
                    lockPickMinigame.startMinigame();
                } else {
                    isMinigameActive = false;
                }
                break;


            case 2:
                if(!bookCompleted) {
                    minigameBook.startMinigame();
                } else {
                    isMinigameActive = false;
                }
                break;

            case 3:
                if(!wireCompleted) {
                    wireMinigame.startMinigame();
                } else {
                    isMinigameActive = false;
                }
                break;

            case 4:
                if(!geradorCompleted){
                    geradorMinigame.startMinigame();
                } else {
                    isMinigameActive = false;
                }
                break;
        }
    }

    public void minigameUpdate(float dt, int id) {
        switch (id) {
            case 0: {
                if (hideMinigame.stage.getActors().size > 0) {
                    spriteBatch.setProjectionMatrix(hideMinigame.stage.getCamera().combined);
                    hideMinigame.verifyConclusion();
                    hideMinigame.stage.act(dt);
                    hideMinigame.stage.draw();

                    if (hideMinigame.getIsFinished()) {
                    	boolean clearStage = hideMinigame.showResults(dt);
                    	
                    	if(clearStage) {
                    		/*clearStageTimer += dt;

                            if (clearStageTimer > 1.5) {
                                clearStageTimer = 0;
                                hideCompleted = true;
                                closeMinigame(0);
                                canStartMinigame = false;
                            }*/

                            closeMinigame(0);
                            game.setIsHiding(false);
                            canStartMinigame = false;
                    	}	
                    }
                }
                break;
            }

            case 1: {
                if (lockPickMinigame.stage.getActors().size > 0) {
                    spriteBatch.setProjectionMatrix(lockPickMinigame.stage.getCamera().combined);
                    lockPickMinigame.stage.act(dt);
                    lockPickMinigame.stage.draw();
                    if (lockPickMinigame.getIsFinished()) {
                        clearStageTimer += dt;

                        if (clearStageTimer > 1.5) {
                            clearStageTimer = 0;
                            lockCompleted = true;
                            closeMinigame(1);
                            canStartMinigame = false;
                        }
                    }
                }

                break;
            }

            case 2: {
                if (minigameBook.stage.getActors().size > 0) {
                    spriteBatch.setProjectionMatrix(minigameBook.stage.getCamera().combined);
                    minigameBook.verifyActorPos();
                    minigameBook.stage.act(dt);
                    minigameBook.stage.draw();
                    if (minigameBook.getIsFinished()) {
                        clearStageTimer += dt;

                        if (clearStageTimer > 1.5) {
                            clearStageTimer = 0;
                            bookCompleted = true;
                            closeMinigame(2);
                            canStartMinigame = false;
                        }
                    }
                }
                break;
            }

            case 3: {
                if (wireMinigame.stage.getActors().size > 0) {
                    spriteBatch.setProjectionMatrix(wireMinigame.stage.getCamera().combined);
                    wireMinigame.stage.act(dt);
                    wireMinigame.stage.draw();
                    wireMinigame.verifyWires();

                    if (wireMinigame.getIsFinished()) {
                        clearStageTimer += dt;

                        if (clearStageTimer > 1.5) {
                            clearStageTimer = 0;
                            wireCompleted = true;
                            closeMinigame(3);
                            canStartMinigame = false;
                        }
                    }
                }
                break;
            }

            case 4: {
                if (geradorMinigame.stage.getActors().size > 0) {
                    spriteBatch.setProjectionMatrix(geradorMinigame.stage.getCamera().combined);
                    geradorMinigame.stage.act(dt);
                    geradorMinigame.stage.draw();

                    if (geradorMinigame.getIsFinished()) {
                        clearStageTimer += dt;

                        if (clearStageTimer > 1.5) {
                            clearStageTimer = 0;
                            geradorCompleted = true;
                            closeMinigame(4);
                            canStartMinigame = false;
                        }
                    }
                }
                break;
            }
        }
    }

    public void minigameResize(int width, int height, int id) {

        switch (id){
            case (0):{
                hideMinigame.stage.getViewport().update(width, height);
                spriteBatch.setProjectionMatrix(hideMinigame.stage.getCamera().combined);
                break;
            }

            case(1):{
                lockPickMinigame.stage.getViewport().update(width, height);
                spriteBatch.setProjectionMatrix(lockPickMinigame.stage.getCamera().combined);
                break;
            }

            case (2):{
                minigameBook.stage.getViewport().update(width, height);
                spriteBatch.setProjectionMatrix(minigameBook.stage.getCamera().combined);
                break;
            }

            case (3):{
                wireMinigame.stage.getViewport().update(width, height);
                spriteBatch.setProjectionMatrix(wireMinigame.stage.getCamera().combined);
                break;
            }

            case 4:
                geradorMinigame.stage.getViewport().update(width, height);
                spriteBatch.setProjectionMatrix(geradorMinigame.stage.getCamera().combined);
                break;
        }
    }

    public void closeMinigame(int id){
        switch (id){
            case 0:
                hideMinigame.closeMinigame();
                isMinigameActive = false;
                break;

            case 1:
                lockPickMinigame.closeMinigame();
                isMinigameActive = false;
                break;

            case 2:
                minigameBook.closeMinigame();
                isMinigameActive = false;
                break;

            case 3:
                wireMinigame.closeMinigame();
                isMinigameActive = false;
                break;

            case 4:
                geradorMinigame.closeMinigame();
                isMinigameActive = false;
                break;
        }
    }

    public void setCanStartMinigame(boolean state) {
        canStartMinigame = state;
        //System.out.println(canStartMinigame);
    }

    public boolean getIsMinigameActive() {
        return isMinigameActive;
    }

    public void setMinigameActive(boolean minigameActive) {
        isMinigameActive = minigameActive;
    }

    public boolean getCanStartMinigame() {
        return canStartMinigame;
    }

    public Array<Actor> getActors(int id){
        Array<Actor> actors = new Array<Actor>();
        switch (id){
            case 0:
                actors = hideMinigame.getStage().getActors();
                break;

            case 1:
                actors = lockPickMinigame.getStage().getActors();
                break;

            case 2:
                actors = minigameBook.getStage().getActors();
                break;

            case 3:
                actors = wireMinigame.getStage().getActors();
                break;

            case 4:
                actors = geradorMinigame.getStage().getActors();
                break;
        }
        return actors;
    }

    public void minigameDispose(int id){
        switch (id){
            case 0:
                hideMinigame.stage.dispose();
                break;
            case 1:
                lockPickMinigame.stage.dispose();
                break;
            case 2:
                minigameBook.stage.dispose();
                break;
            case 3:
                wireMinigame.stage.dispose();
                break;
            case 4:
                geradorMinigame.stage.dispose();
                break;
        }
    }

    public boolean getLockCompleted() {
        return lockCompleted;
    }

    public boolean getWireCompleted() {
        return wireCompleted;
    }

    public boolean getBookCompleted() {
        return bookCompleted;
    }

    public boolean getGeradorCompleted() {
        return geradorCompleted;
    }
}

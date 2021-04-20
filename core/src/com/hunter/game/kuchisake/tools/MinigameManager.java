package com.hunter.game.kuchisake.tools;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.hunter.game.kuchisake.WireMinigame.WireMinigame;
import com.hunter.game.kuchisake.hide.Hide;
import com.hunter.game.kuchisake.lockpick.LockPickMinigame;
import com.hunter.game.kuchisake.minigame.MinigameBook;

public class MinigameManager {

    //Screen screen;
    Hide hideMinigame;
    LockPickMinigame lockPickMinigame;
    MinigameBook minigameBook;
    WireMinigame wireMinigame;
    SpriteBatch spriteBatch;

    boolean canStartMinigame = false;
    float clearStageTimer = 0;

    public MinigameManager(SpriteBatch batch){
    	//SpriteBatch spriteBatch = batch; -> A variável global spriteBatch não estava recebendo o parâmetro batch. 
        spriteBatch = batch;
        hideMinigame = new Hide(batch);
        lockPickMinigame = new LockPickMinigame(batch);
        minigameBook = new MinigameBook(batch);
        wireMinigame = new WireMinigame(batch);

//        int hideMinigame_ID = 0;
//        int lockPickMinigame_ID = 1;
//        int minigameBook_ID = 2;
//        int wireMinigame_ID = 3;
    }

    public void startMinigame (int id){
        switch (id){
            case 0:
                hideMinigame.startMinigame();
                break;

            case 1:
                lockPickMinigame.startMinigame();
                break;

            case 2:
                minigameBook.startMinigame();
                break;

            case 3:
                wireMinigame.startMinigame();
                break;
        }
    }

    public void minigameUpdate(float dt, int id) {
        switch (id){
            case 0: {
                if(hideMinigame.stage.getActors().size > 0) {
                    spriteBatch.setProjectionMatrix(hideMinigame.stage.getCamera().combined);
                    hideMinigame.stage.act(dt);
                    hideMinigame.stage.draw();

                    if(minigameBook.getIsFinished()) {
                        clearStageTimer += dt;

                        if (clearStageTimer > 1.5) {
                            minigameBook.stage.clear();
                            canStartMinigame = false;
                        }
                    }
                }
                break;
            }

            case 1:{
                if(lockPickMinigame.stage.getActors().size > 0) {
                    spriteBatch.setProjectionMatrix(lockPickMinigame.stage.getCamera().combined);
                    lockPickMinigame.stage.act(dt);
                    lockPickMinigame.stage.draw();
                    if(lockPickMinigame.getIsFinished()) {
                        clearStageTimer += dt;

                        if (clearStageTimer > 1.5) {
                            lockPickMinigame.stage.clear();
                            canStartMinigame = false;
                        }
                    }
                }

                break;
            }

            case 2:{
                if(minigameBook.stage.getActors().size > 0) {
                    spriteBatch.setProjectionMatrix(minigameBook.stage.getCamera().combined);
                    minigameBook.verifyActorPos();
                    minigameBook.stage.act(dt);
                    minigameBook.stage.draw();
                    if (minigameBook.getIsFinished()) {
                        clearStageTimer += dt;

                        if (clearStageTimer > 1.5) {
                            minigameBook.stage.clear();
                            canStartMinigame = false;
                        }
                    }
                }
                break;
            }

            case 3:{
                if(wireMinigame.stage.getActors().size > 0) {
                    spriteBatch.setProjectionMatrix(wireMinigame.stage.getCamera().combined);
                    wireMinigame.stage.act(dt);
                    wireMinigame.stage.draw();

                    if (wireMinigame.getIsFinished()) {
                        clearStageTimer += dt;

                        if (clearStageTimer > 1.5) {
                            minigameBook.stage.clear();
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
        }
    }

    public void closeMinigame(int id){
        switch (id){
            case 0:
                hideMinigame.closeMinigame();
                break;

            case 1:
                lockPickMinigame.closeMinigame();
                break;

            case 2:
                minigameBook.closeMinigame();
                break;

            case 3:
                wireMinigame.closeMinigame();
                break;
        }
    }

    public void setCanStartMinigame(boolean state) {
        canStartMinigame = state;
        //System.out.println(canStartMinigame);
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
        }
        return actors;
    }

    public void minigameDispose(){
        hideMinigame.stage.dispose();
        lockPickMinigame.stage.dispose();
        minigameBook.stage.dispose();
        wireMinigame.stage.dispose();
    }
}

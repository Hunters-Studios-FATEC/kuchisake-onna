package com.hunter.game.kuchisake.minigameGerador;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;

import java.util.Random;


public class Arrow extends Actor {
    Sprite seta;
    Sprite correto;

    Array<Sprite> sequencia;
    Array<Integer> rotationArray;
    Array<Sprite> corretos;

    MinigameGerador minigameGerador;

    Texture texture;
    Texture textureCorreto;

    Random randomNum;

    int rotationIndex;
    float nextStageTimer = 0f;
    boolean rowCompleted = false;


    public Arrow(float x_pos, float y_pos, MinigameGerador minigameGerador){
        sequencia = new Array<Sprite>();
        corretos = new Array<Sprite>();
        rotationArray = new Array<Integer>();
        randomNum = new Random();

        this.minigameGerador = minigameGerador;

        rotationIndex = 0;

        add_Arrow(x_pos, y_pos);

        addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.UP) {
                    if(rotationArray.get(rotationIndex) == 0){
                        sequencia.get(rotationIndex).setAlpha(0);
                        corretos.get(rotationIndex).setAlpha(1);
                        if (rotationIndex < sequencia.size - 1){
                            rotationIndex++;
                        }  else {
                            checkCompleted();
                        }
                    }
                }
                if(keycode == Input.Keys.LEFT) {
                    if(rotationArray.get(rotationIndex) == 3){
                        sequencia.get(rotationIndex).setAlpha(0);
                        corretos.get(rotationIndex).setAlpha(1);
                        if (rotationIndex < sequencia.size - 1){
                            rotationIndex++;
                        }  else {
                            checkCompleted();
                        }
                    }
                }
                if(keycode == Input.Keys.DOWN) {
                    if(rotationArray.get(rotationIndex) == 2){
                        sequencia.get(rotationIndex).setAlpha(0);
                        corretos.get(rotationIndex).setAlpha(1);
                        if (rotationIndex < sequencia.size - 1){
                            rotationIndex++;
                        } else {
                            checkCompleted();
                        }
                    }
                }
                if(keycode == Input.Keys.RIGHT) {
                    if(rotationArray.get(rotationIndex) == 1){
                        sequencia.get(rotationIndex).setAlpha(0);
                        corretos.get(rotationIndex).setAlpha(1);
                        if (rotationIndex < sequencia.size - 1){
                            rotationIndex++;
                        } else {
                            checkCompleted();
                        }
                    }
                }

                return true;

            }
        });
    }

    void checkCompleted(){
        rowCompleted = true;
    }

    void timer(float dt){
        if (rowCompleted){
            if (nextStageTimer < 1.5f) {
                nextStageTimer += dt ;
            } else {
                minigameGerador.minigameUpdate();
            }
        }
    }

    void add_Arrow(float xpos, float ypos) {
        float posI_x = xpos;
        float posI_Y = ypos;

        float seta_width = 1.96f; // width e height ja divididos pela escala - 100.
        float seta_height = 1.96f;

        int randomNumber = randomNum.nextInt(4) + 5;

        for (int i = 0; i < randomNumber; i++) {
            texture = new Texture("arrow.png");
            textureCorreto = new Texture("correto.png");
            seta = new Sprite(texture);
            correto = new Sprite(textureCorreto);
            seta.setBounds(posI_x, posI_Y, seta_width, seta_height);
            seta.setPosition(posI_x + seta.getWidth() * i - centerArrows(seta_width, randomNumber), posI_Y);

            correto.setBounds(seta.getX(), seta.getY(), seta.getWidth(), seta.getHeight());
            correto.setPosition(correto.getX(), correto.getY());

            int randomRotation = randomNum.nextInt(4);
            for (int j = 0; j < randomRotation; j++){
                seta.rotate90(true);
            }

            rotationArray.add(randomRotation);
            correto.setAlpha(0);
            corretos.add(correto);
            sequencia.add(seta);
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (int i = 0; i < sequencia.size; i++){
            sequencia.get(i).draw(batch);
            corretos.get(i).draw(batch);
        }
    }

    @Override
    public void act(float delta){
        timer(delta);
    }

    public float centerArrows(float widthSetas, int randomNum){
        int randomNumGen = randomNum;
        float widthArrow = widthSetas;
        return (widthArrow * randomNumGen) / 2;
    }

}

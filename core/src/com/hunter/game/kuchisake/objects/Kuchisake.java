package com.hunter.game.kuchisake.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.hunter.game.kuchisake.TerrorGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Kuchisake extends Thread{

    KuchisakeThread kuchisakeThread;

    int minigameID = -1;
    float isWalking = 0;
    float transitionTime = 0.10f;
    float frameChangeTimer = 0;

    boolean isLookingRight = true;

    Sprite kuchisakeSprite;

    Texture kuchisakeMoving;
    Animation<TextureRegion> animationWalking;

    int currentLine;
    int currentColumn;
    int finalLine;
    int finalColumn;

    Random randomInt;

    int pathStep = 0;

    boolean isSearching = false;
    boolean foundDoorXPos = false;

    public Kuchisake(float initialX, TerrorGame game) {
        kuchisakeMoving = game.getAssetManager().get("CharactersAssets/muie_sprites.png", Texture.class);
        animationWalking = new Animation<TextureRegion>(transitionTime, setFrameAnimation(kuchisakeMoving, 6, 5, 30));
        kuchisakeSprite = new Sprite(new TextureRegion(kuchisakeMoving, 596, 596));

        // Sempre vai começar o jogo da sala secreta, posição 3/0.
        currentLine = 3;
        currentColumn = 0;

        // 5 é o padrão pro player size multiplier.
        kuchisakeSprite.setSize(128 * 5.5f / TerrorGame.SCALE, 128 * 5.5f / TerrorGame.SCALE);
        kuchisakeSprite.setPosition(1750/100f - kuchisakeSprite.getWidth() / 2, (kuchisakeSprite.getY() - kuchisakeSprite.getHeight()) / 2 + (300 / TerrorGame.SCALE));
        kuchisakeThread = new KuchisakeThread();
    }

    Array<TextureRegion> setFrameAnimation(Texture texture, int framesLinha, int framesColuna, int framesTotal){
        Array<TextureRegion> spritesFrames = new Array<TextureRegion>();
        int frameWidth = 596;
        int frameHeight = 596;

        int frameCounter = 0;
        for (int i = 0; i < framesLinha; i++){
            for (int j = 0; j < framesColuna; j++){
                TextureRegion textureRegion = new TextureRegion(texture, frameWidth * j, frameHeight * i , frameWidth, frameHeight);
                if (frameCounter <= framesTotal){
                    spritesFrames.add(textureRegion);
                }
                frameCounter += 1;
            }
        }
        return spritesFrames;
    }

    public TextureRegion changeFrame(float delta){
        TextureRegion textureRegion;
        textureRegion = animationWalking.getKeyFrame(frameChangeTimer, true);

        if ((isWalking < 0 || !isLookingRight) && !textureRegion.isFlipX()){
            textureRegion.flip(true, false);
            isLookingRight = false;
        } else if ((isWalking > 0 || isLookingRight)&& textureRegion.isFlipX()){
            textureRegion.flip(true, false);
            isLookingRight = true;
        }

        frameChangeTimer += delta;
        return textureRegion;
    }

    public void KuchisakeUpdate(float delta){
        kuchisakeSprite.setRegion(changeFrame(delta));
    }

    public void draw(SpriteBatch batch){
        kuchisakeSprite.draw(batch);
    }

    public void setSizeAndPosition(float sizeMultiplicator, float yPosition){
        kuchisakeSprite.setSize(128 * sizeMultiplicator/ TerrorGame.SCALE, 128 * sizeMultiplicator/ TerrorGame.SCALE);
    }

    public void setminigameID(int minigameID) {
        this.minigameID = minigameID;
    }

    public void calculateRoute(){
        randomInt = new Random();

        do {
            finalLine = randomInt.nextInt(4);
            finalColumn = -1;

            switch (finalLine) {
                case 0:
                    finalColumn = randomInt.nextInt(3);
                    break;
                case 1:
                    finalColumn = randomInt.nextInt(6);
                    break;
                case 2:
                    finalColumn = randomInt.nextInt(7);
                    break;
                case 3:
                    finalColumn = 0;
                    break;
            }
        } while (currentLine == finalLine && currentColumn == finalColumn);

        kuchisakeThread.runThread(currentLine, currentColumn, finalLine, finalColumn);
        isSearching = true;

        while (isSearching){
            searchPlayer();
        }


    }

    public void searchPlayer(){
        int nextLine;
        int nextColumn;

        float doorX = 0;

        Object[] portas;
        Float[] doorsPosX = new Float[0];

        ArrayList<Integer[]> path = kuchisakeThread.getPath();

        nextLine = path.get(pathStep)[0];
        nextColumn = path.get(pathStep)[1];

        if (currentLine == nextLine){
            if (currentColumn > nextColumn ) {
                kuchisakeSprite.setX(kuchisakeSprite.getX() - (10f * Gdx.graphics.getDeltaTime()));
                if (kuchisakeSprite.getX() < 0){
                    kuchisakeSprite.setX(3500/TerrorGame.SCALE);
                    currentColumn = nextColumn;
                    pathStep += 1;
                }
            } else if (currentColumn < nextColumn){
                kuchisakeSprite.setX(kuchisakeSprite.getX() + (10f * Gdx.graphics.getDeltaTime()));
                if (kuchisakeSprite.getX() > 3500/TerrorGame.SCALE){
                    kuchisakeSprite.setX(0);
                    currentColumn = nextColumn;
                    pathStep += 1;
                }
            } else {
                currentColumn = nextColumn;
                pathStep += 1;
                isSearching = false;
            }
        } else if (currentLine != nextLine) {
            if (!foundDoorXPos) {
                if (currentLine > nextLine) {
                    portas = Arrays.copyOfRange(kuchisakeThread.getDoors()[currentLine][currentColumn], 0, kuchisakeThread.getDoors()[currentLine][currentColumn].length);
                    for (int i = 0; i < portas.length; i++) {
                        if (portas[i].toString().contains("doorDown" + Integer.toString(nextColumn))) {
                            doorsPosX = kuchisakeThread.getDoorsPosX()[currentLine][currentColumn];
                            doorX = doorsPosX[i];
                            foundDoorXPos = true;
                            break;
                        }
                    }
                } else if (currentLine < nextLine) {
                    portas = Arrays.copyOfRange(kuchisakeThread.getDoors()[currentLine][currentColumn], 0, kuchisakeThread.getDoors()[currentLine][currentColumn].length);
                    for (int i = 0; i < portas.length; i++) {
                        if (portas[i].toString().contains("doorUp" + Integer.toString(nextColumn))) {
                            doorsPosX = kuchisakeThread.getDoorsPosX()[currentLine][currentColumn];
                            doorX = doorsPosX[i];
                            foundDoorXPos = true;
                            break;
                        }
                    }
                }
            } else {
                if (kuchisakeSprite.getX() > doorX){
                    kuchisakeSprite.setX(kuchisakeSprite.getX() - 10f * Gdx.graphics.getDeltaTime());
                } else if (kuchisakeSprite.getX() < doorX){
                    kuchisakeSprite.setX(kuchisakeSprite.getX() + 10f * Gdx.graphics.getDeltaTime());
                } else {
                    foundDoorXPos = false;
                    doorsPosX = kuchisakeThread.getDoorsPosX()[nextLine][nextColumn];
                    portas = Arrays.copyOfRange(kuchisakeThread.getDoors()[nextLine][nextColumn], 0, kuchisakeThread.getDoors()[nextLine][nextColumn].length);

                    for (int i = 0; i < portas.length; i++) {
                        if (currentLine > nextLine) {
                            if (portas[i].toString().contains("doorUp" + Integer.toString(currentColumn))) {
                                doorsPosX = kuchisakeThread.getDoorsPosX()[nextLine][nextColumn];
                                doorX = doorsPosX[i];
                                break;
                            }
                        } else if (currentLine < nextLine){
                            if (portas[i].toString().contains("doorDown" + Integer.toString(currentColumn))) {
                                doorsPosX = kuchisakeThread.getDoorsPosX()[nextLine][nextColumn];
                                doorX = doorsPosX[i];
                                break;
                            }
                        }
                    }

                    kuchisakeSprite.setX(doorX);
                    currentLine = nextLine;
                    currentColumn = nextColumn;
                    pathStep += 1;
                }
            }
        }
        System.out.println("Posicao next L: " + nextLine + " C: " + nextColumn);
    }

    public void walkTowardsDoor(){

    }

    @Override
    public void run() {
        calculateRoute();
    }

    public void stalkPlayer(){
        kuchisakeThread.runThread(currentLine, currentColumn, finalLine, finalColumn);
    }

}

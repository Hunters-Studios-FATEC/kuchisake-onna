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
import com.hunter.game.kuchisake.screen.StandardRoom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Kuchisake extends Thread{

    KuchisakeThread kuchisakeThread;
    
    Body kuchisake;
    BodyDef bodyDef;

    FixtureDef fixtureDef;
    Fixture fixture;

    PolygonShape polygonShape;

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
    
    float doorX = 0;
    float moveTimer = 0;
    
    TerrorGame game;
    
    boolean isWaiting = true;

    boolean isSecondFloor = false;

    public Kuchisake(float initialX, TerrorGame game, World world) {
    	this.game = game;
    	
    	bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        polygonShape = new PolygonShape();
        
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        // Pos Y padrão para as salas - 160 / TerrorGame.SCALE + 1.28f
        bodyDef.position.set(initialX / TerrorGame.SCALE, 160 / TerrorGame.SCALE + 1.28f);
        kuchisake = world.createBody(bodyDef);

        polygonShape.setAsBox(128 / TerrorGame.SCALE, 128 / TerrorGame.SCALE);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;

        fixtureDef.filter.categoryBits = game.getKuchisakeBit();
        fixtureDef.filter.maskBits = game.getPlayerBit();

        fixture = kuchisake.createFixture(fixtureDef);
        fixture.setUserData("kuchisake");
        
        polygonShape.dispose();
        
        kuchisakeMoving = game.getAssetManager().get("CharactersAssets/muie_sprites.png", Texture.class);
        animationWalking = new Animation<TextureRegion>(transitionTime, setFrameAnimation(kuchisakeMoving, 6, 5, 30));
        kuchisakeSprite = new Sprite(new TextureRegion(kuchisakeMoving, 596, 596));

        // Sempre vai começar o jogo da sala secreta, posição 3/0.
        currentLine = 3;
        currentColumn = 0;

        // 5 é o padrão pro player size multiplier.
        kuchisakeSprite.setSize(128 * 5.5f / TerrorGame.SCALE, 128 * 5.5f / TerrorGame.SCALE);
        kuchisakeSprite.setPosition(1750/100f - kuchisakeSprite.getWidth() / 2, (kuchisakeSprite.getY() - kuchisakeSprite.getHeight()) / 2 + (300 / TerrorGame.SCALE));
        kuchisakeSprite.setAlpha(0);
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
        kuchisakeSprite.setPosition(kuchisake.getPosition().x - kuchisakeSprite.getWidth() / 2, 
        							kuchisake.getPosition().y - (1.28f + (16f / 596f) * kuchisakeSprite.getHeight()));
    }

    public void draw(SpriteBatch batch){
        kuchisakeSprite.draw(batch);
    }

    public void setSizeAndPosition(float sizeMultiplicator, float yPosition){
        kuchisakeSprite.setSize(128 * sizeMultiplicator/ TerrorGame.SCALE, 128 * sizeMultiplicator/ TerrorGame.SCALE);
        kuchisake.setTransform(kuchisake.getPosition().x, kuchisake.getPosition().y + yPosition, 0);
        
        kuchisake.setAwake(true);
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
    
    public void searchPlayer() {
        int nextLine;
        int nextColumn;

        //float doorX = 0;

        ArrayList<Integer[]> path = kuchisakeThread.getPath();

        nextLine = path.get(pathStep)[0];
        nextColumn = path.get(pathStep)[1];
        
        if (currentLine == nextLine){
            if (currentColumn > nextColumn ) {
                doorX = 0;
            } else if (currentColumn < nextColumn){
                doorX = 3500f / TerrorGame.SCALE;
            }
        } else if (currentLine != nextLine) {
        	Object[] portas = Arrays.copyOfRange(kuchisakeThread.getDoors()[currentLine][currentColumn], 0, 
					 kuchisakeThread.getDoors()[currentLine][currentColumn].length - 1);;
					 
        	if (currentLine > nextLine) {
                for (int i = 0; i < portas.length; i++) {
                    if (portas[i].toString().contains("doorDown" + Integer.toString(nextColumn))) {
                        Float[] doorsPosX = kuchisakeThread.getDoorsPosX()[currentLine][currentColumn];
                        doorX = doorsPosX[i];
                        //System.out.println(doorX);
                        break;
                    }
                }
            } else if (currentLine < nextLine) {
                for (int i = 0; i < portas.length; i++) {
                    if (portas[i].toString().contains("doorUp" + Integer.toString(nextColumn))) {
                        Float[] doorsPosX = kuchisakeThread.getDoorsPosX()[currentLine][currentColumn];
                        doorX = doorsPosX[i];
                        //System.out.println(doorX);
                        break;
                    }
                }
            }
        }
        
        foundDoorXPos = true;
        
        while(foundDoorXPos) {
        	synchronized (game) {
        		isWaiting = true;
        		
        		while(isWaiting) {
        			try {
						game.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
        		}
        		
        		walkToXPos(nextLine, nextColumn, path);
			}
    	}
    }
    
    public void setIsWaiting(boolean value) {
    	isWaiting = value;
    }
    
    void walkToXPos(int nxtLine, int nxtColumn, ArrayList<Integer[]> path) {
    	int nextLine = nxtLine;
    	int nextColumn = nxtColumn;

        if (kuchisake.getPosition().x > doorX + 1f) {
            kuchisake.setLinearVelocity(-5f, 0);
        } else if (kuchisake.getPosition().x < doorX - 1){
        	kuchisake.setLinearVelocity(5f, 0);
        } else {
        	kuchisake.setLinearVelocity(0, 0);
        	
        	if(moveTimer >= 1.5f) {
        		if(currentLine != nextLine) {
    	            Float[] doorsPosX = kuchisakeThread.getDoorsPosX()[nextLine][nextColumn];
    	            Object[] portas = Arrays.copyOfRange(kuchisakeThread.getDoors()[nextLine][nextColumn], 0, 
    	            									 kuchisakeThread.getDoors()[nextLine][nextColumn].length - 1);
    	            
    	            float newXPos = 0;
    	
    	            for (int i = 0; i < portas.length; i++) {
    	                if (currentLine > nextLine) {
    	                    if (portas[i].toString().contains("doorUp" + Integer.toString(currentColumn))) {
    	                    	newXPos = doorsPosX[i];
    	                        break;
    	                    }
    	                } else if (currentLine < nextLine){
    	                    if (portas[i].toString().contains("doorDown" + Integer.toString(currentColumn))) {
    	                    	newXPos = doorsPosX[i];
    	                        break;
    	                    }
    	                }
    	            }
    	
    	            kuchisake.setTransform(newXPos, kuchisake.getPosition().y, 0);

                    System.out.println(nextLine + " " + nextColumn);

    	            if((nextLine == 1 && nextColumn == 2)) {
    	                isSecondFloor = true;
    	            	setSizeAndPosition(3.75f, 4.25f);
    	            }
    	            else if((nextLine == 0 && nextColumn == 1)) {
    	                isSecondFloor = false;
    	            	setSizeAndPosition(5.5f, -4.25f);
    	            }
    	            else{
    	                isSecondFloor = false;
                        kuchisakeSprite.setSize(128 * 5.5f/ TerrorGame.SCALE, 128 * 5.5f/ TerrorGame.SCALE);
                        kuchisake.setTransform(kuchisake.getPosition().x, 2.88f, 0);
                    }
            	}
            	else {
            		if(currentColumn > nextColumn) {
            			kuchisake.setTransform(3500 / TerrorGame.SCALE, kuchisake.getPosition().y, 0);
            		}
            		else {
            			kuchisake.setTransform(0, kuchisake.getPosition().y, 0);
            		}
            	}

                currentLine = nextLine;
                currentColumn = nextColumn;
                pathStep += 1;
                foundDoorXPos = false;
                
                moveTimer = 0;
                
                if(pathStep == path.size()) {
                	isSearching = false;
                	pathStep = 0;
                }
        	}
        	else {
        		moveTimer += Gdx.graphics.getDeltaTime();
        	}
        }
    }

    /*public void searchPlayer(){
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
                    portas = Arrays.copyOfRange(kuchisakeThread.getDoors()[currentLine][currentColumn], 0, kuchisakeThread.getDoors()[currentLine][currentColumn].length - 1);
                    for (int i = 0; i < portas.length; i++) {
                        if (portas[i].toString().contains("doorDown" + Integer.toString(nextColumn))) {
                            doorsPosX = kuchisakeThread.getDoorsPosX()[currentLine][currentColumn];
                            doorX = doorsPosX[i];
                            foundDoorXPos = true;
                            break;
                        }
                    }
                } else if (currentLine < nextLine) {
                    portas = Arrays.copyOfRange(kuchisakeThread.getDoors()[currentLine][currentColumn], 0, kuchisakeThread.getDoors()[currentLine][currentColumn].length - 1);
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
    }*/


    @Override
    public void run() {
    	while(true) {
    		calculateRoute();
    	}
    }

    public void stalkPlayer(){
        kuchisakeThread.runThread(currentLine, currentColumn, finalLine, finalColumn);
    }
    
    public Body getBody() {
    	return kuchisake;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public int getCurrentColumn() {
        return currentColumn;
    }

    public Sprite getKuchisakeSprite() {
        return kuchisakeSprite;
    }
}

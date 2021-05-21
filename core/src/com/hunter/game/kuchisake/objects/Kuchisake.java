package com.hunter.game.kuchisake.objects;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
import com.hunter.game.kuchisake.tools.InventoryManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Kuchisake extends Thread{

    KuchisakeThread kuchisakeThread;
    KuchisakeThread kuchisakeThread2;
    
    Body kuchisake;
    BodyDef bodyDef;

    FixtureDef fixtureDef;
    Fixture fixture;

    PolygonShape polygonShape;

    //int minigameID = -1;
    //float isWalking = 0;
    float transitionTime = 0.10f;
    float frameChangeTimer = 0;

    boolean isLookingRight = false;

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
    
    boolean canMove = false;
    boolean canChangeRoom = false;
    boolean isChangingRoom = false;

    boolean isSecondFloor = false;
    
    Sound teAchei;
    Sound portaAbrindo;
    Sound madeiraEstralando2;
    Sound madeiraRangendo6;
    Sound madeiraRangendo7;
    Sound madeiraQuebrando;
    
    float portaVolume;
    float madeiraVolume;
    
    int previousFrameIndex = 0;
    
    Random random;
    
    boolean canSetVolume = false;

    public Kuchisake(float initialX, TerrorGame game) {
    	this.game = game;
    	
    	bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        polygonShape = new PolygonShape();
        
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        // Pos Y padrão para as salas - 160 / TerrorGame.SCALE + 1.28f
        bodyDef.position.set(initialX / TerrorGame.SCALE, 160 / TerrorGame.SCALE + 1.28f);
        kuchisake = game.getWorld().createBody(bodyDef);

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
        currentLine = 2;
        currentColumn = 6;

        // 5 é o padrão pro player size multiplier.
        kuchisakeSprite.setSize(128 * 5.5f / TerrorGame.SCALE, 128 * 5.5f / TerrorGame.SCALE);
        kuchisakeSprite.setPosition(1750/100f - kuchisakeSprite.getWidth() / 2, (kuchisakeSprite.getY() - kuchisakeSprite.getHeight()) / 2 + (300 / TerrorGame.SCALE));
        kuchisakeSprite.setAlpha(0);
        
        kuchisakeThread = new KuchisakeThread();
        kuchisakeThread2 = new KuchisakeThread();
        
        setAudioVolume();
        
        teAchei = game.getAssetManager().get("Audio/Sfx/Te achei.ogg");
        portaAbrindo = game.getAssetManager().get("Audio/Sfx/porta abrindo 3.ogg");
        madeiraEstralando2 = game.getAssetManager().get("Audio/Sfx/madeira estralando 2.ogg");
        madeiraRangendo6 = game.getAssetManager().get("Audio/Sfx/madeira rangendo 6.ogg");
        madeiraRangendo7 = game.getAssetManager().get("Audio/Sfx/madeira rangendo 7.ogg");
        madeiraQuebrando = game.getAssetManager().get("Audio/Sfx/madeira quebrando.ogg");
        
        random = new Random();
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

        if ((kuchisake.getLinearVelocity().x < 0 || !isLookingRight) && textureRegion.isFlipX()){
            textureRegion.flip(true, false);
            isLookingRight = false;
        } else if ((kuchisake.getLinearVelocity().x > 0 || isLookingRight) && !textureRegion.isFlipX()){
            textureRegion.flip(true, false);
            isLookingRight = true;
        }

        frameChangeTimer += delta;
        
        if(frameChangeTimer >= transitionTime * 30) {
        	frameChangeTimer -= transitionTime * 30;
        }
        
        if(animationWalking.getKeyFrameIndex(frameChangeTimer) == 2 || 
        		animationWalking.getKeyFrameIndex(frameChangeTimer) == 15) {
        	if(previousFrameIndex == 1 || previousFrameIndex == 15) {
        		if(random.nextInt(2) == 0 && kuchisake.getLinearVelocity().x != 0) {
        			int soundID = random.nextInt(2);
        			
        			switch(soundID) {
	        			case 0:
	        				madeiraEstralando2.play(madeiraVolume);
	        				break;
	        			case 1:
	        				madeiraQuebrando.play(madeiraVolume);
	        				break;
        			}
        		}
        	}
        }
        
        if(previousFrameIndex < animationWalking.getKeyFrameIndex(frameChangeTimer)) {
        	previousFrameIndex = animationWalking.getKeyFrameIndex(frameChangeTimer);
        }
        else if(previousFrameIndex > animationWalking.getKeyFrameIndex(frameChangeTimer)) {
        	previousFrameIndex = 0;
        }
        
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
    
    void searchPlayer() {
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
        		canMove = false;
        		
        		while(!canMove) {
        			try {
						game.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
        		}
			}
        	
        	if(!game.getHasEncountered() && !game.getInventoryManager().getInventoryOpen() && !game.getMinigameManager().getIsMinigameActive()) {
        		walkToXPos(nextLine, nextColumn, path);
        	}
        	else if(game.getHasEncountered()) {
        		foundDoorXPos = false;
        		moveTimer = 0;
        		isSearching = false;
            	pathStep = 0;
        	}
        	else {
        		kuchisake.setLinearVelocity(0, 0);
        	}
    	}
    }
    
    void goToPlayerRoom() {
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
        		canMove = false;
        		
        		while(!canMove) {
        			try {
						game.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
        		}
			}
        	
        	int lastStepLine = path.get(path.size() - 1)[0];
        	int lastStepColumn = path.get(path.size() - 1)[1];
        	
        	if(!game.getIsHiding() && !game.getInventoryManager().getInventoryOpen() && !game.getMinigameManager().getIsMinigameActive() &&
        			(game.getPlayerLine() == lastStepLine && game.getPlayerColumn() == lastStepColumn)) {
        		walkToXPos(nextLine, nextColumn, path);
        	}
        	else if(game.getIsHiding() || !(game.getPlayerLine() == lastStepLine && game.getPlayerColumn() == lastStepColumn)) {
        		foundDoorXPos = false;
        		isSearching = false;
            	pathStep = 0;
        	}
        	else {
        		kuchisake.setLinearVelocity(0, 0);
        	}
    	}
    }
    
    void walkToXPos(int nxtLine, int nxtColumn, ArrayList<Integer[]> path) {
    	int nextLine = nxtLine;
    	int nextColumn = nxtColumn;
    	
    	if(canSetVolume) {
    		setAudioVolume();
    		canSetVolume = false;
    	}
    	
    	if (kuchisake.getPosition().x > doorX + 0.5f) {
            kuchisake.setLinearVelocity(-5f, 0);
        } else if (kuchisake.getPosition().x < doorX - 0.5f){
        	kuchisake.setLinearVelocity(5f, 0);
        } else {
        	kuchisake.setLinearVelocity(0, 0);
        	
        	if(moveTimer > 1.5f) {
        		float newXPos = 0;
        		
        		if(currentLine != nextLine) {
    	            Float[] doorsPosX = kuchisakeThread.getDoorsPosX()[nextLine][nextColumn];
    	            Object[] portas = Arrays.copyOfRange(kuchisakeThread.getDoors()[nextLine][nextColumn], 0, 
    	            									 kuchisakeThread.getDoors()[nextLine][nextColumn].length - 1);
    	
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
            	}
            	else {
            		if(currentColumn > nextColumn) {
            			newXPos = 3500 / TerrorGame.SCALE;
            		}
            		else {
            			newXPos = 0;
            		}
            	}
            	
            	canChangeRoom = false;
                
                synchronized (game) {
        			while(!canChangeRoom) {
        				try {
    						game.wait();
    					} catch (InterruptedException e) {
    						e.printStackTrace();
    					}
        			}
        			
        			isChangingRoom = true;
    			}
            	
                synchronized (this) {
                	kuchisake.setTransform(newXPos, kuchisake.getPosition().y, 0);
                	
                	currentLine = nextLine;
                    currentColumn = nextColumn;
                    
                    setAudioVolume();
                	
                	portaAbrindo.play(portaVolume);
                	
                	if((currentLine == 1 && currentColumn == 2)) {
                        isSecondFloor = true;
                        kuchisake.setTransform(kuchisake.getPosition().x, kuchisake.getPosition().y + 4.25f, 0);
                    }
                    else if(isSecondFloor){
                        isSecondFloor = false;
                        kuchisake.setTransform(kuchisake.getPosition().x, kuchisake.getPosition().y - 4.25f, 0);
                    }
                	
                	pathStep += 1;
                    foundDoorXPos = false;
                    
                    moveTimer = 0;
                    
                    if(pathStep == path.size()) {
                    	isSearching = false;
                    	pathStep = 0;
                    }
                	
                	isChangingRoom = false;
                	notify();
                }
        	}
        	else {
        		moveTimer += Gdx.graphics.getDeltaTime();
        	}
        }
    }
    
    public void setCanMove(boolean value) {
    	canMove = value;
    }
    
    public void setCanChangeRoom(boolean value) {
    	canChangeRoom = value;
    }
    
    public boolean getIsChangingRoom() {
    	return isChangingRoom;
    }

    @Override
    public void run() {
    	while(true) {
    		if(!game.getIsHiding() && !game.getInventoryManager().getInventoryOpen() && !game.getMinigameManager().getIsMinigameActive()) {
    			if(!game.getHasEncountered()) {
        			calculateRoute();
        		}
        		else {
        			stalkPlayer();
        		}
    		}
    		else {
    			kuchisake.setLinearVelocity(0,0);
    		}
    	}
    }

    public void stalkPlayer(){
    	if(currentLine != game.getPlayerLine() || currentColumn != game.getPlayerColumn()) {
    		finalLine = game.getPlayerLine();
        	finalColumn = game.getPlayerColumn();
        	
            kuchisakeThread.runThread(currentLine, currentColumn, finalLine, finalColumn);
            
            isSearching = true;
            
            while (isSearching){
            	goToPlayerRoom();
            }
    	}
    	else {
        	synchronized (game) {
        		canMove = false;
        		
        		while(!canMove) {
        			try {
						game.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
        		}
			}
        	
    		if(game.getPlayerXPos() > kuchisake.getPosition().x) {
    			kuchisake.setLinearVelocity(5f, 0);
    		}
    		else if(game.getPlayerXPos() < kuchisake.getPosition().x){
    			kuchisake.setLinearVelocity(-5f, 0);
    		}
    	}
    }
    
    void setAudioVolume() {
    	if((currentLine != game.getPlayerLine() || currentColumn != game.getPlayerColumn()) && game.getPlayerColumn() != -1) {
        	kuchisakeThread2.runThread(currentLine, currentColumn, game.getPlayerLine(), game.getPlayerColumn());
        	
        	ArrayList<Integer[]> playerDistance = kuchisakeThread2.getPath();
        	
        	portaVolume = 0.7f - playerDistance.size() * 0.175f;
        	madeiraVolume = 0.35f - playerDistance.size() * 0.1f;
    	}
    	else if(game.getPlayerColumn() == -1 && (currentLine != 1 || currentColumn != 0)) {
        	kuchisakeThread2.runThread(currentLine, currentColumn, 1, 0);
        	
        	ArrayList<Integer[]> playerDistance = kuchisakeThread2.getPath();
        	
        	portaVolume = 0.7f - playerDistance.size() * 0.175f;
        	madeiraVolume = 0.35f - playerDistance.size() * 0.1f;
    	}
    	else {
    		portaVolume = 0.7f;
    		madeiraVolume = 0.35f;
    	}
    	
    	if(portaVolume < 0) {
    		portaVolume = 0;
    	}
    	
    	if(madeiraVolume < 0) {
    		madeiraVolume = 0;
    	}
    	
    	System.out.println(madeiraVolume);
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

    public Sprite getSprite() {
        return kuchisakeSprite;
    }
    
    public void playFoundAudio() {
    	teAchei.play(0.5f);
    }
    
    public void stopFoundAudio() {
    	teAchei.stop();
    }
    
    public void setCanSetVolume(boolean value) {
    	canSetVolume = value;
    }
}

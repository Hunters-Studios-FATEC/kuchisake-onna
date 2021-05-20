package com.hunter.game.kuchisake.objects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.minigameGerador.Background;
import com.hunter.game.kuchisake.screen.StandardRoom;
import com.hunter.game.kuchisake.tools.InventoryManager;
import com.hunter.game.kuchisake.tools.MinigameManager;


public class Player {
    Body player;
    BodyDef bodyDef;
    World world;

    FixtureDef fixtureDef;
    Fixture fixture;

    PolygonShape polygonShape;

    int minigameID = -1;
    float isWalking = 0;
    float transitionTime = 0.15f;
    float frameChangeTimer = 0;

    int formerState;
    int currentState;
    String itemName;

    EdgeShape collisionSensor;

    MinigameManager minigameManager;
    InventoryManager inventoryManager;
    StandardRoom standardRoom;

    boolean changeObjectVisual = false;
    boolean canInteractWorld = false;
    boolean canChangeRoom = false;
    boolean isTouchingDoor = false;
    boolean isLookingRight = true;

    Sprite playerSprite;

    Texture playerWalk;
    Texture playerStop;
    
    Sound pickupSound;
    Sound madeiraRangendo2;
    Sound madeiraRangendo6;
    Sound madeiraRangendo7;
    Sound passo;
    
    int previousFrameIndex = 0;

    Animation<TextureRegion> animationStopped;
    Animation<TextureRegion> animationWalking;
    TerrorGame game;

    public Array<TextureRegion> testeAnima;
    
    Random random;

    public Player(World world, MinigameManager minigameManager, InventoryManager inventoryManager, Collisions collisions, StandardRoom standardRoom, float initialX, TerrorGame game) {
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        polygonShape = new PolygonShape();

        this.world = world;
        this.game = game;

        pickupSound = game.getAssetManager().get("Audio/Sfx/item pickup 7.ogg");
        madeiraRangendo2 = game.getAssetManager().get("Audio/Sfx/madeira rangendo 2.ogg");
        madeiraRangendo6 = game.getAssetManager().get("Audio/Sfx/madeira rangendo 6.ogg");
        madeiraRangendo7 = game.getAssetManager().get("Audio/Sfx/madeira rangendo 7.ogg");
        passo = game.getAssetManager().get("Audio/Sfx/passo.ogg");
        
        playerWalk = game.getAssetManager().get("CharactersAssets/sprites_protag_right.png", Texture.class);
        playerStop = game.getAssetManager().get("CharactersAssets/sprite_stoped_right.png", Texture.class);

        animationStopped = new Animation<TextureRegion>(transitionTime, setFrameAnimation(playerStop, 4, 6, 24));
        animationWalking = new Animation<TextureRegion>(transitionTime, setFrameAnimation(playerWalk, 3, 3, 7));
        playerSprite = new Sprite(new TextureRegion(playerStop, 720, 720));

        bodyDef.type = BodyDef.BodyType.DynamicBody;

        // Pos Y padrão para as salas - 160 / TerrorGame.SCALE + 1.28f
        bodyDef.position.set(initialX / TerrorGame.SCALE, 160 / TerrorGame.SCALE + 1.28f);
        player = world.createBody(bodyDef);

        polygonShape.setAsBox(128 / TerrorGame.SCALE, 128 / TerrorGame.SCALE);
        fixtureDef.shape = polygonShape;

        fixtureDef.filter.categoryBits = game.getPlayerBit();
        fixtureDef.filter.maskBits = collisions.getGroundBit();

        fixture = player.createFixture(fixtureDef);
        
        polygonShape.dispose();

        fixtureDef = new FixtureDef();

        collisionSensor = new EdgeShape();
        collisionSensor.set(new Vector2(-64 / TerrorGame.SCALE, 0), new Vector2(64 / TerrorGame.SCALE, 0));

        fixtureDef.shape = collisionSensor;
        fixtureDef.isSensor = true;
        
        //definido o categoryBits (identificador da fixture do sensor)
        fixtureDef.filter.categoryBits = game.getPlayerBit();

        //definido o maskBits (identificador das colisoes que esse sensor detecta)
        fixtureDef.filter.maskBits = (short) (collisions.getHideBit() + collisions.getLockpickBit() + collisions.getShelfBit() + collisions.getWireBit() + collisions.getGeradorBit() + collisions.getPortaBit() + collisions.getITEM_BIT() + collisions.getINTERACTIBLE_BIT());
        fixture = player.createFixture(fixtureDef);
        fixture.setUserData("player sensor");
        
        collisionSensor.dispose();

        // 5 é o padrão pro player size multiplier.
        playerSprite.setSize(128 * 5 / TerrorGame.SCALE, 128 * 5 / TerrorGame.SCALE);
        playerSprite.setPosition(player.getPosition().x - playerSprite.getWidth() / 2, (player.getPosition().y - playerSprite.getHeight()) / 2 + (300 / TerrorGame.SCALE));

        //parametro minigameManager criado e atribuido a variavel global minigameManager.
        this.minigameManager = minigameManager;
        this.inventoryManager = inventoryManager;
        
        this.standardRoom = standardRoom;
        
        random = new Random();
    }


    int checkState(float walkingVelocity){
        if (walkingVelocity != 0){
            //System.out.println("andando");
            return 1;
        } else {
            //System.out.println("parado");
            return 0;
        }
    }


    Array<TextureRegion> setFrameAnimation(Texture texture, int framesLinha, int framesColuna, int framesTotal){
        Array<TextureRegion> spritesFrames = new Array<TextureRegion>();
        int frameWidth = 720;
        int frameHeight = 720;

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
        currentState = checkState(isWalking);
        TextureRegion textureRegion;

        if (currentState == 0) {
            textureRegion = animationStopped.getKeyFrame(frameChangeTimer, true);
        } else {
            textureRegion = animationWalking.getKeyFrame(frameChangeTimer, true);
            
            if(animationStopped.getKeyFrameIndex(frameChangeTimer) == 1 || 
            		animationStopped.getKeyFrameIndex(frameChangeTimer) == 5) {
            	if(previousFrameIndex == 0 || previousFrameIndex == 4) {
            		passo.play(0.25f);
            		
            		if(random.nextInt(4) == 0) {
                		int soundID = random.nextInt(3);
                		switch(soundID) {
                			case 0:
                				madeiraRangendo2.play(0.5f);
                				break;
                			case 1:
                				madeiraRangendo6.play(0.5f);
                				break;
                			case 2:
                				madeiraRangendo7.play(0.5f);
                				break;
                		}
                	}
            	}
        	}
            
            if(animationWalking.getKeyFrameIndex(frameChangeTimer) > previousFrameIndex) {
            	previousFrameIndex = animationWalking.getKeyFrameIndex(frameChangeTimer);
            }
            else if(animationWalking.getKeyFrameIndex(frameChangeTimer) < previousFrameIndex) {
            	previousFrameIndex = 0;
            }
        }

        if ((isWalking < 0 || !isLookingRight) && !textureRegion.isFlipX()){
            textureRegion.flip(true, false);
            isLookingRight = false;
        } else if ((isWalking > 0 || isLookingRight)&& textureRegion.isFlipX()){
            textureRegion.flip(true, false);
            isLookingRight = true;
        }

        frameChangeTimer = (currentState == formerState) ? frameChangeTimer + delta : 0;
        formerState = currentState;
        
        short framesCount = 24;
        
        if(currentState == 1) {
        	framesCount = 8;
        }
        
        if(frameChangeTimer >= transitionTime * framesCount) {
        	frameChangeTimer -= transitionTime * framesCount;
        }
        
        return textureRegion;
    }
    
    public void walkInput() {
    	isWalking = 0;
        if (!minigameManager.getIsMinigameActive() && !inventoryManager.getInventoryOpen() && !standardRoom.getCanSwitchAssets()) {
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//                player.applyLinearImpulse(new Vector2(0.5f, 0), player.getWorldCenter(), true);
                isWalking = 20f;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//                player.applyLinearImpulse(new Vector2(-0.5f, 0), player.getWorldCenter(), true);
                isWalking = -20f;
            }
        }
    }

    public void handleInput() {
        // Vai abrir e fechar
        if (Gdx.input.isKeyJustPressed(Input.Keys.I) && !minigameManager.getIsMinigameActive()){
            if (!inventoryManager.getInventoryOpen()) {
                inventoryManager.openInventory();
                inventoryManager.setInventoryOpen(true);
            }
            else {
                inventoryManager.closeInventory();
                inventoryManager.setInventoryOpen(false);
            }
        }

        player.setLinearVelocity(new Vector2(isWalking, 0));

        if (Gdx.input.isKeyJustPressed(Input.Keys.E) && minigameManager.getCanStartMinigame() && 
        		!inventoryManager.getInventoryOpen()) {
            if (minigameManager.getActors(minigameID).size == 0) {
                minigameManager.setMinigameActive(true);
                minigameManager.startMinigame(minigameID);
            } else {
                minigameManager.closeMinigame(minigameID);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E) && inventoryManager.getCanCollectItem() && 
        		!inventoryManager.getInventoryOpen()){
            inventoryManager.addItem(itemName);
            pickupSound.play(0.5f);
            Array<Body> bodies = new Array<Body>(world.getBodyCount());
            world.getBodies(bodies);
            for (Body body:bodies){
                Array<Fixture> fixtures;
                fixtures = body.getFixtureList();
                for (Fixture fixture:fixtures){
                    if (itemName.equals(fixture.getUserData())){
                        world.destroyBody(body);
                        break;
                    }
                }
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F) && isTouchingDoor && 
        		!minigameManager.getIsMinigameActive() && !inventoryManager.getInventoryOpen()){
        	setCanChangeRoom(true);
            System.out.println("mudou");
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.E) && getCanInteractWorld()){
            setChangeObjectVisual(true);
            System.out.println("mudar Sprite Lareira");
        }
    }

    public void playerUpdate(float delta){
        playerSprite.setPosition(player.getPosition().x - playerSprite.getWidth() / 2, player.getPosition().y - (1.28f + (36f / 720f) * playerSprite.getHeight()));
        playerSprite.setRegion(changeFrame(delta));
    }

    public void draw(SpriteBatch batch){
        playerSprite.draw(batch);
    }

    public void setTouchingDoor(boolean touchingDoor) {
        isTouchingDoor = touchingDoor;
    }

    public void setSizeAndPosition(float sizeMultiplicator, float yPosition){
        playerSprite.setSize(128 * sizeMultiplicator/ TerrorGame.SCALE, 128 * sizeMultiplicator/ TerrorGame.SCALE);
        player.setTransform(player.getPosition().x, player.getPosition().y + yPosition, 0);
        player.setAwake(true);
    }
    public void setCanChangeRoom(boolean canChangeRoom) {
        this.canChangeRoom = canChangeRoom;
    }

    public boolean getCanChangeRoom() {
        return canChangeRoom;
    }

    public void setminigameID(int minigameID) {
        this.minigameID = minigameID;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean getChangeObjectVisual() {
        return changeObjectVisual;
    }

    public void setChangeObjectVisual(boolean changeObjectVisual) {
        this.changeObjectVisual = changeObjectVisual;
    }

    public boolean getCanInteractWorld() {
        return canInteractWorld;
    }

    public void setCanInteractWorld(boolean canInteractWorld) {
        this.canInteractWorld = canInteractWorld;
    }

    public Body getBody() {
        return player;
    }

    /*public PolygonShape getPolygonShape() {
        return polygonShape;
    }*/
    
}

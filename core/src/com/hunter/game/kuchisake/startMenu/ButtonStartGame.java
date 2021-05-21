package com.hunter.game.kuchisake.startMenu;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.cutscenes.Cutscene1;
import com.hunter.game.kuchisake.screen.CorredorQuarto;
import com.hunter.game.kuchisake.screen.CorredorSalas;
import com.hunter.game.kuchisake.screen.Quarto;
import com.hunter.game.kuchisake.screen.Saguao;
import com.hunter.game.kuchisake.screen.Sala1;
import com.hunter.game.kuchisake.screen.Sala2;

public class ButtonStartGame extends Actor {
    Texture image;
    Sprite botao;

    float posX;
    float posY;
    
    TerrorGame game;
    
    Music menuTheme;

    public ButtonStartGame(String imagem_path, float posX, float posY, TerrorGame terrorGame, Music menuTheme){
        game = terrorGame;
        
        this.menuTheme = menuTheme;
        
        image = game.getAssetManager().get(imagem_path, Texture.class);
        botao = new Sprite(image);

        this.posX = posX;
        this.posY = posY;

        botao.setSize(botao.getWidth() / 42f, botao.getHeight() / 42f);
        botao.setPosition(posX - botao.getWidth() / 2, posY);
        setBounds(botao.getX(), botao.getY(), botao.getWidth(), botao.getHeight());

        setTouchable(Touchable.enabled);

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean checaLargura = (event.getStageX() >= botao.getX() && event.getStageX() <= botao.getX() + botao.getWidth());
                boolean checaAltura = (event.getStageY() >= botao.getY() && event.getStageY() <= botao.getY() + botao.getHeight());

                //boolean buttonPressed = Gdx.input.isTouched();

                if (checaLargura && checaAltura){
                    loadScene();
                    System.out.println("INicia porra");
                }

                System.out.println(event.getStageX() + " , " + event.getStageY());
                return true;
            }
        });

    }


    public void loadScene(){
    	game.getAssetManager().setLoader(TiledMap.class, new TmxMapLoader());
    	
    	game.getAssetManager().load("CharactersAssets/sprites_protag_right.png", Texture.class);
    	game.getAssetManager().load("CharactersAssets/sprite_stoped_right.png", Texture.class);
    	
    	game.getAssetManager().load("Tilesets/saguao_segundo.tmx", TiledMap.class);
    	game.getAssetManager().load("ScenaryAssets/saguao/SaguaoObjects.atlas", TextureAtlas.class);
    	
    	game.getAssetManager().load("MinigameAssets/MinigameObjects.atlas", TextureAtlas.class);
    	game.getAssetManager().load("MinigameAssets/MinigameDescription.atlas", TextureAtlas.class);
    	game.getAssetManager().load("Audio/Sfx/wrongBuzzer.wav", Sound.class);
        game.getAssetManager().load("Audio/Sfx/gerador ligando.ogg", Sound.class);
        game.getAssetManager().load("Audio/Sfx/minigame complete 6.ogg", Sound.class);
        game.getAssetManager().load("Audio/Sfx/item pickup 7.ogg", Sound.class);

        game.getAssetManager().load("Coletaveis/Coletaveis.atlas", TextureAtlas.class);
        game.getAssetManager().load("Coletaveis/DescriptionAtlas.atlas", TextureAtlas.class);
        
        game.getAssetManager().load("Cutscenes/Cutscene1Objects.atlas", TextureAtlas.class);
        game.getAssetManager().load("Cutscenes/Cutscene2Objects.atlas", TextureAtlas.class);
        game.getAssetManager().load("Cutscenes/fundo_cutscene.png", Texture.class);
        
        game.getAssetManager().load("ButtonAssets/game_over_text.png", Texture.class);

    	game.getAssetManager().load("Audio/Sfx/porta abrindo 3.ogg", Sound.class);
    	game.getAssetManager().load("Audio/Sfx/porta fechando 3.ogg", Sound.class);
    	game.getAssetManager().load("Audio/Sfx/Achei voce.ogg", Sound.class);
    	game.getAssetManager().load("Audio/Sfx/Te achei.ogg", Sound.class);
    	game.getAssetManager().load("Audio/Sfx/acabou.ogg", Sound.class);
    	game.getAssetManager().load("Audio/Sfx/watashi wa kirei.ogg", Sound.class);
    	game.getAssetManager().load("Audio/Sfx/madeira rangendo 2.ogg", Sound.class);
    	game.getAssetManager().load("Audio/Sfx/madeira rangendo 6.ogg", Sound.class);
    	game.getAssetManager().load("Audio/Sfx/madeira rangendo 7.ogg", Sound.class);
    	game.getAssetManager().load("Audio/Sfx/madeira estralando 2.ogg", Sound.class);
    	game.getAssetManager().load("Audio/Sfx/madeira quebrando.ogg", Sound.class);
    	game.getAssetManager().load("Audio/Sfx/passo.ogg", Sound.class);
    	
    	game.getAssetManager().load("Audio/Music/mansion.ogg", Music.class);
    	game.getAssetManager().load("Audio/Music/Run.ogg", Music.class);
    	game.getAssetManager().load("Audio/Music/Run.ogg", Music.class);
    	game.getAssetManager().load("Audio/Music/Scary Sorrow.wav", Music.class);

    	//game.getAssetManager().load("Tilesets/quarto.tmx", TiledMap.class);
    	//game.getAssetManager().load("ScenaryAssets/quarto/QuartoObjects.atlas", TextureAtlas.class);
    	
    	//game.getAssetManager().load("Tilesets/sala1.tmx", TiledMap.class);
    	//game.getAssetManager().load("ScenaryAssets/sala_1/Sala1Objects.atlas", TextureAtlas.class);
    	
    	//game.getAssetManager().load("Tilesets/sala2.tmx", TiledMap.class);
    	//game.getAssetManager().load("ScenaryAssets/sala_2/Sala2Objects.atlas", TextureAtlas.class);
    	
    	//game.getAssetManager().load("Tilesets/corredor.tmx", TiledMap.class);
    	//game.getAssetManager().load("ScenaryAssets/corredor/CorredorObjects.atlas", TextureAtlas.class);
    	
//    	game.getAssetManager().load("PortasEEscadas/porta1.png", Texture.class);
//    	game.getAssetManager().load("PortasEEscadas/porta2.png", Texture.class);
//    	game.getAssetManager().load("arrow.png", Texture.class);
//    	game.getAssetManager().load("arrow.png", Texture.class);

        game.getAssetManager().finishLoading();
        
        menuTheme.stop();
    	
    	game.getAssetManager().unload("ButtonAssets/fundo_menu.png");
    	game.getAssetManager().unload("ButtonAssets/botao_jogar.png");
    	game.getAssetManager().unload("ButtonAssets/botao_carregar.png");
    	game.getAssetManager().unload("ButtonAssets/botao_controles.png");
    	game.getAssetManager().unload("Audio/Music/Night Wind.wav");
        
        game.setScreen(new Cutscene1(game));
    }

    public Sprite getBotaoStart() {
        return botao;
    }

    public void draw(Batch batch, float parentAlpha) {
        botao.draw(batch);
    }
}

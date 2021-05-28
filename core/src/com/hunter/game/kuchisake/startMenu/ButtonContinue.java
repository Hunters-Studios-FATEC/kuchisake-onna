package com.hunter.game.kuchisake.startMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
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
import com.badlogic.gdx.utils.Array;
import com.hunter.game.kuchisake.TerrorGame;
import com.hunter.game.kuchisake.TesteSQLite;
import com.hunter.game.kuchisake.screen.Saguao;
import com.hunter.game.kuchisake.screen.SalaSegura;

public class ButtonContinue extends Actor {
    Texture image;
    Sprite botao;

    float posX;
    float posY;
    
    TerrorGame game;
    
    Music menuTheme;
    
    TesteSQLite testeSqlite;

        public ButtonContinue(String imagem_path, float posX, float posY, TerrorGame terrorGame, Music menuTheme){
            game = terrorGame;
            
            testeSqlite = new TesteSQLite();
            		
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

                    if (checaLargura && checaAltura){
                        loadGame();
                    }
                    return true;
                }
            });

        }


        public void loadGame(){
        	
        	testeSqlite.getData();
        	
        	int kline = testeSqlite.getKuchisakeLine();
        	int kcol = testeSqlite.getKuchisakeColumn();
        	
        	boolean locComp = testeSqlite.getLockCompleted();
        	boolean livComp = testeSqlite.getLivroCompleted();
        	boolean fiComp = testeSqlite.getFiosCompleted();
        	boolean gerComp = testeSqlite.getGeradorCompleted();
        	
        	int saveN = testeSqlite.getSavesN();
        	int hideN = testeSqlite.getHideN();
        	
        	Array<String> moch = testeSqlite.getBackpack();
        	
        	game.getAssetManager().setLoader(TiledMap.class, new TmxMapLoader());
        	
        	game.getAssetManager().load("CharactersAssets/sprites_protag_right.png", Texture.class);
        	game.getAssetManager().load("CharactersAssets/sprite_stoped_right.png", Texture.class);
        	
        	game.getAssetManager().load("Tilesets/sala_descanso.tmx", TiledMap.class);
        	game.getAssetManager().load("ScenaryAssets/salaDescanso/SalaDescansoObjects.atlas", TextureAtlas.class);
        	
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

            game.getAssetManager().finishLoading();
            
            menuTheme.stop();
        	
        	game.getAssetManager().unload("ButtonAssets/fundo_menu.png");
        	game.getAssetManager().unload("ButtonAssets/botao_jogar.png");
        	game.getAssetManager().unload("ButtonAssets/botao_carregar.png");
        	game.getAssetManager().unload("ButtonAssets/botao_controles.png");
        	game.getAssetManager().unload("Audio/Music/Night Wind.wav");

            game.createMinigameManager();
            game.createInventoryManager();
            
            game.getMinigameManager().setLockCompleted(locComp);
            game.getMinigameManager().setBookCompleted(livComp);
            game.getMinigameManager().setWireCompleted(fiComp);
            game.getMinigameManager().setGeradorCompleted(gerComp);
            
            for (String name : moch) {
				game.getInventoryManager().addItem(name);
			}

            game.setPlayerColumn(-1);
            game.incrementPlayerLine(2);
            
            
            
            game.setSaveCount(saveN - 1);
            game.setHideCount(hideN);
            
            game.createVillain(kline, kcol);

            game.addMusic();
            game.setScreen(new SalaSegura(game, 1750));
        }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        botao.draw(batch);
    }

    public Sprite getBotaoContinue() {
        return botao;
    }

}

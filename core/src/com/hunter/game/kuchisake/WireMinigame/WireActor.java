package com.hunter.game.kuchisake.WireMinigame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.hunter.game.kuchisake.TerrorGame;

public class WireActor extends Actor{
	
	TextureRegion texture;
	int actor_number;
	Sprite sprite;
	boolean correct_position = false;
	boolean accept_input = true;
	
	public WireActor(float x, float y, String texture_path, final int actor_num, TextureAtlas textureAtlas) {
		texture = textureAtlas.findRegion(texture_path);
		actor_number = actor_num;
		sprite = new Sprite(texture);
		sprite.setBounds(x, y, sprite.getWidth() / TerrorGame.SCALE, sprite.getHeight() / TerrorGame.SCALE);

		setBounds(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2, sprite.getWidth(), sprite.getHeight());
		
		setTouchable(Touchable.enabled);
		
		setOrigin(getWidth() / 2, getHeight() / 2);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);

	addListener(new InputListener() {
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			return true;
		}

		public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
			if (accept_input) {
				//System.out.println("up");
				float actor_X = getStage().getActors().get(actor_number).getX();
				float actor_Width = getStage().getActors().get(actor_number).getWidth();

				float actor_Y = getStage().getActors().get(actor_number).getY();
				float actor_Height = getStage().getActors().get(actor_number).getHeight();


				if ((event.getStageX() > actor_X && event.getStageX() < actor_X + actor_Width)
						&& (event.getStageY() > actor_Y && event.getStageY() < actor_Y + actor_Height)) {
					setSize(getLineDistance(getX(), getY(),
							(actor_X + actor_Width / 2),
							(actor_Y + actor_Height / 2)),
							getHeight()
					);

					// Acertar a rotação quando chegar as sprites.
					setRotation(getAngle(getX() + getOriginX(), getY() + getOriginY(), actor_X + (actor_Width / 2), actor_Y + (actor_Height / 2)));

					correct_position = true;
					accept_input = false;
				}
			}
		}

			public void touchDragged(InputEvent event, float x, float y, int pointer) {

				if (accept_input) {

					if (event.getStageX() > getX() + getOriginX()) {
						setSize(getLineDistance(getX(), getY(), event.getStageX() + getOriginX(), event.getStageY() + getOriginY()), getHeight());
						setRotation(getAngle(getX() + getOriginX(), getY() + getOriginY(), event.getStageX(), event.getStageY()));
					}
				}
			}
		});
	}
	
	float getAngle(float x1, float y1, float x2, float y2) {	
		float width = x2 - x1;
		float height = y2 - y1;
		
		float angle = (float) Math.toDegrees(Math.atan(height / width));
		
		return angle;
	}
	
	float getLineDistance(float x1, float y1, float x2, float y2) {
		float xLine = x2 - x1;
		float yLine = y2 - y1;
		
		float lineDistance = (float) Math.sqrt(Math.pow(xLine, 2) + Math.pow(yLine, 2));
		
		return lineDistance;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.setPosition(getX(), getY());
		sprite.setSize(getWidth(), getHeight());
		sprite.setRotation(getRotation());
		sprite.draw(batch);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}

	public boolean getAccept_input() {
		return accept_input;
	}
}

package com.hunter.game.kuchisake.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hunter.game.kuchisake.TerrorGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.addIcon("Icon/Icon_128.png", FileType.Internal);
		config.addIcon("Icon/Icon_32.png", FileType.Internal);
		config.addIcon("Icon/Icon_16.png", FileType.Internal);
		config.title = "WHAT\'S BEHIND";
		new LwjglApplication(new TerrorGame(), config);
	}
}
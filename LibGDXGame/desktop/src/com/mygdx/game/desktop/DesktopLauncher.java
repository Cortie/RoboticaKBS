package com.mygdx.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg)
	{
		//sets the configuration values of the game
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.addIcon("icon.png", Files.FileType.Internal);
		config.title = "Gamotica";
		config.width = 720;
		config.height = 1080;
		//creates the screen with the config set before
		new LwjglApplication(new MyGdxGame(), config);
	}
}
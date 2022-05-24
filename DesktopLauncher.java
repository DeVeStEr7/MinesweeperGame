package com.minesweeper.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.minesweeper.Main;
import com.minesweeper.MyGdxGame;

public class DesktopLauncher extends Main {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = WORLD_WIDTH;
		config.height = WORLD_HEIGHT;
		new LwjglApplication(new MyGdxGame(), config);
	}
}

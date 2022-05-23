package com.minesweeper;

import com.badlogic.gdx.Game;
import com.minesweeper.Main;

public class MyGdxGame extends Game {

	public void create() {
		setScreen(new Main());
	}
}

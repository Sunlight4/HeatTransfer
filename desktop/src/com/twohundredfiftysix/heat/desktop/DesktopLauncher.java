package com.twohundredfiftysix.heat.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.twohundredfiftysix.cellular.CellularAutomata;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 480;
		config.height = 640;
		config.resizable = false;
		new LwjglApplication(new CellularAutomata(), config);
	}
}

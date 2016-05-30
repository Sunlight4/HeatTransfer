package com.twohundredfiftysix.cellular;

import com.badlogic.gdx.graphics.Color;

public abstract class Cellular {
public abstract Color color(float[] cell);
public float startvalue(int i, int j, int k) {
	return 0;
}
public abstract void update(float[][][] data);
public void signal(int signal, int data) {
	
}
}

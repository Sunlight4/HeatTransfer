package com.twohundredfiftysix.cellular;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CellularAutomata extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Texture cell;
	Texture img;
	float[][][] data;
	int param_width = 120;
	int param_height = 120;
	int param_numsheets = 3;
	double param_speed = 1;
	int boxx=param_width/2;
	int boxy=param_height-6;
	double prespeed = 0;
	boolean paused=false;
	boolean ground=false;
	double time=0;
	float cell_height, cell_width;
	Cellular automaton;
	private BitmapFont font;
	boolean grid=false;
	@Override
	public void create () {
		batch = new SpriteBatch();
		cell=new Texture(Gdx.files.internal("cell.png"));
		Gdx.input.setInputProcessor(this);
		font = new BitmapFont();
        font.setColor(Color.WHITE);
		cell_width=480.0f/param_width;
		cell_height=480.0f/param_height;
		init(boxx, boxy);
	}
	public void init(int signal, int datum) {
		automaton = new HeatTransfer();
		automaton.signal(signal, datum);
		cell_height=480.0f/param_height;
		cell_width=480.0f/param_width;
		data = new float[param_width][param_height][param_numsheets];
		for (int i=0; i<param_width; i++) {
			for (int j=0; j<param_height; j++) {
				for (int k=0; k<param_numsheets; k++) {
					data[i][j][k]=automaton.startvalue(i, j, k);
				}
			}
		}
	}
	@Override
    public boolean keyDown(int keycode) {
		System.out.println(keycode);
        if(keycode == Keys.UP) {
            param_speed*=10;
        }
        if(keycode == Keys.DOWN) {
            param_speed*=0.1;
        }
        if (keycode==Keys.W) {
        	boxy+=1;
        	init(boxx, boxy);
        	
        }
        if (keycode==Keys.A) {
        	boxx-=1;
        	init(boxx, boxy);
        	
        }
        if (keycode==Keys.S) {
        	boxy-=1;
        	init(boxx, boxy);
        	
        }
        if (keycode==Keys.D) {
        	boxx+=1;
        	init(boxx, boxy);
        	
        }
        if (keycode == Keys.Z) {
        	init(boxx, boxy);
        }
        if (keycode == Keys.X) {
        	if (!paused) {
        		prespeed=param_speed;
        		param_speed=0;
        	}
        	else {
        		param_speed=prespeed;
        	}
        	paused=!paused;
        }
        if (keycode == Keys.C) {
        	grid=!grid;
        	if (grid) {
        		cell=new Texture(Gdx.files.internal("cell2.png"));
        	}
        	else {
        		cell=new Texture(Gdx.files.internal("cell.png"));
        	}
        }
        if (keycode == Keys.V) {
        	ground=!ground;
        }
        return true;
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		time+=param_speed;
		while (time>1) {
			time--;
			automaton.update(data);
		}
		batch.begin();
		try {
		int x = (int) (Gdx.input.getX()/cell_width);
		int y = (int) ((640-Gdx.input.getY())/cell_height);
		float temp = (float) (int) data[x][y][0];
		font.draw(batch, "Scanning ("+Integer.toString(x)+","+Integer.toString(y)+"), got "+Float.toString(temp), 4, 640-12);
		font.draw(batch, "Speed: "+Double.toString(param_speed), 4, 640-30);
		}
		catch (ArrayIndexOutOfBoundsException error) {
			
		}
		if (ground) {
			for (int i=0; i<param_width; i++) {
				int j=param_height-1;
				batch.setColor(automaton.color(data[i][j]));
				batch.draw(cell, i*cell_width, 0, cell_width, 72);
			}
		}
		else {
		for (int i=0; i<param_width; i++) {
			for (int j=0; j<param_height; j++) {
				batch.setColor(automaton.color(data[i][j]));
				batch.draw(cell, i*cell_width, j*cell_height, cell_width, cell_height);
			}
		}
		}
		batch.end();

	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}

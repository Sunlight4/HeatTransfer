package com.twohundredfiftysix.cellular;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;

public class HeatTransfer extends Cellular {
	double param_threshold=0;
	double param_gradation = 60;
	int param_roomtemp = 341;
	double param_quanta = 0.1;
	double param_airtemp = 391;
	double param_airh = 1;
	double param_airrate = -0.00001;
	//double param_airrate=0;
	int param_x=0;
	int param_y=0;
	double param_timestep = 1;
	double param_minair = 301;
	//double param_timestep = 0.1;
	double param_cellsize = 0.01;
	double roomytempthing = (param_roomtemp - param_gradation);
	Random rand = new Random();
	@Override
	public void signal(int x, int y) {
		param_x=x;
		param_y=y;
	}
	@Override
	public void update(float[][][] data) {
		if (rand.nextFloat()<0.001) {
		System.out.println(data[param_x][data[0].length-1][0]-data[0][data[0].length-1][0]);
		};
		if (param_airtemp>param_minair) param_airtemp+=param_airrate;
		int i1=rand.nextInt(data.length);
		int j1=rand.nextInt(data[0].length);
		float t1=data[i1][j1][0];
		float h1=data[i1][j1][1];
		float c1=data[i1][j1][2];
		while (true) {
			int di = rand.nextInt(3)-1;
			int dj = rand.nextInt(3)-1;
			int i2 = i1 + di;
			int j2 = j1 + dj;
			try {
				float t2=data[i2][j2][0];
				float h2=data[i2][j2][1];
				float c2=data[i2][j2][2];
				float q = transfer(t1, h1, c1, t2, h2, c2);
				data[i1][j1][0]+=q/c1;
				data[i2][j2][0]-=q/c2;
				break;
			}
			catch (ArrayIndexOutOfBoundsException error) {
				if (j2>=data[0].length) {
				float t2=(float) param_airtemp;
				float h2=(float) param_airh;
				float q = transfer(t1, h1, c1, t2, h2, 1);
				data[i1][j1][0]+=q/c1;
				}
			}
		}
		
	}
	public float transfer(float t1, float k1, float c1, float t2, float k2, float c2) {
		float k = (k1+k2)/2;
		float dt=t2-t1;
		float flowrate =(float) (k*param_cellsize*dt);
		float dh = dt*c1*c2/(c1+c2);
		if (Math.abs(dh)<Math.abs(param_timestep*flowrate)) {
			return dh;
		}
		//return q;
		return (float) (param_timestep*flowrate);
	}
	@Override
	public Color color(float[] cell) {
		float temp = cell[0];
		int diff = (int) Math.abs(temp-param_roomtemp);
		float yes = (float) (diff/(param_gradation));
		float g = 1-yes;
		if (cell[2]==0.45f) {
			g=1f;
		}
		if (temp>param_roomtemp) {
			return new Color(1, g, 1-yes, 1);
		}
		else if (temp==param_roomtemp) {
			return new Color(1-yes, g, 1-yes, 1);
		}
		else {
			return new Color(1-yes, g, 1, 1);
		}
	}
	public float startvalue(int i, int j, int k) {
		if (k==0) {
			//return (float) (roomytempthing+rand.nextInt((int) (param_gradation*4))-param_gradation);
			return param_roomtemp;
		}
		else if (k==1) {
			return (i>=param_x-5&&i<=param_x+5&&j>=param_y-5&&j<=param_y+5)?80:0.7f;
			//return 1;
		}
		else {
			return (i>=param_x-5&&i<=param_x+5&&j>=param_y-5&&j<=param_y+5)?0.45f:1.98f;
			//return 1;
		}
	}

}

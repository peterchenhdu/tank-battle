package com.cp.tankGameMap;

import java.util.Vector;

import com.cp.tankGameObject.Brick;
import com.cp.tankGameObject.Iron;
import com.cp.tankGameObject.Water;

public class Map3 extends Map {
	public Map3() {
		Vector<Brick> bricks=this.getBricks();
		Vector<Iron> irons=this.getIrons();
		Vector<Water> waters=this.getWaters();
		for(int i=0;i<25;i++){
			Brick Brick=new Brick(20*i+60, 60);
			bricks.add(Brick);
		}
		for(int i=0;i<25;i++){
			Brick Brick=new Brick(20*i+60, 140);
			bricks.add(Brick);
		}
		for(int i=0;i<25;i++){
			Brick Brick=new Brick(20*i+60, 220);
			bricks.add(Brick);
		}
		for(int i=0;i<25;i++){
			if(i==11||i==12||i==13) continue;
			Brick Brick=new Brick(20*i+60, 460);
			bricks.add(Brick);
		}
		for(int i=0;i<25;i++){
			if(i==11||i==12||i==13) continue;
			Brick Brick=new Brick(20*i+60, 540);
			bricks.add(Brick);
		}
		for(int i=0;i<25;i++){
			Brick Brick=new Brick(60, 20*i+60);
			bricks.add(Brick);
		}
		for(int i=0;i<25;i++){
			Brick Brick=new Brick(540, 20*i+60);
			bricks.add(Brick);
		}
		Brick Brick=new Brick(290, 290);
		bricks.add(Brick);
		Brick=new Brick(310, 290);
		bricks.add(Brick);
		Brick=new Brick(290, 310);
		bricks.add(Brick);
		Brick=new Brick(310, 310);
		bricks.add(Brick);
	}
}

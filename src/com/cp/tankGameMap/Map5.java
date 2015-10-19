package com.cp.tankGameMap;

import java.util.Vector;

import com.cp.tankGameObject.Brick;
import com.cp.tankGameObject.Iron;
import com.cp.tankGameObject.Water;

public class Map5 extends Map{
	public Map5() {
		// TODO Auto-generated constructor stub
		Vector<Brick> bricks=this.getBricks();
		Vector<Iron> irons=this.getIrons();
		Vector<Water> waters=this.getWaters();
	}
}

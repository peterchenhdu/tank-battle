package cn.edu.hdu.tankbattle.model.map;

import java.util.Vector;

import cn.edu.hdu.tankbattle.model.Brick;
import cn.edu.hdu.tankbattle.model.Iron;
import cn.edu.hdu.tankbattle.model.Water;

/**
 * 游戏地图
 * 
 * @author 1052067939
 *
 */
public class Map {
	/**
	 * 地图上的砖块
	 */
	private Vector<Brick> bricks;
	/**
	 * 地图上的铁块
	 */
	private Vector<Iron> irons;
	/**
	 * 地图上的水
	 */
	private Vector<Water> waters;

	/**
	 * 构造方法
	 */
	public Map() {
		// TODO Auto-generated constructor stub
		bricks = new Vector<Brick>();
		irons = new Vector<Iron>();
		waters = new Vector<Water>();
	}

	public Vector<Brick> getBricks() {
		return bricks;
	}

	public void setBricks(Vector<Brick> bricks) {
		this.bricks = bricks;
	}

	public Vector<Iron> getIrons() {
		return irons;
	}

	public void setIrons(Vector<Iron> irons) {
		this.irons = irons;
	}

	public Vector<Water> getWaters() {
		return waters;
	}

	public void setWaters(Vector<Water> waters) {
		this.waters = waters;
	}

}

package cn.edu.hdu.tankbattle.model;

import java.awt.Color;
import java.util.Vector;

import cn.edu.hdu.tankbattle.view.GamePanel;

/**
 * 坦克类，继承自东西类
 * 
 * @author 1052067939
 * @version 1.0
 * @since JavaSe-1.6
 */
public class Tank extends Stuff {
	/**
	 * 我的坦克
	 */
	public static final int MY = 0;
	/**
	 * 敌人坦克
	 */
	public static final int ENEMY = 1;
	/**
	 * 坦克的颜色
	 */
	private Color color = Color.green;
	/**
	 * 坦克的移动速度
	 */
	private int speed = 4; // 坦克移动速度
	/**
	 * 挡住坦克前面的东西
	 */
	private int frontInfomation = -1;
	/**
	 * 坦克的子弹容量
	 */
	private Vector<Bullet> bullets;
	/**
	 * 坦克是否重叠属性,前面的障碍物不可过去
	 */
	private boolean isOverlapNo = false;
	/**
	 * 坦克是否重叠，前面的障碍物可以过去，用子弹可以打掉
	 */
	private boolean isOverlapYes = false;
	/**
	 * 游戏暂停时存储速度
	 */
	private int speedVector;

	/**
	 * 坦克的构造方法
	 * 
	 * @param x
	 *            坦克的x坐标
	 * @param y
	 *            坦克的y坐标
	 * @param direct
	 *            坦克的方向
	 */
	public Tank(int x, int y, int direct) {
		super(x, y);
		this.setDirect(direct);
		this.bullets = new Vector<Bullet>();
		this.setType(Stuff.TANK);
	}

	/**
	 * 射击，发射一颗子弹
	 * 
	 * @param tank
	 *            坦克对象，注意，是自己，不是敌人，呵呵
	 */
	public void shot(Tank tank) {
		Bullet bullet = null;
		switch (tank.getDirect()) { // 选择坦克的方向
		case NORTH:
			bullet = new Bullet(tank.getX(), tank.getY() - 20, NORTH);
			break;
		case SOUTH:
			bullet = new Bullet(tank.getX(), tank.getY() + 20, SOUTH);
			break;
		case WEST:
			bullet = new Bullet(tank.getX() - 20, tank.getY(), WEST);
			break;
		case EAST:
			bullet = new Bullet(tank.getX() + 20, tank.getY(), EAST);
			break;
		}
		tank.getBullets().add(bullet);
		Thread t = new Thread(bullet);
		t.start();
	}

	/**
	 * 坦克往北走
	 */
	public void goNorth() {
		this.setDirect(NORTH);
		if (this.getY() > 20) {
			this.setY(this.getY() - this.speed);
		} else {
			this.setFrontInfomation(Stuff.IRON);
		}
	}

	/**
	 * 坦克往南走
	 */
	public void goSouth() {
		this.setDirect(SOUTH);
		if (this.getY() < GamePanel.HEIGHT - 20) {
			this.setY(this.getY() + this.speed);
		} else {
			this.setFrontInfomation(Stuff.IRON); // 碰到边界就相当于碰到铁块
		}
	}

	/**
	 * 坦克往西走
	 */
	public void goWest() {
		this.setDirect(WEST);
		if (this.getX() > 20 && this.getY() <= GamePanel.HEIGHT - 20) {
			this.setX(this.getX() - this.speed);
		} else {
			this.setFrontInfomation(Stuff.IRON);
		}
	}

	/**
	 * 坦克往东走
	 */
	public void goEast() {
		this.setDirect(EAST);
		if (this.getX() < GamePanel.WIDTH - 20
				&& this.getY() <= GamePanel.HEIGHT - 20) {
			this.setX(this.getX() + this.speed);
		} else {
			this.setFrontInfomation(Stuff.IRON);
		}
	}

	/**
	 * 坦克往指定的方向走
	 * 
	 * @param where
	 *            方向
	 */

	public void go(int where) {
		switch (where) {
		case NORTH:
			this.goNorth();
		case SOUTH:
			this.goSouth();
		case WEST:
			this.goWest();
		case EAST:
			this.goEast();
		}
	}

	/**
	 * 坦克往后走
	 */
	public void goBack() {
		switch (this.getDirect()) {
		case NORTH:
			this.goSouth();
		case SOUTH:
			this.goNorth();
		case WEST:
			this.goEast();
		case EAST:
			this.goWest();
		}
	}

	/**
	 * 判断坦克是否与另一个事物重叠
	 * 
	 * @param stuff
	 *            东西对象
	 * @param length
	 *            两者之间的最短距离
	 * @return 是否重叠
	 */
	public boolean Overlap(Stuff stuff, int length) {
		boolean b = false;
		int x = stuff.getX();
		int y = stuff.getY();
		if (this.getDirect() == Tank.NORTH) {
			this.setY(this.getY() - this.getSpeed()); // 先假设该坦克往前移动一步
			if (Math.abs(this.getY() - y) < length
					&& Math.abs(this.getX() - x) < length) { // 如果在远离，此时他想逃出重叠，所以就设b为false，让它能够动
				b = true;
				this.setY(this.getY() + this.getSpeed());
			} else {
				this.setY(this.getY() + this.getSpeed());
			}
		}
		if (this.getDirect() == Tank.SOUTH) {
			this.setY(this.getY() + this.getSpeed()); // 先假设该坦克往前移动一步
			if (Math.abs(this.getY() - y) < length
					&& Math.abs(this.getX() - x) < length) {
				b = true;
			}
			this.setY(this.getY() - this.getSpeed());
		}
		if (this.getDirect() == Tank.EAST) {
			this.setX(this.getX() + this.getSpeed());
			if (Math.abs(this.getY() - y) < length
					&& Math.abs(this.getX() - x) < length) {
				b = true;
			}
			this.setX(this.getX() - this.getSpeed());
		}
		if (this.getDirect() == Tank.WEST) {
			this.setX(this.getX() - this.getSpeed());
			if (Math.abs(this.getY() - y) < length
					&& Math.abs(this.getX() - x) < length) {
				b = true;
			}
			this.setX(this.getX() + this.getSpeed());
		}
		return b;
	}

	/**
	 * 线程睡眠指定时间
	 * 
	 * @param time
	 *            睡眠时间，单位：毫秒
	 */
	public void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Vector<Bullet> getBullets() {
		return bullets;
	}

	public void setBullets(Vector<Bullet> bullets) {
		this.bullets = bullets;
	}

	public void setSpeedVector(int speedVector) {
		this.speedVector = speedVector;
	}

	public int getSpeedVector() {
		return speedVector;
	}

	public boolean isOverlapNo() {
		return isOverlapNo;
	}

	public void setOverlapNo(boolean isOverlapNo) {
		this.isOverlapNo = isOverlapNo;
	}

	public boolean isOverlapYes() {
		return isOverlapYes;
	}

	public void setOverlapYes(boolean isOverlapYes) {
		this.isOverlapYes = isOverlapYes;
	}

	public int getFrontInfomation() {
		return frontInfomation;
	}

	public void setFrontInfomation(int frontInfomation) {
		this.frontInfomation = frontInfomation;
	}
}

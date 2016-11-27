package cn.edu.hdu.tankbattle.model;

import cn.edu.hdu.tankbattle.view.GamePanel;

/**
 * 子弹类，实现Runnable接口
 * 
 * @author 1052067939
 * @version 1.0
 * @since JavaSe-1.6
 *
 */
public class Bullet implements Runnable {
	/**
	 * 方向北
	 */
	public static final int NORTH = 0;
	/**
	 * 方向南
	 */
	public static final int SOUTH = 1;
	/**
	 * 方向西
	 */
	public static final int WEST = 2;
	/**
	 * 方向东
	 */
	public static final int EAST = 3;
	/**
	 * 子弹的速度
	 */
	private int speed = 4;
	/**
	 * 子弹的x坐标
	 */
	private int x;
	/**
	 * 子弹的y坐标
	 */
	private int y;
	/**
	 * 子弹的方向
	 */
	private int direct;
	/**
	 * 子弹是否存活
	 */
	private boolean isLive = true;
	/**
	 * 游戏暂停时存储子弹速度
	 */
	private int speedVector;

	/**
	 * 子弹类构造方法
	 * 
	 * @param x
	 *            子弹x坐标
	 * @param y
	 *            子弹y坐标
	 * @param direct
	 *            子弹的方向
	 */
	public Bullet(int x, int y, int direct) {
		this.x = x;
		this.y = y;
		this.direct = direct;
		Thread threadBullet = new Thread(this); // 创建子弹线程
		threadBullet.start();
	}

	@Override
	public void run() {
		this.bulletRun(); // 子弹开始移动
	}

	/**
	 * 子弹向前移动
	 */
	public void bulletRun() {
		while (true) {
			switch (direct) { // 选择子弹的方向
			case NORTH:
				this.setY(this.getY() - this.getSpeed());
				break;
			case SOUTH:
				this.setY(this.getY() + this.getSpeed());
				break;
			case WEST:
				this.setX(this.getX() - this.getSpeed());
				break;
			case EAST:
				this.setX(this.getX() + this.getSpeed());
				break;
			}
			if (x < 5 || x > GamePanel.WIDTH - 5 || y < 5
					|| y > GamePanel.HEIGHT - 5) { // 判断子弹是否碰到边界
				this.isLive = false; // 子弹死亡
				break;
			}
			try {
				Thread.sleep(36); // 每隔25毫秒移动一次
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public void setSpeedVector(int speedVector) {
		this.speedVector = speedVector;
	}

	public int getSpeedVector() {
		return speedVector;
	}
}
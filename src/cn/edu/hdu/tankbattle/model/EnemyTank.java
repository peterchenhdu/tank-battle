package cn.edu.hdu.tankbattle.model;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import cn.edu.hdu.tankbattle.model.map.Map;
import cn.edu.hdu.tankbattle.view.GamePanel;

/**
 * 敌人坦克 继承自Tank类，并且实现Runnable接口 敌人坦克的线程是每隔36毫秒行走的，各种判断（30毫秒）会在这36毫秒以内判断完毕
 * 
 * @author 1052067939
 * @version 1.0
 * @since JavaSe-1.6
 *
 */
public class EnemyTank extends Tank implements Runnable {
	/**
	 * 敌人坦克刚出现时的位置，5个固定地方
	 */
	private int location;
	/**
	 * 我的坦克在敌人坦克的相对位置，正北方‘正南方’正西方‘正东方，-1为不知道
	 */
	private int myTankLocation = -1;
	/**
	 * 我的坦克方向
	 */
	private int myTankDirect = Tank.NORTH;
	/**
	 * 定时器
	 */
	private Timer timer;
	/**
	 * 是否要开火
	 */
	private boolean isShot = false;
	/**
	 * 是否在地图中
	 */
	private boolean isInMap = false;

	/**
	 * 敌人坦克构造方法
	 * 
	 * @param x
	 * @param y
	 * @param direct
	 */
	public EnemyTank(int x, int y, int direct) {
		super(x, y, direct);
		this.setSpeed(4);
		this.setType2(Tank.ENEMY);
		this.setDirect(Tank.NORTH);
		this.setColor(Color.red);
		this.setBlood(10);
		this.setSpeedVector(0); // 设为0表示没有保存坦克的速度，按下暂停时速度就不会是0
		Thread threadTank = new Thread(this); // 创建敌人坦克线程
		threadTank.start(); // 启动线程
		// 定时器 每隔0.5秒打一发子弹
		MyTimerTask task = new MyTimerTask(this);
		timer = new Timer();
		timer.schedule(task, 0, 500);
	}

	@Override
	public void run() {
		this.enemyTankRun();
	}

	/**
	 * 敌人坦克移动
	 */
	public void enemyTankRun() {
		while (true) {
			switch (this.getDirect()) { // 选择坦克方向
			case EnemyTank.NORTH:
				for (;;) {
					// 睡眠36毫秒，36毫秒可以保证坦克的信息已经判断过一次了
					this.sleep(36);
					// 如果我的坦克在敌人坦克的正西方
					if (this.getMyTankLocation() == EnemyTank.WEST) {
						this.setDirect(EnemyTank.WEST);
						this.enemyGoWest();
					}
					// 如果我的坦克在敌人坦克的正东方
					if (this.getMyTankLocation() == EnemyTank.EAST) {
						this.setDirect(EnemyTank.EAST);
						this.enemyGoEast();
					}
					// 如果我的坦克在敌人坦克的正南方
					if (this.getMyTankLocation() == EnemyTank.SOUTH) {
						this.setDirect(EnemyTank.SOUTH);
						this.enemyGoSouth();
					}
					// 如果我的坦克在敌人坦克的正北方
					if (this.getMyTankLocation() == EnemyTank.NORTH) {
						this.enemyGoNorth();
					}
					// 如果出界或者重叠的话 选择其他方向 跳出
					if (this.getY() <= 20 || this.isOverlapNo() == true) {
						this.setDirect(this.getRandomDirect(Tank.SOUTH,
								Tank.WEST, Tank.EAST));
						break;
					}
					// 如果现在坦克的方向不是北方，跳出
					if (this.getDirect() != Tank.NORTH)
						break;
					// 如果不重叠，前进
					if (this.isOverlapYes() == false)
						this.goNorth();
				}
				break;
			case EnemyTank.SOUTH:
				for (;;) {
					this.sleep(36);
					if (this.getMyTankLocation() == EnemyTank.WEST) {
						this.setDirect(EnemyTank.WEST);
						this.enemyGoWest();
					}
					if (this.getMyTankLocation() == EnemyTank.EAST) {
						this.setDirect(EnemyTank.EAST);
						this.enemyGoEast();
					}
					if (this.getMyTankLocation() == EnemyTank.NORTH) {
						this.setDirect(EnemyTank.NORTH);
						this.enemyGoNorth();
					}
					if (this.getMyTankLocation() == EnemyTank.SOUTH) {
						this.enemyGoSouth();
					}
					if (this.getY() >= GamePanel.HEIGHT - 20
							|| this.isOverlapNo() == true) {
						this.setDirect(this.getRandomDirect(Tank.NORTH,
								Tank.WEST, Tank.EAST));
						break;
					}
					if (this.getDirect() != Tank.SOUTH)
						break;
					if (this.isOverlapYes() == false)
						this.goSouth();
				}
				break;
			case EnemyTank.WEST:
				for (;;) {
					this.sleep(36);
					if (this.getMyTankLocation() == EnemyTank.NORTH) {
						this.setDirect(EnemyTank.NORTH);
						this.enemyGoNorth();
					}
					if (this.getMyTankLocation() == EnemyTank.EAST) {
						this.setDirect(EnemyTank.EAST);
						this.enemyGoEast();
					}
					if (this.getMyTankLocation() == EnemyTank.SOUTH) {
						this.setDirect(EnemyTank.SOUTH);
						this.enemyGoSouth();
					}
					if (this.getMyTankLocation() == EnemyTank.WEST) {
						this.enemyGoWest();
					}
					if (this.getX() <= 20 || this.getY() <= 20
							|| this.isOverlapNo() == true) {
						this.setDirect(this.getRandomDirect(Tank.NORTH,
								Tank.SOUTH, Tank.EAST));
						break;
					}
					if (this.getDirect() != Tank.WEST)
						break;
					if (this.isOverlapYes() == false)
						this.goWest();
				}
				break;
			case EnemyTank.EAST:
				for (;;) {
					this.sleep(36);
					if (this.getMyTankLocation() == EnemyTank.WEST) {
						this.setDirect(EnemyTank.WEST);
						this.enemyGoWest();
					}
					if (this.getMyTankLocation() == EnemyTank.NORTH) {
						this.setDirect(EnemyTank.NORTH);
						this.enemyGoNorth();
					}
					if (this.getMyTankLocation() == EnemyTank.SOUTH) {
						this.setDirect(EnemyTank.SOUTH);
						this.enemyGoSouth();
					}
					if (this.getMyTankLocation() == EnemyTank.EAST) {
						this.enemyGoEast();
					}
					if (this.getX() >= GamePanel.WIDTH - 20
							|| this.getY() <= 20 || this.isOverlapNo() == true) {
						this.setDirect(this.getRandomDirect(Tank.NORTH,
								Tank.SOUTH, Tank.WEST));
						break;
					}
					if (this.getDirect() != Tank.EAST)
						break;
					if (this.isOverlapYes() == false)
						this.goEast();
				}
				break;
			}
			this.sleep(216); // 改变一个方向的话，不要让他很快
			if (this.isLive() == false) { // 如果坦克死亡的话 该坦克线程结束
				break;
			}
		}
	}

	/**
	 * 判断自己跟别的坦克是否重叠
	 * 
	 * @param enemys
	 *            敌人坦克容量
	 * @param myTanks
	 *            我的坦克容量
	 * @return 是否重叠
	 */
	public boolean isOverlap_(Vector<EnemyTank> enemys, Vector<MyTank> myTanks) {
		for (int i = 0; i < enemys.size(); i++) { // 依次取出每一个敌人坦克
			if (this != enemys.get(i)) {
				if (this.Overlap(enemys.get(i), 40) == true) { // 判断这两辆坦克是否重叠
					this.setOverlapNo(true);
					return true; // 一旦有重叠则返回真
				}
			}
		}
		for (int j = 0; j < myTanks.size(); j++) { // 依次取出每个我的坦克
			if (this.Overlap(myTanks.get(j), 40) == true) { // 判断这两辆坦克是否重叠
				this.setOverlapYes(true); // 面对我的坦克，敌人坦克开炮过去
				return true; // 一旦有重叠则返回真
			}
		}
		// 如果前面没有返回的话，说明没重叠，返回假
		this.setOverlapNo(false);
		this.setOverlapYes(false);
		return false; // 没有重叠现象则返回假
	}

	/**
	 * 每隔36毫秒 一直向西走
	 */
	public void enemyGoWest() {
		for (;;) {
			this.sleep(36);
			if (this.isOverlapNo() == false && this.isOverlapYes() == false) { // 不重叠的话
				this.goWest();
			}
			if (this.getMyTankLocation() != Tank.WEST) { // 我的坦克不在正西方的时候
				this.setDirect(this.getMyTankDirect()); // 让敌人坦克与我的坦克方向一致
				break;
			}
		}
	}

	/**
	 * 每隔36毫秒 一直向东走
	 */
	public void enemyGoEast() {
		for (;;) {
			this.sleep(36);
			if (this.isOverlapNo() == false && this.isOverlapYes() == false) {
				this.goEast();
			}
			if (this.getMyTankLocation() != Tank.EAST) {
				this.setDirect(this.getMyTankDirect());
				break;
			}
		}
	}

	/**
	 * 每隔36毫秒 一直向北走
	 */
	public void enemyGoNorth() {
		for (;;) {
			this.sleep(36);
			if (this.isOverlapNo() == false && this.isOverlapYes() == false) {
				this.goNorth();
			}
			if (this.getMyTankLocation() != Tank.NORTH) {
				this.setDirect(this.getMyTankDirect());
				break;
			}
		}
	}

	/**
	 * 每隔36毫秒 一直向南走
	 */
	public void enemyGoSouth() {
		for (;;) {
			this.sleep(36);
			if (this.isOverlapNo() == false && this.isOverlapYes() == false) {
				this.goSouth();
			}
			if (this.getMyTankLocation() != Tank.SOUTH) {
				this.setDirect(this.getMyTankDirect());
				break;
			}
		}
	}

	/**
	 * 从指定的三个方向中随机选择一个
	 * 
	 * @param direct1
	 *            方向1
	 * @param direct2
	 *            方向2
	 * @param direct3
	 *            方向3
	 */
	public int getRandomDirect(int direct1, int direct2, int direct3) {
		int random = (int) (Math.random() * 3);
		int returnDirect = -1;
		switch (random) {
		case 0:
			returnDirect = direct1;
			break;
		case 1:
			returnDirect = direct2;
			break;
		case 2:
			returnDirect = direct3;
			break;
		}
		return returnDirect;
	}

	/**
	 * 让敌人坦克能够发现我的坦克并开炮
	 * 
	 * @param myTank
	 *            我的坦克
	 * @param map
	 *            地图对象
	 */
	public void findAndKill(MyTank myTank, Map map) {
		int myX = myTank.getX();
		int myY = myTank.getY();
		int enX = this.getX();
		int enY = this.getY();
		if (Math.abs(myX - enX) < 20 && myY <= 580) { // 如果我的坦克在敌人坦克的正北方或正南方
			if (enY < myY) { // 我的坦克在正南方
				int s = 0;
				for (int t = 0; t < map.getIrons().size(); t++) {
					Iron iron = map.getIrons().get(t);
					if (Math.abs(enX - iron.getX()) <= 10 && iron.getY() > enY
							&& iron.getY() < myY) {
						s = 1; // 只要出现一个铁块能挡住子弹，就让s变为1，跳出判断
						break;
					}
				}
				if (s == 0) { // 如果s==1说明没有铁块挡住子弹，可以发射
					this.setShot(true);
					this.setMyTankLocation(EnemyTank.SOUTH);
				}
			} else { // 我的坦克在正北方
				int s = 0;
				for (int t = 0; t < map.getIrons().size(); t++) {
					Iron iron = map.getIrons().get(t);
					if (Math.abs(enX - iron.getX()) <= 10 && iron.getY() < enY
							&& iron.getY() > myY) {
						s = 1;
						break;
					}
				}
				if (s == 0) {
					this.setShot(true);
					this.setMyTankLocation(EnemyTank.NORTH);
				}
			}
		} else if (Math.abs(myY - enY) < 20 && myY <= 580) { // 如果我的坦克在敌人坦克的正西方或正东方
			if (enX > myX) { // 我的坦克在正西方
				int s = 0;
				for (int t = 0; t < map.getIrons().size(); t++) {
					Iron iron = map.getIrons().get(t);
					// 铁块能挡住子弹，而且在我的坦克和敌人坦克之间
					if (Math.abs(enY - iron.getY()) <= 10 && iron.getX() < enX
							&& iron.getX() > myX) {
						s = 1;
						break;
					}
				}
				if (s == 0) {
					this.setShot(true);
					this.setMyTankLocation(EnemyTank.WEST);
				}
			} else { // 我的坦克在正东方
				int s = 0;
				for (int t = 0; t < map.getIrons().size(); t++) {
					Iron iron = map.getIrons().get(t);
					if (Math.abs(enY - iron.getY()) <= 10 && iron.getX() > enX
							&& iron.getX() < myX) {
						s = 1;
						break;
					}
				}
				if (s == 0) {
					this.setShot(true);
					this.setMyTankLocation(EnemyTank.EAST);
				}
			}
		} else { // 其他情况敌人坦克不能判断我的坦克位置，要完善的话，还可以继续加进去
			this.setShot(false);
			this.setMyTankLocation(-1);
		}
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public int getMyTankLocation() {
		return myTankLocation;
	}

	public void setMyTankLocation(int myTankLocation) {
		this.myTankLocation = myTankLocation;
	}

	public boolean isShot() {
		return isShot;
	}

	public void setShot(boolean isShot) {
		this.isShot = isShot;
	}

	public int getMyTankDirect() {
		return myTankDirect;
	}

	public void setMyTankDirect(int myTankDirect) {
		this.myTankDirect = myTankDirect;
	}

	public boolean isInMap() {
		return isInMap;
	}

	public void setInMap(boolean isInMap) {
		this.isInMap = isInMap;
	}
}

/**
 * 发射子弹
 * 
 * @author 1052067939
 *
 */
class MyTimerTask extends TimerTask {
	EnemyTank tank;

	public MyTimerTask(EnemyTank tank) {
		this.tank = tank;
	}

	@Override
	public void run() {
		if (tank.getSpeedVector() == 0 && tank.isShot() == true)
			tank.shot(tank);
	}

}
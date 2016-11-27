package cn.edu.hdu.tankbattle.control;

import java.util.Vector;

import cn.edu.hdu.tankbattle.model.Bomb;
import cn.edu.hdu.tankbattle.model.Brick;
import cn.edu.hdu.tankbattle.model.Bullet;
import cn.edu.hdu.tankbattle.model.EnemyTank;
import cn.edu.hdu.tankbattle.model.GameResource;
import cn.edu.hdu.tankbattle.model.Iron;
import cn.edu.hdu.tankbattle.model.MyTank;
import cn.edu.hdu.tankbattle.model.Stuff;
import cn.edu.hdu.tankbattle.model.Tank;
import cn.edu.hdu.tankbattle.model.Water;
import cn.edu.hdu.tankbattle.model.map.Map;
import cn.edu.hdu.tankbattle.model.map.Map1;
import cn.edu.hdu.tankbattle.model.map.Map2;
import cn.edu.hdu.tankbattle.model.map.Map3;
import cn.edu.hdu.tankbattle.model.map.Map4;
import cn.edu.hdu.tankbattle.model.map.Map5;
import cn.edu.hdu.tankbattle.view.GamePanel;

/**
 * 游戏控制类
 * 
 * @author 1052067939
 * @version 1.0
 * @since JavaSe-1.6
 */
public class Control {
	public static final int STARTBULLETNUM = 500;
	/**
	 * 敌人坦克总的数量
	 */
	private int enemyTankNum;
	/**
	 * 我的坦克数量
	 */
	private int myTankNum;
	/**
	 * 我的坦克被杀数量
	 */
	private int beKilled;
	/**
	 * 我的子弹数量
	 */
	private int myBulletNum;
	/**
	 * 是否已经开始
	 */
	private boolean isStart = false;
	/**
	 * 是否暂停
	 */
	private boolean isStop = false;
	/**
	 * 是否按了向上的方向键
	 */
	private boolean up = false;
	/**
	 * 是否按了向下的方向键
	 */
	private boolean down = false;
	/**
	 * 是否按了向左的方向键
	 */
	private boolean left = false;
	/**
	 * 是否按了向右的方向键
	 */
	private boolean right = false;
	/**
	 * 游戏关卡
	 */
	private int level = 1;

	/**
	 * 动态笑脸的控制flag
	 */
	private boolean iconSmile;
	/**
	 * 游戏失败或成功时的图片的y坐标
	 */
	private int dy = 600;
	/**
	 * 游戏开始的移动文字
	 */
	private int ky = 600;
	/**
	 * 游戏开始的笑脸icon
	 */
	private int kx = 0;

	/**
	 * 构造方法 完成初始化: 敌人坦克数量8辆 我的坦克数量2辆 我的子弹数量200发 我杀死的敌人坦克数量0辆
	 */
	public Control() {
		this.enemyTankNum = 8;
		this.myTankNum = 4;
		this.myBulletNum = Control.STARTBULLETNUM;
		this.beKilled = 0;
	}

	/**
	 * 判断子弹击中情况 坦克属性初始化等
	 * 
	 * @param myTanks
	 *            我的坦克容器
	 * @param enemys
	 *            敌人坦克容器
	 * @param map
	 *            地图对象
	 * @param bombs
	 *            爆炸容器
	 * @return 无
	 */
	public void judge(Vector<MyTank> myTanks, Vector<EnemyTank> enemys,
			Map map, Vector<Bomb> bombs) {
		if (this.myTankNum == 0) { // 游戏结束时
			for (int i = 0; i < enemys.size(); i++) {
				EnemyTank enemyTank = enemys.get(i);
				enemyTank.setShot(false);
			}
		}
		for (int j = 0; j < myTanks.size(); j++) { // 从我的坦克容器中取出一辆我的坦克
			MyTank myTank = myTanks.get(j);

			for (int i = 0; i < enemys.size(); i++) { // 从敌人的坦克容器中取出一辆敌人的坦克
				EnemyTank enemyTank = enemys.get(i);

				if (enemyTank.getY() >= 20)
					enemyTank.setInMap(true);
				enemyTank.setMyTankDirect(myTank.getDirect()); // 设置敌人坦克的“我的坦克”位置
				enemyTank.findAndKill(myTank, map); // 让敌人坦克能够发现我的坦克并开炮

				for (int k = 0; k < enemyTank.getBullets().size(); k++) {
					Bullet eb = enemyTank.getBullets().get(k); // 从敌人的子弹容器中取出一颗子弹
					if (Math.abs(eb.getX() - myTank.getX()) <= 20
							&& Math.abs(eb.getY() - myTank.getY()) <= 20) { // 判断是否击中我的坦克
						this.afterShotTank(eb, myTank, bombs); // 击中我的坦克以后
					}
					for (int l = 0; l < map.getBricks().size(); l++) { // 取出每个砖块对象与子弹比较
						Brick brick = map.getBricks().get(l);
						if (Math.abs(eb.getX() - brick.getX()) <= brick
								.getWidth() / 2
								&& Math.abs(eb.getY() - brick.getY()) <= brick
										.getWidth() / 2) {// 子弹击中砖块
							this.afterShotStuff(eb, brick, bombs, enemyTank);// 击中事物
						}
					}
					for (int l = 0; l < map.getIrons().size(); l++) { // 取出没个铁块对象与子弹比较
						Iron iron = map.getIrons().get(l);
						if (Math.abs(eb.getX() - iron.getX()) <= iron
								.getWidth() / 2
								&& Math.abs(eb.getY() - iron.getY()) <= iron
										.getWidth() / 2) {// 子弹击中铁块
							this.afterShotStuff(eb, iron, bombs, enemyTank); // 击中事物
						}
					}
				}

				for (int k = 0; k < myTank.getBullets().size(); k++) {
					Bullet mb = myTank.getBullets().get(k); // 从我的子弹容器中取出一颗子弹
					for (int t = 0; t < enemyTank.getBullets().size(); t++) {
						// 判断是否击中敌人坦克的子弹
						if (Math.abs(mb.getX()
								- enemyTank.getBullets().get(t).getX()) <= 6
								&& Math.abs(mb.getY()
										- enemyTank.getBullets().get(t).getY()) <= 6) {
							// 子弹死亡、坦克也死亡
							mb.setLive(false);
							enemyTank.getBullets().get(t).setLive(false);
							myTank.getBullets().remove(mb);
							enemyTank.getBullets().remove(
									enemyTank.getBullets().get(t));
							// 产生爆炸
							Bomb bomb = new Bomb(mb.getX(), mb.getY());
							bomb.setL(20);
							bombs.add(bomb);
						}
					}
					// 判断是否击中敌人坦克
					if (Math.abs(mb.getX() - enemyTank.getX()) <= 20
							&& Math.abs(mb.getY() - enemyTank.getY()) <= 20) { // 判断是否击中敌人的坦克
						this.afterShotTank(mb, enemyTank, bombs); // 击中以后
					}
					for (int l = 0; l < map.getBricks().size(); l++) { // 取出每个砖块对象与子弹比较
						Brick brick = map.getBricks().get(l);
						if (Math.abs(mb.getX() - brick.getX()) <= brick
								.getWidth() / 2
								&& Math.abs(mb.getY() - brick.getY()) <= brick
										.getWidth() / 2) {// 子弹击中砖块
							this.afterShotStuff(mb, brick, bombs, myTank);
						}
					}
					for (int t = 0; t < map.getIrons().size(); t++) {
						Iron iron = map.getIrons().get(t);
						if (Math.abs(mb.getX() - iron.getX()) <= iron
								.getWidth() / 2
								&& Math.abs(mb.getY() - iron.getY()) <= iron
										.getWidth() / 2) {// 子弹击中砖块
							this.afterShotStuff(mb, iron, bombs, myTank);
						}
					}
				}
			}
		}
	}

	/**
	 * 判断是否重叠
	 * 
	 * @param myTanks
	 *            我的坦克容量
	 * @param enemys
	 *            敌人坦克容量
	 * @param map
	 *            地图对象
	 */
	public void judgeOverlap(Vector<MyTank> myTanks, Vector<EnemyTank> enemys,
			Map map) {
		Vector<Brick> bricks = map.getBricks();
		Vector<Iron> irons = map.getIrons();
		Vector<Water> waters = map.getWaters();
		for (int i = 0; i < myTanks.size(); i++) {
			MyTank myTank = myTanks.get(i);
			// 先初始化，让我的坦克不重叠
			myTank.setOverlapNo(false);
			myTank.setOverlapYes(false);

			if (myTank.isOverlap_(enemys) == true) { // 判断我的坦克是否与敌人坦克重叠
				myTank.setOverlapYes(true);
			}
			for (int j = 0; j < bricks.size(); j++) { // 判断我的坦克是否与砖块重叠
				if (myTanks.get(i).Overlap(bricks.get(j), 20 + 10) == true) {
					myTanks.get(i).setOverlapYes(true);
					break;
				}
			}
			for (int j = 0; j < irons.size(); j++) { // 判断我的坦克是否与铁块重叠
				if (myTanks.get(i).Overlap(irons.get(j), 20 + 10) == true) {
					myTanks.get(i).setOverlapNo(true);
					break;
				}
			}
			for (int j = 0; j < waters.size(); j++) { // 判断我的坦克是否与河流重叠
				if (myTanks.get(i).Overlap(waters.get(j), 20 + 10) == true) {
					myTanks.get(i).setOverlapNo(true);
					break;
				}
			}
		}

		for (int i = 0; i < enemys.size(); i++) {
			EnemyTank enemyTank = enemys.get(i);
			// 先初始化，让敌人坦克不重叠，前面无东西挡住
			enemyTank.setOverlapNo(false);
			enemyTank.setOverlapYes(false);
			enemyTank.setFrontInfomation(-1); // 没有东西挡住

			if (enemyTank.isOverlap_(enemys, myTanks) == true) {
				enemyTank.setOverlapYes(true);
			}

			for (int j = 0; j < bricks.size(); j++) {
				if (enemyTank.Overlap(bricks.get(j), 20 + 10) == true) {
					// 判断前面挡住砖块是否能被子弹打掉，能的话，就开炮
					if ((Math.abs(bricks.get(j).getX() - enemyTank.getX()) <= 10 && (enemyTank
							.getDirect() == EnemyTank.SOUTH || enemyTank
							.getDirect() == EnemyTank.NORTH))
							|| (Math.abs(bricks.get(j).getY()
									- enemyTank.getY()) <= 10 && (enemyTank
									.getDirect() == EnemyTank.EAST || enemyTank
									.getDirect() == EnemyTank.WEST))) {
						enemyTank.setFrontInfomation(Stuff.BRICK);
						enemyTank.setOverlapYes(true);
						enemyTank.setShot(true);
					} else {
						enemyTank.setOverlapNo(true);
					}
				}
			}
			for (int j = 0; j < irons.size(); j++) {
				if (enemyTank.Overlap(irons.get(j), 20 + 10) == true) {
					enemyTank.setFrontInfomation(Stuff.IRON); // 挡住的东西是铁块
					enemyTank.setOverlapNo(true);
					break;
				}
			}

			for (int j = 0; j < waters.size(); j++) {
				if (enemyTank.Overlap(waters.get(j), 20 + 10) == true) {
					enemyTank.setFrontInfomation(Stuff.WATER);
					enemyTank.setOverlapNo(true);
					break;
				}
			}
		}
	}

	/**
	 * 移走已经死亡的坦克和子弹，创建要创建的坦克
	 * 
	 * @param myTanks
	 *            我的坦克容量
	 * @param enemys
	 *            敌人坦克容量
	 * @param map
	 *            地图对象
	 * @param bombs
	 *            爆炸对象
	 */
	public void cleanAndCreat(Vector<MyTank> myTanks, Vector<EnemyTank> enemys,
			Map map, Vector<Bomb> bombs) {
		for (int i = 0; i < myTanks.size(); i++) {
			MyTank myTank = myTanks.get(i);
			Vector<Bullet> mb = myTank.getBullets();
			// 清除我的坦克死亡的子弹
			for (int j = 0; j < mb.size(); j++) {
				Bullet b = mb.get(j);
				if (b.isLive() == false) {
					mb.remove(b);
				}
			}
			// 清除我的死亡的坦克
			if (myTank.isLive() == false) {
				myTanks.remove(myTank);
				this.myTankNum--; // 我的坦克数量减1
				this.beKilled++;
				if (this.myTankNum >= 1) { // 如果还有我的坦克就创建一个，刚开始面板上就创建了一个我的坦克，所以大于等于
											// 1
					MyTank myTanktemp = new MyTank(300, 620, Tank.NORTH); // 创建一个我的坦克
					myTanks.add(myTanktemp);
				}
			}
		}

		for (int i = 0; i < enemys.size(); i++) {
			EnemyTank enemy = enemys.get(i);
			Vector<Bullet> eb = enemy.getBullets();
			// 清除敌人坦克的死亡的子弹
			for (int j = 0; j < eb.size(); j++) {
				Bullet b = eb.get(j);
				if (b.isLive() == false) {
					eb.remove(b);
				}
			}
			// 清除死亡的敌人坦克，并创建新的坦克
			if (enemy.isLive() == false) {
				enemy.getTimer().cancel(); // 取消定时发射子弹
				int r, isOk = 0;
				for (int p = 0; p < enemys.size(); p++) {
					if (enemys.get(p).isInMap() == false) { // 有坦克还没有出去
						isOk = -1;
						break;
					}
				}
				if (isOk == 0) { // 所有坦克都已经在地图中了
					this.enemyTankNum--; // 敌人坦克的数量减1
					r = (int) (Math.random() * 5); // 随机选择三个位置中的一个
					enemys.remove(enemy); // 敌人坦克死亡后马上产生一个新的敌人坦克
					if (this.enemyTankNum >= 5) { // 如果还有敌人坦克，刚开始时面板上就创建了3个，所以大于等于3
						EnemyTank enemyTank = new EnemyTank((r) * 140 + 20,
								-20, Tank.SOUTH); // 创建一个敌人坦克对象
						enemyTank.setLocation(r);
						enemys.add(enemyTank); // 将该坦克加入敌人坦克容器中
					}
					break;
				}
			}
		}
		// 清除死亡的炸弹
		for (int i = 0; i < bombs.size(); i++) {
			if (bombs.get(i).isLive() == false) {
				bombs.remove(bombs.get(i));
			}
		}
		// 清除死亡的砖块
		for (int i = 0; i < map.getBricks().size(); i++) {
			if (map.getBricks().get(i).isLive() == false) {
				map.getBricks().remove(map.getBricks().get(i));
			}
		}
	}

	/**
	 * 击中坦克以后
	 * 
	 * @param bullet
	 *            击中别人的子弹
	 * @param t
	 *            被击中的坦克
	 * @param bombs
	 *            炸弹容量
	 */
	public void afterShotTank(Bullet bullet, Tank tank, Vector<Bomb> bombs) {
		// 击中，子弹死亡 敌人坦克死亡 爆炸
		bullet.setLive(false); // 击中坦克的子弹死亡
		Bomb bomb; // 一颗炸弹
		if (tank.getBlood() == 1) { // 只剩下最后一滴血
			tank.setLive(false); // 坦克死亡
			bomb = new Bomb(tank.getX(), tank.getY()); // 创建炸弹，由坦克中心开始爆炸
			tank.setBlood(tank.getBlood() - 1); // 坦克的血减1
			bomb.setL(120); // 爆炸宽度120
			bombs.add(bomb);
		} else {
			bomb = new Bomb(bullet.getX(), bullet.getY());// 创建炸弹，由子弹位置开始爆炸
			tank.setBlood(tank.getBlood() - 1); // 坦克的血量减1
			bomb.setL(40); // 爆炸宽度40
			bombs.add(bomb); // 将该炸弹加入炸弹容器中
		}
	}

	/**
	 * 击中东西以后
	 * 
	 * @param bullet
	 *            集中别人的子弹
	 * @param stuff
	 *            被击中的东西
	 * @param bombs
	 *            炸弹容量
	 */
	public void afterShotStuff(Bullet bullet, Stuff stuff, Vector<Bomb> bombs,
			Tank tank) {
		Bomb bomb;
		switch (stuff.getType()) {
		case Stuff.BRICK: // 砖块
			bullet.setLive(false);
			stuff.setLive(false);
			bomb = new Bomb(stuff.getX(), stuff.getY());
			bomb.setL(40);
			bombs.add(bomb);
			break;
		case Stuff.IRON: // 铁块
			bomb = new Bomb(bullet.getX(), bullet.getY());
			bullet.setLive(false);
			bomb.setL(20);
			bombs.add(bomb);
		}
	}

	/**
	 * 我的坦克事件，观察我按了什么键
	 * 
	 * @param resource
	 *            .getMyTanks() 我的坦克容量
	 */
	public void myTankEvent(GameResource resource) {
		for (int i = 0; i < resource.getMyTanks().size(); i++) {
			MyTank myTank = resource.getMyTanks().get(i);
			if (up == true && myTank.isOverlapNo() == false
					&& myTank.isOverlapYes() == false) {
				myTank.goNorth();
			} else if (down == true && myTank.isOverlapNo() == false
					&& myTank.isOverlapYes() == false) {
				myTank.goSouth();
			} else if (left == true && myTank.isOverlapNo() == false
					&& myTank.isOverlapYes() == false) {
				myTank.goWest();
			} else if (right == true && myTank.isOverlapNo() == false
					&& myTank.isOverlapYes() == false) {
				myTank.goEast();
			}
		}
	}

	/**
	 * 下一关
	 */
	public void nextGame(GameResource resource) {
		if (level == 1) { // 游戏关卡
			resource.setMap(new Map1());
		} else if (level == 2) {
			resource.setMap(new Map2());
		} else if (level == 3) {
			resource.setMap(new Map3());
		} else if (level == 4) {
			resource.setMap(new Map4());
		} else if (level == 5) {
			resource.setMap(new Map5());
		}

		for (int i = 0; i < 5; i++) {
			EnemyTank enemy = new EnemyTank((i) * 140 + 20, -20, Tank.SOUTH); // 创建一个敌人坦克对象
			enemy.setLocation(i);
			resource.getEnemys().add(enemy); // 将该坦克加入敌人坦克容器中 //将该子弹加入该坦克的子弹容器中
		}
		this.setEnemyTankNum(8);
		for (int i = 0; i < resource.getMyTanks().size(); i++) {
			MyTank myTank = resource.getMyTanks().get(i);
			myTank.setX(300);
			myTank.setY(620);
		}
	}

	/**
	 * 开始游戏
	 */
	public void startGame(GameResource resource) {
		resource.reset();
		this.setDy(600);
		if (level == 1) { // 游戏关卡
			resource.setMap(new Map1());
		} else if (level == 2) {
			resource.setMap(new Map2());
		} else if (level == 3) {
			resource.setMap(new Map3());
		} else if (level == 4) {
			resource.setMap(new Map4());
		} else if (level == 5) {
			resource.setMap(new Map5());
		}
		for (int i = 0; i < 5; i++) {
			EnemyTank enemy = new EnemyTank((i) * 140 + 20, -20, Tank.SOUTH); // 创建一个敌人坦克对象
			enemy.setLocation(i);
			resource.getEnemys().add(enemy); // 将该坦克加入敌人坦克容器中 //将该子弹加入该坦克的子弹容器中
		}
		for (int i = 0; i < 1; i++) {
			MyTank myTank = new MyTank(300, 620, Tank.NORTH); // 创建一个我的坦克
			resource.getMyTanks().add(myTank); // 将我的坦克加入我的坦克容器中
		}
	}

	/**
	 * 游戏暂停
	 * 
	 * @param myTank
	 *            我的坦克
	 * @param resource
	 *            .getEnemys() 敌人坦克容量
	 */
	public void gameEventStop(GameResource resource) {
		for (int i = 0; i < resource.getMyTanks().size(); i++) {
			MyTank myTank = resource.getMyTanks().get(i);
			if (myTank.getSpeedVector() == 0) { // 已经暂停的不能在执行下面的语句
				setStop(true);
				myTank.setSpeedVector(myTank.getSpeed()); // 保存当前坦克的速度
				myTank.setSpeed(0); // 设置当前坦克速度为0
				for (int j = 0; j < myTank.getBullets().size(); j++) {
					myTank.getBullets()
							.get(j)
							.setSpeedVector(
									myTank.getBullets().get(j).getSpeed());
					myTank.getBullets().get(j).setSpeed(0); // 设置子弹为0
				}
				for (int j = 0; j < resource.getEnemys().size(); j++) {
					resource.getEnemys()
							.get(j)
							.setSpeedVector(
									resource.getEnemys().get(j).getSpeed());
					resource.getEnemys().get(j).setSpeed(0);
					for (int k = 0; k < resource.getEnemys().get(j)
							.getBullets().size(); k++) {
						resource.getEnemys()
								.get(j)
								.getBullets()
								.get(k)
								.setSpeedVector(
										resource.getEnemys().get(j)
												.getBullets().get(k).getSpeed());
						resource.getEnemys().get(j).getBullets().get(k)
								.setSpeed(0);
					}
				}
			} else {
				setStop(false);
				myTank.setSpeed(myTank.getSpeedVector());
				myTank.setSpeedVector(0);
				for (int j = 0; j < myTank.getBullets().size(); j++) {
					myTank.getBullets()
							.get(j)
							.setSpeed(
									myTank.getBullets().get(j).getSpeedVector());
				}
				for (int j = 0; j < resource.getEnemys().size(); j++) {
					resource.getEnemys()
							.get(j)
							.setSpeed(
									resource.getEnemys().get(j)
											.getSpeedVector());
					resource.getEnemys().get(j).setSpeedVector(0);
					for (int k = 0; k < resource.getEnemys().get(j)
							.getBullets().size(); k++) {
						resource.getEnemys()
								.get(j)
								.getBullets()
								.get(k)
								.setSpeed(
										resource.getEnemys().get(j)
												.getBullets().get(k)
												.getSpeedVector());
					}
				}
			}
		}
	}

	/**
	 * 游戏没开始时的欢迎图片上的字体移动
	 */
	public void fontMove(GameResource resource, GamePanel panel) {

		if (ky > 0 && ky != 21)
			ky = ky - 8; // 当字体刚出来时，向上移动
		if (ky == 0) { // 当字体移到顶端时，以7的速度向下移动5步
			for (int i = 0; i < 5; i++) {
				ky = ky + 7;
				panel.repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for (int i = 0; i < 4; i++) { // 以6的速度向上移动4步
				ky = ky - 6;
				panel.repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for (int i = 0; i < 3; i++) { // 以5的速度向下移动
				ky = ky + 5;
				panel.repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for (int i = 0; i < 2; i++) { // 以4的速度向上移动
				ky = ky - 4;
				panel.repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			ky = ky + 3;// 最后，字体的图片停留在（0，21）的地方
		}
	}

	public int getEnemyTankNum() {
		return enemyTankNum;
	}

	public void setEnemyTankNum(int enemyTankNum) {
		this.enemyTankNum = enemyTankNum;
	}

	public int getMyTankNum() {
		return myTankNum;
	}

	public void setMyTankNum(int myTankNum) {
		this.myTankNum = myTankNum;
	}

	public int getMyBulletNum() {
		return myBulletNum;
	}

	public void setMyBulletNum(int myBulletNum) {
		this.myBulletNum = myBulletNum;
	}

	public int getBeKilled() {
		return beKilled;
	}

	public void setBeKilled(int beKilled) {
		this.beKilled = beKilled;
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isStop() {
		return isStop;
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}

	public boolean isIconSmile() {
		return iconSmile;
	}

	public void setIconSmile(boolean iconSmile) {
		this.iconSmile = iconSmile;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public int getKy() {
		return ky;
	}

	public void setKy(int ky) {
		this.ky = ky;
	}

	public int getKx() {
		return kx;
	}

	public void setKx(int kx) {
		this.kx = kx;
	}

}

package cn.edu.hdu.tankbattle.control;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Vector;

import javax.swing.JPanel;

import cn.edu.hdu.tankbattle.model.Bomb;
import cn.edu.hdu.tankbattle.model.Brick;
import cn.edu.hdu.tankbattle.model.Bullet;
import cn.edu.hdu.tankbattle.model.EnemyTank;
import cn.edu.hdu.tankbattle.model.Iron;
import cn.edu.hdu.tankbattle.model.MyTank;
import cn.edu.hdu.tankbattle.model.Stuff;
import cn.edu.hdu.tankbattle.model.Tank;
import cn.edu.hdu.tankbattle.model.TankGameImages;
import cn.edu.hdu.tankbattle.model.Water;
import cn.edu.hdu.tankbattle.model.map.Map;
import cn.edu.hdu.tankbattle.view.GamePanel;

/**
 * 游戏绘画类
 * 
 * @author 1052067939
 * @since JavaSe-1.6
 */
public class Draw {
	/**
	 * 画出东西（包括坦克、障碍物。。）
	 * 
	 * @param g
	 *            Graphics
	 * @param stuff
	 *            东西对象
	 * @param panel
	 *            被画的那个面板
	 */
	public void drawStuff(Graphics g, Stuff stuff, JPanel panel) {
		switch (stuff.getType()) {
		case Stuff.TANK:
			switch (stuff.getDirect()) { // 判断所朝的方向
			case Stuff.NORTH:
				this.drawNorth(g, stuff, panel);
				break;
			case Stuff.SOUTH:
				this.drawSouth(g, stuff, panel);
				break;
			case Stuff.WEST:
				this.drawWest(g, stuff, panel);
				break;
			case Stuff.EAST:
				this.drawEast(g, stuff, panel);
				break;
			}
			break;
		case Stuff.BRICK:
			/*
			 * g.setColor(new Color(216,90,49)); g.fill3DRect(stuff.getX()-20,
			 * stuff.getY()-20, 40, 40, false);
			 */
			g.drawImage(TankGameImages.stuffImg[Stuff.BRICK],
					stuff.getX() - 10, stuff.getY() - 10, 20, 20, panel);
			break;
		case Stuff.IRON:
			/*
			 * g.setColor(new Color(225,225,225)); g.fill3DRect(stuff.getX()-20,
			 * stuff.getY()-20, 40, 40, false);
			 */
			g.drawImage(TankGameImages.stuffImg[Stuff.IRON], stuff.getX() - 10,
					stuff.getY() - 10, 20, 20, panel);
			break;
		case Stuff.WATER:
			/*
			 * g.setColor(new Color(65,64,253)); g.fillRect(stuff.getX()-20,
			 * stuff.getY()-20, 40, 40);
			 */
			g.drawImage(TankGameImages.stuffImg[Stuff.WATER],
					stuff.getX() - 10, stuff.getY() - 10, 20, 20, panel);
			break;
		}

	}

	/**
	 * 画出爆炸
	 * 
	 * @param g
	 *            Graphics
	 * @param bombs
	 *            炸弹对象容器
	 * @param panel
	 *            被画的那个面板
	 */
	public void drawBomb(Graphics g, Vector<Bomb> bombs, JPanel panel) {
		for (int i = 0; i < bombs.size(); i++) {
			int l = bombs.get(i).getL();
			Bomb b = bombs.get(i); // 从炸弹容器中取出一颗炸弹
			if (b.getLifeTime() > 24) { // 生命值21-25
				g.drawImage(TankGameImages.bomb[0], b.getX() - l / 2, b.getY()
						- l / 2, l, l, panel);
			} else if (b.getLifeTime() > 18) { // 生命值16-20
				g.drawImage(TankGameImages.bomb[1], b.getX() - l / 2, b.getY()
						- l / 2, l, l, panel);
			} else if (b.getLifeTime() > 12) { // 生命值11-15
				g.drawImage(TankGameImages.bomb[2], b.getX() - l / 2, b.getY()
						- l / 2, l, l, panel);
			} else if (b.getLifeTime() > 6) { // 生命值6-10
				g.drawImage(TankGameImages.bomb[3], b.getX() - l / 2, b.getY()
						- l / 2, l, l, panel);
			} else { // 生命值低于6
				g.drawImage(TankGameImages.bomb[4], b.getX() - l / 2, b.getY()
						- l / 2, l, l, panel);
			}
			b.lifeDown(); // 生命随时间衰减
			if (b.getLifeTime() == 0) { // 该炸弹死亡
				b.setLive(false);
			}
		}
	}

	/**
	 * 画出敌人坦克和子弹
	 * 
	 * @param g
	 *            Graphics
	 * @param enemys
	 *            敌人坦克容量
	 * @param panel
	 *            被画的面板
	 */
	public void drawEnemyTank(Graphics g, Vector<EnemyTank> enemys, JPanel panel) {
		for (int i = 0; i < enemys.size(); i++) {
			this.drawStuff(g, enemys.get(i), panel); // 画出敌人的坦克
			for (int j = 0; j < enemys.get(i).getBullets().size(); j++) {
				if (enemys.get(i).getBullets().get(j) != null) {
					Bullet eb = enemys.get(i).getBullets().get(j);
					g.drawImage(TankGameImages.bullet, eb.getX() - 2,
							eb.getY() - 2, 4, 4, panel);
				}
			}
		}
	}

	/**
	 * 画出我的坦克和子弹
	 * 
	 * @param g
	 *            Graphics
	 * @param myTanks
	 *            我的坦克容量
	 * @param panel
	 *            被画的那个面板
	 */
	public void drawMyTank(Graphics g, Vector<MyTank> myTanks, JPanel panel) {
		for (int m = 0; m < myTanks.size(); m++) {
			MyTank myTank = myTanks.get(m); // 取出我的坦克
			this.drawStuff(g, myTank, panel); // 画出我的坦克
			for (int i = 0; i < myTank.getBullets().size(); i++) {
				if (myTank.getBullets().get(i) != null) {
					Bullet b = myTank.getBullets().get(i);
					g.drawImage(TankGameImages.bullet, b.getX() - 2,
							b.getY() - 2, 4, 4, panel);
				}
			}
		}
	}

	/**
	 * 画出地图
	 * 
	 * @param g
	 *            Graphics
	 * @param map
	 *            地图对象
	 * @param panel
	 *            被画的那个面板
	 */
	public void drawMap(Graphics g, Map map, JPanel panel) {
		Vector<Brick> bricks = map.getBricks();
		Vector<Iron> irons = map.getIrons();
		Vector<Water> waters = map.getWaters();
		for (int i = 0; i < bricks.size(); i++) {
			this.drawStuff(g, bricks.get(i), panel);
		}
		for (int i = 0; i < irons.size(); i++) {
			this.drawStuff(g, irons.get(i), panel);
		}
		for (int i = 0; i < waters.size(); i++) {
			this.drawStuff(g, waters.get(i), panel);
		}
	}

	/**
	 * 画出一个面朝北的坦克
	 * 
	 * @param g
	 *            Graphics
	 * @param tank
	 *            东西对象
	 * @param panel
	 *            被画的那个面板
	 */
	public void drawNorth(Graphics g, Stuff tank, JPanel panel) {
		/*
		 * int x=tank.getX(); int y=tank.getY(); //0.设置画笔颜色
		 * g.setColor(Color.white); //1.画出左边的矩形 g.fill3DRect(x-20, y-20, 10,
		 * 40,false); //2.画出右边矩形 g.fill3DRect(x+10, y-20, 10, 40,false);
		 * //3.更换画笔颜色 g.setColor(tank.getColor()); //4.画出轮子条纹 for(int
		 * i=0;i<20-1;i++){ g.drawLine(x-20, y-20+(i+1)*2, x-10-1,y-20+(i+1)*2);
		 * g.drawLine(x+10, y-20+(i+1)*2, x+20-1,y-20+(i+1)*2); } //5.画出中间1矩形
		 * g.fill3DRect(x-15, y-14, 30, 28,false); //6.更换画笔颜色
		 * g.setColor(Color.white); //7.画出中间2矩形 g.draw3DRect(x-10, y-9, 20,
		 * 18,false); //8.画出中间3矩形 g.draw3DRect(x-3, y-5, 6, 10,false); //9.画直线
		 * g.drawLine(x-15, y-14, x-10, y-9); g.drawLine(x+15, y-14, x+10, y-9);
		 * g.drawLine(x-15, y+14, x-10, y+9); g.drawLine(x+15, y+14, x+10, y+9);
		 * g.setColor(tank.getColor()); //10.画矩形 g.fill3DRect(x-3, y-12, 6, 3,
		 * false); g.fill3DRect(x-2, y-20, 4, 2, false); g.fill3DRect(x-1, y-20,
		 * 2, 11, false);
		 */
		Image image;
		if (tank.getType2() == Tank.MY) {
			g.setColor(Color.green);
			image = TankGameImages.myTankImg[Stuff.NORTH];// 初始化图片
		} else {
			image = TankGameImages.enemyTankImg[Stuff.NORTH];
			g.setColor(Color.gray);
		}
		g.drawImage(image, tank.getX() - 20, tank.getY() - 20, 40, 40, panel);
		g.fillRect(tank.getX() - 20, tank.getY() - 30, tank.getBlood() * 4, 5);
	}

	/**
	 * 画出一个方向朝南的坦克
	 * 
	 * @param g
	 *            Graphics
	 * @param tank
	 *            东西对象
	 * @param panel
	 *            被画的那个面板
	 */
	public void drawSouth(Graphics g, Stuff tank, JPanel panel) {
		/*
		 * int x=tank.getX(); int y=tank.getY(); g.setColor(Color.white);
		 * g.fill3DRect(x-20, y-20, 10, 40,false); g.fill3DRect(x+10, y-20, 10,
		 * 40,false); g.setColor(tank.getColor()); for(int i=0;i<20-1;i++){
		 * g.drawLine(x-20, y-20+(i+1)*2, x-10-1,y-20+(i+1)*2); g.drawLine(x+10,
		 * y-20+(i+1)*2, x+20-1,y-20+(i+1)*2); } g.fill3DRect(x-15, y-14, 30,
		 * 28,false); g.setColor(Color.white); g.draw3DRect(x-10, y-9, 20,
		 * 18,false); g.draw3DRect(x-3, y-5, 6, 10,false); g.drawLine(x-15,
		 * y-14, x-10, y-9); g.drawLine(x+15, y-14, x+10, y-9); g.drawLine(x-15,
		 * y+14, x-10, y+9); g.drawLine(x+15, y+14, x+10, y+9);
		 * g.setColor(tank.getColor()); g.fill3DRect(x-3, y+9, 6, 3, false);
		 * g.fill3DRect(x-1, y+9, 2, 11, false); g.fill3DRect(x-2, y+18, 4, 2,
		 * false);
		 */
		Image image;
		if (tank.getType2() == Tank.MY) {
			g.setColor(Color.green);
			image = TankGameImages.myTankImg[Stuff.SOUTH];// 初始化图片
		} else {
			image = TankGameImages.enemyTankImg[Stuff.SOUTH];
			g.setColor(Color.gray);
		}
		g.drawImage(image, tank.getX() - 20, tank.getY() - 20, 40, 40, panel);
		g.fillRect(tank.getX() - 20, tank.getY() - 30, tank.getBlood() * 4, 5);
	}

	/**
	 * 画出一个方向朝西的坦克
	 * 
	 * @param g
	 *            Graphics
	 * @param tank
	 *            东西对象
	 * @param panel
	 *            被画的那个面板
	 */
	public void drawWest(Graphics g, Stuff tank, JPanel panel) {
		/*
		 * int x=tank.getX(); int y=tank.getY(); g.setColor(Color.white);
		 * g.fill3DRect(x-20, y-20, 40, 10,false); g.fill3DRect(x-20, y+10, 40,
		 * 10,false); g.setColor(tank.getColor()); for(int i=0;i<20-1;i++){
		 * g.drawLine(x-20+(i+1)*2, y-20, x-20+(i+1)*2,y-10-1);
		 * g.drawLine(x-20+(i+1)*2, y-20+30, x-20+(i+1)*2,y-10-1+30); }
		 * g.fill3DRect(x-14, y-15, 28, 30,false); g.setColor(Color.white);
		 * g.draw3DRect(x-9, y-10, 18, 20,false); g.draw3DRect(x-5, y-3, 10,
		 * 6,false); g.drawLine(x-15, y-14, x-10, y-9); g.drawLine(x+15, y-14,
		 * x+10, y-9); g.drawLine(x-15, y+14, x-10, y+9); g.drawLine(x+15, y+14,
		 * x+10, y+9); g.setColor(tank.getColor()); g.fill3DRect(x-12, y-3, 3,
		 * 6, false); g.fill3DRect(x-20, y-1, 11, 2, false); g.fill3DRect(x-20,
		 * y-2, 2, 4, false);
		 */
		Image image;
		if (tank.getType2() == Tank.MY) {
			image = TankGameImages.myTankImg[Stuff.WEST];// 初始化图片
			g.setColor(Color.green);
		} else {
			image = TankGameImages.enemyTankImg[Stuff.WEST];
			g.setColor(Color.gray);
		}
		g.drawImage(image, tank.getX() - 20, tank.getY() - 20, 40, 40, panel);
		g.fillRect(tank.getX() - 20, tank.getY() - 30, tank.getBlood() * 4, 5);
	}

	/**
	 * 画出一个方向朝东的坦克
	 * 
	 * @param g
	 *            Graphics
	 * @param tank
	 *            东西对象
	 * @param panel
	 *            被画的那个面板
	 */
	public void drawEast(Graphics g, Stuff tank, JPanel panel) {
		/*
		 * int x=tank.getX(); int y=tank.getY(); g.setColor(Color.white);
		 * g.fill3DRect(x-20, y-20, 40, 10,false); g.fill3DRect(x-20, y+10, 40,
		 * 10,false); g.setColor(tank.getColor()); for(int i=0;i<20-1;i++){
		 * g.drawLine(x-20+(i+1)*2, y-20, x-20+(i+1)*2,y-10-1);
		 * g.drawLine(x-20+(i+1)*2, y-20+30, x-20+(i+1)*2,y-10-1+30); }
		 * g.fill3DRect(x-14, y-15, 28, 30,false); g.setColor(Color.white);
		 * g.draw3DRect(x-9, y-10, 18, 20,false); g.draw3DRect(x-5, y-3, 10,
		 * 6,false); g.drawLine(x-15, y-14, x-10, y-9); g.drawLine(x+15, y-14,
		 * x+10, y-9); g.drawLine(x-15, y+14, x-10, y+9); g.drawLine(x+15, y+14,
		 * x+10, y+9); g.setColor(tank.getColor()); g.fill3DRect(x+9, y-3, 3, 6,
		 * false); g.fill3DRect(x+9, y-1, 11, 2, false); g.fill3DRect(x+18, y-2,
		 * 2, 4, false);
		 */
		Image image;
		if (tank.getType2() == Tank.MY) {
			image = TankGameImages.myTankImg[Stuff.EAST];// 初始化图片
			g.setColor(Color.green);
		} else {
			image = TankGameImages.enemyTankImg[Stuff.EAST];
			g.setColor(Color.gray);
		}
		g.drawImage(image, tank.getX() - 20, tank.getY() - 20, 40, 40, panel);
		g.fillRect(tank.getX() - 20, tank.getY() - 30, tank.getBlood() * 4, 5);
	}

	/**
	 * 画出游戏右边的那个面板
	 * 
	 * @param g
	 *            Graphics
	 * @param tgp
	 *            游戏主要面板对象
	 */
	public void drawRight(Graphics g, GamePanel tgp) {
		for (int i = 0; i < tgp.getControl().getEnemyTankNum(); i++) {
			if (i >= 4) {
				g.drawImage(TankGameImages.enemyTankImg[Stuff.NORTH],
						402 + 50 * i, 100, 40, 40, tgp);
			} else {
				g.drawImage(TankGameImages.enemyTankImg[Stuff.NORTH],
						602 + 50 * i, 20, 40, 40, tgp);
			}
		}
		for (int j = 0; j < tgp.getControl().getMyTankNum(); j++) {
			g.drawImage(TankGameImages.myTankImg[Stuff.NORTH], 602 + 50 * j,
					400, 40, 40, tgp);
		}
		g.drawString("我的坦克子弹数目:" + tgp.getControl().getMyBulletNum(), 620, 500);
	}
}

package cn.edu.hdu.tankbattle.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cn.edu.hdu.tankbattle.control.Control;
import cn.edu.hdu.tankbattle.control.Draw;
import cn.edu.hdu.tankbattle.control.UpdateThread;
import cn.edu.hdu.tankbattle.model.GameResource;
import cn.edu.hdu.tankbattle.model.MyTank;
import cn.edu.hdu.tankbattle.model.Tank;
import cn.edu.hdu.tankbattle.model.TankGameImages;

/**
 * 游戏面板，继承自JPanel，实现KeyListener,ActionListener接口
 * <p>
 * 游戏主要类，坦克游戏就在该面板上进行
 * 
 * @author 1052067939
 *
 */
public class GamePanel extends JPanel implements KeyListener, ActionListener {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 3979366008132703255L;
	/**
	 * 游戏面板的宽度
	 */
	public final static int WIDTH = 600;
	/**
	 * 游戏面板的高度
	 */
	public final static int HEIGHT = 600;

	/**
	 * 坦克游戏画笔对象
	 */
	private Draw pen = new Draw();;

	/**
	 * 面板上的资源，坦克...等
	 */
	private GameResource resource = new GameResource();
	/**
	 * 游戏控制相关
	 */
	private Control control = new Control();

	/**
	 * 构造方法
	 */
	public GamePanel() {

		UpdateThread updateThread = new UpdateThread();
		updateThread.setPannel(this);
		new Thread(updateThread).start();
	}

	/*
	 * 重新paint
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (control.isStart() == true) {
			g.setColor(Color.black);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.fillRect(280, 600, 40, 40);
			pen.drawMap(g, resource.getMap(), this);
			pen.drawMyTank(g, resource.getMyTanks(), this); // 画出我的坦克（包括子弹）
			pen.drawEnemyTank(g, resource.getEnemys(), this); // 画出敌人坦克（包括子弹）
			pen.drawBomb(g, resource.getBombs(), this); // 画出爆炸
			pen.drawRight(g, this);

			if (this.control.getMyTankNum() == 0) { // 如果我的坦克数量为0
				g.drawImage(TankGameImages.gameOver, 250, control.getDy(), 100,
						100, this);
			}

			if (this.control.getEnemyTankNum() == 0) { // 如果敌人坦克的数量为0
				g.drawImage(TankGameImages.gameWin, 250, control.getDy(), 100,
						100, this);
			}
			if (control.getDy() == 250) {
				g.fillRect(0, 0, 800, 600);
				g.setColor(Color.BLUE);
				if (this.control.getMyTankNum() == 0) {
					g.drawString("失败了！！！", 300, 220);
				} else {
					g.drawString("挑战成功，请稍等...", 300, 220);
				}
				g.drawString(
						("敌人坦克死亡数量:" + (8 - this.control.getEnemyTankNum())),
						300, 260);
				g.drawString("我的坦克死亡总数量:" + this.control.getBeKilled(), 300,
						280);
				g.drawString(
						"我的炮弹消耗总数量:"
								+ (Control.STARTBULLETNUM - this.control
										.getMyBulletNum()), 300, 300);
				g.drawString("敌人坦克剩余数量:" + this.control.getEnemyTankNum(), 300,
						320);
				g.drawString("我的坦克剩余总数量:" + this.control.getMyTankNum(), 300,
						340);
				g.drawString("我的炮弹剩余总数量:" + this.control.getMyBulletNum(), 300,
						360);
			}
		} else {
			g.drawImage(TankGameImages.startImage, 0, 0, 800, 700, this);
			g.drawImage(TankGameImages.font, 0, control.getKy(), this);
			if (control.isIconSmile() == true) {
				g.drawImage(TankGameImages.yct_smile1, control.getKx(), 45,
						this);
				control.setIconSmile(false);
			} else {
				g.drawImage(TankGameImages.yct_smile2, control.getKx(), 45,
						this);
				control.setIconSmile(true);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		for (int i = 0; i < resource.getMyTanks().size(); i++) {
			MyTank myTank = resource.getMyTanks().get(i);

			if (myTank.isLive() == false) {
				control.setUp(false);
				control.setDown(false);
				control.setLeft(false);
				control.setRight(false);
			}

			if ((e.getKeyCode() == KeyEvent.VK_UP) && myTank.isLive()) {
				myTank.setDirect(Tank.NORTH);
				control.setUp(true);
				control.setDown(false);
				control.setLeft(false);
				control.setRight(false);
			} else if ((e.getKeyCode() == KeyEvent.VK_DOWN) && myTank.isLive()) {
				myTank.setDirect(Tank.SOUTH);
				control.setUp(false);
				control.setDown(true);
				control.setLeft(false);
				control.setRight(false);
			} else if ((e.getKeyCode() == KeyEvent.VK_LEFT) && myTank.isLive()
					&& myTank.getY() <= 580) {
				myTank.setDirect(Tank.WEST);
				control.setUp(false);
				control.setDown(false);
				control.setLeft(true);
				control.setRight(false);
			} else if ((e.getKeyCode() == KeyEvent.VK_RIGHT) && myTank.isLive()
					&& myTank.getY() <= 580) {
				myTank.setDirect(Tank.EAST);
				control.setUp(false);
				control.setDown(false);
				control.setLeft(false);
				control.setRight(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_X && myTank.isLive()
					&& myTank.getY() <= 580) {
				if (myTank.getBullets().size() <= 1
						&& this.control.getMyBulletNum() > 0) { // 最多颗子弹
					this.control
							.setMyBulletNum(this.control.getMyBulletNum() - 1);
					myTank.shot(myTank); // 这时才会往容器中添加子弹对象
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_P) { // 暂停
				control.gameEventStop(resource);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			control.setUp(false);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			control.setDown(false);
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			control.setLeft(false);
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			control.setRight(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "stop") {
			control.gameEventStop(resource);
		} else if (e.getActionCommand() == "start") { // 当点了开始游戏
			if (control.isStart() == false) { // 还没开始
				control.setStart(true);// 已经开始了
				control.startGame(resource);
				this.setVisible(true);
			} else if (control.isStop() == false
					&& this.control.getMyTankNum() != 0) {
				// 暂停
				control.gameEventStop(resource);
				JOptionPane.showMessageDialog(null, "游戏已经开始", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				// 恢复游戏
				control.gameEventStop(resource);
			} else if (control.isStop() == true) {
				JOptionPane.showMessageDialog(null, "游戏已经开始", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (control.isStop() == false
					&& (this.control.getMyTankNum() == 0)) {
				control.setStart(true);
				control.startGame(resource);
			}
		} else if (e.getActionCommand() == "exit") { // 退出按钮
			// 暂停游戏
			control.gameEventStop(resource);
			int select = JOptionPane.showConfirmDialog(null, "退出游戏吗？", "退出确认",
					JOptionPane.YES_NO_OPTION);
			if (select == JOptionPane.OK_OPTION) {
				// 退出游戏
				System.exit(0);
			} else {
				// 恢复游戏
				control.gameEventStop(resource);
			}
		} else if (e.getActionCommand() == "again") { // 重来
			if (control.isStart() == true) {
				if (control.isStop() == true) {
					// 先恢复
					control.gameEventStop(resource);
					control.startGame(resource);
				} else {
					control.startGame(resource);
				}
			}
		} else if (e.getActionCommand() == "first") {
			if (control.isStart() == true) {
				if (control.isStop() == false) {// 暂停游戏
					control.gameEventStop(resource);
				}
				int select = JOptionPane.showConfirmDialog(null,
						"您选择的是第一关，点击确定开始游戏", "选择确认",
						JOptionPane.OK_CANCEL_OPTION);
				if (select == JOptionPane.OK_OPTION) {
					if (control.isStart() == true) {
						// 恢复游戏
						control.gameEventStop(resource);
					}
					control.setStart(true);
					control.setLevel(1);
					control.startGame(resource);
					this.setVisible(true);
				} else {
					if (control.isStart() == true) {
						// 恢复游戏
						control.gameEventStop(resource);
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "请先点击游戏Game菜单下的开始游戏", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getActionCommand() == "second") {
			if (control.isStart() == true) {
				if (control.isStop() == false) {// 暂停游戏
					control.gameEventStop(resource);
				}
				int select = JOptionPane.showConfirmDialog(null,
						"您选择的是第二关，点击确定开始游戏", "选择确认",
						JOptionPane.OK_CANCEL_OPTION);
				if (select == JOptionPane.OK_OPTION) {
					if (control.isStart() == true) {
						// 恢复游戏
						control.gameEventStop(resource);
					}
					control.setStart(true);
					control.setLevel(2);
					control.startGame(resource);
					this.setVisible(true);
				} else {
					if (control.isStart() == true) {
						// 恢复游戏
						control.gameEventStop(resource);
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "请先点击游戏Game菜单下的开始游戏", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getActionCommand() == "third") {
			if (control.isStart() == true) {
				if (control.isStop() == false) {// 暂停游戏
					control.gameEventStop(resource);
				}
				int select = JOptionPane.showConfirmDialog(null,
						"您选择的是第三关，点击确定开始游戏", "选择确认",
						JOptionPane.OK_CANCEL_OPTION);
				if (select == JOptionPane.OK_OPTION) {
					if (control.isStart() == true) {
						// 恢复游戏
						control.gameEventStop(resource);
					}
					control.setStart(true);
					control.setLevel(3);
					control.startGame(resource);
					this.setVisible(true);
				} else {
					if (control.isStart() == true) {
						// 恢复游戏
						control.gameEventStop(resource);
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "请先点击游戏Game菜单下的开始游戏", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getActionCommand() == "fourth") {
			if (control.isStart() == true) {
				if (control.isStop() == false) {// 暂停游戏
					control.gameEventStop(resource);
				}
				int select = JOptionPane.showConfirmDialog(null,
						"您选择的是第四关，点击确定开始游戏", "选择确认",
						JOptionPane.OK_CANCEL_OPTION);
				if (select == JOptionPane.OK_OPTION) {
					if (control.isStart() == true) {
						// 恢复游戏
						control.gameEventStop(resource);
					}
					control.setStart(true);
					control.setLevel(4);
					control.startGame(resource);
					this.setVisible(true);
				} else {
					if (control.isStart() == true) {
						// 恢复游戏
						control.gameEventStop(resource);
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "请先点击游戏Game菜单下的开始游戏", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getActionCommand() == "fifth") {
			if (control.isStart() == true) {
				if (control.isStop() == false) {// 暂停游戏
					control.gameEventStop(resource);
				}
				int select = JOptionPane.showConfirmDialog(null,
						"您选择的是第五关，点击确定开始游戏", "选择确认",
						JOptionPane.OK_CANCEL_OPTION);
				if (select == JOptionPane.OK_OPTION) {
					if (control.isStart() == true) {
						// 恢复游戏
						control.gameEventStop(resource);
					}
					control.setStart(true);
					control.setLevel(5);
					control.startGame(resource);
					this.setVisible(true);
				} else {
					if (control.isStart() == true) {
						// 恢复游戏
						control.gameEventStop(resource);
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "请先点击游戏Game菜单下的开始游戏", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getActionCommand() == "gameHelp") {
			JOptionPane
					.showMessageDialog(
							null,
							"坦克移动:---方向键---\n坦克开炮:------X键------\n每关只要打掉敌人8辆坦克即可过关，\n第五关有5辆坦克同时在地图上",
							"游戏帮助", JOptionPane.INFORMATION_MESSAGE);
		} else if (e.getActionCommand() == "aboutGame") {
			JOptionPane.showMessageDialog(null,
					"JAVA坦克大战CP版1.0\n作者:hdu-1052067939\n", "关于游戏",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public GameResource getResource() {
		return resource;
	}

	public void setResource(GameResource resource) {
		this.resource = resource;
	}

	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
	}
}

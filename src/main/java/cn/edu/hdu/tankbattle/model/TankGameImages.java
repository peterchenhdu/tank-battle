package cn.edu.hdu.tankbattle.model;

import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;

/**
 * 坦克游戏图像类，全部要用到的图像
 * 
 * @author 1052067939
 * @version 1.0
 * @since JavaSe-1.6
 */
public class TankGameImages {
	/**
	 * 我的坦克四个方向图像数组
	 */
	public static Image myTankImg[] = {
			Toolkit.getDefaultToolkit().getImage(
					Panel.class.getResource("/images/UTank_.gif")),
			Toolkit.getDefaultToolkit().getImage(
					Panel.class.getResource("/images/DTank_.gif")),
			Toolkit.getDefaultToolkit().getImage(
					Panel.class.getResource("/images/LTank_.gif")),
			Toolkit.getDefaultToolkit().getImage(
					Panel.class.getResource("/images/RTank_.gif")) };
	/**
	 * 敌人坦克四个方向图像数组
	 */
	public static Image enemyTankImg[] = {
			Toolkit.getDefaultToolkit().getImage(
					Panel.class.getResource("/images/UTank.gif")),
			Toolkit.getDefaultToolkit().getImage(
					Panel.class.getResource("/images/DTank.gif")),
			Toolkit.getDefaultToolkit().getImage(
					Panel.class.getResource("/images/LTank.gif")),
			Toolkit.getDefaultToolkit().getImage(
					Panel.class.getResource("/images/RTank.gif")) };
	/**
	 * 障碍物图像数组
	 */
	public static Image stuffImg[] = {
			Toolkit.getDefaultToolkit().getImage(
					Panel.class.getResource("/images/brick.gif")),
			Toolkit.getDefaultToolkit().getImage(
					Panel.class.getResource("/images/iron.gif")),
			Toolkit.getDefaultToolkit().getImage(
					Panel.class.getResource("/images/water.gif")) };
	/**
	 * 游戏开始图像
	 */
	public static Image startImage = Toolkit.getDefaultToolkit().getImage(
			Panel.class.getResource("/images/gameStart.png"));
	/**
	 * 子弹图像
	 */
	public static Image bullet = Toolkit.getDefaultToolkit().getImage(
			Panel.class.getResource("/images/bullet.gif"));
	/**
	 * 爆炸图像
	 */
	public static Image bomb[] = {
			Toolkit.getDefaultToolkit().getImage(
					Panel.class.getResource("/images/bomb_1.png")),
			Toolkit.getDefaultToolkit().getImage(
					Panel.class.getResource("/images/bomb_2.png")),
			Toolkit.getDefaultToolkit().getImage(
					Panel.class.getResource("/images/bomb_3.png")),
			Toolkit.getDefaultToolkit().getImage(
					Panel.class.getResource("/images/bomb_4.png")),
			Toolkit.getDefaultToolkit().getImage(
					Panel.class.getResource("/images/bomb_5.png")), };
	/**
	 * 游戏失败时图像
	 */
	public static Image gameOver = Toolkit.getDefaultToolkit().getImage(
			Panel.class.getResource("/images/gameOver.gif"));
	/**
	 * 胜利时的图像
	 */
	public static Image gameWin = Toolkit.getDefaultToolkit().getImage(
			Panel.class.getResource("/images/gameWin.gif"));
	/**
	 * 洋葱头 笑
	 */
	public static Image yct_smile1 = Toolkit.getDefaultToolkit().getImage(
			Panel.class.getResource("/images/yct1.png"));
	public static Image yct_smile2 = Toolkit.getDefaultToolkit().getImage(
			Panel.class.getResource("/images/yct2.png"));
	public static Image font = Toolkit.getDefaultToolkit().getImage(
			Panel.class.getResource("/images/font.png"));
}

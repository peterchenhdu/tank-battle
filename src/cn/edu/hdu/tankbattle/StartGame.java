package cn.edu.hdu.tankbattle;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import cn.edu.hdu.tankbattle.view.GameFrame;

/**
 * 
 * @author Pi Chen
 * @version tankbattle V1.0.0, 2016年11月27日
 * @see
 * @since tankbattle V1.0.0
 */
public class StartGame {

	public static void main(String[] args) {

		GameFrame gameFrame = new GameFrame();
		gameFrame.setSize(800, 700);
		gameFrame.setTitle("MyTankGame");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		gameFrame.setVisible(true);

		// 显示器屏幕大小
		Dimension screenSizeInfo = Toolkit.getDefaultToolkit().getScreenSize();
		int leftTopX = ((int) screenSizeInfo.getWidth() - gameFrame.getWidth()) / 2;
		int leftTopY = ((int) screenSizeInfo.getHeight() - gameFrame
				.getHeight()) / 2;

		// 设置显示的位置在屏幕中间
		gameFrame.setLocation(leftTopX, leftTopY);
	}
}

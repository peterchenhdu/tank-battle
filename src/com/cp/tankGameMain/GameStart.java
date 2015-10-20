package com.cp.tankGameMain;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.apache.log4j.Logger;

import com.cp.common.constants.ParameterConstant;
import com.cp.tankGamePanel.TankGamePanel;


/**
 * 游戏主函数
 * 第一次修改2011.1.1
 * 初步完成
 * 第二次修改2011.1.30
 * 让窗口显示在屏幕中间
 * @author 1052067939
 * @version 1.0 日期
 *
 */
public class GameStart extends JFrame{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(GameStart.class);
	/**
	 * 菜单条
	 */
	private JMenuBar 		jMenuBar;					//菜单条
	/**
	 * 菜单		游戏
	 */
	private JMenu 			game;
	/**
	 * 开始
	 */
	private JMenuItem 		gameStart;
	/**
	 * 重新开始
	 */
	private JMenuItem		startAgain;
	/**
	 * 停止
	 */
	private JMenuItem 		gameStop;
	/**
	 * 退出游戏
	 */
	private JMenuItem 		gameExit;
	/**
	 * 选择关卡菜单
	 */
	private JMenu 			selectLevel ;
	private JMenuItem		firstLevel;
	private JMenuItem		secondLevel;
	private JMenuItem		thirdLevel;
	private JMenuItem		fourthLevel;
	private JMenuItem		fifthLevel;
	/**
	 * 帮助菜单
	 */
	private JMenu 			help ;
	private JMenuItem		gameHelp;
	private JMenuItem		aboutGame;
	/**
	 * 游戏面板
	 */
	private TankGamePanel 	tankGamePanel;

	private Dimension  		dim;
	/**
	 * 构造函数
	 */
	public GameStart() {
		dim 		= 		Toolkit.getDefaultToolkit().getScreenSize(); 
		tankGamePanel = new TankGamePanel();
		jMenuBar	=new 	JMenuBar();
		game		=new 	JMenu("游戏/Game");
		gameStart	=new 	JMenuItem("开始游戏");
		startAgain	=new 	JMenuItem("重新开始");
		gameStop	=new	JMenuItem("暂停/恢复");
		gameExit	=new 	JMenuItem("退出游戏");
		selectLevel	=new 	JMenu("选择关卡");
		firstLevel	=new	JMenuItem("第一关");
		secondLevel	=new	JMenuItem("第二关");
		thirdLevel	=new	JMenuItem("第三关");
		fourthLevel	=new	JMenuItem("第四关");
		fifthLevel	=new	JMenuItem("第五关");
		help		=new 	JMenu("游戏帮助");
		gameHelp	=new	JMenuItem("游戏帮助");
		aboutGame	=new	JMenuItem("关于游戏");
		
		gameStart.setActionCommand("start");
		startAgain.setActionCommand("again");
		gameStop.setActionCommand("stop");
		gameExit.setActionCommand("exit");
		firstLevel.setActionCommand("first");
		secondLevel.setActionCommand("second");
		thirdLevel.setActionCommand("third");
		fourthLevel.setActionCommand("fourth");
		fifthLevel.setActionCommand("fifth");
		gameHelp.setActionCommand("gameHelp");
		aboutGame.setActionCommand("aboutGame");
		gameStart.addActionListener(tankGamePanel);
		startAgain.addActionListener(tankGamePanel);
		gameExit.addActionListener(tankGamePanel);
		firstLevel.addActionListener(tankGamePanel);
		secondLevel.addActionListener(tankGamePanel);
		thirdLevel.addActionListener(tankGamePanel);
		fourthLevel.addActionListener(tankGamePanel);
		fifthLevel.addActionListener(tankGamePanel);
		gameStop.addActionListener(tankGamePanel);		//游戏面板作为监听者
		gameHelp.addActionListener(tankGamePanel);
		aboutGame.addActionListener(tankGamePanel);
		this.addKeyListener(tankGamePanel);				//游戏面板来作为按键侦听器
		this.setJMenuBar(jMenuBar);
		//--游戏/Game--菜单
		jMenuBar.add(game);
		game.add(gameStart);
		game.add(startAgain);
		game.add(gameStop);
		game.add(gameExit);
		
		//--选择关卡--菜单
		jMenuBar.add(selectLevel);
		selectLevel.add(firstLevel);
		selectLevel.add(secondLevel);
		selectLevel.add(thirdLevel);
		selectLevel.add(fourthLevel);
		selectLevel.add(fifthLevel);
		
		//--游戏帮助--菜单
		jMenuBar.add(help);
		help.add(gameHelp);
		help.add(aboutGame);
		
		this.add(tankGamePanel);
		this.setSize(ParameterConstant.WINDOW_WIDTH, ParameterConstant.WINDOW_HEIGHT);
		this.setTitle("MyTankGame");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		//设置显示的位置在屏幕中间
		this.setLocation(((int)dim.getWidth()-this.getWidth())/2,((int)dim.getHeight()-this.getHeight())/2);
	}
	public static void main(String args[]){  
		logger.info("start game.");		
		new GameStart();
		
	}

}
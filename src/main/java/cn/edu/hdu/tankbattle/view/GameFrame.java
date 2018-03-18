package cn.edu.hdu.tankbattle.view;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * 游戏Frame
 * 
 * @author 1052067939
 * @version 1.0
 *
 */
public class GameFrame extends JFrame {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -1176914786963603304L;
	/**
	 * 菜单条
	 */
	private JMenuBar jMenuBar;
	/**
	 * 一级菜单，游戏
	 */
	private JMenu game;
	/**
	 * 二级菜单，开始
	 */
	private JMenuItem gameStart;
	/**
	 * 二级菜单，重新开始
	 */
	private JMenuItem startAgain;
	/**
	 * 二级菜单，停止
	 */
	private JMenuItem gameStop;
	/**
	 * 二级菜单，退出游戏
	 */
	private JMenuItem gameExit;
	/**
	 * 一级菜单，选择关卡
	 */
	private JMenu selectLevel;
	private JMenuItem firstLevel;
	private JMenuItem secondLevel;
	private JMenuItem thirdLevel;
	private JMenuItem fourthLevel;
	private JMenuItem fifthLevel;
	/**
	 * 帮助一级菜单
	 */
	private JMenu help;
	private JMenuItem gameHelp;
	private JMenuItem aboutGame;
	/**
	 * 游戏面板
	 */
	private GamePanel tankGamePanel;

	/**
	 * 构造函数，初始化相关信息
	 */
	public GameFrame() {

		tankGamePanel = new GamePanel();

		// 菜单相关
		jMenuBar = new JMenuBar();

		game = new JMenu("游戏/Game");
		gameStart = new JMenuItem("开始游戏");
		startAgain = new JMenuItem("重新开始");
		gameStop = new JMenuItem("暂停/恢复");
		gameExit = new JMenuItem("退出游戏");

		selectLevel = new JMenu("选择关卡");
		firstLevel = new JMenuItem("第一关");
		secondLevel = new JMenuItem("第二关");
		thirdLevel = new JMenuItem("第三关");
		fourthLevel = new JMenuItem("第四关");
		fifthLevel = new JMenuItem("第五关");

		help = new JMenu("游戏帮助");
		gameHelp = new JMenuItem("游戏帮助");
		aboutGame = new JMenuItem("关于游戏");

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

		// 添加一系列的listener
		gameStart.addActionListener(tankGamePanel);
		startAgain.addActionListener(tankGamePanel);
		gameExit.addActionListener(tankGamePanel);
		firstLevel.addActionListener(tankGamePanel);
		secondLevel.addActionListener(tankGamePanel);
		thirdLevel.addActionListener(tankGamePanel);
		fourthLevel.addActionListener(tankGamePanel);
		fifthLevel.addActionListener(tankGamePanel);
		gameStop.addActionListener(tankGamePanel); // 游戏面板作为监听者
		gameHelp.addActionListener(tankGamePanel);
		aboutGame.addActionListener(tankGamePanel);
		this.addKeyListener(tankGamePanel); // 游戏面板来作为按键侦听器

		// 菜单条
		this.setJMenuBar(jMenuBar);

		// --游戏/Game--菜单
		jMenuBar.add(game);
		game.add(gameStart);
		game.add(startAgain);
		game.add(gameStop);
		game.add(gameExit);

		// --选择关卡--菜单
		jMenuBar.add(selectLevel);
		selectLevel.add(firstLevel);
		selectLevel.add(secondLevel);
		selectLevel.add(thirdLevel);
		selectLevel.add(fourthLevel);
		selectLevel.add(fifthLevel);

		// --游戏帮助--菜单
		jMenuBar.add(help);
		help.add(gameHelp);
		help.add(aboutGame);

		// 将panel添加到Frame
		this.add(tankGamePanel);

	}

}
package com.cp.tankGamePanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.cp.tankGameControl.TankGameControl;
import com.cp.tankGameMap.Map;
import com.cp.tankGameMap.Map2;
import com.cp.tankGameMap.Map3;
import com.cp.tankGameMap.Map4;
import com.cp.tankGameMap.Map5;
import com.cp.tankGameMap.Map1;
import com.cp.tankGameObject.Bomb;
import com.cp.tankGameObject.EnemyTank;
import com.cp.tankGameObject.MyTank;
import com.cp.tankGameObject.Tank;
import com.cp.tankGameObject.TankGameImages;
import com.cp.tankGameObject.TankGamePen;
/**
 * 游戏面板，继承自JPanel，实现KeyListener,Runnable,ActionListener接口
 * <p>
 * 游戏主要类，坦克游戏就在该面板上进行
 * @author 1052067939
 *
 */
public class TankGamePanel extends JPanel implements KeyListener,Runnable,ActionListener{
	private static final long serialVersionUID = 1L;
	/**
	 * 游戏面板的宽度
	 */
	public final static int WIDTH=600;
	/**
	 * 游戏面板的高度
	 */
	public final static int HEIGHT=600;
	/**
	 * 是否已经开始
	 */
	private boolean isStart	=	false;
	/**
	 * 是否按了向上的方向键
	 */
	private boolean up=false;
	/**
	 * 是否按了向下的方向键
	 */
	private boolean down=false;
	/**
	 * 是否按了向左的方向键
	 */
	private boolean left=false;
	/**
	 * 是否按了向右的方向键
	 */
	private boolean right=false;
	/**
	 * 游戏关卡
	 */
	private int 	level=1;
	/**
	 * 我的坦克容量
	 */
	private Vector<MyTank> 		myTanks;	
	/**
	 *敌人坦克容量
	 */
	private Vector<EnemyTank> 	enemys;								//敌人坦克组
	/**
	 * 炸弹容量
	 */
	private Vector<Bomb> 		bombs;								//炸弹集合
	/**
	 * 坦克游戏画笔对象
	 */
	private TankGamePen 		pen;								//一只坦克游戏画笔
	/**
	 * 坦克游戏地图对象
	 */
	private Map  map;
	/**
	 * 游戏开始的那个欢迎图片标签
	 */
	private JLabel 			startImage;
	/**
	 * 坦克游戏控制对象
	 */
	private TankGameControl	tgc;
	/**
	 * 游戏失败或成功时的图片的y坐标
	 */
	private int dy=600;
	/**
	 * 是否暂停
	 */
	private boolean isStop=false; 
	private Thread thread;
	private int k=0;
	private int ky=600;
	private int kx=0;
	/**
	 * 构造方法
	 */
	public TankGamePanel() {	
		this.initGame();
		thread=new Thread(this);
		thread.start();
	}
	/*
	 * 重新paint
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(this.isStart==true){
			g.setColor(Color.black);
			g.fillRect(0, 0, WIDTH, HEIGHT);	
			g.fillRect(280, 600, 40, 40);
			pen.drawMap(g, map,this);
			pen.drawMyTank(g, myTanks,this);			//画出我的坦克（包括子弹）
			pen.drawEnemyTank(g, enemys,this);			//画出敌人坦克（包括子弹）
			pen.drawBomb(g, bombs, this);				//画出爆炸		
			pen.drawRight(g, this);
		
			if(this.tgc.getMyTankNum()==0){				//如果我的坦克数量为0
				g.drawImage(TankGameImages.gameOver,250, dy, 100, 100, this);
			}
			
			if(this.tgc.getEnemyTankNum()==0){			//如果敌人坦克的数量为0
				g.drawImage(TankGameImages.gameWin,250, dy, 100, 100, this);
			}
			if(dy==250){
				g.fillRect(0, 0, 800, 600);
				g.setColor(Color.BLUE);
				if(this.tgc.getMyTankNum()==0){
					g.drawString("很遗憾，你的技术太菜了！！！", 300, 220);
				}else{
					g.drawString("不错啊，请稍等，更艰巨的任务即将到来", 300, 220);
				}
				g.drawString(("这关敌人坦克死亡数量:"+(8-this.tgc.getEnemyTankNum())), 300, 260);
				g.drawString("我的坦克死亡总数量:"+this.tgc.getBeKilled(), 300, 280);
				g.drawString("我的炮弹消耗总数量:"+(TankGameControl.STARTBULLETNUM-this.tgc.getMyBulletNum()), 300, 300);
				g.drawString("这关敌人坦克剩余数量:"+this.tgc.getEnemyTankNum(), 300, 320);
				g.drawString("我的坦克剩余总数量:"+this.tgc.getMyTankNum(), 300, 340);
				g.drawString("我的炮弹剩余总数量:"+this.tgc.getMyBulletNum(), 300, 360);
			}
		}else{
			g.drawImage(TankGameImages.startImage, 0, 0, 800, 700, this);
			g.drawImage(TankGameImages.font, 0, ky,  this);
			if(k==0){
				g.drawImage(TankGameImages.yct_smile1, kx, 45,  this);
				k=1;
			}else{
				g.drawImage(TankGameImages.yct_smile2, kx, 45,  this);
				k=0;
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		for(int i=0;i<myTanks.size();i++){
			MyTank myTank=myTanks.get(i);
			if(myTank.isLive()==false) up=down=left=right=false;
			if((e.getKeyCode()==KeyEvent.VK_UP)&&myTank.isLive()){
				myTank.setDirect(Tank.NORTH);
				up=true;
				down=false;
				left=false;
				right=false;
			}else if((e.getKeyCode()==KeyEvent.VK_DOWN)&&myTank.isLive()){
				myTank.setDirect(Tank.SOUTH);
				down=true;
				up=false;
				left=false;
				right=false;
			}else if((e.getKeyCode()==KeyEvent.VK_LEFT)&&myTank.isLive()&&myTank.getY()<=580){
				myTank.setDirect(Tank.WEST);
				left=true;
				up=false;
				down=false;
				right=false;
			}else if((e.getKeyCode()==KeyEvent.VK_RIGHT)&&myTank.isLive()&&myTank.getY()<=580){
				myTank.setDirect(Tank.EAST);
				right=true;	
				up=false;
				down=false;
				left=false;
			}	
			if(e.getKeyCode()==KeyEvent.VK_X&&myTank.isLive()&&myTank.getY()<=580){
				if(myTank.getBullets().size()<=1&&this.tgc.getMyBulletNum()>0){			//最多颗子弹
					this.tgc.setMyBulletNum(this.tgc.getMyBulletNum()-1);
					myTank.shot(myTank);                	//这时才会往容器中添加子弹对象
					}
				}
			if(e.getKeyCode()==KeyEvent.VK_P){				//暂停	
				this.gameEventStop(myTanks, enemys);
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_UP){
			up=false;
		}else if(e.getKeyCode()==KeyEvent.VK_DOWN){
			down=false;
		}else if(e.getKeyCode()==KeyEvent.VK_LEFT){
			left=false;
		}else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			right=false;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {

	}
	@Override
	public void run() {
			//每隔30毫秒重画
			while(true){
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(this.isStart==true){
				if((this.tgc.getMyTankNum()==0||this.tgc.getEnemyTankNum()==0)&&dy>250){
					dy=dy-2;
				}
				if(dy==250){
					this.repaint();
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(this.level==5){
						this.level=0;
					}
					if(this.tgc.getMyTankNum()>=1&&this.level<=4){
						this.level++;
						dy=600;
						this.nextGame();
					}
				}
				if(this.isStop==false&&dy==600){
				tgc.cleanAndCreat(myTanks, enemys,map,bombs);					//从容器中移除死亡的对象
				tgc.judge(myTanks, enemys,map, bombs);							//判断子弹是否击中坦克
				tgc.judgeOverlap(myTanks, enemys,map);							//判断坦克间是否出现重叠
				this.myTankEvent(myTanks);

				}
				}else{
					if(ky==21&&kx<=654) kx=kx+2;
					this.fontMove();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				this.repaint();
			}
	}

	/**
	 * 我的坦克事件，观察我按了什么键
	 * @param myTanks	我的坦克容量
	 */
	public void myTankEvent(Vector<MyTank> myTanks){
		for(int i=0;i<myTanks.size();i++){
			MyTank myTank=myTanks.get(i);
			if(up==true&&myTank.isOverlapNo()==false&&myTank.isOverlapYes()==false){
				myTank.goNorth();
			}else if(down==true&&myTank.isOverlapNo()==false&&myTank.isOverlapYes()==false){
				myTank.goSouth();
			}else if(left==true&&myTank.isOverlapNo()==false&&myTank.isOverlapYes()==false){
				myTank.goWest();
			}else if(right==true&&myTank.isOverlapNo()==false&&myTank.isOverlapYes()==false){
				myTank.goEast();
			}
		}
	}
	/**
	 * 游戏初始化
	 */
	public void initGame(){
		enemys=new Vector<EnemyTank>();
		myTanks=new Vector<MyTank>();
		bombs=new Vector<Bomb>();
		pen=new TankGamePen();
		tgc=new TankGameControl();				//创建一个坦克游戏控制对象
	}
	/**
	 * 下一关
	 */
	public void nextGame(){
		if(level==1){					//游戏关卡
			map=new Map1();
		}else if(level==2){
			map=new Map2();
		}else if(level==3){
			map=new Map3();
		}else if(level==4){
			map=new Map4();
		}else if(level==5){
			map=new Map5();
		}
		for(int i=0;i<5;i++){
			EnemyTank enemy=new EnemyTank((i)*140+20, -20, Tank.SOUTH);		//创建一个敌人坦克对象
			enemy.setLocation(i);
			enemys.add(enemy);												//将该坦克加入敌人坦克容器中								//将该子弹加入该坦克的子弹容器中
		}
		this.tgc.setEnemyTankNum(8);
		for(int i=0;i<myTanks.size();i++){
			MyTank myTank=myTanks.get(i);				
			myTank.setX(300);
			myTank.setY(620);
		}	
	}
	/**
	 * 开始游戏
	 */
	public void startGame(){
		enemys=new Vector<EnemyTank>();
		myTanks=new Vector<MyTank>();
		bombs=new Vector<Bomb>();
		pen=new TankGamePen();
		tgc=new TankGameControl();				//创建一个坦克游戏控制对象
		dy=600;
		if(level==1){					//游戏关卡
			map=new Map1();
		}else if(level==2){
			map=new Map2();
		}else if(level==3){
			map=new Map3();
		}else if(level==4){
			map=new Map4();
		}else if(level==5){
			map=new Map5();
		}
		for(int i=0;i<5;i++){
			EnemyTank enemy=new EnemyTank((i)*140+20, -20, Tank.SOUTH);		//创建一个敌人坦克对象
			enemy.setLocation(i);
			enemys.add(enemy);												//将该坦克加入敌人坦克容器中								//将该子弹加入该坦克的子弹容器中
		}
		for(int i=0;i<1;i++){
			MyTank myTank=new MyTank(300, 620,Tank.NORTH);					//创建一个我的坦克
			myTanks.add(myTank);											//将我的坦克加入我的坦克容器中
		}							
	}
	public Vector<EnemyTank> getEnemys() {
		return enemys;
	}
	public void setEnemys(Vector<EnemyTank> enemys) {
		this.enemys = enemys;
	}
	public Vector<Bomb> getBombs() {
		return bombs;
	}
	public void setBombs(Vector<Bomb> bombs) {
		this.bombs = bombs;
	}
	public Map getMap() {
		return map;
	}
	/**
	 * 游戏暂停
	 * @param myTank	我的坦克
	 * @param enemys	敌人坦克容量
	 */
	public void gameEventStop(Vector<MyTank> myTanks,Vector<EnemyTank> enemys){
		for(int i=0;i<myTanks.size();i++){
			MyTank myTank=myTanks.get(i);
			if(myTank.getSpeedVector()==0){							//已经暂停的不能在执行下面的语句
				this.setStop(true);
				myTank.setSpeedVector(myTank.getSpeed());			//保存当前坦克的速度
				myTank.setSpeed(0);									//设置当前坦克速度为0
				for(int j=0;j<myTank.getBullets().size();j++){
					myTank.getBullets().get(j).setSpeedVector(myTank.getBullets().get(j).getSpeed());
					myTank.getBullets().get(j).setSpeed(0);			//设置子弹为0
				}
				for(int j=0;j<enemys.size();j++){
					enemys.get(j).setSpeedVector(enemys.get(j).getSpeed());
					enemys.get(j).setSpeed(0);
					for(int k=0;k<enemys.get(j).getBullets().size();k++){
						enemys.get(j).getBullets().get(k).setSpeedVector(enemys.get(j).getBullets().get(k).getSpeed());
						enemys.get(j).getBullets().get(k).setSpeed(0);
					}
				}
			}else{
				this.setStop(false);
				myTank.setSpeed(myTank.getSpeedVector());
				myTank.setSpeedVector(0);
				for(int j=0;j<myTank.getBullets().size();j++){
					myTank.getBullets().get(j).setSpeed(myTank.getBullets().get(j).getSpeedVector());
				}
				for(int j=0;j<enemys.size();j++){
					enemys.get(j).setSpeed(enemys.get(j).getSpeedVector());
					enemys.get(j).setSpeedVector(0);
					for(int k=0;k<enemys.get(j).getBullets().size();k++){
						enemys.get(j).getBullets().get(k).setSpeed(enemys.get(j).getBullets().get(k).getSpeedVector());
					}
				}
			}
		}
	}
	public void setMap(Map map) {
		this.map = map;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="stop"){
			this.gameEventStop(myTanks, enemys);
		}else if(e.getActionCommand()=="start"){						//当点了开始游戏
			if(this.isStart==false){							//还没开始
				this.isStart=true;								//已经开始了
				this.startGame();
				this.setVisible(true);
			}else if(this.isStop==false&&this.tgc.getMyTankNum()!=0){
				//暂停
				this.gameEventStop(myTanks, enemys);	
				JOptionPane.showMessageDialog(null, "大哥，你已经开始游戏了", "提示", JOptionPane.INFORMATION_MESSAGE);  
				//恢复游戏
				this.gameEventStop(myTanks, enemys);
			}else if(this.isStop==true){
				JOptionPane.showMessageDialog(null, "大哥，你已经开始游戏了", "提示", JOptionPane.INFORMATION_MESSAGE);  
			}else if(this.isStop==false&&(this.tgc.getMyTankNum()==0)){
				this.isStart=true;	
				this.startGame();
			}
		}else if(e.getActionCommand()=="exit"){					//退出按钮
			//暂停游戏
			this.gameEventStop(myTanks, enemys);
			int select=JOptionPane.showConfirmDialog(null, "真的要退出游戏吗？", "退出确认", JOptionPane.YES_NO_OPTION);
			if(select==JOptionPane.OK_OPTION){
				//退出游戏
				System.exit(0);
			}else{
				//恢复游戏
				this.gameEventStop(myTanks, enemys);
			}
		}else if(e.getActionCommand()=="again"){				//重来
			if(this.isStart==true){
				if(this.isStop==true){
					//先恢复
					this.gameEventStop(myTanks, enemys);
					this.startGame();
				}else{
					this.startGame();
				}
			}
		}else if(e.getActionCommand()=="first"){
			if(this.isStart==true){
			if(this.isStop==false){//暂停游戏
				this.gameEventStop(myTanks, enemys);
			}
			int select=JOptionPane.showConfirmDialog(null, "您选择的是第一关，点击确定开始游戏", "选择确认", JOptionPane.OK_CANCEL_OPTION);
			if(select==JOptionPane.OK_OPTION){						
				if(this.isStart==true){
					//恢复游戏
					this.gameEventStop(myTanks, enemys);
				}
				this.isStart=true;	
				this.setLevel(1);
				this.startGame();
				this.setVisible(true);
			}else{
				if(this.isStart==true){
					//恢复游戏
					this.gameEventStop(myTanks, enemys);
				}
			}
			}else{
				JOptionPane.showMessageDialog(null, "大哥，别急，你还没点游戏Game菜单下的开始游戏呐", "提示", JOptionPane.INFORMATION_MESSAGE);  
			}
		}else if(e.getActionCommand()=="second"){
			if(this.isStart==true){
			if(this.isStop==false){//暂停游戏
				this.gameEventStop(myTanks, enemys);
			}
			int select=JOptionPane.showConfirmDialog(null, "您选择的是第二关，点击确定开始游戏", "选择确认", JOptionPane.OK_CANCEL_OPTION);
			if(select==JOptionPane.OK_OPTION){						
				if(this.isStart==true){
					//恢复游戏
					this.gameEventStop(myTanks, enemys);
				}
				this.isStart=true;	
				this.setLevel(2);
				this.startGame();
				this.setVisible(true);
			}else{
				if(this.isStart==true){
					//恢复游戏
					this.gameEventStop(myTanks, enemys);	
				}
			}
			}else{
				JOptionPane.showMessageDialog(null, "大哥，别急，你还没点游戏Game菜单下的开始游戏呐", "提示", JOptionPane.INFORMATION_MESSAGE);  
			}
		}else if(e.getActionCommand()=="third"){
			if(this.isStart==true){
			if(this.isStop==false){//暂停游戏
				this.gameEventStop(myTanks, enemys);
			}
			int select=JOptionPane.showConfirmDialog(null, "您选择的是第三关，点击确定开始游戏", "选择确认", JOptionPane.OK_CANCEL_OPTION);
			if(select==JOptionPane.OK_OPTION){						
				if(this.isStart==true){
					//恢复游戏
					this.gameEventStop(myTanks, enemys);	
				}
				this.isStart=true;	
				this.setLevel(3);
				this.startGame();
				this.setVisible(true);
			}else{
				if(this.isStart==true){
					//恢复游戏
					this.gameEventStop(myTanks, enemys);
				}
			}
			}else{
				JOptionPane.showMessageDialog(null, "大哥，别急，你还没点游戏Game菜单下的开始游戏呐", "提示", JOptionPane.INFORMATION_MESSAGE);  
			}
		}else if(e.getActionCommand()=="fourth"){
			if(this.isStart==true){
			if(this.isStop==false){//暂停游戏
				this.gameEventStop(myTanks, enemys);
			}
			int select=JOptionPane.showConfirmDialog(null, "您选择的是第四关，点击确定开始游戏", "选择确认", JOptionPane.OK_CANCEL_OPTION);
			if(select==JOptionPane.OK_OPTION){						
				if(this.isStart==true){
					//恢复游戏
					this.gameEventStop(myTanks, enemys);	
					}
				this.isStart=true;	
				this.setLevel(4);
				this.startGame();
				this.setVisible(true);
			}else{
				if(this.isStart==true){
					//恢复游戏
					this.gameEventStop(myTanks, enemys);
				}
			}
			}else{
				JOptionPane.showMessageDialog(null, "大哥，别急，你还没点游戏Game菜单下的开始游戏呐", "提示", JOptionPane.INFORMATION_MESSAGE);  
			}
		}else if(e.getActionCommand()=="fifth"){
			if(this.isStart==true){
			if(this.isStop==false){//暂停游戏
				this.gameEventStop(myTanks, enemys);
			}
			int select=JOptionPane.showConfirmDialog(null, "您选择的是第五关，点击确定开始游戏", "选择确认", JOptionPane.OK_CANCEL_OPTION);
			if(select==JOptionPane.OK_OPTION){						
				if(this.isStart==true){
					//恢复游戏
					this.gameEventStop(myTanks, enemys);	
					}
				this.isStart=true;	
				this.setLevel(5);
				this.startGame();
				this.setVisible(true);
			}else{
				if(this.isStart==true){
					//恢复游戏
					this.gameEventStop(myTanks, enemys);
				}
			}
			}else{
				JOptionPane.showMessageDialog(null, "大哥，别急，你还没点游戏Game菜单下的开始游戏呐", "提示", JOptionPane.INFORMATION_MESSAGE);  
			}
		}else if(e.getActionCommand()=="gameHelp"){
			JOptionPane.showMessageDialog(null, "坦克移动:---方向键---\n坦克开炮:------X键------\n每关只要打掉敌人8辆坦克即可过关，\n第五关有5辆坦克同时在地图上哦", "游戏帮助", JOptionPane.INFORMATION_MESSAGE); 
		}else if(e.getActionCommand()=="aboutGame"){
			JOptionPane.showMessageDialog(null, "JAVA坦克大战CP版1.0\n作者:杭电1052067939\nO(∩_∩)O哈哈哈~", "关于游戏", JOptionPane.INFORMATION_MESSAGE); 
		}
	}
	public TankGameControl getTgc() {
		return tgc;
	}
	public void setTgc(TankGameControl tgc) {
		this.tgc = tgc;
	}
	public boolean isStop() {
		return isStop;
	}
	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * 游戏没开始时的欢迎图片上的字体移动
	 */
	public void fontMove(){
		if(ky>0&&ky!=21) ky=ky-8;		//当字体刚出来时，向上移动
		if(ky==0){						//当字体移到顶端时，以7的速度向下移动5步
			for(int i=0;i<5;i++){
				ky=ky+7;
				this.repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(int i=0;i<4;i++){		//以6的速度向上移动4步
				ky=ky-6;
				this.repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(int i=0;i<3;i++){		//以5的速度向下移动
				ky=ky+5;
				this.repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(int i=0;i<2;i++){	//以4的速度向上移动
				ky=ky-4;
				this.repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			ky=ky+3;			//最后，字体的图片停留在（0，21）的地方
		}
	}
}



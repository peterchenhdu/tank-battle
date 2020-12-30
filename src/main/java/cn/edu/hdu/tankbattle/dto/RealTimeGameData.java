/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.dto;

import cn.edu.hdu.tankbattle.entity.Bomb;
import cn.edu.hdu.tankbattle.entity.EnemyTank;
import cn.edu.hdu.tankbattle.entity.MyTank;
import cn.edu.hdu.tankbattle.enums.LevelEnum;
import cn.edu.hdu.tankbattle.enums.StuffTypeEnum;
import cn.edu.hdu.tankbattle.resource.map.Map;

import java.util.Vector;

/**
 * 实时游戏数据...
 *
 * @author chenpi
 * @since 2018/3/20 23:01
 */
public class RealTimeGameData {

    /**
     * 我方坦克
     */
    private Vector<MyTank> myTanks = new Vector<>();
    /**
     * 敌方坦克
     */
    private Vector<EnemyTank> enemies = new Vector<>();
    /**
     * 炸弹
     */
    private Vector<Bomb> bombs = new Vector<>();

    /**
     * 当前游戏模式：是否是地图编辑模式
     */
    private Boolean mapMakingMode = Boolean.FALSE;
    /**
     * 地图编辑模式时，当前选中的物体
     */
    private StuffTypeEnum currentSelectedStuff = StuffTypeEnum.BRICK;

    /**
     * 当前游戏实时地图数据
     */
    private Map map;

    /**
     * 敌方坦克数量
     */
    private int enemyTankNum;
    /**
     * 我方坦克数量
     */
    private int myTankNum;
    /**
     * 我的坦克死亡总数量
     */
    private int beKilled;

    /**
     * 我方坦克当前剩余子弹数量
     */
    private int myBulletNum;

    /**
     * 游戏开始标志
     */
    private boolean isStart = false;

    /**
     * 游戏暂停标志
     */
    private boolean isStop = false;
    /**
     * 游戏退出标志
     */
    private boolean isExit = false;

    /**
     * 当前上下左右按键状态
     */
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;

    /**
     * 当前关卡
     */
    private LevelEnum level = LevelEnum.FIRST_LEVEL;

    /**
     * 是否要画图标标志
     */
    private boolean iconSmile;

    /**
     * 游戏失败或成功图标y坐标（起始位置），动态的
     */
    private int dy = 600;

    /**
     * 游戏启动画面："java坦克大战文字" y坐标（起始位置），动态的
     */
    private int ky = 600;
    /**
     * 游戏启动画面：笑脸x坐标（起始位置），动态的
     */
    private int kx = 0;

    public void keyPressedDirect(Boolean up, Boolean down, Boolean left, Boolean right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    /**
     * 清空数据，释放资源
     */
    public void clear() {
        enemies.forEach(e -> e.setLive(Boolean.FALSE));
        myTanks.clear();
        enemies.clear();
        bombs.clear();
        map = null;
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

    public int getBeKilled() {
        return beKilled;
    }

    public void setBeKilled(int beKilled) {
        this.beKilled = beKilled;
    }

    public int getMyBulletNum() {
        return myBulletNum;
    }

    public void setMyBulletNum(int myBulletNum) {
        this.myBulletNum = myBulletNum;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
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

    public LevelEnum getLevel() {
        return level;
    }

    public void setLevel(LevelEnum level) {
        this.level = level;
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

    public Vector<MyTank> getMyTanks() {
        return myTanks;
    }

    public void setMyTanks(Vector<MyTank> myTanks) {
        this.myTanks = myTanks;
    }

    public Vector<EnemyTank> getEnemies() {
        return enemies;
    }

    public void setEnemies(Vector<EnemyTank> enemies) {
        this.enemies = enemies;
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

    public void setMap(Map map) {
        this.map = map;
    }

    public boolean isExit() {
        return isExit;
    }

    public void setExit(boolean exit) {
        isExit = exit;
    }

    @Override
    public String toString() {
        return "RealTimeGameData{" +
                ", enemyTankNum=" + enemyTankNum +
                ", myTankNum=" + myTankNum +
                ", beKilled=" + beKilled +
                ", myBulletNum=" + myBulletNum +
                ", isStart=" + isStart +
                ", isStop=" + isStop +
                ", up=" + up +
                ", down=" + down +
                ", left=" + left +
                ", right=" + right +
                ", level=" + level +
                ", iconSmile=" + iconSmile +
                ", dy=" + dy +
                ", ky=" + ky +
                ", kx=" + kx +
                '}';
    }

    public StuffTypeEnum getCurrentSelectedStuff() {
        return currentSelectedStuff;
    }

    public void setCurrentSelectedStuff(StuffTypeEnum currentSelectedStuff) {
        this.currentSelectedStuff = currentSelectedStuff;
    }

    public Boolean getMapMakingMode() {
        return mapMakingMode;
    }

    public void setMapMakingMode(Boolean mapMakingMode) {
        this.mapMakingMode = mapMakingMode;
    }
}

/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.dto;

import cn.edu.hdu.tankbattle.entity.Bomb;
import cn.edu.hdu.tankbattle.entity.EnemyTank;
import cn.edu.hdu.tankbattle.entity.MyTank;
import cn.edu.hdu.tankbattle.enums.StuffTypeEnum;
import cn.edu.hdu.tankbattle.resource.map.Map;

import java.util.Vector;

/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/3/20 23:01
 */
public class RealTimeGameData {
    /**
     * 我的坦克容量
     */
    private Vector<MyTank> myTanks = new Vector<>();
    /**
     * 敌人坦克容量
     */
    private Vector<EnemyTank> enemies = new Vector<>();
    /**
     * 炸弹容量
     */
    private Vector<Bomb> bombs = new Vector<>();

    private Boolean mapMakingFlag = Boolean.FALSE;
    private StuffTypeEnum currentStiff = StuffTypeEnum.BRICK;

    /**
     * 坦克游戏地图对象
     */
    private Map map;

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


    public void keyPressedDirect(Boolean up, Boolean down, Boolean left, Boolean right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public void reset() {
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
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

    public StuffTypeEnum getCurrentStiff() {
        return currentStiff;
    }

    public void setCurrentStiff(StuffTypeEnum currentStiff) {
        this.currentStiff = currentStiff;
    }

    public Boolean getMapMakingFlag() {
        return mapMakingFlag;
    }

    public void setMapMakingFlag(Boolean mapMakingFlag) {
        this.mapMakingFlag = mapMakingFlag;
    }
}

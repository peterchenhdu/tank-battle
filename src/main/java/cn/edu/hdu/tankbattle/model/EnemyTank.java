/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.model;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import cn.edu.hdu.tankbattle.constant.GameConstants;
import cn.edu.hdu.tankbattle.model.map.Map;
import cn.edu.hdu.tankbattle.thread.GameTimeUnit;

/**
 * EnemyTank...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public class EnemyTank extends Tank {
    /**
     * 敌人坦克刚出现时的位置，5个固定地方
     */
    private int location;
    /**
     * 我的坦克在敌人坦克的相对位置，正北方‘正南方’正西方‘正东方，-1为不知道
     */
    private int myTankLocation = -1;
    /**
     * 我的坦克方向
     */
    private int myTankDirect = Tank.NORTH;
    /**
     * 定时器
     */
    private Timer timer;
    /**
     * 是否要开火
     */
    private boolean isShot = false;
    /**
     * 是否在地图中
     */
    //private boolean isInMap = false;

    /**
     * 敌人坦克构造方法
     *
     * @param x
     * @param y
     * @param direct
     */
    public EnemyTank(int x, int y, int direct) {
        super(x, y, direct);
        this.setSpeed(4);
        this.setType2(Tank.ENEMY);
        this.setDirect(Tank.NORTH);
        this.setColor(Color.red);
        this.setBlood(10);
        this.setSpeedVector(0); // 设为0表示没有保存坦克的速度，按下暂停时速度就不会是0
        timer = new Timer();

    }


    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public int getMyTankLocation() {
        return myTankLocation;
    }

    public void setMyTankLocation(int myTankLocation) {
        this.myTankLocation = myTankLocation;
    }

    public boolean isShot() {
        return isShot;
    }

    public void setShot(boolean isShot) {
        this.isShot = isShot;
    }

    public int getMyTankDirect() {
        return myTankDirect;
    }

    public void setMyTankDirect(int myTankDirect) {
        this.myTankDirect = myTankDirect;
    }

}


/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.model;


/**
 * Bullet...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public class Bullet {
    /**
     * 方向北
     */
    public static final int NORTH = 0;
    /**
     * 方向南
     */
    public static final int SOUTH = 1;
    /**
     * 方向西
     */
    public static final int WEST = 2;
    /**
     * 方向东
     */
    public static final int EAST = 3;
    /**
     * 子弹的速度
     */
    private int speed = 6;
    /**
     * 子弹的x坐标
     */
    private int x;
    /**
     * 子弹的y坐标
     */
    private int y;
    /**
     * 子弹的方向
     */
    private int direct;
    /**
     * 子弹是否存活
     */
    private boolean isLive = true;
    /**
     * 游戏暂停时存储子弹速度
     */
    private int speedVector;

    private int width;
    private int height;

    /**
     * 子弹类构造方法
     *
     * @param x      子弹x坐标
     * @param y      子弹y坐标
     * @param direct 子弹的方向
     */
    public Bullet(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
        this.width = 4;
        this.height = 4;

    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean isLive) {
        this.isLive = isLive;
    }

    public void setSpeedVector(int speedVector) {
        this.speedVector = speedVector;
    }

    public int getSpeedVector() {
        return speedVector;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.model;

import cn.edu.hdu.tankbattle.enums.DirectionEnum;
import cn.edu.hdu.tankbattle.enums.StuffTypeEnum;

import java.awt.Color;

/**
 * Stuff...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public abstract class Stuff {
    /**
     * x坐标
     */
    private int x;
    /**
     * y坐标
     */
    private int y;
    /**
     * 宽度
     */
    private int width;
    /**
     * 长度
     */
    private int height;
    /**
     * 东西第一个类型属性
     */
    private StuffTypeEnum type;

    /**
     * 是否活着
     */
    private boolean isLive = true;
    /**
     * 方向
     */
    private DirectionEnum direct;
    /**
     * 颜色
     */
    private Color color;
    /**
     * 血量
     */
    private int blood;

    /**
     * 东西类构造方法
     *
     * @param x 事物对象的x坐标
     * @param y 事物对象的y坐标
     */
    public Stuff(int x, int y) {
        this.x = x;
        this.y = y;
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

    public StuffTypeEnum getType() {
        return type;
    }

    public void setType(StuffTypeEnum type) {
        this.type = type;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean isLive) {
        this.isLive = isLive;
    }

    public DirectionEnum getDirect() {
        return direct;
    }

    public void setDirect(DirectionEnum direct) {
        this.direct = direct;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }



    public void setBlood(int blood) {
        this.blood = blood;
    }

    public int getBlood() {
        return blood;
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

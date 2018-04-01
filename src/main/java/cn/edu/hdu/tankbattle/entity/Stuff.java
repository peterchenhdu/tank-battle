/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.entity;

import cn.edu.hdu.tankbattle.enums.DirectionEnum;
import cn.edu.hdu.tankbattle.enums.StuffTypeEnum;


/**
 * Stuff...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public class Stuff {
    /**
     * x坐标
     */
    private Integer x;
    /**
     * y坐标
     */
    private Integer y;
    /**
     * 宽度
     */
    private Integer width;
    /**
     * 长度
     */
    private Integer height;
    /**
     * 东西第一个类型属性
     */
    private StuffTypeEnum type;

    /**
     * 是否活着
     */
    private Boolean isLive;
    /**
     * 方向
     */
    private DirectionEnum direct;
    /**
     * 血量
     */
    private Integer blood;

    /**
     * Stuff
     *
     * @param x x
     * @param y y
     */
    public Stuff(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.isLive = Boolean.TRUE;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public StuffTypeEnum getType() {
        return type;
    }

    public void setType(StuffTypeEnum type) {
        this.type = type;
    }

    public Boolean getLive() {
        return isLive;
    }

    public void setLive(Boolean live) {
        isLive = live;
    }

    public DirectionEnum getDirect() {
        return direct;
    }

    public void setDirect(DirectionEnum direct) {
        this.direct = direct;
    }

    public Integer getBlood() {
        return blood;
    }

    public void setBlood(Integer blood) {
        this.blood = blood;
    }
}

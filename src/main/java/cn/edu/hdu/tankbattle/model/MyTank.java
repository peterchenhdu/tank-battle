/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.model;

import cn.edu.hdu.tankbattle.enums.DirectionEnum;

import java.awt.Color;
import java.util.Vector;

/**
 * MyTank...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public class MyTank extends Tank {
    /**
     * 构造方法
     *
     * @param x      x坐标
     * @param y      y坐标
     * @param direct 方向
     */
    public MyTank(int x, int y, DirectionEnum direct) {
        super(x, y, direct);
        this.setColor(Color.yellow);
        this.setType2(Tank.MY);
        this.setBlood(10);
    }


}
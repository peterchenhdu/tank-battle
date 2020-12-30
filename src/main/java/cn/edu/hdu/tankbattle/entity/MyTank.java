/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.entity;

import cn.edu.hdu.tankbattle.enums.DirectionEnum;
import cn.edu.hdu.tankbattle.enums.TankTypeEnum;

import java.awt.Color;

/**
 * 我方坦克...
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
        this.setTankType(TankTypeEnum.MY);
        this.setBlood(10);
    }


}

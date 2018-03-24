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

    /**
     * 判断是否重叠
     *
     * @param enemies 敌人坦克容量
     * @return 是否重叠
     */
    public boolean isOverlap(Vector<EnemyTank> enemies) {
        for (int i = 0; i < enemies.size(); i++) { // 依次取出每个敌人坦克
            if (this.Overlap(enemies.get(i), 40))// 如果这两辆坦克重叠
                return true; // 则返回真
        }
        return false; // 不重叠返回假
    }
}
/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.model;

/**
 * Brick...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public class Brick extends Stuff {
    /**
     * 砖块类的构造函数
     *
     * @param x 砖块的x坐标
     * @param y 砖块的y坐标
     */
    public Brick(int x, int y) {
        super(x, y);
        this.setType(Stuff.BRICK);
        this.setWidth(20);
        this.setHeight(20);
    }

}

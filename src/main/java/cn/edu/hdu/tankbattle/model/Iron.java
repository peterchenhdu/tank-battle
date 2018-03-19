/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.model;

/**
 * Iron...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public class Iron extends Stuff {
    /**
     * 构造方法
     *
     * @param x x坐标
     * @param y y坐标
     */
    public Iron(int x, int y) {
        super(x, y);
        this.setType(IRON);
        this.setWidth(20);
        this.setHeight(20);
    }

}

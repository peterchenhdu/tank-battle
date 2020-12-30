/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.resource.image;

import java.awt.Image;
import java.awt.Toolkit;

/**
 * 图片资源...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public class Images {
    /**
     * 我方坦克图片
     */
    public static Image[] myTankImg = {
            getImage("/static/img/UTank_.gif"),
            getImage("/static/img/DTank_.gif"),
            getImage("/static/img/LTank_.gif"),
            getImage("/static/img/RTank_.gif")
    };

    /**
     * 敌方坦克图片
     */
    public static Image[] enemyTankImg = {
            getImage("/static/img/UTank.gif"),
            getImage("/static/img/DTank.gif"),
            getImage("/static/img/LTank.gif"),
            getImage("/static/img/RTank.gif")
    };

    /**
     * 地图上的物体图片（砖块、铁块、河流）
     */
    public static Image[] stuffImg = {
            getImage("/static/img/brick.gif"),
            getImage("/static/img/iron.gif"),
            getImage("/static/img/water.gif")
    };

    /**
     * 爆炸图片
     */
    public static Image[] bomb = {
            getImage("/static/img/bomb_1.png"),
            getImage("/static/img/bomb_2.png"),
            getImage("/static/img/bomb_3.png"),
            getImage("/static/img/bomb_4.png"),
            getImage("/static/img/bomb_5.png")
    };

    /**
     * 子弹图片
     */
    public static Image bullet = getImage("/static/img/bullet.gif");
    /**
     * 游戏开始背景图
     */
    public static Image startImage = getImage("/static/img/gameStart.png");
    /**
     * 游戏失败
     */
    public static Image gameOver = getImage("/static/img/gameOver.gif");
    /**
     * 游戏成功
     */
    public static Image gameWin = getImage("/static/img/gameWin.gif");
    /**
     * 笑脸1
     */
    public static Image yctSmile1 = getImage("/static/img/yct1.png");
    /**
     * 笑脸2
     */
    public static Image yctSmile2 = getImage("/static/img/yct2.png");
    /**
     * 游戏开始动态文字
     */
    public static Image font = getImage("/static/img/font.png");

    private static Image getImage(String name) {
        return Toolkit.getDefaultToolkit().getImage(Images.class.getResource(name));
    }
}

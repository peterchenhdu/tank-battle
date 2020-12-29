package cn.edu.hdu.tankbattle.util;

import cn.edu.hdu.tankbattle.entity.Bomb;
import cn.edu.hdu.tankbattle.entity.Bullet;
import cn.edu.hdu.tankbattle.entity.Stuff;
import cn.edu.hdu.tankbattle.entity.Tank;

import java.util.Vector;

/**
 * 坦克大战游戏常用工具类
 *
 * @author chenpi
 * @since 2020/12/29
 **/
public class GameUtils {
    /**
     * 判断是否击中物体
     *
     * @param bullet 子弹
     * @param stuff  物体
     * @return 是否击中
     */
    public static Boolean isHitting(Bullet bullet, Stuff stuff) {
        return (Math.abs(bullet.getX() - stuff.getX()) <= (stuff.getWidth() + bullet.getWidth()) / 2 &&
                Math.abs(bullet.getY() - stuff.getY()) <= (stuff.getWidth() + bullet.getHeight()) / 2);
    }

    /**
     * 判断子弹是否击中子弹
     *
     * @param bullet1 子弹1
     * @param bullet2  子弹2
     * @return 是否击中
     */
    public static Boolean isHitting(Bullet bullet1, Bullet bullet2) {
        return (Math.abs(bullet1.getX() - bullet2.getX()) <= bullet1.getWidth() &&
                Math.abs(bullet1.getY() - bullet2.getY()) <= bullet1.getHeight());
    }

    /**
     * 击中东西以后
     *
     * @param bullet 集中别人的子弹
     * @param stuff  被击中的东西
     * @param bombs  炸弹容量
     */
    public static void afterShotStuff(Bullet bullet, Stuff stuff, Vector<Bomb> bombs) {
        Bomb bomb;
        switch (stuff.getType()) {
            case BRICK: // 砖块
                bullet.setLive(false);
                stuff.setLive(false);
                bomb = new Bomb(stuff.getX(), stuff.getY());
                bomb.setL(40);
                bombs.add(bomb);
                break;
            case IRON: // 铁块
                bomb = new Bomb(bullet.getX(), bullet.getY());
                bullet.setLive(false);
                bomb.setL(20);
                bombs.add(bomb);
        }
    }

    /**
     * 击中坦克以后
     *
     * @param bullet 击中别人的子弹
     * @param tank   被击中的坦克
     * @param bombs  炸弹容量
     */
    public static void afterShotTank(Bullet bullet, Tank tank, Vector<Bomb> bombs) {
        bullet.setLive(false);
        Bomb bomb;
        if (tank.getBlood() == 1) {
            tank.setLive(false);
            bomb = new Bomb(tank.getX(), tank.getY());
            tank.setBlood(tank.getBlood() - 1);
            bomb.setL(120);
        } else {
            bomb = new Bomb(bullet.getX(), bullet.getY());
            tank.setBlood(tank.getBlood() - 1);
            bomb.setL(40);
        }
        bombs.add(bomb);
    }


    /**
     * 比较两个物体坐标是否相等
     * @param s1 物体1
     * @param s2 物体2
     * @return 是否相等
     */
    public static boolean equals(Stuff s1, Stuff s2) {
        return s2.getX().equals(s1.getX()) && s2.getY().equals(s1.getY());
    }

}

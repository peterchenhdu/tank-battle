package cn.edu.hdu.tankbattle.service;

import cn.edu.hdu.tankbattle.constant.GameConstants;
import cn.edu.hdu.tankbattle.entity.*;
import cn.edu.hdu.tankbattle.enums.DirectionEnum;
import org.springframework.stereotype.Service;

import java.util.Vector;

/**
 * 计算服务
 *
 * @author chenpi
 * @since 2020/12/30
 **/
@Service
public class ComputingService {
    /**
     * 判断是否击中物体
     *
     * @param bullet 子弹
     * @param stuff  物体
     * @return 是否击中
     */
    public Boolean isHitting(Bullet bullet, Stuff stuff) {
        return (Math.abs(bullet.getX() - stuff.getX()) <= (stuff.getWidth() + bullet.getWidth()) / 2 &&
                Math.abs(bullet.getY() - stuff.getY()) <= (stuff.getWidth() + bullet.getHeight()) / 2);
    }

    /**
     * 判断子弹是否击中子弹
     *
     * @param bullet1 子弹1
     * @param bullet2 子弹2
     * @return 是否击中
     */
    public Boolean isHitting(Bullet bullet1, Bullet bullet2) {
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
    public void afterShotStuff(Bullet bullet, Stuff stuff, Vector<Bomb> bombs) {
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
                break;
        }
    }

    /**
     * 击中坦克以后
     *
     * @param bullet 击中别人的子弹
     * @param tank   被击中的坦克
     * @param bombs  炸弹容量
     */
    public void afterShotTank(Bullet bullet, Tank tank, Vector<Bomb> bombs) {
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
     *
     * @param s1 物体1
     * @param s2 物体2
     * @return 是否相等
     */
    public boolean equals(Stuff s1, Stuff s2) {
        return s2.getX().equals(s1.getX()) && s2.getY().equals(s1.getY());
    }


    /**
     * 判断我的坦克是否与敌方坦克重叠
     *
     * @param enemies 敌人坦克容量
     * @return 是否重叠
     */
    public boolean isMyTankOverlap(MyTank tank, Vector<EnemyTank> enemies) {
        for (EnemyTank enemy : enemies) {
            if (isTankOverlap(tank, enemy, GameConstants.TANK_WIDTH))
                return true;
        }
        return false;
    }

    /**
     * 判断敌方坦克是否和敌方坦克或我的坦克重叠
     *
     * @param enemies 敌人坦克容量
     * @param myTanks 我的坦克容量
     * @return 是否重叠
     */
    public boolean isEnemyTankOverlap(EnemyTank enemy, Vector<EnemyTank> enemies, Vector<MyTank> myTanks) {
        for (EnemyTank enemyTank : enemies) {
            if (enemy != enemyTank) {
                if (isTankOverlap(enemy, enemyTank, GameConstants.TANK_WIDTH)) {
                    enemy.setOverlapAndCanShot(true);
                    return true;
                }
            }
        }

        for (MyTank myTank : myTanks) {
            if (isTankOverlap(enemy, myTank, GameConstants.TANK_WIDTH)) {
                enemy.setOverlapCanNotShot(true);
                return true;
            }
        }

        enemy.setOverlapAndCanShot(false);
        enemy.setOverlapCanNotShot(false);
        return false;
    }

    /**
     * 判断坦克是否与另一个事物重叠
     *
     * @param stuff  东西对象
     * @param length 两者之间的最短距离
     * @return 是否重叠
     */
    public boolean isTankOverlap(Tank tank, Stuff stuff, int length) {
        boolean b = false;
        int x = stuff.getX();
        int y = stuff.getY();

        switch (tank.getDirect()) {
            case NORTH:
                tank.setY(tank.getY() - tank.getSpeed());
                if (Math.abs(tank.getY() - y) < length && Math.abs(tank.getX() - x) < length) {
                    b = true;
                }
                tank.setY(tank.getY() + tank.getSpeed());
                break;
            case SOUTH:
                tank.setY(tank.getY() + tank.getSpeed());
                if (Math.abs(tank.getY() - y) < length && Math.abs(tank.getX() - x) < length) {
                    b = true;
                }
                tank.setY(tank.getY() - tank.getSpeed());
                break;
            case EAST:
                tank.setX(tank.getX() + tank.getSpeed());
                if (Math.abs(tank.getY() - y) < length && Math.abs(tank.getX() - x) < length) {
                    b = true;
                }
                tank.setX(tank.getX() - tank.getSpeed());
                break;
            case WEST:
                tank.setX(tank.getX() - tank.getSpeed());
                if (Math.abs(tank.getY() - y) < length && Math.abs(tank.getX() - x) < length) {
                    b = true;
                }
                tank.setX(tank.getX() + tank.getSpeed());
                break;

        }
        return b;
    }

    /**
     * 从指定的三个方向中随机选择一个
     *
     * @param direct1 方向1
     * @param direct2 方向2
     * @param direct3 方向3
     */
    public DirectionEnum enemyGetRandomDirect(DirectionEnum direct1, DirectionEnum direct2, DirectionEnum direct3) {
        int random = (int) (Math.random() * 3);

        DirectionEnum returnDirect = DirectionEnum.INVALID;
        switch (random) {
            case 0:
                returnDirect = direct1;
                break;
            case 1:
                returnDirect = direct2;
                break;
            case 2:
                returnDirect = direct3;
                break;
        }
        return returnDirect;
    }

}

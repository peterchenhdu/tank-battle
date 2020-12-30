/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.service;

import cn.edu.hdu.tankbattle.context.GameContext;
import cn.edu.hdu.tankbattle.entity.*;
import cn.edu.hdu.tankbattle.enums.DirectionEnum;
import cn.edu.hdu.tankbattle.resource.map.Map;
import cn.edu.hdu.tankbattle.task.BulletMoveTask;
import cn.edu.hdu.tankbattle.task.EnemyTankAutoShotTask;
import cn.edu.hdu.tankbattle.task.EnemyTankMoveTask;
import cn.edu.hdu.tankbattle.util.GameTimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Vector;

/**
 * 坦克控制服务...
 *
 * @author chenpi
 * @since 2018/3/24 19:52
 */
@Service
public class TankControlService {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private GameContext gameContext;
    @Autowired
    private ComputingService computingService;
    @Autowired
    private TankControlService tankControlService;

    /**
     * 激活敌方坦克线程
     */
    public void enableEnemyTanks() {
        Vector<EnemyTank> enemyTanks = gameContext.getRealTimeGameData().getEnemies();
        enemyTanks.forEach(enemyTank -> {
            taskExecutor.execute(new EnemyTankMoveTask(enemyTank, gameContext));
            enemyTank.getTimer().schedule(new EnemyTankAutoShotTask(enemyTank, tankControlService), 0, 500);
        });
    }

    /**
     * 激活敌方坦克线程
     */
    public void enableEnemyTank(EnemyTank tank) {
        taskExecutor.execute(new EnemyTankMoveTask(tank, gameContext));
        tank.getTimer().schedule(new EnemyTankAutoShotTask(tank, tankControlService), 0, 500);
    }




    /**
     * 每隔36毫秒 一直向西走
     */
    public void enemyGoWest(EnemyTank enemy) {
        for (; ; ) {
            GameTimeUnit.sleepMillis(36);
            if (!enemy.isOverlapAndCanShot() && !enemy.isOverlapCanNotShot()) {
                enemy.goWest();
            }
            if (enemy.getMyTankLocation() != DirectionEnum.WEST) {
                enemy.setDirect(enemy.getMyTankDirect());
                break;
            }
        }
    }

    /**
     * 每隔36毫秒 一直向东走
     */
    public void enemyGoEast(EnemyTank enemy) {
        for (; ; ) {
            GameTimeUnit.sleepMillis(36);
            if (!enemy.isOverlapAndCanShot() && !enemy.isOverlapCanNotShot()) {
                enemy.goEast();
            }
            if (enemy.getMyTankLocation() != DirectionEnum.EAST) {
                enemy.setDirect(enemy.getMyTankDirect());
                break;
            }
        }
    }

    /**
     * 每隔36毫秒 一直向北走
     */
    public void enemyGoNorth(EnemyTank enemy) {
        for (; ; ) {
            GameTimeUnit.sleepMillis(36);
            if (!enemy.isOverlapAndCanShot() && !enemy.isOverlapCanNotShot()) {
                enemy.goNorth();
            }
            if (enemy.getMyTankLocation() != DirectionEnum.NORTH) {
                enemy.setDirect(enemy.getMyTankDirect());
                break;
            }
        }
    }

    /**
     * 每隔36毫秒 一直向南走
     */
    public void enemyGoSouth(EnemyTank enemy) {
        for (; ; ) {
            GameTimeUnit.sleepMillis(36);
            if (!enemy.isOverlapAndCanShot() && !enemy.isOverlapCanNotShot()) {
                enemy.goSouth();
            }
            if (enemy.getMyTankLocation() != DirectionEnum.SOUTH) {
                enemy.setDirect(enemy.getMyTankDirect());
                break;
            }
        }
    }

    /**
     * 让敌人坦克能够发现我的坦克并开炮
     *
     * @param myTank 我的坦克
     * @param map    地图对象
     */
    public void enemyFindAndKill(EnemyTank enemy, MyTank myTank, Map map) {
        int myX = myTank.getX();
        int myY = myTank.getY();
        int enX = enemy.getX();
        int enY = enemy.getY();
        if (Math.abs(myX - enX) < 20 && myY <= 580) {
            if (enY < myY) {
                int s = 0;
                for (int t = 0; t < map.getIrons().size(); t++) {
                    Iron iron = map.getIrons().get(t);
                    if (Math.abs(enX - iron.getX()) <= 10 && iron.getY() > enY
                            && iron.getY() < myY) {
                        s = 1;
                        break;
                    }
                }
                if (s == 0) {
                    enemy.setShot(true);
                    enemy.setMyTankLocation(DirectionEnum.SOUTH);
                }
            } else {
                int s = 0;
                for (int t = 0; t < map.getIrons().size(); t++) {
                    Iron iron = map.getIrons().get(t);
                    if (Math.abs(enX - iron.getX()) <= 10 && iron.getY() < enY
                            && iron.getY() > myY) {
                        s = 1;
                        break;
                    }
                }
                if (s == 0) {
                    enemy.setShot(true);
                    enemy.setMyTankLocation(DirectionEnum.NORTH);
                }
            }
        } else if (Math.abs(myY - enY) < 20 && myY <= 580) {
            if (enX > myX) {
                int s = 0;
                for (int t = 0; t < map.getIrons().size(); t++) {
                    Iron iron = map.getIrons().get(t);
                    if (Math.abs(enY - iron.getY()) <= 10 && iron.getX() < enX
                            && iron.getX() > myX) {
                        s = 1;
                        break;
                    }
                }
                if (s == 0) {
                    enemy.setShot(true);
                    enemy.setMyTankLocation(DirectionEnum.WEST);
                }
            } else {
                int s = 0;
                for (int t = 0; t < map.getIrons().size(); t++) {
                    Iron iron = map.getIrons().get(t);
                    if (Math.abs(enY - iron.getY()) <= 10 && iron.getX() > enX
                            && iron.getX() < myX) {
                        s = 1;
                        break;
                    }
                }
                if (s == 0) {
                    enemy.setShot(true);
                    enemy.setMyTankLocation(DirectionEnum.EAST);
                }
            }
        } else {
            enemy.setShot(false);
            enemy.setMyTankLocation(DirectionEnum.INVALID);
        }
    }
    /**
     * 射击，发射一颗子弹
     *
     * @param tank 坦克对象，注意，是自己，不是敌人，呵呵
     */
    public void shot(Tank tank) {
        Bullet bullet = null;
        switch (tank.getDirect()) {
            case NORTH:
                bullet = new Bullet(tank.getX(), tank.getY() - 20, DirectionEnum.NORTH);
                break;
            case SOUTH:
                bullet = new Bullet(tank.getX(), tank.getY() + 20, DirectionEnum.SOUTH);
                break;
            case WEST:
                bullet = new Bullet(tank.getX() - 20, tank.getY(), DirectionEnum.WEST);
                break;
            case EAST:
                bullet = new Bullet(tank.getX() + 20, tank.getY(), DirectionEnum.EAST);
                break;
        }
        tank.getBullets().add(bullet);
        taskExecutor.execute(new BulletMoveTask(bullet));
    }
}

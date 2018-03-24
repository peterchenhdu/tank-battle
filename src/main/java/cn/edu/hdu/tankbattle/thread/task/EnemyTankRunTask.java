/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.thread.task;

import cn.edu.hdu.tankbattle.constant.GameConstants;
import cn.edu.hdu.tankbattle.model.EnemyTank;
import cn.edu.hdu.tankbattle.model.Tank;
import cn.edu.hdu.tankbattle.thread.GameTimeUnit;

/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/3/24 19:29
 */
public class EnemyTankRunTask implements Runnable {
    EnemyTank tank;
    public EnemyTankRunTask(EnemyTank tank) {
        this.tank = tank;
    }
    @Override
    public void run() {
        while (true) {
            switch (tank.getDirect()) { // 选择坦克方向
                case EnemyTank.NORTH:
                    while (tank.activate()) {
                        // 睡眠36毫秒，36毫秒可以保证坦克的信息已经判断过一次了
                        GameTimeUnit.sleepMillis(36);
                        // 如果我的坦克在敌人坦克的正西方
                        if (tank.getMyTankLocation() == EnemyTank.WEST) {
                            tank.setDirect(EnemyTank.WEST);
                            tank.enemyGoWest();
                        }
                        // 如果我的坦克在敌人坦克的正东方
                        if (tank.getMyTankLocation() == EnemyTank.EAST) {
                            tank.setDirect(EnemyTank.EAST);
                            tank.enemyGoEast();
                        }
                        // 如果我的坦克在敌人坦克的正南方
                        if (tank.getMyTankLocation() == EnemyTank.SOUTH) {
                            tank.setDirect(EnemyTank.SOUTH);
                            tank.enemyGoSouth();
                        }
                        // 如果我的坦克在敌人坦克的正北方
                        if (tank.getMyTankLocation() == EnemyTank.NORTH) {
                            tank.enemyGoNorth();
                        }
                        // 如果出界或者重叠的话 选择其他方向 跳出
                        if (tank.getY() <= 20 || tank.isOverlapNo() == true) {
                            tank.setDirect(tank.getRandomDirect(Tank.SOUTH,
                                    Tank.WEST, Tank.EAST));
                            break;
                        }
                        // 如果现在坦克的方向不是北方，跳出
                        if (tank.getDirect() != Tank.NORTH)
                            break;
                        // 如果不重叠，前进
                        if (!tank.isOverlapYes())
                            tank.goNorth();
                    }
                    break;
                case EnemyTank.SOUTH:
                    for (; ; ) {
                        GameTimeUnit.sleepMillis(36);
                        if (tank.getMyTankLocation() == EnemyTank.WEST) {
                            tank.setDirect(EnemyTank.WEST);
                            tank.enemyGoWest();
                        }
                        if (tank.getMyTankLocation() == EnemyTank.EAST) {
                            tank.setDirect(EnemyTank.EAST);
                            tank.enemyGoEast();
                        }
                        if (tank.getMyTankLocation() == EnemyTank.NORTH) {
                            tank.setDirect(EnemyTank.NORTH);
                            tank.enemyGoNorth();
                        }
                        if (tank.getMyTankLocation() == EnemyTank.SOUTH) {
                            tank.enemyGoSouth();
                        }
                        if (tank.getY() >= GameConstants.GAME_PANEL_HEIGHT - 20
                                || tank.isOverlapNo()) {
                            tank.setDirect(tank.getRandomDirect(Tank.NORTH,
                                    Tank.WEST, Tank.EAST));
                            break;
                        }
                        if (tank.getDirect() != Tank.SOUTH)
                            break;
                        if (!tank.isOverlapYes())
                            tank.goSouth();
                    }
                    break;
                case EnemyTank.WEST:
                    for (; ; ) {
                        GameTimeUnit.sleepMillis(36);
                        if (tank.getMyTankLocation() == EnemyTank.NORTH) {
                            tank.setDirect(EnemyTank.NORTH);
                            tank.enemyGoNorth();
                        }
                        if (tank.getMyTankLocation() == EnemyTank.EAST) {
                            tank.setDirect(EnemyTank.EAST);
                            tank.enemyGoEast();
                        }
                        if (tank.getMyTankLocation() == EnemyTank.SOUTH) {
                            tank.setDirect(EnemyTank.SOUTH);
                            tank.enemyGoSouth();
                        }
                        if (tank.getMyTankLocation() == EnemyTank.WEST) {
                            tank.enemyGoWest();
                        }
                        if (tank.getX() <= 20 || tank.getY() <= 20
                                || tank.isOverlapNo()) {
                            tank.setDirect(tank.getRandomDirect(Tank.NORTH,
                                    Tank.SOUTH, Tank.EAST));
                            break;
                        }
                        if (tank.getDirect() != Tank.WEST)
                            break;
                        if (!tank.isOverlapYes())
                            tank.goWest();
                    }
                    break;
                case EnemyTank.EAST:
                    for (; ; ) {
                        GameTimeUnit.sleepMillis(36);
                        if (tank.getMyTankLocation() == EnemyTank.WEST) {
                            tank.setDirect(EnemyTank.WEST);
                            tank.enemyGoWest();
                        }
                        if (tank.getMyTankLocation() == EnemyTank.NORTH) {
                            tank.setDirect(EnemyTank.NORTH);
                            tank.enemyGoNorth();
                        }
                        if (tank.getMyTankLocation() == EnemyTank.SOUTH) {
                            tank.setDirect(EnemyTank.SOUTH);
                            tank.enemyGoSouth();
                        }
                        if (tank.getMyTankLocation() == EnemyTank.EAST) {
                            tank.enemyGoEast();
                        }
                        if (tank.getX() >= GameConstants.GAME_PANEL_WIDTH - 20
                                || tank.getY() <= 20 || tank.isOverlapNo()) {
                            tank.setDirect(tank.getRandomDirect(Tank.NORTH,
                                    Tank.SOUTH, Tank.WEST));
                            break;
                        }
                        if (tank.getDirect() != Tank.EAST)
                            break;
                        if (!tank.isOverlapYes())
                            tank.goEast();
                    }
                    break;
            }
            GameTimeUnit.sleepMillis(216); // 改变一个方向的话，不要让他很快
            if (!tank.isLive()) { // 如果坦克死亡的话 该坦克线程结束
                break;
            }
        }
    }
}

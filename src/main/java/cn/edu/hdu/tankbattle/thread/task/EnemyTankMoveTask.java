/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.thread.task;

import cn.edu.hdu.tankbattle.constant.GameConstants;
import cn.edu.hdu.tankbattle.enums.DirectionEnum;
import cn.edu.hdu.tankbattle.entity.EnemyTank;
import cn.edu.hdu.tankbattle.service.TankEventService;
import cn.edu.hdu.tankbattle.thread.GameTimeUnit;

/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/3/24 19:29
 */
public class EnemyTankMoveTask implements Runnable {
    EnemyTank tank;
    TankEventService enemyTankEventService;

    public EnemyTankMoveTask(EnemyTank tank, TankEventService enemyTankEventService) {
        this.tank = tank;
        this.enemyTankEventService = enemyTankEventService;
    }

    @Override
    public void run() {
        while (true) {
            switch (tank.getDirect()) { // 选择坦克方向
                case NORTH:
                    while (tank.activate()) {
                        // 睡眠36毫秒，36毫秒可以保证坦克的信息已经判断过一次了
                        GameTimeUnit.sleepMillis(36);
                        // 如果我的坦克在敌人坦克的正西方
                        if (DirectionEnum.WEST.equals(tank.getMyTankLocation())) {
                            tank.setDirect(DirectionEnum.WEST);
                            enemyTankEventService.enemyGoWest(tank);
                        }
                        // 如果我的坦克在敌人坦克的正东方
                        if (DirectionEnum.EAST.equals(tank.getMyTankLocation())) {
                            tank.setDirect(DirectionEnum.EAST);
                            enemyTankEventService.enemyGoEast(tank);
                        }
                        // 如果我的坦克在敌人坦克的正南方
                        if (tank.getMyTankLocation() == DirectionEnum.SOUTH) {
                            tank.setDirect(DirectionEnum.SOUTH);
                            enemyTankEventService.enemyGoSouth(tank);
                        }
                        // 如果我的坦克在敌人坦克的正北方
                        if (tank.getMyTankLocation() == DirectionEnum.NORTH) {
                            enemyTankEventService.enemyGoNorth(tank);
                        }
                        // 如果出界或者重叠的话 选择其他方向 跳出
                        if (tank.getY() <= 20 || tank.isOverlapNo() == true) {
                            tank.setDirect(enemyTankEventService.enemyGetRandomDirect(DirectionEnum.SOUTH,
                                    DirectionEnum.WEST, DirectionEnum.EAST));
                            break;
                        }
                        // 如果现在坦克的方向不是北方，跳出
                        if (tank.getDirect() != DirectionEnum.NORTH)
                            break;
                        // 如果不重叠，前进
                        if (!tank.isOverlapYes())
                            tank.goNorth();
                    }
                    break;
                case SOUTH:
                    for (; ; ) {
                        GameTimeUnit.sleepMillis(36);
                        if (tank.getMyTankLocation() == DirectionEnum.WEST) {
                            tank.setDirect(DirectionEnum.WEST);
                            enemyTankEventService.enemyGoWest(tank);
                        }
                        if (tank.getMyTankLocation() == DirectionEnum.EAST) {
                            tank.setDirect(DirectionEnum.EAST);
                            enemyTankEventService.enemyGoEast(tank);
                        }
                        if (tank.getMyTankLocation() == DirectionEnum.NORTH) {
                            tank.setDirect(DirectionEnum.NORTH);
                            enemyTankEventService.enemyGoNorth(tank);
                        }
                        if (tank.getMyTankLocation() == DirectionEnum.SOUTH) {
                            enemyTankEventService.enemyGoSouth(tank);
                        }
                        if (tank.getY() >= GameConstants.GAME_PANEL_HEIGHT - 20
                                || tank.isOverlapNo()) {
                            tank.setDirect(enemyTankEventService.enemyGetRandomDirect(DirectionEnum.NORTH,
                                    DirectionEnum.WEST, DirectionEnum.EAST));
                            break;
                        }
                        if (tank.getDirect() != DirectionEnum.SOUTH)
                            break;
                        if (!tank.isOverlapYes())
                            tank.goSouth();
                    }
                    break;
                case WEST:
                    for (; ; ) {
                        GameTimeUnit.sleepMillis(36);
                        if (tank.getMyTankLocation() == DirectionEnum.NORTH) {
                            tank.setDirect(DirectionEnum.NORTH);
                            enemyTankEventService.enemyGoNorth(tank);
                        }
                        if (tank.getMyTankLocation() == DirectionEnum.EAST) {
                            tank.setDirect(DirectionEnum.EAST);
                            enemyTankEventService.enemyGoEast(tank);
                        }
                        if (tank.getMyTankLocation() == DirectionEnum.SOUTH) {
                            tank.setDirect(DirectionEnum.SOUTH);
                            enemyTankEventService.enemyGoSouth(tank);
                        }
                        if (tank.getMyTankLocation() == DirectionEnum.WEST) {
                            enemyTankEventService.enemyGoWest(tank);
                        }
                        if (tank.getX() <= 20 || tank.getY() <= 20
                                || tank.isOverlapNo()) {
                            tank.setDirect(enemyTankEventService.enemyGetRandomDirect(DirectionEnum.NORTH,
                                    DirectionEnum.SOUTH, DirectionEnum.EAST));
                            break;
                        }
                        if (tank.getDirect() != DirectionEnum.WEST)
                            break;
                        if (!tank.isOverlapYes())
                            tank.goWest();
                    }
                    break;
                case EAST:
                    for (; ; ) {
                        GameTimeUnit.sleepMillis(36);
                        if (tank.getMyTankLocation() == DirectionEnum.WEST) {
                            tank.setDirect(DirectionEnum.WEST);
                            enemyTankEventService.enemyGoWest(tank);
                        }
                        if (tank.getMyTankLocation() == DirectionEnum.NORTH) {
                            tank.setDirect(DirectionEnum.NORTH);
                            enemyTankEventService.enemyGoNorth(tank);
                        }
                        if (tank.getMyTankLocation() == DirectionEnum.SOUTH) {
                            tank.setDirect(DirectionEnum.SOUTH);
                            enemyTankEventService.enemyGoSouth(tank);
                        }
                        if (tank.getMyTankLocation() == DirectionEnum.EAST) {
                            enemyTankEventService.enemyGoEast(tank);
                        }
                        if (tank.getX() >= GameConstants.GAME_PANEL_WIDTH - 20
                                || tank.getY() <= 20 || tank.isOverlapNo()) {
                            tank.setDirect(enemyTankEventService.enemyGetRandomDirect(DirectionEnum.NORTH,
                                    DirectionEnum.SOUTH, DirectionEnum.WEST));
                            break;
                        }
                        if (tank.getDirect() != DirectionEnum.EAST)
                            break;
                        if (!tank.isOverlapYes())
                            tank.goEast();
                    }
                    break;
            }
            GameTimeUnit.sleepMillis(216); // 改变一个方向的话，不要让他很快
            if (!tank.getLive()) { // 如果坦克死亡的话 该坦克线程结束
                break;
            }
        }
    }
}

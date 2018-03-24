/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.thread.task;

import cn.edu.hdu.tankbattle.model.EnemyTank;
import cn.edu.hdu.tankbattle.service.GameEventService;

import java.util.TimerTask;

/**
 * MyTimerTask...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public class BulletShotTask extends TimerTask {
    EnemyTank tank;
    GameEventService gameEventService;

    public BulletShotTask(EnemyTank tank, GameEventService gameEventService) {
        this.tank = tank;
        this.gameEventService = gameEventService;
    }

    @Override
    public void run() {
        if (tank.getSpeedVector() == 0 && tank.isShot() && tank.activate()) {
            gameEventService.shot(tank);
        }

    }

}
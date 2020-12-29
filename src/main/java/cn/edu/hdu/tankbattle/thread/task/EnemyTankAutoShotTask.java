/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.thread.task;

import cn.edu.hdu.tankbattle.entity.EnemyTank;
import cn.edu.hdu.tankbattle.service.GameEventService;

import java.util.TimerTask;

/**
 * MyTimerTask...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public class EnemyTankAutoShotTask extends TimerTask {
    EnemyTank enemyTank;
    GameEventService gameEventService;

    public EnemyTankAutoShotTask(EnemyTank enemyTank, GameEventService gameEventService) {
        this.enemyTank = enemyTank;
        this.gameEventService = gameEventService;
    }

    @Override
    public void run() {
        if (enemyTank.getSpeedVector() == 0 && enemyTank.isShot() && enemyTank.activate()) {
            gameEventService.shot(enemyTank);
        }

    }

}

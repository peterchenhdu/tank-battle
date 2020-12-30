/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.task;

import cn.edu.hdu.tankbattle.entity.EnemyTank;
import cn.edu.hdu.tankbattle.service.TankControlService;

import java.util.TimerTask;

/**
 * MyTimerTask...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public class EnemyTankAutoShotTask extends TimerTask {
    EnemyTank enemyTank;
    TankControlService tankControlService;

    public EnemyTankAutoShotTask(EnemyTank enemyTank, TankControlService tankControlService) {
        this.enemyTank = enemyTank;
        this.tankControlService = tankControlService;
    }

    @Override
    public void run() {
        if (enemyTank.getSpeedVector() == 0 && enemyTank.isShot() && enemyTank.activate()) {
            tankControlService.shot(enemyTank);
        }

    }

}

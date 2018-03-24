/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.thread.task;

import cn.edu.hdu.tankbattle.model.EnemyTank;

import java.util.TimerTask;

/**
 * MyTimerTask...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public class BulletTimerTask extends TimerTask {
    EnemyTank tank;

    public BulletTimerTask(EnemyTank tank) {
        this.tank = tank;
    }

    @Override
    public void run() {
        if (tank.getSpeedVector() == 0 && tank.isShot() && tank.activate())
            tank.shot(tank);
    }

}
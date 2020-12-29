/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.thread.executor;

import cn.edu.hdu.tankbattle.context.GameContext;
import cn.edu.hdu.tankbattle.entity.EnemyTank;
import cn.edu.hdu.tankbattle.service.TankControlService;
import cn.edu.hdu.tankbattle.service.GameEventService;
import cn.edu.hdu.tankbattle.thread.task.EnemyTankAutoShotTask;
import cn.edu.hdu.tankbattle.thread.task.EnemyTankMoveTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Vector;

/**
 * 敌人坦克线程控制器...
 *
 * @author chenpi
 * @since 2018/3/24 19:12
 */
@Component
public class EnemyTankThreadController {
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private GameContext gameContext;

    @Autowired
    private TankControlService enemyTankControlService;
    @Autowired
    private GameEventService gameEventService;

    public void enableEnemyTanks() {
        Vector<EnemyTank> enemyTanks = gameContext.getRealTimeGameData().getEnemies();
        enemyTanks.forEach(enemyTank -> {
            taskExecutor.execute(new EnemyTankMoveTask(enemyTank, enemyTankControlService));
            enemyTank.getTimer().schedule(new EnemyTankAutoShotTask(enemyTank, gameEventService), 0, 500);
        });
    }

    public void enableEnemyTank(EnemyTank tank) {
        taskExecutor.execute(new EnemyTankMoveTask(tank, enemyTankControlService));
        tank.getTimer().schedule(new EnemyTankAutoShotTask(tank, gameEventService), 0, 500);
    }
}

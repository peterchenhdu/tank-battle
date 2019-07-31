/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.thread.executor;

import cn.edu.hdu.tankbattle.context.GameContext;
import cn.edu.hdu.tankbattle.entity.EnemyTank;
import cn.edu.hdu.tankbattle.service.TankEventService;
import cn.edu.hdu.tankbattle.service.GameEventService;
import cn.edu.hdu.tankbattle.thread.task.EnemyTankAutoShotTask;
import cn.edu.hdu.tankbattle.thread.task.EnemyTankMoveTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Vector;

/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/3/24 19:12
 */
@Component
public class TaskExecutor {
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private GameContext gameContext;
    @Autowired
    private TankEventService enemyTankEventService;
    @Autowired
    private GameEventService gameEventService;

    public void startEnemyTankThreads() {
        Vector<EnemyTank> enemies = gameContext.getRealTimeGameData().getEnemies();
        enemies.forEach(e -> {
            taskExecutor.execute(new EnemyTankMoveTask(e, enemyTankEventService));
            e.getTimer().schedule(new EnemyTankAutoShotTask(e, gameEventService), 0, 500);
        });
    }

    public void startSingleEnemyTankTask(EnemyTank tank) {
        taskExecutor.execute(new EnemyTankMoveTask(tank, enemyTankEventService));
        tank.getTimer().schedule(new EnemyTankAutoShotTask(tank, gameEventService), 0, 500);
    }
}

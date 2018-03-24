/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.thread.executor;

import cn.edu.hdu.tankbattle.context.GameContext;
import cn.edu.hdu.tankbattle.model.EnemyTank;
import cn.edu.hdu.tankbattle.service.EnemyTankEventService;
import cn.edu.hdu.tankbattle.service.GameEventService;
import cn.edu.hdu.tankbattle.thread.task.BulletShotTask;
import cn.edu.hdu.tankbattle.thread.task.EnemyTankRunTask;
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
    private EnemyTankEventService enemyTankEventService;
    @Autowired
    private GameEventService gameEventService;

    public void startEnemyTankThreads() {
        Vector<EnemyTank> enemies = gameContext.getGameData().getGameResource().getEnemies();
        enemies.forEach(e -> {
            taskExecutor.execute(new EnemyTankRunTask(e, enemyTankEventService));
            e.getTimer().schedule(new BulletShotTask(e, gameEventService), 0, 500);
        });
    }

    public void startSingleEnemyTankTask(EnemyTank tank) {
        taskExecutor.execute(new EnemyTankRunTask(tank, enemyTankEventService));
        tank.getTimer().schedule(new BulletShotTask(tank, gameEventService), 0, 500);
    }
}

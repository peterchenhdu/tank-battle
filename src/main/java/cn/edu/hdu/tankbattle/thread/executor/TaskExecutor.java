/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.thread.executor;

import cn.edu.hdu.tankbattle.context.GameContext;
import cn.edu.hdu.tankbattle.model.EnemyTank;
import cn.edu.hdu.tankbattle.thread.task.BulletTimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Timer;
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

    public void startEnemyTankThreads() {
        Vector<EnemyTank> enemies = gameContext.getGameData().getGameResource().getEnemies();
        enemies.forEach(e -> {
            taskExecutor.execute(e);
            e.getTimer().schedule(new BulletTimerTask(e), 0, 500);
        });

    }
}

/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.thread.task;

import cn.edu.hdu.tankbattle.context.GameContext;
import cn.edu.hdu.tankbattle.enums.LevelEnum;
import cn.edu.hdu.tankbattle.service.GameEventService;
import cn.edu.hdu.tankbattle.dto.RealTimeGameData;
import cn.edu.hdu.tankbattle.util.GameTimeUnit;
import cn.edu.hdu.tankbattle.view.panel.GamePanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 游戏数据更新线程...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public class GameDataUpdateTask implements Runnable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private GameContext gameContext;


    public GameDataUpdateTask(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public void run() {
        GamePanel panel = gameContext.getGamePanel();
        RealTimeGameData gameData = gameContext.getRealTimeGameData();
        GameEventService control = gameContext.getGameEventService();
        // 每隔30毫秒重画
        while (true) {
            GameTimeUnit.sleepMillis(30);
            if (gameData.isStart()) {
                if ((gameData.getMyTankNum() == 0 || gameData.getEnemyTankNum() == 0)
                        && gameData.getDy() > 250) {
                    gameData.setDy(gameData.getDy() - 2);
                }
                if (gameData.getDy() == 250) {
                    panel.repaint();
                    GameTimeUnit.sleepMillis(4000);

                    if (gameData.getMyTankNum() >= 1) {
                        gameData.setLevel(LevelEnum.nextLevel(gameData.getLevel()));
                        gameData.setDy(600);
                        control.nextGame(gameData);
                    }
                }
                if (!gameData.isStop() && gameData.getDy() == 600) {
                    control.cleanAndCreate(); // 从容器中移除死亡的对象
                    control.refreshState();
                    control.doBulletEvent();
                    control.doOverlapJudge(); // 判断坦克间是否出现重叠
                    control.myTankEvent(gameData);

                }
            } else {
                if (gameData.getKy() == 21 && gameData.getKx() <= 654) {
                    gameData.setKx(gameData.getKx() + 2);
                }
                control.fontMove(panel);
                GameTimeUnit.sleepMillis(100);

            }
            panel.repaint();
            logger.debug("data : {}", gameData);
        }
    }


}

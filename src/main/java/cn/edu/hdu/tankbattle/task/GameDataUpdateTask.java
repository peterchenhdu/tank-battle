/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.task;

import cn.edu.hdu.tankbattle.context.GameContext;
import cn.edu.hdu.tankbattle.dto.RealTimeGameData;
import cn.edu.hdu.tankbattle.enums.LevelEnum;
import cn.edu.hdu.tankbattle.service.GameEventService;
import cn.edu.hdu.tankbattle.service.StateFlushService;
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
        GameEventService gameEventService = gameContext.getGameEventService();
        StateFlushService stateFlushService = gameContext.getStateFlushService();
        // 每隔30毫秒重画
        while (!gameContext.getRealTimeGameData().isExit()) {
            GameTimeUnit.sleepMillis(30);
            if (gameData.isStart()) {
                if ((gameData.getMyTankNum() == 0 || gameData.getEnemyTankNum() == 0) && gameData.getDy() > 250) {
                    //游戏关卡成功或失败时动态图片上移
                    gameData.setDy(gameData.getDy() - 2);
                }

                if (gameData.getDy() == 250) {
                    //游戏关卡成功或失败时动态图片上移结束，准备下一关卡
                    panel.repaint();
                    GameTimeUnit.sleepMillis(4000);

                    if (gameData.getMyTankNum() >= 1) {
                        //下一关卡
                        gameData.setLevel(LevelEnum.nextLevel(gameData.getLevel()));
                        gameData.setDy(600);
                        gameEventService.nextGame(gameData);
                    }
                }

                //游戏运行中，刷新数据
                if (!gameData.isStop() && gameData.getDy() == 600) {
                    stateFlushService.cleanAndCreate(); // 从容器中移除死亡的对象
                    stateFlushService.refreshEnemyTankState();
                    stateFlushService.refreshBulletState();
                    stateFlushService.refreshOverlapState(); // 判断坦克间是否出现重叠
                    stateFlushService.refreshMyTankState(gameData);

                }
            } else {
                //游戏未开始
                if (gameData.getKy() == 21 && gameData.getKx() <= 654) {
                    //文字到达顶部后，移动笑脸
                    gameData.setKx(gameData.getKx() + 2);
                }
                //文字动态移动
                gameEventService.fontDynamicMove(panel);
                GameTimeUnit.sleepMillis(100);
            }

            //重绘
            panel.repaint();
            logger.debug("data : {}", gameData);
        }
    }


}

/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.thread.task;

import cn.edu.hdu.tankbattle.context.GameContext;
import cn.edu.hdu.tankbattle.control.Control;
import cn.edu.hdu.tankbattle.dto.RealTimeGameData;
import cn.edu.hdu.tankbattle.model.GameResource;
import cn.edu.hdu.tankbattle.thread.GameTimeUnit;
import cn.edu.hdu.tankbattle.view.panel.GamePanel;

/**
 * UpdateThread...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public class UpdateTask implements Runnable {

    private GameContext gameContext;



    public UpdateTask(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public void run() {
        GamePanel panel = gameContext.getGamePanel();
        RealTimeGameData gameData = gameContext.getGameData();
        Control control = gameContext.getControl();
        GameResource resource = gameContext.getResource();
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
                    if (gameData.getLevel() == 5) {
                        gameData.setLevel(0);
                    }
                    if (gameData.getMyTankNum() >= 1 && gameData.getLevel() <= 4) {
                        gameData.setLevel(gameData.getLevel() + 1);
                        gameData.setDy(600);
                        control.nextGame(resource);
                    }
                }
                if (!gameData.isStop() && gameData.getDy() == 600) {
                    control.cleanAndCreate(resource.getMyTanks(),
                            resource.getEnemies(), resource.getMap(),
                            resource.getBombs()); // 从容器中移除死亡的对象
                    control.judge(resource.getMyTanks(), resource.getEnemies(),
                            resource.getMap(), resource.getBombs()); // 判断子弹是否击中坦克
                    control.judgeOverlap(resource.getMyTanks(),
                            resource.getEnemies(), resource.getMap()); // 判断坦克间是否出现重叠
                    control.myTankEvent(resource);

                }
            } else {
                if (gameData.getKy() == 21 && gameData.getKx() <= 654)
                    gameData.setKx(gameData.getKx() + 2);
                control.fontMove(resource, panel);
                GameTimeUnit.sleepMillis(100);

            }
            panel.repaint();
        }
    }


}

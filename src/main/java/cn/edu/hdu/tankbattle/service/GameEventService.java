/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.service;

import cn.edu.hdu.tankbattle.constant.GameConstants;
import cn.edu.hdu.tankbattle.context.GameContext;
import cn.edu.hdu.tankbattle.dto.RealTimeGameData;
import cn.edu.hdu.tankbattle.entity.EnemyTank;
import cn.edu.hdu.tankbattle.entity.MyTank;
import cn.edu.hdu.tankbattle.enums.DirectionEnum;
import cn.edu.hdu.tankbattle.util.GameTimeUnit;
import cn.edu.hdu.tankbattle.view.panel.GamePanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 游戏事件服务...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
@Service
public class GameEventService {

    @Autowired
    private GameContext context;
    @Autowired
    private TankControlService tankControlService;

    /**
     * 下一关
     */
    public void nextGame(RealTimeGameData resource) {
        RealTimeGameData data = context.getRealTimeGameData();
        resource.setMap(data.getLevel().getMap());
        //初始化敌方坦克
        int xStepLength = (GameConstants.GAME_PANEL_WIDTH - GameConstants.TANK_WIDTH) / (GameConstants.INIT_ENEMY_TANK_IN_MAP_NUM - 1);
        for (int i = 0; i < GameConstants.INIT_ENEMY_TANK_IN_MAP_NUM; i++) {
            EnemyTank enemy = new EnemyTank((i) * xStepLength + GameConstants.TANK_WIDTH / 2, -GameConstants.TANK_HEIGHT / 2, DirectionEnum.SOUTH);
            enemy.setActivate(Boolean.TRUE);
            enemy.setLocation(i);
            resource.getEnemies().add(enemy);
        }
        data.setEnemyTankNum(GameConstants.INIT_ENEMY_TANK_NUM);

        for (int i = 0; i < resource.getMyTanks().size(); i++) {
            MyTank myTank = resource.getMyTanks().get(i);
            myTank.setActivate(Boolean.TRUE);
            myTank.setX(GameConstants.GAME_PANEL_WIDTH / 2);
            myTank.setY(GameConstants.GAME_PANEL_HEIGHT + GameConstants.TANK_HEIGHT / 2);
        }
        tankControlService.enableEnemyTanks();
    }


    /**
     * 游戏暂停或恢复
     *
     * @param gameData 实时数据
     */
    public void pauseOrResume(RealTimeGameData gameData) {

        for (int i = 0; i < gameData.getMyTanks().size(); i++) {
            MyTank myTank = gameData.getMyTanks().get(i);
            if (!gameData.isStop()) {
                //暂停
                gameData.setStop(true);
                myTank.setSpeedVector(myTank.getSpeed());
                myTank.setSpeed(0);
                for (int j = 0; j < myTank.getBullets().size(); j++) {
                    myTank.getBullets().get(j).setSpeedVector(myTank.getBullets().get(j).getSpeed());
                    myTank.getBullets().get(j).setSpeed(0);
                }
                for (int j = 0; j < gameData.getEnemies().size(); j++) {
                    gameData.getEnemies().get(j).setSpeedVector(gameData.getEnemies().get(j).getSpeed());
                    gameData.getEnemies().get(j).setSpeed(0);
                    for (int k = 0; k < gameData.getEnemies().get(j).getBullets().size(); k++) {
                        gameData.getEnemies().get(j).getBullets().get(k).setSpeedVector(
                                gameData.getEnemies().get(j).getBullets().get(k).getSpeed());
                        gameData.getEnemies().get(j).getBullets().get(k).setSpeed(0);
                    }
                }
            } else {
                //恢复
                gameData.setStop(false);
                myTank.setSpeed(myTank.getSpeedVector());
                myTank.setSpeedVector(0);
                for (int j = 0; j < myTank.getBullets().size(); j++) {
                    myTank.getBullets().get(j).setSpeed(myTank.getBullets().get(j).getSpeedVector());
                }
                for (int j = 0; j < gameData.getEnemies().size(); j++) {
                    gameData.getEnemies().get(j).setSpeed(gameData.getEnemies().get(j).getSpeedVector());
                    gameData.getEnemies().get(j).setSpeedVector(0);
                    for (int k = 0; k < gameData.getEnemies().get(j).getBullets().size(); k++) {
                        gameData.getEnemies().get(j).getBullets().get(k).setSpeed(gameData.getEnemies().get(j).getBullets().get(k).getSpeedVector());
                    }
                }
            }
        }
    }

    /**
     * 游戏没开始时的欢迎图片上的字体移动
     *
     * @param panel GamePanel
     */
    public void fontDynamicMove(GamePanel panel) {
        RealTimeGameData data = context.getRealTimeGameData();
        int ky = data.getKy();
        if (ky > 0 && ky != 21)
            ky = ky - 8; // 当字体刚出来时，向上移动
        if (ky == 0) { // 当字体移到顶端时，以7的速度向下移动5步
            for (int i = 0; i < 5; i++) {
                ky = ky + 7;
                panel.repaint();
                GameTimeUnit.sleepMillis(100);
            }
            for (int i = 0; i < 4; i++) { // 以6的速度向上移动4步
                ky = ky - 6;
                panel.repaint();
                GameTimeUnit.sleepMillis(100);
            }
            for (int i = 0; i < 3; i++) { // 以5的速度向下移动
                ky = ky + 5;
                panel.repaint();
                GameTimeUnit.sleepMillis(100);
            }
            for (int i = 0; i < 2; i++) { // 以4的速度向上移动
                ky = ky - 4;
                panel.repaint();
                GameTimeUnit.sleepMillis(100);
            }
            ky = ky + 3;// 最后，字体的图片停留在（0，21）的地方
        }
        data.setKy(ky);
    }

}

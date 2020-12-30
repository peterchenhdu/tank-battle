/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.listener;

import cn.edu.hdu.tankbattle.constant.GameConstants;
import cn.edu.hdu.tankbattle.context.GameContext;
import cn.edu.hdu.tankbattle.dto.RealTimeGameData;
import cn.edu.hdu.tankbattle.entity.MyTank;
import cn.edu.hdu.tankbattle.enums.DirectionEnum;
import cn.edu.hdu.tankbattle.enums.StuffTypeEnum;
import cn.edu.hdu.tankbattle.service.GameEventService;
import cn.edu.hdu.tankbattle.service.TankControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 按键事件监听...
 *
 * @author chenpi
 * @since 2018/3/19 20:13
 */
@Component
public class KeyEventListener implements KeyListener {
    @Autowired
    private TankControlService tankControlService;
    @Autowired
    private GameEventService gameEventService;
    @Autowired
    private GameContext gameContext;

    /**
     * 按键按下操作
     *
     * @param e 事件
     */
    @Override
    public void keyPressed(KeyEvent e) {
        RealTimeGameData gameData = gameContext.getRealTimeGameData();

        //地图编辑模式，按键C操作
        if (gameData.getMapMakingMode() && e.getKeyCode() == KeyEvent.VK_C) {
            switch (gameData.getCurrentSelectedStuff()) {
                case BRICK:
                    gameData.setCurrentSelectedStuff(StuffTypeEnum.IRON);
                    break;
                case IRON:
                    gameData.setCurrentSelectedStuff(StuffTypeEnum.WATER);
                    break;
                case WATER:
                    gameData.setCurrentSelectedStuff(StuffTypeEnum.INVALID);
                    break;
                default:
                    gameData.setCurrentSelectedStuff(StuffTypeEnum.BRICK);
                    break;
            }
            return;
        }


        // 暂停游戏
        if (e.getKeyCode() == KeyEvent.VK_P) {
            gameEventService.pauseOrResume(gameData);
            return;
        }

        //我方坦克按键处理
        gameData.getMyTanks().forEach(myTank -> processMyTank(myTank, gameData, e));
    }

    /**
     * 处理我方坦克按键按下操作
     *
     * @param myTank 我方坦克
     * @param data   实时数据
     * @param e      事件
     */
    private void processMyTank(MyTank myTank, RealTimeGameData data, KeyEvent e) {
        //坦克已死亡
        if (!myTank.getLive()) {
            data.keyPressedDirect(false, false, false, false);
            return;
        }

        //向上按键
        if ((e.getKeyCode() == KeyEvent.VK_UP)) {
            myTank.setDirect(DirectionEnum.NORTH);
            data.keyPressedDirect(true, false, false, false);
            return;
        }

        //坦克处于地图外面(default, 600*600)
        if (myTank.getY() > GameConstants.GAME_PANEL_HEIGHT - GameConstants.TANK_HEIGHT / 2) {
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                myTank.setDirect(DirectionEnum.SOUTH);
                data.keyPressedDirect(false, true, false, false);
                break;
            case KeyEvent.VK_LEFT:
                myTank.setDirect(DirectionEnum.WEST);
                data.keyPressedDirect(false, false, true, false);
                break;
            case KeyEvent.VK_RIGHT:
                myTank.setDirect(DirectionEnum.EAST);
                data.keyPressedDirect(false, false, false, true);
                break;
            case KeyEvent.VK_X:
                //开火射击
                if (myTank.getBullets().size() < GameConstants.MY_TANK_BULLET_LIVE_NUM && data.getMyBulletNum() > 0) {
                    data.setMyBulletNum(data.getMyBulletNum() - 1);
                    tankControlService.shot(myTank);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 释放按键操作
     *
     * @param e 按键事件
     */
    @Override
    public void keyReleased(KeyEvent e) {
        RealTimeGameData data = gameContext.getRealTimeGameData();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                data.setUp(false);
                break;
            case KeyEvent.VK_DOWN:
                data.setDown(false);
                break;
            case KeyEvent.VK_LEFT:
                data.setLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                data.setRight(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}

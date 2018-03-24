/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.control;

import cn.edu.hdu.tankbattle.context.GameContext;
import cn.edu.hdu.tankbattle.dto.GameResource;
import cn.edu.hdu.tankbattle.dto.RealTimeGameData;
import cn.edu.hdu.tankbattle.util.RefUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;

/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/3/24 10:27
 */
@Service
public class CommandService {
    @Autowired
    private Control commond;

    @Autowired
    private GameContext context;

    public void executeByCmd(String cmd) {
        RefUtils.executeByMethodName(this, cmd, null, null);
    }

    public void stop() {
        RealTimeGameData gameData = context.getGameData();
        GameResource resource = gameData.getGameResource();
        commond.gameEventStop(resource);
    }

    public void start() {
        RealTimeGameData gameData = context.getGameData();
        GameResource resource = gameData.getGameResource();
        if (!gameData.isStart()) { // 还没开始
            context.startGame();// 已经开始了
            //this.setVisible(true);
        } else if (!gameData.isStop()
                && gameData.getMyTankNum() != 0) {
            // 暂停
            commond.gameEventStop(resource);
            JOptionPane.showMessageDialog(null, "游戏已经开始", "提示",
                    JOptionPane.INFORMATION_MESSAGE);
            // 恢复游戏
            commond.gameEventStop(resource);
        } else if (gameData.isStop()) {
            JOptionPane.showMessageDialog(null, "游戏已经开始", "提示",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (!gameData.isStop()
                && (gameData.getMyTankNum() == 0)) {
            context.startGame();
        }
    }

    public void exit() {
        RealTimeGameData gameData = context.getGameData();
        GameResource resource = gameData.getGameResource();
        // 暂停游戏
        commond.gameEventStop(resource);
        int select = JOptionPane.showConfirmDialog(null, "退出游戏吗？", "退出确认",
                JOptionPane.YES_NO_OPTION);
        if (select == JOptionPane.OK_OPTION) {
            // 退出游戏
            System.exit(0);
        } else {
            // 恢复游戏
            commond.gameEventStop(resource);
        }
    }

    public void again() {
        RealTimeGameData gameData = context.getGameData();
        GameResource resource = gameData.getGameResource();
        if (gameData.isStart()) {
            if (gameData.isStop()) {
                // 先恢复
                commond.gameEventStop(resource);
            }

            int select = JOptionPane.showConfirmDialog(null,
                    "确认重来", "选择确认",
                    JOptionPane.OK_CANCEL_OPTION);
            if (select == JOptionPane.OK_OPTION) {
                if (gameData.isStart()) {
                    // 恢复游戏
                    commond.gameEventStop(resource);
                }
                context.startLevel(gameData.getLevel());
            }
        }
    }

    public void first() {
        selectLevel(1);
    }

    public void second() {
        selectLevel(2);
    }

    public void third() {
        selectLevel(3);
    }

    public void fourth() {
        selectLevel(4);
    }

    public void fifth() {
        selectLevel(5);
    }

    public void selectLevel(int level) {
        RealTimeGameData gameData = context.getGameData();
        GameResource resource = gameData.getGameResource();
        if (gameData.isStart()) {
            if (!gameData.isStop()) {// 暂停游戏
                commond.gameEventStop(resource);
            }
            int select = JOptionPane.showConfirmDialog(null,
                    "您选择的是第" + level + "关，点击确定开始游戏", "选择确认",
                    JOptionPane.OK_CANCEL_OPTION);
            if (select == JOptionPane.OK_OPTION) {
                if (gameData.isStart()) {
                    // 恢复游戏
                    commond.gameEventStop(resource);
                }
                context.startLevel(level);
            } else {
                if (gameData.isStart()) {
                    // 恢复游戏
                    commond.gameEventStop(resource);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "请先点击游戏Game菜单下的开始游戏", "提示",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void help() {
        JOptionPane.showMessageDialog(
                null,
                "坦克移动:---方向键---\n坦克开炮:------X键------\n每关只要打掉敌人8辆坦克即可过关，\n第五关有5辆坦克同时在地图上",
                "游戏帮助", JOptionPane.INFORMATION_MESSAGE);
    }

    public void about() {
        JOptionPane.showMessageDialog(null,
                "JAVA坦克大战CP版1.0\n作者:hdu-chenpi\n", "关于游戏",
                JOptionPane.INFORMATION_MESSAGE);
    }
}

/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.view.menu;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * GameMenu...
 *
 * @author chenpi
 * @since 2018/3/19 19:36
 */
public class GameMenu extends JMenu {
    private static final long serialVersionUID = -3078540026626514620L;

    public GameMenu(ActionListener listener) {
        super("游戏/Game");
        JMenuItem gameStart = new JMenuItem("开始游戏");
        JMenuItem startAgain = new JMenuItem("重新开始");
        JMenuItem gameStop = new JMenuItem("暂停/恢复");
        JMenuItem gameExit = new JMenuItem("退出游戏");

        gameStart.setActionCommand("start");
        startAgain.setActionCommand("again");
        gameStop.setActionCommand("stop");
        gameExit.setActionCommand("exit");

        gameStart.addActionListener(listener);
        startAgain.addActionListener(listener);
        gameExit.addActionListener(listener);
        gameStop.addActionListener(listener);

        this.add(gameStart);
        this.add(startAgain);
        this.add(gameStop);
        this.add(gameExit);
    }
}

/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.view.menu;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * HelpMenu...
 *
 * @author chenpi
 * @since 2018/3/19 19:36
 */
public class HelpMenu extends JMenu {
    public HelpMenu(ActionListener listener) {
        super("游戏帮助");
        JMenuItem gameHelp = new JMenuItem("游戏帮助");
        JMenuItem aboutGame = new JMenuItem("关于游戏");

        gameHelp.setActionCommand("gameHelp");
        aboutGame.setActionCommand("aboutGame");

        gameHelp.addActionListener(listener);
        aboutGame.addActionListener(listener);

        this.add(gameHelp);
        this.add(aboutGame);
    }
}

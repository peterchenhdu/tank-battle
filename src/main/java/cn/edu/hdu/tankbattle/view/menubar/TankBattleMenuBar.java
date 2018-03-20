/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.view.menubar;

import cn.edu.hdu.tankbattle.view.menu.GameMenu;
import cn.edu.hdu.tankbattle.view.menu.HelpMenu;
import cn.edu.hdu.tankbattle.view.menu.LevelMenu;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * TankBattleMenuBar...
 *
 * @author chenpi
 * @since 2018/3/19 19:36
 */
public class TankBattleMenuBar extends JMenuBar {

    private static final long serialVersionUID = -4010376438320829163L;
    private GameMenu gameMenu;
    private LevelMenu levelMenu;
    private HelpMenu helpMenu;

    public TankBattleMenuBar(ActionListener listener) {
        super();

        gameMenu = new GameMenu(listener);
        levelMenu = new LevelMenu(listener);
        helpMenu = new HelpMenu(listener);
        // --游戏/Game--菜单
        this.add(gameMenu);
        // --选择关卡--菜单
        this.add(levelMenu);
        // --游戏帮助--菜单
        this.add(helpMenu);
    }

    public GameMenu getGameMenu() {
        return gameMenu;
    }

    public LevelMenu getLevelMenu() {
        return levelMenu;
    }

    @Override
    public HelpMenu getHelpMenu() {
        return helpMenu;
    }
}

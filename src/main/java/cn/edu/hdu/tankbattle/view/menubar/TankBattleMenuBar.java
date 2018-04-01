/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.view.menubar;

import cn.edu.hdu.tankbattle.view.menu.GameMenu;
import cn.edu.hdu.tankbattle.view.menu.HelpMenu;
import cn.edu.hdu.tankbattle.view.menu.LevelMenu;
import cn.edu.hdu.tankbattle.view.menu.MapMenu;

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
    private MapMenu mapMenu;

    public TankBattleMenuBar(ActionListener listener) {
        super();

        gameMenu = new GameMenu(listener);
        levelMenu = new LevelMenu(listener);
        helpMenu = new HelpMenu(listener);
        mapMenu = new MapMenu(listener);

        this.add(gameMenu);
        this.add(levelMenu);
        this.add(helpMenu);
        this.add(mapMenu);
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

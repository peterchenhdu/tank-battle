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
    public TankBattleMenuBar(ActionListener listener) {
        super();

        // --游戏/Game--菜单
        this.add(new GameMenu(listener));
        // --选择关卡--菜单
        this.add(new LevelMenu(listener));
        // --游戏帮助--菜单
        this.add(new HelpMenu(listener));
    }
}

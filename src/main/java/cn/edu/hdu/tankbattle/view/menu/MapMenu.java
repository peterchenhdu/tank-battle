/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.view.menu;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/4/1 9:57
 */
public class MapMenu extends JMenu {
    public MapMenu(ActionListener listener) {
        super("自定义地图");
        JMenuItem createMap = new JMenuItem("地图制作");
        JMenuItem saveMap = new JMenuItem("地图保存");

        createMap.setActionCommand("createMap");
        saveMap.setActionCommand("saveMap");

        createMap.addActionListener(listener);
        saveMap.addActionListener(listener);

        this.add(createMap);
        this.add(saveMap);
    }
}

/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.listener;

import cn.edu.hdu.tankbattle.service.MenuActionService;
import cn.edu.hdu.tankbattle.util.RefUtils;
import cn.edu.hdu.tankbattle.view.menu.CustomMapMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 菜单事件监听...
 *
 * @author chenpi
 * @since 2018/3/19 20:18
 */
@Component
public class MenuActionEventListener implements ActionListener {
    private Logger logger = LoggerFactory.getLogger(MenuActionEventListener.class);
    @Autowired
    private MenuActionService menuActionService;

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (!(o instanceof JMenuItem)) {
            logger.info("nothing can do with command:" + e.getActionCommand());
            return;
        }

        if (CustomMapMenu.CUSTOM_MAP_ACTION_COMMAND.equals(e.getActionCommand())) {
            //加载自定义地图菜单操作
            menuActionService.loadCustomMap(((JMenuItem) o).getText());
        } else {
            //其它菜单操作
            RefUtils.executeByMethodName(menuActionService, e.getActionCommand(), null, null);
        }

    }
}

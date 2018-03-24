/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.listener;

import cn.edu.hdu.tankbattle.control.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/3/19 20:18
 */
@Component
public class MenuActionListener implements ActionListener {
    @Autowired
    private CommandService commandService;

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("stop".equals(e.getActionCommand())) {
            commandService.stop();
        } else if ("start".equals(e.getActionCommand())) {
            commandService.start();
        } else if ("exit".equals(e.getActionCommand())) {
            commandService.exit();
        } else if ("again".equals(e.getActionCommand())) {
            commandService.again();
        } else if ("first".equals(e.getActionCommand())) {
            commandService.selectLevel(1);
        } else if ("second".equals(e.getActionCommand())) {
            commandService.selectLevel(2);
        } else if ("third".equals(e.getActionCommand())) {
            commandService.selectLevel(3);
        } else if ("fourth".equals(e.getActionCommand())) {
            commandService.selectLevel(4);
        } else if ("fifth".equals(e.getActionCommand())) {
            commandService.selectLevel(5);
        } else if ("help".equals(e.getActionCommand())) {
            commandService.help();
        } else if ("about".equals(e.getActionCommand())) {
            commandService.about();
        }
    }
}

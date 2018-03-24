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
        commandService.executeByCmd(e.getActionCommand());

    }
}

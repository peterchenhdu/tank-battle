/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.view.frame;

import cn.edu.hdu.tankbattle.view.panel.GamePanel;
import cn.edu.hdu.tankbattle.view.menubar.TankBattleMenuBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

/**
 * GameFrame...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
@Component
public class GameFrame extends JFrame {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1176914786963603304L;
    /**
     * 游戏面板
     */
    @Autowired
    private GamePanel tankGamePanel;


    @PostConstruct
    public void init() {


        this.addKeyListener(tankGamePanel); // 游戏面板来作为按键侦听器


        // 菜单条
        this.setJMenuBar(new TankBattleMenuBar(tankGamePanel));


        // 将panel添加到Frame
        this.add(tankGamePanel);


        this.setSize(800, 700);
        this.setTitle("MyTankGame");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        // 显示器屏幕大小
        Dimension screenSizeInfo = Toolkit.getDefaultToolkit().getScreenSize();
        int leftTopX = ((int) screenSizeInfo.getWidth() - this.getWidth()) / 2;
        int leftTopY = ((int) screenSizeInfo.getHeight() - this.getHeight()) / 2;

        // 设置显示的位置在屏幕中间
        this.setLocation(leftTopX, leftTopY);
    }

}
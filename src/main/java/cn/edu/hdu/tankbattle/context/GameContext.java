/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.context;

import cn.edu.hdu.tankbattle.control.Control;
import cn.edu.hdu.tankbattle.control.Painter;
import cn.edu.hdu.tankbattle.control.UpdateTask;
import cn.edu.hdu.tankbattle.listener.MainFrameKeyListener;
import cn.edu.hdu.tankbattle.listener.MenuActionListener;
import cn.edu.hdu.tankbattle.model.GameResource;
import cn.edu.hdu.tankbattle.view.frame.GameFrame;
import cn.edu.hdu.tankbattle.view.menubar.TankBattleMenuBar;
import cn.edu.hdu.tankbattle.view.panel.GamePanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/3/19 20:32
 */
@Component
public class GameContext {
    private GameFrame gameFrame;
    private TankBattleMenuBar tankBattleMenuBar;
    private GamePanel gamePanel;

    @Autowired
    private MainFrameKeyListener mainFrameKeyListener;
    @Autowired
    private MenuActionListener menuActionListener;
    @Autowired
    private Control control;
    @Autowired
    private GameResource gameResource;
    @Autowired
    private Painter painter;

    @PostConstruct
    public void init() {
        this.gameFrame = new GameFrame();
        this.tankBattleMenuBar = new TankBattleMenuBar(menuActionListener);
        this.gamePanel = new GamePanel(painter);

        this.gameFrame.setJMenuBar(this.tankBattleMenuBar);
        this.gameFrame.add(this.gamePanel);

        this.gameFrame.addKeyListener(mainFrameKeyListener);
        this.gameFrame.init();


        UpdateTask updateTask = new UpdateTask(control, gameResource, gamePanel);
        new Thread(updateTask).start();
    }


    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public TankBattleMenuBar getTankBattleMenuBar() {
        return tankBattleMenuBar;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public Control getControl() {
        return control;
    }
}

/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.context;

import cn.edu.hdu.tankbattle.control.Control;
import cn.edu.hdu.tankbattle.control.PanelPainter;
import cn.edu.hdu.tankbattle.model.EnemyTank;
import cn.edu.hdu.tankbattle.model.MyTank;
import cn.edu.hdu.tankbattle.model.Tank;
import cn.edu.hdu.tankbattle.thread.task.UpdateTask;
import cn.edu.hdu.tankbattle.listener.MainFrameKeyListener;
import cn.edu.hdu.tankbattle.listener.MenuActionListener;
import cn.edu.hdu.tankbattle.model.GameResource;
import cn.edu.hdu.tankbattle.view.frame.GameFrame;
import cn.edu.hdu.tankbattle.view.menubar.TankBattleMenuBar;
import cn.edu.hdu.tankbattle.view.panel.GamePanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Frame
     */
    private GameFrame gameFrame;
    /**
     * 菜单条
     */
    private TankBattleMenuBar tankBattleMenuBar;
    /**
     * Panel
     */
    private GamePanel gamePanel;
    private GameResource resource;


    @Autowired
    private MainFrameKeyListener mainFrameKeyListener;
    @Autowired
    private MenuActionListener menuActionListener;
    @Autowired
    private Control control;

    @Autowired
    private PanelPainter painter;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @PostConstruct
    public void init() {
        logger.info("GameContext...");
        loadResource();

        //创建Frame
        this.gameFrame = new GameFrame();
        //创建MenuBar
        this.tankBattleMenuBar = new TankBattleMenuBar(menuActionListener);
        //创建Panel
        this.gamePanel = new GamePanel(painter);

        //设置Frame的MenuBar
        this.gameFrame.setJMenuBar(this.tankBattleMenuBar);
        //添加Panel到Frame中
        this.gameFrame.add(this.gamePanel);
        //设置Frame的KeyListener
        this.gameFrame.addKeyListener(mainFrameKeyListener);
        //设置Frame为可见
        this.gameFrame.setVisible(true);

        logger.info("execute UpdateTask...");
        taskExecutor.execute(new UpdateTask(control, resource, gamePanel));
        logger.info("game start success...");

    }

    private void loadResource() {
        resource = new GameResource();
        resource.reset();
        control.setDy(600);

        control.setMapByLevel(control.getLevel(), resource);

        for (int i = 0; i < 5; i++) {
            EnemyTank enemy = new EnemyTank((i) * 140 + 20, -20, Tank.SOUTH); // 创建一个敌人坦克对象
            enemy.setLocation(i);
            resource.getEnemies().add(enemy); // 将该坦克加入敌人坦克容器中 //将该子弹加入该坦克的子弹容器中
        }
        for (int i = 0; i < 1; i++) {
            MyTank myTank = new MyTank(300, 620, Tank.NORTH); // 创建一个我的坦克
            resource.getMyTanks().add(myTank); // 将我的坦克加入我的坦克容器中
        }
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

    public GameResource getResource() {
        return resource;
    }
}

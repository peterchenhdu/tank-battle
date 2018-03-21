/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.context;

import cn.edu.hdu.tankbattle.constant.GameConstants;
import cn.edu.hdu.tankbattle.control.Control;
import cn.edu.hdu.tankbattle.control.PanelPainter;
import cn.edu.hdu.tankbattle.dto.RealTimeGameData;
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
    /**
     * Game Resource
     */
    private GameResource resource;

    /**
     * RealTimeGameData
     */
    private RealTimeGameData gameData;

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
        initGameData();


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
        taskExecutor.execute(new UpdateTask(this));
        logger.info("game start success...");

    }

    private void loadResource() {
        resource = new GameResource();
        resource.reset();




        for (int i = 0; i < 5; i++) {
            EnemyTank enemy = new EnemyTank((i) * 140 + 20, -20, Tank.SOUTH);
            enemy.setLocation(i);
            resource.getEnemies().add(enemy);
        }
        for (int i = 0; i < 1; i++) {
            MyTank myTank = new MyTank(300, 620, Tank.NORTH);
            resource.getMyTanks().add(myTank);
        }
    }

    public void initGameData(){
        gameData = new RealTimeGameData();
        gameData.setEnemyTankNum(8);
        gameData.setMyTankNum(4);
        gameData.setMyBulletNum(GameConstants.MY_TANK_INIT_BULLET_NUM);
        gameData.setBeKilled(0);
        gameData.setDy(600);

        control.setMapByLevel(gameData.getLevel(), resource);
        logger.info("init Game Data...");
    }


    public void startGame() {
        gameData.setStart(Boolean.TRUE);
        resource.getEnemies().forEach(t->t.setActivate(Boolean.TRUE));
        resource.getMyTanks().forEach(t->t.setActivate(Boolean.TRUE));
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

    public RealTimeGameData getGameData() {
        return gameData;
    }

    public Control getControl() {
        return control;
    }
}

/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.context;

import cn.edu.hdu.tankbattle.constant.GameConstants;
import cn.edu.hdu.tankbattle.control.Control;
import cn.edu.hdu.tankbattle.control.PanelPainter;
import cn.edu.hdu.tankbattle.dto.RealTimeGameData;
import cn.edu.hdu.tankbattle.enums.LevelEnum;
import cn.edu.hdu.tankbattle.model.EnemyTank;
import cn.edu.hdu.tankbattle.model.MyTank;
import cn.edu.hdu.tankbattle.model.Tank;
import cn.edu.hdu.tankbattle.thread.executor.TaskExecutor;
import cn.edu.hdu.tankbattle.thread.task.UpdateTask;
import cn.edu.hdu.tankbattle.listener.MainFrameKeyListener;
import cn.edu.hdu.tankbattle.listener.MenuActionListener;
import cn.edu.hdu.tankbattle.dto.GameResource;
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
    @Autowired
    private TaskExecutor threadTaskExecutor;

    @PostConstruct
    public void init() {
        logger.info("GameContext...");

        initGameData(1);


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
        threadTaskExecutor.startEnemyTankThreads();
        logger.info("game start success...");

    }


    public void initGameData(int level) {

        GameResource resource = new GameResource();


        resource.reset();

        for (int i = 0; i < GameConstants.INIT_ENEMY_TANK_IN_MAP_NUM; i++) {
            EnemyTank enemy = new EnemyTank((i) * 140 + 20, -20, Tank.SOUTH);
            enemy.setLocation(i);
            resource.getEnemies().add(enemy);
        }
        for (int i = 0; i < 1; i++) {
            MyTank myTank = new MyTank(300, 620, Tank.NORTH);
            resource.getMyTanks().add(myTank);
        }

        resource.setMap(LevelEnum.getByLevel(level).getMap());

        gameData = new RealTimeGameData();
        gameData.setGameResource(resource);
        gameData.setEnemyTankNum(GameConstants.INIT_ENEMY_TANK_NUM);
        gameData.setMyTankNum(GameConstants.INIT_MY_TANK_NUM);
        gameData.setMyBulletNum(GameConstants.MY_TANK_INIT_BULLET_NUM);
        gameData.setBeKilled(0);
        gameData.setDy(600);
        gameData.setLevel(level);
        logger.info("init Game Data...");
    }


    public void reset(int level) {
        GameResource resource = gameData.getGameResource();
        resource.reset();

        for (int i = 0; i < GameConstants.INIT_ENEMY_TANK_IN_MAP_NUM; i++) {
            EnemyTank enemy = new EnemyTank((i) * 140 + 20, -20, Tank.SOUTH);
            enemy.setLocation(i);
            resource.getEnemies().add(enemy);
        }
        for (int i = 0; i < 1; i++) {
            MyTank myTank = new MyTank(300, 620, Tank.NORTH);
            resource.getMyTanks().add(myTank);
        }

        resource.setMap(LevelEnum.getByLevel(level).getMap());

        gameData = new RealTimeGameData();
        gameData.setGameResource(resource);
        gameData.setEnemyTankNum(GameConstants.INIT_ENEMY_TANK_NUM);
        gameData.setMyTankNum(GameConstants.INIT_MY_TANK_NUM);
        gameData.setMyBulletNum(GameConstants.MY_TANK_INIT_BULLET_NUM);
        gameData.setBeKilled(0);
        gameData.setDy(600);
        gameData.setLevel(level);
        logger.info("init Game Data...");
    }


    public void startGame() {
        gameData.setStart(Boolean.TRUE);
        gameData.getGameResource().getEnemies().forEach(t -> t.setActivate(Boolean.TRUE));
        gameData.getGameResource().getMyTanks().forEach(t -> t.setActivate(Boolean.TRUE));
    }

    public void startLevel(int level) {
        reset(level);
        this.startGame();
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

    public RealTimeGameData getGameData() {
        return gameData;
    }

    public Control getControl() {
        return control;
    }


}

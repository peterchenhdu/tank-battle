/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.context;

import cn.edu.hdu.tankbattle.constant.GameConstants;
import cn.edu.hdu.tankbattle.dto.RealTimeGameData;
import cn.edu.hdu.tankbattle.entity.EnemyTank;
import cn.edu.hdu.tankbattle.entity.MyTank;
import cn.edu.hdu.tankbattle.enums.DirectionEnum;
import cn.edu.hdu.tankbattle.enums.LevelEnum;
import cn.edu.hdu.tankbattle.listener.KeyEventListener;
import cn.edu.hdu.tankbattle.listener.MenuActionEventListener;
import cn.edu.hdu.tankbattle.listener.MouseEventListener;
import cn.edu.hdu.tankbattle.service.*;
import cn.edu.hdu.tankbattle.task.GameDataUpdateTask;
import cn.edu.hdu.tankbattle.view.frame.GameFrame;
import cn.edu.hdu.tankbattle.view.menubar.TankBattleMenuBar;
import cn.edu.hdu.tankbattle.view.panel.GamePanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 游戏上下文环境...
 *
 * @author chenpi
 * @since 2018/3/19 20:32
 */
@Component
public class GameContext {
    private Logger logger = LoggerFactory.getLogger(GameContext.class);
    /**
     * 游戏Frame
     */
    private GameFrame gameFrame;
    /**
     * 游戏画板
     */
    private GamePanel gamePanel;
    /**
     * 游戏实时数据
     */
    private RealTimeGameData realTimeGameData;

    @Autowired
    private KeyEventListener keyEventListener;
    @Autowired
    private MenuActionEventListener menuActionEventListener;
    @Autowired
    private MouseEventListener mouseEventListener;


    @Autowired
    private GameEventService gameEventService;
    @Autowired
    private PaintService paintService;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private TankControlService tankControlService;
    @Autowired
    private StateFlushService stateFlushService;
    @Autowired
    private ComputingService computingService;
    @EventListener
    public void init(ApplicationReadyEvent applicationReadyEvent) {
        logger.info("Application Started..., applicationReadyEvent = {}", applicationReadyEvent);

        //初始化第一关
        initLevelData(LevelEnum.FIRST_LEVEL);

        this.gamePanel = new GamePanel(paintService, mouseEventListener);

        this.gameFrame = new GameFrame();
        this.gameFrame.setJMenuBar(new TankBattleMenuBar(menuActionEventListener));
        this.gameFrame.add(this.gamePanel);
        this.gameFrame.addKeyListener(keyEventListener);
        this.gameFrame.setVisible(true);


        logger.info("execute UpdateTask...");
        taskExecutor.execute(new GameDataUpdateTask(this));
        logger.info("game start success...");
    }


    /**
     * 初始化指定关卡游戏数据
     *
     * @param level 关卡
     */
    private void initLevelData(LevelEnum level) {
        logger.info("init Game Data start...");
        //初始化实时数据
        realTimeGameData = new RealTimeGameData();

        //初始化敌方坦克
        int xStepLength = (GameConstants.GAME_PANEL_WIDTH - GameConstants.TANK_WIDTH) / (GameConstants.INIT_ENEMY_TANK_IN_MAP_NUM - 1);
        for (int i = 0; i < GameConstants.INIT_ENEMY_TANK_IN_MAP_NUM; i++) {
            EnemyTank enemy = new EnemyTank(i * xStepLength + GameConstants.TANK_WIDTH / 2, -GameConstants.TANK_HEIGHT / 2, DirectionEnum.SOUTH);
            enemy.setLocation(i);
            realTimeGameData.getEnemies().add(enemy);
        }

        //初始化我方坦克
        for (int i = 0; i < GameConstants.INIT_MY_TANK_IN_MAP_NUM; i++) {
            MyTank myTank = new MyTank(GameConstants.GAME_PANEL_WIDTH / 2, GameConstants.GAME_PANEL_HEIGHT + GameConstants.TANK_HEIGHT / 2, DirectionEnum.NORTH);
            realTimeGameData.getMyTanks().add(myTank);
        }

        realTimeGameData.setMap(level.getMap());
        //realTimeGameData.setMap(new Map(MapParser.getMapFromXml()));
        realTimeGameData.setEnemyTankNum(GameConstants.INIT_ENEMY_TANK_NUM);
        realTimeGameData.setMyTankNum(GameConstants.INIT_MY_TANK_NUM);
        realTimeGameData.setMyBulletNum(GameConstants.MY_TANK_INIT_BULLET_NUM);
        realTimeGameData.setBeKilled(0);
        realTimeGameData.setLevel(level);
        logger.info("init Game Data end...");
    }

    /**
     * 开始游戏
     */
    public void startGame() {
        realTimeGameData.setStart(Boolean.TRUE);
        realTimeGameData.getEnemies().forEach(t -> t.setActivate(Boolean.TRUE));
        realTimeGameData.getMyTanks().forEach(t -> t.setActivate(Boolean.TRUE));
        tankControlService.enableEnemyTanks();
    }

    /**
     * 从某个关卡开始游戏
     * @param level 关卡
     */
    public void startLevel(LevelEnum level) {
        realTimeGameData.clear();
        initLevelData(level);
        startGame();
    }

    public void clean() {
        realTimeGameData.clear();
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public RealTimeGameData getRealTimeGameData() {
        return realTimeGameData;
    }

    public GameEventService getGameEventService() {
        return gameEventService;
    }

    public StateFlushService getStateFlushService() {
        return stateFlushService;
    }

    public TankControlService getTankControlService() {
        return tankControlService;
    }

    public ComputingService getComputingService() {
        return computingService;
    }
}

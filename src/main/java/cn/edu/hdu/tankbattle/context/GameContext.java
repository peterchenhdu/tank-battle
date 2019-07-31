/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.context;

import cn.edu.hdu.tankbattle.constant.GameConstants;
import cn.edu.hdu.tankbattle.enums.DirectionEnum;
import cn.edu.hdu.tankbattle.listener.MainFrameMouseListener;
import cn.edu.hdu.tankbattle.service.GameEventService;
import cn.edu.hdu.tankbattle.service.PaintService;
import cn.edu.hdu.tankbattle.dto.RealTimeGameData;
import cn.edu.hdu.tankbattle.enums.LevelEnum;
import cn.edu.hdu.tankbattle.entity.EnemyTank;
import cn.edu.hdu.tankbattle.entity.MyTank;
import cn.edu.hdu.tankbattle.thread.executor.TaskExecutor;
import cn.edu.hdu.tankbattle.thread.task.GameUpdateTask;
import cn.edu.hdu.tankbattle.listener.MainFrameKeyListener;
import cn.edu.hdu.tankbattle.listener.MenuActionListener;
import cn.edu.hdu.tankbattle.util.LogUtils;
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
    private RealTimeGameData realTimeGameData;

    @Autowired
    private MainFrameKeyListener mainFrameKeyListener;
    @Autowired
    private MenuActionListener menuActionListener;
    @Autowired
    private MainFrameMouseListener mainFrameMouseListener;
    @Autowired
    private GameEventService control;
    @Autowired
    private PaintService paintService;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private TaskExecutor threadTaskExecutor;

    @EventListener
    public void init(ApplicationReadyEvent applicationStartedEvent) {
        LogUtils.info("Application Started... applicationStartedEvent={}", applicationStartedEvent);

        //初始化第一关
        initGameData(1);

        this.gameFrame = new GameFrame();
        this.tankBattleMenuBar = new TankBattleMenuBar(menuActionListener);
        this.gamePanel = new GamePanel(paintService);

        this.gameFrame.setJMenuBar(this.tankBattleMenuBar);
        this.gameFrame.add(this.gamePanel);
        this.gameFrame.addKeyListener(mainFrameKeyListener);
        this.gamePanel.addMouseListener(mainFrameMouseListener);

        this.gameFrame.setVisible(true);

        logger.info("execute UpdateTask...");
        taskExecutor.execute(new GameUpdateTask(this));
        logger.info("game start success...");
    }


    /**
     * 初始化指定关卡游戏数据
     *
     * @param level 关卡
     */
    private void initGameData(int level) {
        realTimeGameData = new RealTimeGameData();

        for (int i = 0; i < GameConstants.INIT_ENEMY_TANK_IN_MAP_NUM; i++) {
            EnemyTank enemy = new EnemyTank((i) * 140 + 20, -20, DirectionEnum.SOUTH);
            enemy.setLocation(i);
            realTimeGameData.getEnemies().add(enemy);
        }
        for (int i = 0; i < 1; i++) {
            MyTank myTank = new MyTank(300, 620, DirectionEnum.NORTH);
            realTimeGameData.getMyTanks().add(myTank);
        }

        realTimeGameData.setMap(LevelEnum.getByLevel(level).getMap());
        //realTimeGameData.setMap(new Map(MapParser.getMapFromXml()));
        realTimeGameData.setEnemyTankNum(GameConstants.INIT_ENEMY_TANK_NUM);
        realTimeGameData.setMyTankNum(GameConstants.INIT_MY_TANK_NUM);
        realTimeGameData.setMyBulletNum(GameConstants.MY_TANK_INIT_BULLET_NUM);
        realTimeGameData.setBeKilled(0);
        realTimeGameData.setDy(600);
        realTimeGameData.setLevel(level);
        threadTaskExecutor.startEnemyTankThreads();
        logger.info("init Game Data end...");
    }


    private void reset(int level) {
        realTimeGameData.reset();
        initGameData(level);
        logger.info("init Game Data...");
    }


    public void startGame() {
        realTimeGameData.setStart(Boolean.TRUE);
        realTimeGameData.getEnemies().forEach(t -> t.setActivate(Boolean.TRUE));
        realTimeGameData.getMyTanks().forEach(t -> t.setActivate(Boolean.TRUE));
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

    public RealTimeGameData getRealTimeGameData() {
        return realTimeGameData;
    }

    public GameEventService getControl() {
        return control;
    }

}

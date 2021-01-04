package cn.edu.hdu.tankbattle.service;

import cn.edu.hdu.tankbattle.constant.GameConstants;
import cn.edu.hdu.tankbattle.context.GameContext;
import cn.edu.hdu.tankbattle.dto.RealTimeGameData;
import cn.edu.hdu.tankbattle.entity.*;
import cn.edu.hdu.tankbattle.enums.DirectionEnum;
import cn.edu.hdu.tankbattle.enums.StuffTypeEnum;
import cn.edu.hdu.tankbattle.resource.map.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * 状态刷新服务
 *
 * @author chenpi
 * @since 2020/12/30
 **/
@Service
public class StateFlushService {
    @Autowired
    private GameContext context;
    @Autowired
    private TankControlService tankControlService;
    @Autowired
    private ComputingService computingService;

    /**
     * 更新敌方坦克状态
     */
    public void refreshEnemyTankState() {
        RealTimeGameData realTimeGameData = context.getRealTimeGameData();
        Vector<EnemyTank> enemies = realTimeGameData.getEnemies();
        Vector<MyTank> myTanks = realTimeGameData.getMyTanks();
        Map map = realTimeGameData.getMap();

        if (myTanks.isEmpty()) {
            return;
        }
        MyTank myTank = myTanks.get(0);

        enemies.forEach(enemyTank -> {
            enemyTank.setMyTankDirect(myTank.getDirect());
            tankControlService.enemyFindAndKill(enemyTank, myTank, map);
        });

    }


    /**
     * 刷新子弹状态...
     */
    public void refreshBulletState() {
        RealTimeGameData realTimeGameData = context.getRealTimeGameData();
        Vector<MyTank> myTanks = realTimeGameData.getMyTanks();
        Vector<EnemyTank> enemies = realTimeGameData.getEnemies();
        Vector<Bomb> bombs = realTimeGameData.getBombs();
        Map map = realTimeGameData.getMap();

        if (myTanks.isEmpty()) {
            enemies.forEach(enemyTank -> enemyTank.setShot(false));
            return;
        }
        MyTank myTank = myTanks.get(0);

        enemies.forEach(enemyTank -> {
            //敌方坦克子弹击中情况
            enemyTank.getBullets().forEach(eb -> processBulletShotEvent(eb, myTank, bombs, map));

            //我方坦克子弹击中情况
            myTank.getBullets().forEach(mb -> processBulletShotEvent(mb, enemyTank, bombs, map));

            //敌方坦克子弹与我方坦克碰撞
            myTank.getBullets().forEach(mb -> enemyTank.getBullets().stream().filter(eb -> computingService.isHitting(mb, eb)).forEach(eb -> {
                mb.setLive(false);
                eb.setLive(false);
                Bomb bomb = new Bomb(mb.getX(), mb.getY());
                bomb.setL(20);
                bombs.add(bomb);
            }));

        });


    }

    /**
     * 处理子弹击中坦克情况
     *
     * @param b     子弹
     * @param tank  坦克
     * @param bombs 炸弹
     */
    private void processBulletShotEvent(Bullet b, Tank tank, Vector<Bomb> bombs, Map map) {
        if (computingService.isHitting(b, tank)) {
            afterShotTank(b, tank, bombs);
        }
        map.getBricks().stream().filter(brick -> computingService.isHitting(b, brick)).forEach(brick -> afterShotStuff(b, brick, bombs));
        map.getIrons().stream().filter(iron -> computingService.isHitting(b, iron)).forEach(iron -> afterShotStuff(b, iron, bombs));
    }


    /**
     * 刷新重叠状态
     */
    public void refreshOverlapState() {
        RealTimeGameData realTimeGameData = context.getRealTimeGameData();
        Vector<MyTank> myTanks = realTimeGameData.getMyTanks();
        Vector<EnemyTank> enemyTanks = realTimeGameData.getEnemies();
        Map map = realTimeGameData.getMap();
        Vector<Brick> bricks = map.getBricks();
        Vector<Iron> irons = map.getIrons();
        Vector<Water> waters = map.getWaters();

        //我方坦克
        myTanks.forEach(myTank -> {
            //初始化都不重叠
            myTank.setOverlapAndCanNotShot(false);
            myTank.setOverlapCanShot(false);
            //设置重叠可射击标志
            myTank.setOverlapCanShot(computingService.isMyTankOverlap(myTank, enemyTanks));
            bricks.stream().filter(brick -> computingService.isTankOverlap(myTank, brick, 20 + 10)).forEach(brick -> myTank.setOverlapCanShot(true));
            //设置重叠不可射击标志
            irons.stream().filter(iron -> computingService.isTankOverlap(myTank, iron, 20 + 10)).forEach(iron -> myTank.setOverlapAndCanNotShot(true));
            waters.stream().filter(water -> computingService.isTankOverlap(myTank, water, 20 + 10)).forEach(water -> myTank.setOverlapAndCanNotShot(true));
        });

        //敌方坦克
        enemyTanks.forEach(enemyTank -> {
            //初始化都不重叠，前面无物体阻挡
            enemyTank.setOverlapAndCanNotShot(false);
            enemyTank.setOverlapCanShot(false);
            enemyTank.setFrontStuff(StuffTypeEnum.INVALID);

            enemyTank.setOverlapCanShot(computingService.isEnemyTankOverlap(enemyTank, enemyTanks, myTanks));

            bricks.stream().filter(brick -> computingService.isTankOverlap(enemyTank, brick, 20 + 10)).forEach(brick -> {
                if (isBrickInFront(brick, enemyTank)) {
                    enemyTank.setFrontStuff(StuffTypeEnum.BRICK);
                    enemyTank.setOverlapCanShot(true);
                    enemyTank.setShot(true);
                } else {
                    enemyTank.setOverlapAndCanNotShot(true);
                }
            });

            irons.stream().filter(iron -> computingService.isTankOverlap(enemyTank, iron, 20 + 10)).forEach(iron -> {
                enemyTank.setFrontStuff(StuffTypeEnum.IRON);
                enemyTank.setOverlapAndCanNotShot(true);
            });

            waters.stream().filter(water -> computingService.isTankOverlap(enemyTank, water, 20 + 10)).forEach(water -> {
                enemyTank.setOverlapAndCanNotShot(true);
                enemyTank.setOverlapAndCanNotShot(true);
            });
        });
    }

    /**
     * 判断物体是否在坦克正前或正后方
     *
     * @param brick     砖块
     * @param enemyTank 坦克
     * @return 是否在前方
     */
    private boolean isBrickInFront(Brick brick, EnemyTank enemyTank) {
        return (Math.abs(brick.getX() - enemyTank.getX()) <= 10 && (enemyTank.getDirect() == DirectionEnum.SOUTH || enemyTank.getDirect() == DirectionEnum.NORTH)) ||
                (Math.abs(brick.getY() - enemyTank.getY()) <= 10 && (enemyTank.getDirect() == DirectionEnum.EAST || enemyTank.getDirect() == DirectionEnum.WEST));
    }


    /**
     * 销毁And创建
     */
    public void cleanAndCreate() {
        RealTimeGameData realTimeGameData = context.getRealTimeGameData();

        Vector<MyTank> myTanks = realTimeGameData.getMyTanks();
        Vector<EnemyTank> enemyTanks = realTimeGameData.getEnemies();
        Vector<Bomb> bombs = realTimeGameData.getBombs();
        Map map = realTimeGameData.getMap();

        List<MyTank> diedMyTanks = new ArrayList<>();
        List<EnemyTank> diedEnemyTanks = new ArrayList<>();
        List<MyTank> addMyTanks = new ArrayList<>();
        List<EnemyTank> addEnemyTanks = new ArrayList<>();

        for (MyTank myTank : myTanks) {
            Vector<Bullet> mb = myTank.getBullets();
            mb.removeIf(b -> !b.isLive()); //移除死亡的子弹

            if (!myTank.getLive()) {
                //我方坦克死亡
                diedMyTanks.add(myTank);
                realTimeGameData.setMyTankNum(realTimeGameData.getMyTankNum() - 1);
                realTimeGameData.setBeKilled(realTimeGameData.getBeKilled() + 1);

                if (realTimeGameData.getMyTankNum() >= 1) {
                    // 地图上 +1
                    MyTank myTankTemp = new MyTank(300, 620, DirectionEnum.NORTH);
                    addMyTanks.add(myTankTemp);
                }
            }
        }

        for (EnemyTank enemy : enemyTanks) {
            Vector<Bullet> eb = enemy.getBullets();
            eb.removeIf(b -> !b.isLive()); //移除死亡的子弹

            if (!enemy.getLive()) {
                //敌方坦克死亡
                enemy.getTimer().cancel();
                diedEnemyTanks.add(enemy);
                realTimeGameData.setEnemyTankNum(realTimeGameData.getEnemyTankNum() - 1);
                if (realTimeGameData.getEnemyTankNum() >= GameConstants.INIT_ENEMY_TANK_IN_MAP_NUM) {
                    int r = new Random().nextInt(GameConstants.INIT_ENEMY_TANK_IN_MAP_NUM);
                    int xStepLength = (GameConstants.GAME_PANEL_WIDTH - GameConstants.TANK_WIDTH) / (GameConstants.INIT_ENEMY_TANK_IN_MAP_NUM - 1);
                    EnemyTank enemyTank = new EnemyTank((r) * xStepLength + GameConstants.TANK_WIDTH / 2, -GameConstants.TANK_HEIGHT / 2, DirectionEnum.SOUTH);
                    enemyTank.setLocation(r);
                    enemyTank.setActivate(Boolean.TRUE);
                    tankControlService.enableEnemyTank(enemyTank);
                    addEnemyTanks.add(enemyTank);
                }
            }
        }

        diedMyTanks.forEach(myTanks::remove);
        diedEnemyTanks.forEach(enemyTanks::remove);
        myTanks.addAll(addMyTanks);
        enemyTanks.addAll(addEnemyTanks);

        bombs.removeIf(bomb -> !bomb.isLive());
        map.getBricks().removeIf(brick -> !brick.getLive());
    }

    /**
     * 刷新我方坦克状态
     *
     * @param gameData 我的坦克容量
     */
    public void refreshMyTankState(RealTimeGameData gameData) {
        RealTimeGameData realTimeGameData = context.getRealTimeGameData();
        for (MyTank myTank : gameData.getMyTanks()) {
            if (myTank.isOverlapAndCanNotShot() || myTank.isOverlapCanShot()) {
                return;
            }

            if (realTimeGameData.isUp()) {
                myTank.goNorth();
            } else if (realTimeGameData.isDown()) {
                myTank.goSouth();
            } else if (realTimeGameData.isLeft()) {
                myTank.goWest();
            } else if (realTimeGameData.isRight()) {
                myTank.goEast();
            }
        }
    }


    /**
     * 击中东西以后
     *
     * @param bullet 集中别人的子弹
     * @param stuff  被击中的东西
     * @param bombs  炸弹容量
     */
    public void afterShotStuff(Bullet bullet, Stuff stuff, Vector<Bomb> bombs) {
        Bomb bomb;
        switch (stuff.getType()) {
            case BRICK: // 砖块
                bullet.setLive(false);
                stuff.setLive(false);
                bomb = new Bomb(stuff.getX(), stuff.getY());
                bomb.setL(40);
                bombs.add(bomb);
                break;
            case IRON: // 铁块
                bomb = new Bomb(bullet.getX(), bullet.getY());
                bullet.setLive(false);
                bomb.setL(20);
                bombs.add(bomb);
                break;
            default:
                break;
        }
    }

    /**
     * 击中坦克以后
     *
     * @param bullet 击中别人的子弹
     * @param tank   被击中的坦克
     * @param bombs  炸弹容量
     */
    public void afterShotTank(Bullet bullet, Tank tank, Vector<Bomb> bombs) {
        bullet.setLive(false);
        Bomb bomb;
        if (tank.getBlood() == 1) {
            tank.setLive(false);
            bomb = new Bomb(tank.getX(), tank.getY());
            tank.setBlood(tank.getBlood() - 1);
            bomb.setL(120);
        } else {
            bomb = new Bomb(bullet.getX(), bullet.getY());
            tank.setBlood(tank.getBlood() - 1);
            bomb.setL(40);
        }
        bombs.add(bomb);
    }

}

package cn.edu.hdu.tankbattle.service;

import cn.edu.hdu.tankbattle.context.GameContext;
import cn.edu.hdu.tankbattle.dto.RealTimeGameData;
import cn.edu.hdu.tankbattle.entity.*;
import cn.edu.hdu.tankbattle.enums.DirectionEnum;
import cn.edu.hdu.tankbattle.enums.StuffTypeEnum;
import cn.edu.hdu.tankbattle.resource.map.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 更新敌方坦克我方坦克的方向
     */
    public void refreshEnemyTankState() {
        RealTimeGameData resource = context.getRealTimeGameData();
        Vector<EnemyTank> enemies = resource.getEnemies();
        Vector<MyTank> myTanks = resource.getMyTanks();

        if (!myTanks.isEmpty()) {
            enemies.forEach(enemyTank -> enemyTank.setMyTankDirect(myTanks.get(0).getDirect()));
        }
    }

    /**
     * 刷新子弹状态...
     */
    public void refreshBulletState() {
        RealTimeGameData resource = context.getRealTimeGameData();

        Vector<MyTank> myTanks = resource.getMyTanks();
        Vector<EnemyTank> enemies = resource.getEnemies();
        Vector<Bomb> bombs = resource.getBombs();
        Map map = resource.getMap();

        if (myTanks.isEmpty()) {
            enemies.forEach(enemyTank -> enemyTank.setShot(false));
        }

        myTanks.forEach(myTank ->
                enemies.forEach(enemyTank -> {

                    tankControlService.enemyFindAndKill(enemyTank, myTank, map);

                    enemyTank.getBullets().forEach(eb -> {
                        if (computingService.isHitting(eb, myTank)) {
                            computingService.afterShotTank(eb, myTank, bombs);
                        }

                        map.getBricks().stream().filter(brick -> computingService.isHitting(eb, brick))
                                .forEach(brick -> computingService.afterShotStuff(eb, brick, bombs));

                        map.getIrons().stream().filter(iron -> computingService.isHitting(eb, iron))
                                .forEach(iron -> computingService.afterShotStuff(eb, iron, bombs));
                    });

                    myTank.getBullets().forEach(mb -> {
                        enemyTank.getBullets().stream().filter(eb -> computingService.isHitting(mb, eb))
                                .forEach(eb -> {
                                    mb.setLive(false);
                                    eb.setLive(false);
                                    Bomb bomb = new Bomb(mb.getX(), mb.getY());
                                    bomb.setL(20);
                                    bombs.add(bomb);
                                });


                        if (computingService.isHitting(mb, enemyTank)) {
                            computingService.afterShotTank(mb, enemyTank, bombs);
                        }

                        map.getBricks().stream().filter(brick -> computingService.isHitting(mb, brick))
                                .forEach(brick -> computingService.afterShotStuff(mb, brick, bombs));

                        map.getIrons().stream().filter(iron -> computingService.isHitting(mb, iron))
                                .forEach(iron -> computingService.afterShotStuff(mb, iron, bombs));
                    });


                })
        );
    }

    /**
     * 刷新重叠状态
     */
    public void refreshOverlapState() {
        RealTimeGameData resource = context.getRealTimeGameData();
        Vector<MyTank> myTanks = resource.getMyTanks();
        Vector<EnemyTank> enemies = resource.getEnemies();
        Map map = resource.getMap();
        Vector<Brick> bricks = map.getBricks();
        Vector<Iron> irons = map.getIrons();
        Vector<Water> waters = map.getWaters();

        myTanks.forEach(myTank -> {
            myTank.setOverlapAndCanShot(false);
            myTank.setOverlapCanNotShot(false);

            if (computingService.isMyTankOverlap(myTank, enemies)) {
                myTank.setOverlapCanNotShot(true);
            }

            bricks.stream().filter(brick -> computingService.isTankOverlap(myTank, brick, 20 + 10))
                    .forEach(brick -> myTank.setOverlapCanNotShot(true));

            irons.stream().filter(iron -> computingService.isTankOverlap(myTank, iron, 20 + 10))
                    .forEach(iron -> myTank.setOverlapAndCanShot(true));

            waters.stream().filter(water -> computingService.isTankOverlap(myTank, water, 20 + 10))
                    .forEach(water -> myTank.setOverlapAndCanShot(true));
        });

        enemies.forEach(enemyTank -> {
            enemyTank.setOverlapAndCanShot(false);
            enemyTank.setOverlapCanNotShot(false);
            enemyTank.setFrontStuff(StuffTypeEnum.INVALID);

            if (computingService.isEnemyTankOverlap(enemyTank, enemies, myTanks)) {
                enemyTank.setOverlapCanNotShot(true);
            }


            bricks.stream().filter(brick -> computingService.isTankOverlap(enemyTank, brick, 20 + 10))
                    .forEach(brick -> {
                        if ((Math.abs(brick.getX() - enemyTank.getX()) <= 10 && (enemyTank
                                .getDirect() == DirectionEnum.SOUTH || enemyTank
                                .getDirect() == DirectionEnum.NORTH))
                                || (Math.abs(brick.getY()
                                - enemyTank.getY()) <= 10 && (enemyTank
                                .getDirect() == DirectionEnum.EAST || enemyTank
                                .getDirect() == DirectionEnum.WEST))) {
                            enemyTank.setFrontStuff(StuffTypeEnum.BRICK);
                            enemyTank.setOverlapCanNotShot(true);
                            enemyTank.setShot(true);
                        } else {
                            enemyTank.setOverlapAndCanShot(true);
                        }

                    });

            irons.stream().filter(iron -> computingService.isTankOverlap(enemyTank, iron, 20 + 10))
                    .forEach(iron -> {
                        enemyTank.setFrontStuff(StuffTypeEnum.IRON);
                        enemyTank.setOverlapAndCanShot(true);
                    });

            waters.stream().filter(water -> computingService.isTankOverlap(enemyTank, water, 20 + 10))
                    .forEach(water -> {
                        enemyTank.setOverlapAndCanShot(true);
                        enemyTank.setOverlapAndCanShot(true);
                    });

        });


    }


    /**
     * 销毁And创建
     */
    public void cleanAndCreate() {
        RealTimeGameData data = context.getRealTimeGameData();

        Vector<MyTank> myTanks = data.getMyTanks();
        Vector<EnemyTank> enemies = data.getEnemies();
        Vector<Bomb> bombs = data.getBombs();
        Map map = data.getMap();


        for (int i = 0; i < myTanks.size(); i++) {
            MyTank myTank = myTanks.get(i);
            Vector<Bullet> mb = myTank.getBullets();
            mb.removeIf(b -> !b.isLive());

            if (!myTank.getLive()) {
                myTanks.remove(myTank);
                data.setMyTankNum(data.getMyTankNum() - 1);
                data.setBeKilled(data.getBeKilled() + 1);

                if (data.getMyTankNum() >= 1) {
                    // 1
                    MyTank myTankTemp = new MyTank(300, 620, DirectionEnum.NORTH);
                    myTanks.add(myTankTemp);
                }
            }
        }

        for (int i = 0; i < enemies.size(); i++) {
            EnemyTank enemy = enemies.get(i);
            Vector<Bullet> eb = enemy.getBullets();
            eb.removeIf(b -> !b.isLive());

            if (!enemy.getLive()) {
                enemy.getTimer().cancel();
                int r;


                data.setEnemyTankNum(data.getEnemyTankNum() - 1);
                r = (int) (Math.random() * 5);
                enemies.remove(enemy);
                if (data.getEnemyTankNum() >= 5) {
                    EnemyTank enemyTank = new EnemyTank((r) * 140 + 20,
                            -20, DirectionEnum.SOUTH);
                    enemyTank.setLocation(r);
                    enemyTank.setActivate(Boolean.TRUE);
                    tankControlService.enableEnemyTank(enemyTank);
                    enemies.add(enemyTank);
                }
                break;

            }
        }


        bombs.removeIf(bomb -> !bomb.isLive());

        map.getBricks().removeIf(brick -> !brick.getLive());

    }

    /**
     * 刷新我方坦克状态
     *
     * @param gameData 我的坦克容量
     */
    public void refreshMyTankState(RealTimeGameData gameData) {
        RealTimeGameData data = context.getRealTimeGameData();
        for (int i = 0; i < gameData.getMyTanks().size(); i++) {
            MyTank myTank = gameData.getMyTanks().get(i);
            if (data.isUp() && !myTank.isOverlapAndCanShot() && !myTank.isOverlapCanNotShot()) {
                myTank.goNorth();
            } else if (data.isDown() && !myTank.isOverlapAndCanShot() && !myTank.isOverlapCanNotShot()) {
                myTank.goSouth();
            } else if (data.isLeft() && !myTank.isOverlapAndCanShot() && !myTank.isOverlapCanNotShot()) {
                myTank.goWest();
            } else if (data.isRight() && !myTank.isOverlapAndCanShot() && !myTank.isOverlapCanNotShot()) {
                myTank.goEast();
            }
        }
    }
}

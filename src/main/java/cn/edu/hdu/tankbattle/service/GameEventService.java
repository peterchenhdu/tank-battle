/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.service;

import cn.edu.hdu.tankbattle.context.GameContext;
import cn.edu.hdu.tankbattle.dto.RealTimeGameData;
import cn.edu.hdu.tankbattle.entity.*;
import cn.edu.hdu.tankbattle.enums.DirectionEnum;
import cn.edu.hdu.tankbattle.enums.StuffTypeEnum;
import cn.edu.hdu.tankbattle.resource.map.Map;
import cn.edu.hdu.tankbattle.thread.executor.EnemyTankThreadController;
import cn.edu.hdu.tankbattle.thread.task.BulletMoveTask;
import cn.edu.hdu.tankbattle.util.GameTimeUnit;
import cn.edu.hdu.tankbattle.util.GameUtils;
import cn.edu.hdu.tankbattle.view.panel.GamePanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Vector;

/**
 * Control...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
@Service
public class GameEventService {

    @Autowired
    private GameContext context;
    @Autowired
    private EnemyTankThreadController threadEnemyTankThreadController;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private TankControlService tankControlService;

    public void refreshState() {
        RealTimeGameData resource = context.getRealTimeGameData();
        Vector<EnemyTank> enemies = resource.getEnemies();
        Vector<MyTank> myTanks = resource.getMyTanks();

        if (!myTanks.isEmpty()) {
            enemies.forEach(enemyTank -> {
                enemyTank.setMyTankDirect(myTanks.get(0).getDirect());
            });
        }


    }

    /**
     * Bullets Event...
     */
    public void doBulletEvent() {
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
                        if (GameUtils.isHitting(eb, myTank)) {
                            GameUtils.afterShotTank(eb, myTank, bombs);
                        }

                        map.getBricks().stream().filter(brick -> GameUtils.isHitting(eb, brick))
                                .forEach(brick -> GameUtils.afterShotStuff(eb, brick, bombs));

                        map.getIrons().stream().filter(iron -> GameUtils.isHitting(eb, iron))
                                .forEach(iron -> GameUtils.afterShotStuff(eb, iron, bombs));
                    });

                    myTank.getBullets().forEach(mb -> {
                        enemyTank.getBullets().stream().filter(eb -> GameUtils.isHitting(mb, eb))
                                .forEach(eb -> {
                                    mb.setLive(false);
                                    eb.setLive(false);
                                    Bomb bomb = new Bomb(mb.getX(), mb.getY());
                                    bomb.setL(20);
                                    bombs.add(bomb);
                                });


                        if (GameUtils.isHitting(mb, enemyTank)) {
                            GameUtils.afterShotTank(mb, enemyTank, bombs);
                        }

                        map.getBricks().stream().filter(brick -> GameUtils.isHitting(mb, brick))
                                .forEach(brick -> GameUtils.afterShotStuff(mb, brick, bombs));

                        map.getIrons().stream().filter(iron -> GameUtils.isHitting(mb, iron))
                                .forEach(iron -> GameUtils.afterShotStuff(mb, iron, bombs));
                    });


                })
        );
    }

    /**
     * doOverlapJudge
     */
    public void doOverlapJudge() {
        RealTimeGameData resource = context.getRealTimeGameData();
        Vector<MyTank> myTanks = resource.getMyTanks();
        Vector<EnemyTank> enemies = resource.getEnemies();
        Map map = resource.getMap();
        Vector<Brick> bricks = map.getBricks();
        Vector<Iron> irons = map.getIrons();
        Vector<Water> waters = map.getWaters();

        myTanks.stream().forEach(myTank -> {
            myTank.setOverlapNo(false);
            myTank.setOverlapYes(false);

            if (tankControlService.isMyTankOverlap(myTank, enemies)) {
                myTank.setOverlapYes(true);
            }

            bricks.stream().filter(brick -> tankControlService.isTankOverlap(myTank, brick, 20 + 10))
                    .forEach(brick -> myTank.setOverlapYes(true));

            irons.stream().filter(iron -> tankControlService.isTankOverlap(myTank, iron, 20 + 10))
                    .forEach(iron -> myTank.setOverlapNo(true));

            waters.stream().filter(water -> tankControlService.isTankOverlap(myTank, water, 20 + 10))
                    .forEach(water -> myTank.setOverlapNo(true));
        });

        enemies.stream().forEach(enemyTank -> {
            enemyTank.setOverlapNo(false);
            enemyTank.setOverlapYes(false);
            enemyTank.setFrontStuff(StuffTypeEnum.INVALID);

            if (tankControlService.isEnemyTankOverlap(enemyTank, enemies, myTanks)) {
                enemyTank.setOverlapYes(true);
            }


            bricks.stream().filter(brick -> tankControlService.isTankOverlap(enemyTank, brick, 20 + 10))
                    .forEach(brick -> {
                        if ((Math.abs(brick.getX() - enemyTank.getX()) <= 10 && (enemyTank
                                .getDirect() == DirectionEnum.SOUTH || enemyTank
                                .getDirect() == DirectionEnum.NORTH))
                                || (Math.abs(brick.getY()
                                - enemyTank.getY()) <= 10 && (enemyTank
                                .getDirect() == DirectionEnum.EAST || enemyTank
                                .getDirect() == DirectionEnum.WEST))) {
                            enemyTank.setFrontStuff(StuffTypeEnum.BRICK);
                            enemyTank.setOverlapYes(true);
                            enemyTank.setShot(true);
                        } else {
                            enemyTank.setOverlapNo(true);
                        }

                    });

            irons.stream().filter(iron -> tankControlService.isTankOverlap(enemyTank, iron, 20 + 10))
                    .forEach(iron -> {
                        enemyTank.setFrontStuff(StuffTypeEnum.IRON);
                        enemyTank.setOverlapNo(true);
                    });

            waters.stream().filter(water -> tankControlService.isTankOverlap(enemyTank, water, 20 + 10))
                    .forEach(water -> {
                        enemyTank.setOverlapNo(true);
                        enemyTank.setOverlapNo(true);
                    });

        });


    }

    /**
     * cleanAndCreate
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
                    threadEnemyTankThreadController.enableEnemyTank(enemyTank);
                    enemies.add(enemyTank);
                }
                break;

            }
        }


        bombs.removeIf(bomb -> !bomb.isLive());

        map.getBricks().removeIf(brick -> !brick.getLive());

    }

    /**
     * 我的坦克事件，观察我按了什么键
     *
     * @param resource .getMyTanks() 我的坦克容量
     */
    public void myTankEvent(RealTimeGameData resource) {
        RealTimeGameData data = context.getRealTimeGameData();
        for (int i = 0; i < resource.getMyTanks().size(); i++) {
            MyTank myTank = resource.getMyTanks().get(i);
            if (data.isUp() && !myTank.isOverlapNo() && !myTank.isOverlapYes()) {
                myTank.goNorth();
            } else if (data.isDown() && !myTank.isOverlapNo() && !myTank.isOverlapYes()) {
                myTank.goSouth();
            } else if (data.isLeft() && !myTank.isOverlapNo() && !myTank.isOverlapYes()) {
                myTank.goWest();
            } else if (data.isRight() && !myTank.isOverlapNo() && !myTank.isOverlapYes()) {
                myTank.goEast();
            }
        }
    }

    /**
     * 下一关
     */
    public void nextGame(RealTimeGameData resource) {
        RealTimeGameData data = context.getRealTimeGameData();

        resource.setMap(data.getLevel().getMap());

        for (int i = 0; i < 5; i++) {
            EnemyTank enemy = new EnemyTank((i) * 140 + 20, -20, DirectionEnum.SOUTH);
            enemy.setActivate(Boolean.TRUE);
            enemy.setLocation(i);
            resource.getEnemies().add(enemy);
        }
        data.setEnemyTankNum(8);
        for (int i = 0; i < resource.getMyTanks().size(); i++) {
            MyTank myTank = resource.getMyTanks().get(i);
            myTank.setActivate(Boolean.TRUE);
            myTank.setX(300);
            myTank.setY(620);
        }
        threadEnemyTankThreadController.enableEnemyTanks();
    }


    /**
     * 游戏暂停
     *
     * @param resource .getEnemies() 敌人坦克容量
     */
    public void gameEventStop(RealTimeGameData resource) {
        RealTimeGameData data = context.getRealTimeGameData();

        for (int i = 0; i < resource.getMyTanks().size(); i++) {
            MyTank myTank = resource.getMyTanks().get(i);
            if (myTank.getSpeedVector() == 0) {
                data.setStop(true);
                myTank.setSpeedVector(myTank.getSpeed());
                myTank.setSpeed(0);
                for (int j = 0; j < myTank.getBullets().size(); j++) {
                    myTank.getBullets()
                            .get(j)
                            .setSpeedVector(
                                    myTank.getBullets().get(j).getSpeed());
                    myTank.getBullets().get(j).setSpeed(0);
                }
                for (int j = 0; j < resource.getEnemies().size(); j++) {
                    resource.getEnemies()
                            .get(j)
                            .setSpeedVector(
                                    resource.getEnemies().get(j).getSpeed());
                    resource.getEnemies().get(j).setSpeed(0);
                    for (int k = 0; k < resource.getEnemies().get(j)
                            .getBullets().size(); k++) {
                        resource.getEnemies()
                                .get(j)
                                .getBullets()
                                .get(k)
                                .setSpeedVector(
                                        resource.getEnemies().get(j)
                                                .getBullets().get(k).getSpeed());
                        resource.getEnemies().get(j).getBullets().get(k)
                                .setSpeed(0);
                    }
                }
            } else {
                data.setStop(false);
                myTank.setSpeed(myTank.getSpeedVector());
                myTank.setSpeedVector(0);
                for (int j = 0; j < myTank.getBullets().size(); j++) {
                    myTank.getBullets()
                            .get(j)
                            .setSpeed(
                                    myTank.getBullets().get(j).getSpeedVector());
                }
                for (int j = 0; j < resource.getEnemies().size(); j++) {
                    resource.getEnemies()
                            .get(j)
                            .setSpeed(
                                    resource.getEnemies().get(j)
                                            .getSpeedVector());
                    resource.getEnemies().get(j).setSpeedVector(0);
                    for (int k = 0; k < resource.getEnemies().get(j)
                            .getBullets().size(); k++) {
                        resource.getEnemies()
                                .get(j)
                                .getBullets()
                                .get(k)
                                .setSpeed(
                                        resource.getEnemies().get(j)
                                                .getBullets().get(k)
                                                .getSpeedVector());
                    }
                }
            }
        }
    }

    /**
     * 游戏没开始时的欢迎图片上的字体移动
     */
    public void fontMove(GamePanel panel) {
        RealTimeGameData data = context.getRealTimeGameData();
        int ky = data.getKy();
        if (ky > 0 && ky != 21)
            ky = ky - 8; // 当字体刚出来时，向上移动
        if (ky == 0) { // 当字体移到顶端时，以7的速度向下移动5步
            for (int i = 0; i < 5; i++) {
                ky = ky + 7;
                panel.repaint();
                GameTimeUnit.sleepMillis(100);
            }
            for (int i = 0; i < 4; i++) { // 以6的速度向上移动4步
                ky = ky - 6;
                panel.repaint();
                GameTimeUnit.sleepMillis(100);
            }
            for (int i = 0; i < 3; i++) { // 以5的速度向下移动
                ky = ky + 5;
                panel.repaint();
                GameTimeUnit.sleepMillis(100);
            }
            for (int i = 0; i < 2; i++) { // 以4的速度向上移动
                ky = ky - 4;
                panel.repaint();
                GameTimeUnit.sleepMillis(100);
            }
            ky = ky + 3;// 最后，字体的图片停留在（0，21）的地方
        }
        data.setKy(ky);
    }

    /**
     * 射击，发射一颗子弹
     *
     * @param tank 坦克对象，注意，是自己，不是敌人，呵呵
     */
    public void shot(Tank tank) {
        Bullet bullet = null;
        switch (tank.getDirect()) {
            case NORTH:
                bullet = new Bullet(tank.getX(), tank.getY() - 20, DirectionEnum.NORTH);
                break;
            case SOUTH:
                bullet = new Bullet(tank.getX(), tank.getY() + 20, DirectionEnum.SOUTH);
                break;
            case WEST:
                bullet = new Bullet(tank.getX() - 20, tank.getY(), DirectionEnum.WEST);
                break;
            case EAST:
                bullet = new Bullet(tank.getX() + 20, tank.getY(), DirectionEnum.EAST);
                break;
        }
        tank.getBullets().add(bullet);
        taskExecutor.execute(new BulletMoveTask(bullet));
    }


}

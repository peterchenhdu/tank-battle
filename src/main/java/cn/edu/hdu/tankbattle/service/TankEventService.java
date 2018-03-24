/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.service;

import cn.edu.hdu.tankbattle.enums.DirectionEnum;
import cn.edu.hdu.tankbattle.model.EnemyTank;
import cn.edu.hdu.tankbattle.model.Iron;
import cn.edu.hdu.tankbattle.model.MyTank;
import cn.edu.hdu.tankbattle.model.Tank;
import cn.edu.hdu.tankbattle.model.map.Map;
import cn.edu.hdu.tankbattle.thread.GameTimeUnit;
import org.springframework.stereotype.Service;

import java.util.Vector;

/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/3/24 19:52
 */
@Service
public class TankEventService {

    /**
     * 判断自己跟别的坦克是否重叠
     *
     * @param enemys  敌人坦克容量
     * @param myTanks 我的坦克容量
     * @return 是否重叠
     */
    public boolean isOverlap(EnemyTank enemy, Vector<EnemyTank> enemys, Vector<MyTank> myTanks) {
        for (int i = 0; i < enemys.size(); i++) { // 依次取出每一个敌人坦克
            if (enemy != enemys.get(i)) {
                if (enemy.Overlap(enemys.get(i), 40)) { // 判断这两辆坦克是否重叠
                    enemy.setOverlapNo(true);
                    return true; // 一旦有重叠则返回真
                }
            }
        }
        for (int j = 0; j < myTanks.size(); j++) { // 依次取出每个我的坦克
            if (enemy.Overlap(myTanks.get(j), 40)) { // 判断这两辆坦克是否重叠
                enemy.setOverlapYes(true); // 面对我的坦克，敌人坦克开炮过去
                return true; // 一旦有重叠则返回真
            }
        }
        // 如果前面没有返回的话，说明没重叠，返回假
        enemy.setOverlapNo(false);
        enemy.setOverlapYes(false);
        return false; // 没有重叠现象则返回假
    }

    /**
     * 每隔36毫秒 一直向西走
     */
    public void enemyGoWest(EnemyTank enemy) {
        for (; ; ) {
            GameTimeUnit.sleepMillis(36);
            if (enemy.isOverlapNo() == false && enemy.isOverlapYes() == false) { // 不重叠的话
                enemy.goWest();
            }
            if (enemy.getMyTankLocation() != DirectionEnum.WEST) { // 我的坦克不在正西方的时候
                enemy.setDirect(enemy.getMyTankDirect()); // 让敌人坦克与我的坦克方向一致
                break;
            }
        }
    }

    /**
     * 每隔36毫秒 一直向东走
     */
    public void enemyGoEast(EnemyTank enemy) {
        for (; ; ) {
            GameTimeUnit.sleepMillis(36);
            if (!enemy.isOverlapNo() && !enemy.isOverlapYes()) {
                enemy.goEast();
            }
            if (enemy.getMyTankLocation() != DirectionEnum.EAST) {
                enemy.setDirect(enemy.getMyTankDirect());
                break;
            }
        }
    }

    /**
     * 每隔36毫秒 一直向北走
     */
    public void enemyGoNorth(EnemyTank enemy) {
        for (; ; ) {
            GameTimeUnit.sleepMillis(36);
            if (enemy.isOverlapNo() == false && enemy.isOverlapYes() == false) {
                enemy.goNorth();
            }
            if (enemy.getMyTankLocation() != DirectionEnum.NORTH) {
                enemy.setDirect(enemy.getMyTankDirect());
                break;
            }
        }
    }

    /**
     * 每隔36毫秒 一直向南走
     */
    public void enemyGoSouth(EnemyTank enemy) {
        for (; ; ) {
            GameTimeUnit.sleepMillis(36);
            if (enemy.isOverlapNo() == false && enemy.isOverlapYes() == false) {
                enemy.goSouth();
            }
            if (enemy.getMyTankLocation() != DirectionEnum.SOUTH) {
                enemy.setDirect(enemy.getMyTankDirect());
                break;
            }
        }
    }

    /**
     * 从指定的三个方向中随机选择一个
     *
     * @param direct1 方向1
     * @param direct2 方向2
     * @param direct3 方向3
     */
    public DirectionEnum enemyGetRandomDirect(DirectionEnum direct1, DirectionEnum direct2, DirectionEnum direct3) {
        int random = (int) (Math.random() * 3);

        DirectionEnum returnDirect = DirectionEnum.INVALID;
        switch (random) {
            case 0:
                returnDirect = direct1;
                break;
            case 1:
                returnDirect = direct2;
                break;
            case 2:
                returnDirect = direct3;
                break;
        }
        return returnDirect;
    }

    /**
     * 让敌人坦克能够发现我的坦克并开炮
     *
     * @param myTank 我的坦克
     * @param map    地图对象
     */
    public void enemyFindAndKill(EnemyTank enemy, MyTank myTank, Map map) {
        int myX = myTank.getX();
        int myY = myTank.getY();
        int enX = enemy.getX();
        int enY = enemy.getY();
        if (Math.abs(myX - enX) < 20 && myY <= 580) { // 如果我的坦克在敌人坦克的正北方或正南方
            if (enY < myY) { // 我的坦克在正南方
                int s = 0;
                for (int t = 0; t < map.getIrons().size(); t++) {
                    Iron iron = map.getIrons().get(t);
                    if (Math.abs(enX - iron.getX()) <= 10 && iron.getY() > enY
                            && iron.getY() < myY) {
                        s = 1; // 只要出现一个铁块能挡住子弹，就让s变为1，跳出判断
                        break;
                    }
                }
                if (s == 0) { // 如果s==1说明没有铁块挡住子弹，可以发射
                    enemy.setShot(true);
                    enemy.setMyTankLocation(DirectionEnum.SOUTH);
                }
            } else { // 我的坦克在正北方
                int s = 0;
                for (int t = 0; t < map.getIrons().size(); t++) {
                    Iron iron = map.getIrons().get(t);
                    if (Math.abs(enX - iron.getX()) <= 10 && iron.getY() < enY
                            && iron.getY() > myY) {
                        s = 1;
                        break;
                    }
                }
                if (s == 0) {
                    enemy.setShot(true);
                    enemy.setMyTankLocation(DirectionEnum.NORTH);
                }
            }
        } else if (Math.abs(myY - enY) < 20 && myY <= 580) { // 如果我的坦克在敌人坦克的正西方或正东方
            if (enX > myX) { // 我的坦克在正西方
                int s = 0;
                for (int t = 0; t < map.getIrons().size(); t++) {
                    Iron iron = map.getIrons().get(t);
                    // 铁块能挡住子弹，而且在我的坦克和敌人坦克之间
                    if (Math.abs(enY - iron.getY()) <= 10 && iron.getX() < enX
                            && iron.getX() > myX) {
                        s = 1;
                        break;
                    }
                }
                if (s == 0) {
                    enemy.setShot(true);
                    enemy.setMyTankLocation(DirectionEnum.WEST);
                }
            } else { // 我的坦克在正东方
                int s = 0;
                for (int t = 0; t < map.getIrons().size(); t++) {
                    Iron iron = map.getIrons().get(t);
                    if (Math.abs(enY - iron.getY()) <= 10 && iron.getX() > enX
                            && iron.getX() < myX) {
                        s = 1;
                        break;
                    }
                }
                if (s == 0) {
                    enemy.setShot(true);
                    enemy.setMyTankLocation(DirectionEnum.EAST);
                }
            }
        } else { // 其他情况敌人坦克不能判断我的坦克位置，要完善的话，还可以继续加进去
            enemy.setShot(false);
            enemy.setMyTankLocation(DirectionEnum.INVALID);
        }
    }
}

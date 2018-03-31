/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.thread.task;


import cn.edu.hdu.tankbattle.constant.GameConstants;
import cn.edu.hdu.tankbattle.entity.Bullet;
import cn.edu.hdu.tankbattle.thread.GameTimeUnit;

/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/3/24 20:08
 */
public class BulletMoveTask implements Runnable {
    private Bullet bullet;

    public BulletMoveTask(Bullet bullet) {
        this.bullet = bullet;
    }

    @Override
    public void run() {
        while (bullet.isLive()) {
            switch (bullet.getDirect()) { // 选择子弹的方向
                case NORTH:
                    bullet.setY(bullet.getY() - bullet.getSpeed());
                    break;
                case SOUTH:
                    bullet.setY(bullet.getY() + bullet.getSpeed());
                    break;
                case WEST:
                    bullet.setX(bullet.getX() - bullet.getSpeed());
                    break;
                case EAST:
                    bullet.setX(bullet.getX() + bullet.getSpeed());
                    break;
            }

            if (bullet.getX() < 5 || bullet.getX() > GameConstants.GAME_PANEL_WIDTH - 5 || bullet.getY() < 5
                    || bullet.getY() > GameConstants.GAME_PANEL_HEIGHT - 5) { // 判断子弹是否碰到边界
                bullet.setLive(false); // 子弹死亡
            }

            GameTimeUnit.sleepMillis(36);
        }
    }
}

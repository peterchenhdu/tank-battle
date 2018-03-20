/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.listener;

import cn.edu.hdu.tankbattle.context.GameContext;
import cn.edu.hdu.tankbattle.control.Control;
import cn.edu.hdu.tankbattle.dto.RealTimeGameData;
import cn.edu.hdu.tankbattle.model.GameResource;
import cn.edu.hdu.tankbattle.model.MyTank;
import cn.edu.hdu.tankbattle.model.Tank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/3/19 20:13
 */
@Component
public class MainFrameKeyListener implements KeyListener {
    @Autowired
    private Control control;

    @Autowired
    private GameContext context;
    @Override
    public void keyPressed(KeyEvent e) {
        GameResource resource = context.getResource();
        RealTimeGameData data = context.getGameData();
        for (int i = 0; i < resource.getMyTanks().size(); i++) {
            MyTank myTank = resource.getMyTanks().get(i);

            if (!myTank.isLive()) {
                data.setUp(false);
                data.setDown(false);
                data.setLeft(false);
                data.setRight(false);
            }

            if ((e.getKeyCode() == KeyEvent.VK_UP) && myTank.isLive()) {
                myTank.setDirect(Tank.NORTH);
                data.setUp(true);
                data.setDown(false);
                data.setLeft(false);
                data.setRight(false);
            } else if ((e.getKeyCode() == KeyEvent.VK_DOWN) && myTank.isLive()) {
                myTank.setDirect(Tank.SOUTH);
                data.setUp(false);
                data.setDown(true);
                data.setLeft(false);
                data.setRight(false);
            } else if ((e.getKeyCode() == KeyEvent.VK_LEFT) && myTank.isLive()
                    && myTank.getY() <= 580) {
                myTank.setDirect(Tank.WEST);
                data.setUp(false);
                data.setDown(false);
                data.setLeft(true);
                data.setRight(false);
            } else if ((e.getKeyCode() == KeyEvent.VK_RIGHT) && myTank.isLive()
                    && myTank.getY() <= 580) {
                myTank.setDirect(Tank.EAST);
                data.setUp(false);
                data.setDown(false);
                data.setLeft(false);
                data.setRight(true);
            }
            if (e.getKeyCode() == KeyEvent.VK_X && myTank.isLive()
                    && myTank.getY() <= 580) {
                if (myTank.getBullets().size() <= 1
                        && data.getMyBulletNum() > 0) { // 最多颗子弹
                    data
                            .setMyBulletNum(data.getMyBulletNum() - 1);
                    myTank.shot(myTank); // 这时才会往容器中添加子弹对象
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_P) { // 暂停
                control.gameEventStop(resource);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        RealTimeGameData data = context.getGameData();
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            data.setUp(false);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            data.setDown(false);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            data.setLeft(false);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            data.setRight(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}

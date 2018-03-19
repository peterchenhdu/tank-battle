/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.listener;

import cn.edu.hdu.tankbattle.control.Control;
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
    private GameResource resource;
    @Override
    public void keyPressed(KeyEvent e) {
        for (int i = 0; i < resource.getMyTanks().size(); i++) {
            MyTank myTank = resource.getMyTanks().get(i);

            if (!myTank.isLive()) {
                control.setUp(false);
                control.setDown(false);
                control.setLeft(false);
                control.setRight(false);
            }

            if ((e.getKeyCode() == KeyEvent.VK_UP) && myTank.isLive()) {
                myTank.setDirect(Tank.NORTH);
                control.setUp(true);
                control.setDown(false);
                control.setLeft(false);
                control.setRight(false);
            } else if ((e.getKeyCode() == KeyEvent.VK_DOWN) && myTank.isLive()) {
                myTank.setDirect(Tank.SOUTH);
                control.setUp(false);
                control.setDown(true);
                control.setLeft(false);
                control.setRight(false);
            } else if ((e.getKeyCode() == KeyEvent.VK_LEFT) && myTank.isLive()
                    && myTank.getY() <= 580) {
                myTank.setDirect(Tank.WEST);
                control.setUp(false);
                control.setDown(false);
                control.setLeft(true);
                control.setRight(false);
            } else if ((e.getKeyCode() == KeyEvent.VK_RIGHT) && myTank.isLive()
                    && myTank.getY() <= 580) {
                myTank.setDirect(Tank.EAST);
                control.setUp(false);
                control.setDown(false);
                control.setLeft(false);
                control.setRight(true);
            }
            if (e.getKeyCode() == KeyEvent.VK_X && myTank.isLive()
                    && myTank.getY() <= 580) {
                if (myTank.getBullets().size() <= 1
                        && this.control.getMyBulletNum() > 0) { // 最多颗子弹
                    this.control
                            .setMyBulletNum(this.control.getMyBulletNum() - 1);
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
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            control.setUp(false);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            control.setDown(false);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            control.setLeft(false);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            control.setRight(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}

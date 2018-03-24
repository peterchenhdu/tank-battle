/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.listener;

import cn.edu.hdu.tankbattle.context.GameContext;
import cn.edu.hdu.tankbattle.control.Control;
import cn.edu.hdu.tankbattle.dto.RealTimeGameData;
import cn.edu.hdu.tankbattle.dto.GameResource;
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
        RealTimeGameData data = context.getGameData();
        GameResource resource = data.getGameResource();
        if (e.getKeyCode() == KeyEvent.VK_P) { // 暂停
            control.gameEventStop(resource);
        }

        resource.getMyTanks().forEach(myTank -> {
            if (!myTank.isLive()) {
                data.keyPressedDirect(false, false, false, false);
            } else {
                if ((e.getKeyCode() == KeyEvent.VK_UP)) {
                    myTank.setDirect(Tank.NORTH);
                    data.keyPressedDirect(true, false, false, false);

                } else if ((e.getKeyCode() == KeyEvent.VK_DOWN) && myTank.getY() <= 580) {
                    myTank.setDirect(Tank.SOUTH);
                    data.keyPressedDirect(false, true, false, false);
                } else if ((e.getKeyCode() == KeyEvent.VK_LEFT) && myTank.getY() <= 580) {
                    myTank.setDirect(Tank.WEST);
                    data.keyPressedDirect(false, false, true, false);
                } else if ((e.getKeyCode() == KeyEvent.VK_RIGHT) && myTank.getY() <= 580) {
                    myTank.setDirect(Tank.EAST);
                    data.keyPressedDirect(false, false, false, true);
                }
                if (e.getKeyCode() == KeyEvent.VK_X && myTank.getY() <= 580) {
                    if (myTank.getBullets().size() <= 1 && data.getMyBulletNum() > 0) {
                        data.setMyBulletNum(data.getMyBulletNum() - 1);
                        myTank.shot(myTank); // 这时才会往容器中添加子弹对象
                    }
                }
            }
        });

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

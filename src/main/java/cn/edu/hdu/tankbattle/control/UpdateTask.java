/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.control;

import cn.edu.hdu.tankbattle.model.GameResource;
import cn.edu.hdu.tankbattle.view.panel.GamePanel;

/**
 * UpdateThread...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public class UpdateTask implements Runnable {

    private GameResource resource;

    private Control control;
    private GamePanel pannel;

    public UpdateTask(Control control, GameResource resource, GamePanel pannel) {
        this.control = control;
        this.resource = resource;
        this.pannel = pannel;
    }

    @Override
    public void run() {
        // 每隔30毫秒重画
        while (true) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (control.isStart()) {
                if ((control.getMyTankNum() == 0 || control.getEnemyTankNum() == 0)
                        && control.getDy() > 250) {
                    control.setDy(control.getDy() - 2);
                }
                if (control.getDy() == 250) {
                    pannel.repaint();
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (control.getLevel() == 5) {
                        control.setLevel(0);
                    }
                    if (control.getMyTankNum() >= 1 && control.getLevel() <= 4) {
                        control.setLevel(control.getLevel() + 1);
                        control.setDy(600);
                        control.nextGame(resource);
                    }
                }
                if (!control.isStop() && control.getDy() == 600) {
                    control.cleanAndCreat(resource.getMyTanks(),
                            resource.getEnemys(), resource.getMap(),
                            resource.getBombs()); // 从容器中移除死亡的对象
                    control.judge(resource.getMyTanks(), resource.getEnemys(),
                            resource.getMap(), resource.getBombs()); // 判断子弹是否击中坦克
                    control.judgeOverlap(resource.getMyTanks(),
                            resource.getEnemys(), resource.getMap()); // 判断坦克间是否出现重叠
                    control.myTankEvent(resource);

                }
            } else {
                if (control.getKy() == 21 && control.getKx() <= 654)
                    control.setKx(control.getKx() + 2);
                control.fontMove(resource, pannel);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            pannel.repaint();
        }
    }


}

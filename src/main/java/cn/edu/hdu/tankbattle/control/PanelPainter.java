/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.control;

import cn.edu.hdu.tankbattle.context.GameContext;
import cn.edu.hdu.tankbattle.model.GameResource;
import cn.edu.hdu.tankbattle.model.TankGameImages;
import cn.edu.hdu.tankbattle.view.panel.GamePanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/3/19 21:11
 */
@Component
public class PanelPainter {

    /**
     * 游戏面板的宽度
     */
    public final static int WIDTH = 600;
    /**
     * 游戏面板的高度
     */
    public final static int HEIGHT = 600;

    /**
     * 坦克游戏画笔对象
     */
    @Autowired
    private Drawer pen;

    @Autowired
    private GameContext context;

    /**
     * 游戏控制相关
     */
    @Autowired
    private Control control;
    public void paint(GamePanel panel, Graphics g){
        GameResource resource = context.getResource();
        if (control.isStart()) {
            g.setColor(Color.black);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.fillRect(280, 600, 40, 40);
            pen.drawMap(g, resource.getMap(), panel);
            pen.drawMyTank(g, resource.getMyTanks(), panel); // 画出我的坦克（包括子弹）
            pen.drawEnemyTank(g, resource.getEnemies(), panel); // 画出敌人坦克（包括子弹）
            pen.drawBomb(g, resource.getBombs(), panel); // 画出爆炸
            pen.drawRight(g, panel, control);

            if (this.control.getMyTankNum() == 0) { // 如果我的坦克数量为0
                g.drawImage(TankGameImages.gameOver, 250, control.getDy(), 100,
                        100, panel);
            }

            if (this.control.getEnemyTankNum() == 0) { // 如果敌人坦克的数量为0
                g.drawImage(TankGameImages.gameWin, 250, control.getDy(), 100,
                        100, panel);
            }
            if (control.getDy() == 250) {
                g.fillRect(0, 0, 800, 600);
                g.setColor(Color.BLUE);
                if (this.control.getMyTankNum() == 0) {
                    g.drawString("失败了！！！", 300, 220);
                } else {
                    g.drawString("挑战成功，请稍等...", 300, 220);
                }
                g.drawString(
                        ("敌人坦克死亡数量:" + (8 - this.control.getEnemyTankNum())),
                        300, 260);
                g.drawString("我的坦克死亡总数量:" + this.control.getBeKilled(), 300,
                        280);
                g.drawString(
                        "我的炮弹消耗总数量:"
                                + (Control.START_BULLET_NUM - this.control
                                .getMyBulletNum()), 300, 300);
                g.drawString("敌人坦克剩余数量:" + this.control.getEnemyTankNum(), 300,
                        320);
                g.drawString("我的坦克剩余总数量:" + this.control.getMyTankNum(), 300,
                        340);
                g.drawString("我的炮弹剩余总数量:" + this.control.getMyBulletNum(), 300,
                        360);
            }
        } else {
            g.drawImage(TankGameImages.startImage, 0, 0, 800, 700, panel);
            g.drawImage(TankGameImages.font, 0, control.getKy(), panel);
            if (control.isIconSmile()) {
                g.drawImage(TankGameImages.yct_smile1, control.getKx(), 45,
                        panel);
                control.setIconSmile(false);
            } else {
                g.drawImage(TankGameImages.yct_smile2, control.getKx(), 45,
                        panel);
                control.setIconSmile(true);
            }
        }
    }
}

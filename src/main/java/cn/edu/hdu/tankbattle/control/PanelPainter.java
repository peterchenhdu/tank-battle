/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.control;

import cn.edu.hdu.tankbattle.constant.GameConstants;
import cn.edu.hdu.tankbattle.context.GameContext;
import cn.edu.hdu.tankbattle.dto.RealTimeGameData;
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
        RealTimeGameData data = context.getGameData();
        if (data.isStart()) {
            g.setColor(Color.black);
            g.fillRect(0, 0, GameConstants.GAME_PANEL_WIDTH, GameConstants.GAME_PANEL_HEIGHT);
            g.fillRect(280, 600, 40, 40);
            pen.drawMap(g, resource.getMap(), panel);
            pen.drawMyTank(g, resource.getMyTanks(), panel); // 画出我的坦克（包括子弹）
            pen.drawEnemyTank(g, resource.getEnemies(), panel); // 画出敌人坦克（包括子弹）
            pen.drawBomb(g, resource.getBombs(), panel); // 画出爆炸
            pen.drawRight(g, panel, data);

            if (data.getMyTankNum() == 0) { // 如果我的坦克数量为0
                g.drawImage(TankGameImages.gameOver, 250, data.getDy(), 100,
                        100, panel);
            }

            if (data.getEnemyTankNum() == 0) { // 如果敌人坦克的数量为0
                g.drawImage(TankGameImages.gameWin, 250, data.getDy(), 100,
                        100, panel);
            }
            if (data.getDy() == 250) {
                g.fillRect(0, 0, 800, 600);
                g.setColor(Color.BLUE);
                if (data.getMyTankNum() == 0) {
                    g.drawString("失败了！！！", 300, 220);
                } else {
                    g.drawString("挑战成功，请稍等...", 300, 220);
                }
                g.drawString(
                        ("敌人坦克死亡数量:" + (8 - data.getEnemyTankNum())),
                        300, 260);
                g.drawString("我的坦克死亡总数量:" + data.getBeKilled(), 300,
                        280);
                g.drawString(
                        "我的炮弹消耗总数量:"
                                + (GameConstants.MY_TANK_INIT_BULLET_NUM - data
                                .getMyBulletNum()), 300, 300);
                g.drawString("敌人坦克剩余数量:" + data.getEnemyTankNum(), 300,
                        320);
                g.drawString("我的坦克剩余总数量:" + data.getMyTankNum(), 300,
                        340);
                g.drawString("我的炮弹剩余总数量:" + data.getMyBulletNum(), 300,
                        360);
            }
        } else {
            g.drawImage(TankGameImages.startImage, 0, 0, 800, 700, panel);
            g.drawImage(TankGameImages.font, 0, data.getKy(), panel);
            if (data.isIconSmile()) {
                g.drawImage(TankGameImages.yct_smile1, data.getKx(), 45,
                        panel);
                data.setIconSmile(false);
            } else {
                g.drawImage(TankGameImages.yct_smile2, data.getKx(), 45,
                        panel);
                data.setIconSmile(true);
            }
        }
    }
}

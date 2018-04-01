/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.listener;

import cn.edu.hdu.tankbattle.context.GameContext;
import cn.edu.hdu.tankbattle.dto.RealTimeGameData;
import cn.edu.hdu.tankbattle.entity.Brick;
import cn.edu.hdu.tankbattle.entity.Iron;
import cn.edu.hdu.tankbattle.entity.Water;
import cn.edu.hdu.tankbattle.enums.StuffTypeEnum;
import cn.edu.hdu.tankbattle.resource.map.Map;
import cn.edu.hdu.tankbattle.service.TankEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/4/1 10:41
 */
@Component
public class MainFrameMouseListener implements MouseListener{
    @Autowired
    private GameContext gameContext;

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX()+","+e.getY());
        RealTimeGameData gameData = gameContext.getGameData();
        Map map = gameData.getMap();
        Vector<Brick> bricks = map.getBricks();
        Vector<Iron> irons = map.getIrons();
        Vector<Water> waters = map.getWaters();
        if(gameData.getCurrentStiff() == StuffTypeEnum.BRICK){
            Brick b = new Brick(e.getX(), e.getY());
            //判断，重新调整一个合适的位置

            map.getBricks().add(b);
        } else if(gameData.getCurrentStiff()==StuffTypeEnum.IRON){
            Iron i = new Iron(e.getX(), e.getY());
            //判断，重新调整一个合适的位置

            map.getIrons().add(i);
        } else {
            Water w = new Water(e.getX(), e.getY());
            //判断，重新调整一个合适的位置

            map.getWaters().add(w);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

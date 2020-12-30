/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.listener;

import cn.edu.hdu.tankbattle.context.GameContext;
import cn.edu.hdu.tankbattle.dto.RealTimeGameData;
import cn.edu.hdu.tankbattle.entity.Brick;
import cn.edu.hdu.tankbattle.entity.Iron;
import cn.edu.hdu.tankbattle.entity.Stuff;
import cn.edu.hdu.tankbattle.entity.Water;
import cn.edu.hdu.tankbattle.resource.map.Map;
import cn.edu.hdu.tankbattle.service.ComputingService;
import cn.edu.hdu.tankbattle.util.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

/**
 * 鼠标点击事件...
 *
 * @author chenpi
 * @since 2018/4/1 10:41
 */
@Component
public class MouseEventListener implements MouseListener {
    private Logger logger = LoggerFactory.getLogger(MouseEventListener.class);
    @Autowired
    private GameContext gameContext;
    @Autowired
    private ComputingService computingService;
    @Override
    public void mouseClicked(MouseEvent e) {
        logger.info("Mouse Coordinate:" + e.getX() + "," + e.getY());
        RealTimeGameData gameData = gameContext.getRealTimeGameData();
        //鼠标点击事件只针对地图编辑模式有效
        if (!gameData.getMapMakingMode()) {
            return;
        }

        //以下针对地图编辑模式操作...
        Map map = gameData.getMap();
        Vector<Brick> bricks = map.getBricks();
        Vector<Iron> irons = map.getIrons();
        Vector<Water> waters = map.getWaters();

        Stuff s = MapUtils.getNearestStuff(e.getX(), e.getY());
        switch (gameData.getCurrentSelectedStuff()) {
            case BRICK:
                bricks.add(new Brick(s.getX(), s.getY()));
                break;
            case IRON:
                irons.add(new Iron(s.getX(), s.getY()));
                break;
            case WATER:
                waters.add(new Water(s.getX(), s.getY()));
                break;
            case INVALID: //橡皮擦
                bricks.stream().filter(b -> computingService.equals(b, s)).forEach(bricks::remove);
                irons.stream().filter(i -> computingService.equals(i, s)).forEach(irons::remove);
                waters.stream().filter(w -> computingService.equals(w, s)).forEach(waters::remove);
                break;
            default:
                break;
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

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
import cn.edu.hdu.tankbattle.enums.StuffTypeEnum;
import cn.edu.hdu.tankbattle.resource.map.Map;
import cn.edu.hdu.tankbattle.util.MapUtils;
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
public class MainFrameMouseListener implements MouseListener {
    @Autowired
    private GameContext gameContext;

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX() + "," + e.getY());
        RealTimeGameData gameData = gameContext.getGameData();
        if(gameData.getMapMakingFlag().equals(Boolean.FALSE)){
            return;
        }

        Map map = gameData.getMap();
        Vector<Brick> bricks = map.getBricks();
        Vector<Iron> irons = map.getIrons();
        Vector<Water> waters = map.getWaters();

        Stuff s = MapUtils.getNearestStuff(e.getX(), e.getY());

        Brick rb = null;
        Iron ri = null;
        Water rw = null;
        for (Brick b : bricks) {
            if (b.getX().equals(s.getX()) && b.getY().equals(s.getY())) {
                if (gameData.getCurrentStuff() == StuffTypeEnum.INVALID) {
                    rb = b;
                    break;
                } else {
                    return;
                }

            }
        }
        for (Iron i : irons) {
            if (i.getX().equals(s.getX()) && i.getY().equals(s.getY())) {
                if (gameData.getCurrentStuff() == StuffTypeEnum.INVALID) {
                    ri = i;
                    break;
                } else {
                    return;
                }
            }
        }
        for (Water w : waters) {
            if (w.getX().equals(s.getX()) && w.getY().equals(s.getY())) {
                if (gameData.getCurrentStuff() == StuffTypeEnum.INVALID) {
                    rw = w;
                    break;
                } else {
                    return;
                }
            }
        }

        if (gameData.getCurrentStuff() == StuffTypeEnum.BRICK) {
            Brick b = new Brick(s.getX(), s.getY());

            map.getBricks().add(b);
        } else if (gameData.getCurrentStuff() == StuffTypeEnum.IRON) {
            Iron i = new Iron(s.getX(), s.getY());

            map.getIrons().add(i);
        } else if (gameData.getCurrentStuff() == StuffTypeEnum.WATER) {
            Water w = new Water(s.getX(), s.getY());

            map.getWaters().add(w);
        } else {
            map.getBricks().remove(rb);
            map.getIrons().remove(ri);
            map.getWaters().remove(rw);
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

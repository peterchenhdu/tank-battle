/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.resource.map;

import java.util.Vector;

import cn.edu.hdu.tankbattle.entity.Brick;
import cn.edu.hdu.tankbattle.entity.Iron;
import cn.edu.hdu.tankbattle.entity.Water;
import cn.edu.hdu.tankbattle.resource.map.xml.XmlMap;

/**
 * map...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public class Map {

    private Vector<Brick> bricks;

    private Vector<Iron> irons;

    private Vector<Water> waters;


    public Map() {
        // TODO Auto-generated constructor stub
        bricks = new Vector<>();
        irons = new Vector<>();
        waters = new Vector<>();
    }

    public Map(XmlMap xmlMap) {
        bricks = new Vector<>();
        irons = new Vector<>();
        waters = new Vector<>();

        xmlMap.getBricks().getBricks().forEach(b -> bricks.add(new Brick(b.getX(), b.getY())));
        xmlMap.getIrons().getIrons().forEach(i -> irons.add(new Iron(i.getX(), i.getY())));
        xmlMap.getWaters().getWaters().forEach(w -> waters.add(new Water(w.getX(), w.getY())));

    }

    public Vector<Brick> getBricks() {
        return bricks;
    }

    public void setBricks(Vector<Brick> bricks) {
        this.bricks = bricks;
    }

    public Vector<Iron> getIrons() {
        return irons;
    }

    public void setIrons(Vector<Iron> irons) {
        this.irons = irons;
    }

    public Vector<Water> getWaters() {
        return waters;
    }

    public void setWaters(Vector<Water> waters) {
        this.waters = waters;
    }

}

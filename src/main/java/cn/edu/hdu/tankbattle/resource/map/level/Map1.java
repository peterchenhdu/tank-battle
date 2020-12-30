/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.resource.map.level;

import cn.edu.hdu.tankbattle.entity.Brick;
import cn.edu.hdu.tankbattle.entity.Iron;
import cn.edu.hdu.tankbattle.entity.Water;
import cn.edu.hdu.tankbattle.resource.map.Map;

import java.util.Vector;

/**
 * 游戏地图默认关卡1...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public class Map1 extends Map {
    /**
     * 构造方法
     */
    public Map1() {
        Vector<Brick> bricks = this.getBricks();
        Vector<Iron> irons = this.getIrons();
        Vector<Water> waters = this.getWaters();
        // 画出砖块
        for (int i = 0; i < 30; i++) {
            if (i % 2 == 0)
                continue;
            Brick brick = new Brick(20 * i + 10, 470);
            bricks.add(brick);
        }
        // 画出河流
        for (int i = 0; i < 25; i++) {
            Water water = new Water(20 * i + 60, 130);
            waters.add(water);
        }
        for (int i = 8; i < 15; i++) {
            Water water = new Water(100, 20 * i + 120);
            waters.add(water);
        }
        for (int i = 0; i < 25; i++) {
            if (i == 11 || i == 12 || i == 13)
                continue;
            Water water = new Water(20 * i + 60, 60);
            waters.add(water);
        }
        // 画出铁块
        for (int i = 0; i < 29; i++) {
            if (i % 2 == 0 || i % 3 == 0)
                continue;
            Iron iron = new Iron(20 * i + 10, 540);
            irons.add(iron);
        }
        Iron iron = new Iron(10, 540);
        irons.add(iron);
        for (int i = 0; i < 27; i++) {
            if (i % 3 == 0)
                continue;
            if (i >= 10 && i <= 15)
                continue;
            iron = new Iron(20 * i - 10, 200);
            irons.add(iron);
        }
        iron = new Iron(230, 150);
        irons.add(iron);
        iron = new Iron(230, 170);
        irons.add(iron);
        iron = new Iron(290, 200);
        irons.add(iron);

        iron = new Iron(290, 290);
        irons.add(iron);
        iron = new Iron(310, 290);
        irons.add(iron);
        iron = new Iron(290, 310);
        irons.add(iron);
        iron = new Iron(310, 310);
        irons.add(iron);
        iron = new Iron(590, 400);
        irons.add(iron);
        iron = new Iron(570, 400);
        irons.add(iron);

        Brick brick = new Brick(200, 290);
        bricks.add(brick);
        brick = new Brick(220, 290);
        bricks.add(brick);
        brick = new Brick(200, 310);
        bricks.add(brick);
        brick = new Brick(220, 310);
        bricks.add(brick);

        Water water = new Water(380, 290);
        waters.add(water);
        water = new Water(400, 290);
        waters.add(water);
        water = new Water(380, 310);
        waters.add(water);
        water = new Water(400, 310);
        waters.add(water);
    }

}

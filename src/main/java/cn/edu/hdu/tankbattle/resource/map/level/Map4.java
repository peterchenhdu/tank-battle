/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.resource.map.level;

import cn.edu.hdu.tankbattle.entity.Water;
import cn.edu.hdu.tankbattle.resource.map.Map;

import java.util.Vector;

/**
 * 游戏地图默认关卡4...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public class Map4 extends Map {
    public Map4() {
        Vector<Water> waters = this.getWaters();
        for (int i = 0; i < 25; i++) {
            if (i == 11 || i == 12 || i == 13)
                continue;
            Water Water = new Water(20 * i + 60, 60);
            waters.add(Water);
        }
        for (int i = 0; i < 25; i++) {
            if (i == 11 || i == 12 || i == 13)
                continue;
            Water Water = new Water(20 * i + 60, 460);
            waters.add(Water);
        }
        for (int i = 0; i < 25; i++) {
            if (i == 11 || i == 12 || i == 13)
                continue;
            Water Water = new Water(20 * i + 60, 140);
            waters.add(Water);
        }
        for (int i = 0; i < 25; i++) {
            if (i == 11 || i == 12 || i == 13)
                continue;
            Water Water = new Water(20 * i + 60, 540);
            waters.add(Water);
        }
        for (int i = 0; i < 25; i++) {
            if (i == 11 || i == 12 || i == 13)
                continue;
            Water Water = new Water(60, 20 * i + 60);
            waters.add(Water);
        }
        for (int i = 0; i < 25; i++) {
            if (i == 11 || i == 12 || i == 13)
                continue;
            Water Water = new Water(540, 20 * i + 60);
            waters.add(Water);
        }
        Water Water = new Water(290, 290);
        waters.add(Water);
        Water = new Water(310, 290);
        waters.add(Water);
        Water = new Water(290, 310);
        waters.add(Water);
        Water = new Water(310, 310);
        waters.add(Water);
    }
}

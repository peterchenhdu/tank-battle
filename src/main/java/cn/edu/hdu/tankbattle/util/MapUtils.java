/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.util;

import cn.edu.hdu.tankbattle.entity.Stuff;
import cn.edu.hdu.tankbattle.exception.TankBattleGameException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/4/1 20:09
 */
public class MapUtils {
    public static List<Stuff> getFullMapStuff() {
        List<Stuff> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                list.add(new Stuff(20 * i + 10, 20 * j + 10));
            }
        }
        return list;
    }

    public static Stuff getNearestStuff(int x, int y) {
        List<Stuff> list = getFullMapStuff();
        for (Stuff s : list) {
            if (Math.abs(s.getX() - x) <= 10 && Math.abs(s.getY() - y) <= 10) {
                return s;
            }
        }
        throw new TankBattleGameException("...");
    }
}

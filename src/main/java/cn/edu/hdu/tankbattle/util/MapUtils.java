/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.util;

import cn.edu.hdu.tankbattle.entity.Stuff;
import cn.edu.hdu.tankbattle.exception.TankBattleGameException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/4/1 20:09
 */
public class MapUtils {
    /**
     * 获取地图中所有可以存放物体的坐标集合
     *
     * @return 坐标集合
     */
    public static List<Stuff> getFullMapStuff() {
        List<Stuff> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                list.add(new Stuff(20 * i + 10, 20 * j + 10));
            }
        }
        return list;
    }

    /**
     * 获取离x,y坐标最近的一个物体坐标
     *
     * @param x 坐标
     * @param y 坐标
     * @return 最近的一个物体坐标
     */
    public static Stuff getNearestStuff(int x, int y) {
        List<Stuff> list = getFullMapStuff();
        for (Stuff s : list) {
            if (Math.abs(s.getX() - x) <= 10 && Math.abs(s.getY() - y) <= 10) {
                return s;
            }
        }
        throw new TankBattleGameException("get Nearest Stuff error");
    }

    /**
     * 获取自定义地图列表
     *
     * @return List
     */
    public static List<String> getCustomFileList() {
        File customFile = new File(System.getProperty("user.home") + File.separator + ".tankBattle" + File.separator + "custom");
        if (!customFile.exists()) {
            if (!customFile.mkdirs()) {
                throw new TankBattleGameException("create custom file error...");
            }
        }

        Collection<File> listFiles = FileUtils.listFiles(customFile, FileFilterUtils.suffixFileFilter("xml"), DirectoryFileFilter.INSTANCE);
        List<String> list = new ArrayList<>();
        for (File file : listFiles) {
            list.add(file.getName().substring(0, file.getName().lastIndexOf(".")));
            System.out.println(file.getName());
        }
        return list;
    }

}

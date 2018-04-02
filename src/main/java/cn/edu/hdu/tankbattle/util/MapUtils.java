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

    public static List<String> getCustomFileList() {
        Collection<File> listFiles = FileUtils.listFiles(new File(System.getProperty("user.home") + File.separator +
                ".tankBattle" + File.separator + "custom"), FileFilterUtils.suffixFileFilter("xml"), DirectoryFileFilter
                .INSTANCE);
        List<String> list = new ArrayList<>();
        for (File file : listFiles) {
            list.add(file.getName().substring(0, file.getName().lastIndexOf(".")));
            System.out.println(file.getName());
        }
        return list;
    }

    public static void main(String[] args) {
        getCustomFileList();
    }
}

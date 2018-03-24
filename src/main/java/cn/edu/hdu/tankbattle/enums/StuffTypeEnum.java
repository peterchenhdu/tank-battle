/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.enums;


/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/3/24 21:03
 */
public enum StuffTypeEnum {
    INVALID(-1, "无效"),
    BRICK(0, "砖块"),
    IRON(1, "铁块"),
    WATER(2, "水池"),
    TANK(3, "坦克"),
    MAP(4, "地图");

    private Integer key;
    private String name;

    StuffTypeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }

    public static StuffTypeEnum getByKey(Integer key) {
        for (StuffTypeEnum tmp : StuffTypeEnum.values()) {
            if (tmp.getKey().equals(key)) {
                return tmp;
            }
        }
        return INVALID;
    }

    public Integer getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}

/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.enums;

/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/3/24 21:18
 */
public enum DirectionEnum {
    INVALID(-1, "无效"),
    NORTH(0, "北"),
    SOUTH(1, "南"),
    WEST(2, "西"),
    EAST(3, "东");
    private Integer key;
    private String name;

    DirectionEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }

    public static DirectionEnum getByKey(Integer key) {
        for (DirectionEnum tmp : DirectionEnum.values()) {
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

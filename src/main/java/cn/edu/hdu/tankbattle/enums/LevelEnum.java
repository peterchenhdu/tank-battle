/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.enums;

import cn.edu.hdu.tankbattle.resource.map.*;
import cn.edu.hdu.tankbattle.resource.map.level.*;

/**
 * 关卡枚举...
 *
 * @author chenpi
 * @since 2018/3/21 21:12
 */
public enum LevelEnum {
    INVALID_LEVEL(-1, "无效", null),
    FIRST_LEVEL(1, "第一关", new Map1()),
    SECOND_LEVEL(2, "第二关", new Map2()),
    THIRD_LEVEL(3, "第三关", new Map3()),
    FOUR_LEVEL(4, "第四关", new Map4()),
    FIVE_LEVEL(5, "第五关", new Map5());

    private Integer value;
    private String name;
    private Map map;

    LevelEnum(Integer value, String name, Map map) {
        this.value = value;
        this.name = name;
        this.map = map;
    }

    public static LevelEnum getByLevel(Integer level) {
        for (LevelEnum levelEnum : LevelEnum.values()) {
            if (levelEnum.getValue().equals(level)) {
                return levelEnum;
            }
        }
        return INVALID_LEVEL;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static LevelEnum nextLevel(LevelEnum level) {
        int nextValue = level.getValue() + 1;
        LevelEnum nextLevel = getByLevel(nextValue);
        return nextLevel == INVALID_LEVEL ? FIRST_LEVEL : nextLevel;
    }

    public Map getMap() {
        return map;
    }
}

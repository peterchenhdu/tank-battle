/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.resource.map.xmlparse.dto;

import org.apache.commons.digester3.annotations.rules.BeanPropertySetter;
import org.apache.commons.digester3.annotations.rules.ObjectCreate;

/**
 * XmlBrick...
 *
 * @author chenpi
 * @since 2018/3/31 9:10
 */
@ObjectCreate(pattern = "map/bricks/brick")
public class XmlBrick {
    @BeanPropertySetter(pattern = "map/bricks/brick/x")
    private Integer x;
    @BeanPropertySetter(pattern = "map/bricks/brick/y")
    private Integer y;

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}

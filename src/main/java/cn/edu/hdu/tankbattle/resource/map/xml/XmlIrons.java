/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.resource.map.xml;

import org.apache.commons.digester3.annotations.rules.ObjectCreate;
import org.apache.commons.digester3.annotations.rules.SetNext;

import java.util.Vector;

/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/3/31 9:17
 */
@ObjectCreate(pattern = "map/irons")
public class XmlIrons {
    private Vector<XmlIron> irons = new Vector<>();


    @SetNext
    public void addIron(XmlIron iron) {
        this.irons.add(iron);
    }
    public Vector<XmlIron> getIrons() {
        return irons;
    }

    public void setIrons(Vector<XmlIron> irons) {
        this.irons = irons;
    }
}

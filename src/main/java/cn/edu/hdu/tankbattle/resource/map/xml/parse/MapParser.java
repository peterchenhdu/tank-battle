/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.resource.map.xml.parse;

import cn.edu.hdu.tankbattle.resource.map.xml.XmlMap;
import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.annotations.FromAnnotationsRuleModule;
import org.apache.commons.digester3.binder.DigesterLoader;

public class MapParser {
    public static XmlMap getXmlMap() {
        try {

            DigesterLoader loader = DigesterLoader.newLoader(new FromAnnotationsRuleModule() {
                @Override
                protected void configureRules() {
                    bindRulesFrom(XmlMap.class);
                }

            });

            Digester digester = loader.newDigester();
            return digester.parse(MapParser.class.getResource("/static/xml/map.xml"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
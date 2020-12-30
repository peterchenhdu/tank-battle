/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.resource.map.xmlparse;

import cn.edu.hdu.tankbattle.entity.Stuff;
import cn.edu.hdu.tankbattle.exception.TankBattleGameException;
import cn.edu.hdu.tankbattle.resource.map.Map;
import cn.edu.hdu.tankbattle.resource.map.xmlparse.dto.XmlMap;
import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.annotations.FromAnnotationsRuleModule;
import org.apache.commons.digester3.binder.DigesterLoader;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;

/**
 * 地图xml解析...
 *
 * @author chenpi
 * @since 2018/3/31
 */
public class MapParser {
    private static Logger logger = LoggerFactory.getLogger(MapParser.class);

    /**
     * xml转XmlMap对象
     *
     * @param name 文件名
     * @return XmlMap对象
     */
    public static XmlMap getMapFromXml(String name) {
        DigesterLoader loader = DigesterLoader.newLoader(new FromAnnotationsRuleModule() {
            @Override
            protected void configureRules() {
                bindRulesFrom(XmlMap.class);
            }
        });

        try {
            Digester digester = loader.newDigester();
            String filePath = System.getProperty("user.home") + File.separator + ".tankBattle" + File.separator + "custom" + File.separator + name + ".xml";
            return digester.parse(new FileInputStream(new File(filePath)));
        } catch (Exception e) {
            logger.error(e.toString(), e);
            throw new TankBattleGameException("xml解析失败");
        }
    }

    /**
     * 根据Map对象生成xml文件
     *
     * @param map     Map对象
     * @param mapName 地图名字
     */
    public static void generateXmlFromMap(Map map, String mapName) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element rootElement = document.createElement("map");

            Element bricks = document.createElement("bricks");
            map.getBricks().forEach(b -> bricks.appendChild(generateElementByStuff(document, b)));
            rootElement.appendChild(bricks);

            Element irons = document.createElement("irons");
            map.getIrons().forEach(i -> irons.appendChild(generateElementByStuff(document, i)));
            rootElement.appendChild(irons);

            Element waters = document.createElement("waters");
            map.getWaters().forEach(w -> waters.appendChild(generateElementByStuff(document, w)));
            rootElement.appendChild(waters);

            document.appendChild(rootElement);

            String filePath = System.getProperty("user.home") + File.separator + ".tankBattle" + File.separator + "custom" + File.separator + mapName + ".xml";
            Result outputTarget = new StreamResult(FileUtils.openOutputStream(new File(filePath)));
            TransformerFactory.newInstance().newTransformer().transform(new DOMSource(document), outputTarget);
        } catch (Exception e) {
            logger.error(e.toString(), e);
            throw new TankBattleGameException("xml文件生成失败");
        }
    }

    /**
     * 根据物体生成Element
     *
     * @param document org.w3c.dom.Document
     * @param stuff    Stuff
     * @return Element
     */
    private static Element generateElementByStuff(Document document, Stuff stuff) {
        Element element = document.createElement(stuff.getType().getXmlMark());
        Element x = document.createElement("x");
        x.setTextContent(stuff.getX().toString());
        Element y = document.createElement("y");
        y.setTextContent(stuff.getY().toString());
        element.appendChild(x);
        element.appendChild(y);
        return element;
    }
}

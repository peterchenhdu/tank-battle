/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.view.panel;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import cn.edu.hdu.tankbattle.control.Control;
import cn.edu.hdu.tankbattle.control.Draw;
import cn.edu.hdu.tankbattle.control.Painter;
import cn.edu.hdu.tankbattle.model.GameResource;
import cn.edu.hdu.tankbattle.model.TankGameImages;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * GamePanel...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public class GamePanel extends JPanel {

    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 3979366008132703255L;

    private Painter painter;

    public GamePanel(Painter painter) {

        this.painter = painter;
    }


    /*
     * 重新paint
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.painter.paint(this, g);
        LogFactory.getLog(this.getClass()).debug("paint...");
    }


}

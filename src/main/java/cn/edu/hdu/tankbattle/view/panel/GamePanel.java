/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.view.panel;

import java.awt.Graphics;

import javax.swing.JPanel;


import cn.edu.hdu.tankbattle.control.Painter;
import org.apache.commons.logging.LogFactory;

/**
 * GamePanel...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public class GamePanel extends JPanel {


    private static final long serialVersionUID = 2933760710140135907L;
    private Painter painter;

    public GamePanel(Painter painter) {
        super();
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

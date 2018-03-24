/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.view.panel;

import java.awt.Graphics;

import javax.swing.JPanel;


import cn.edu.hdu.tankbattle.service.PaintService;
import org.apache.commons.logging.LogFactory;

/**
 * GamePanel...
 *
 * @author chenpi
 * @since 2011-02-10 19:29
 */
public class GamePanel extends JPanel {


    private static final long serialVersionUID = 2933760710140135907L;
    private PaintService paintService;

    public GamePanel(PaintService paintService) {
        super();
        this.paintService = paintService;
    }

    /*
     * 重新paint
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.paintService.rePaintPanel(this, g);
        LogFactory.getLog(this.getClass()).debug("paint...");
    }
}

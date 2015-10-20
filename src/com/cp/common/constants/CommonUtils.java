
package com.cp.common.constants;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.cp.tankGameMain.GameStart;



public class CommonUtils {
	private static Logger logger = Logger.getLogger(GameStart.class);
	public static void cuSleep(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			logger.error("exception in Thread.sleep function.");
			e.printStackTrace();
		}
	}
	
	public static void repaintPerMsec(JPanel jpanel, long msec){
		jpanel.repaint();
		CommonUtils.cuSleep(msec);
	}
}

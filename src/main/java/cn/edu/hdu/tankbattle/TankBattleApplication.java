/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 坦克大战游戏Application...
 *
 * @author chenpi
 * @since 2018/3/19 19:36
 */
@SpringBootApplication
public class TankBattleApplication {
    private static final Logger logger = LoggerFactory.getLogger(TankBattleApplication.class);
    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        new SpringApplicationBuilder(TankBattleApplication.class).web(WebApplicationType.NONE).run(args);
        logger.info("TankBattleApplication started...");
    }
}

/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TankBattleApplication...
 *
 * @author chenpi
 * @since 2018/3/19 19:36
 */
@SpringBootApplication
public class TankBattleApplication {
    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(TankBattleApplication.class, args);
        System.out.println("started...");
    }
}

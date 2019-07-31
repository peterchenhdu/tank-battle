/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志记录工具类
 * <p>
 * Created by chenpi on 2019/02/05.
 */
public class LogUtils {

    private static Map<String, Logger> loggerMap = new HashMap<>();

    public static void debug(String message, Object... params) {
        Logger log = getLogger(getClassName());
        if (log.isDebugEnabled()) {
            log.debug(message, params);
        }
    }


    public static void info(String message, Object... params) {
        Logger log = getLogger(getClassName());
        if (log.isInfoEnabled()) {
            log.info(message, params);
        }
    }


    public static void warn(String message, Object... params) {
        Logger log = getLogger(getClassName());
        log.warn(message, params);
    }


    public static void error(String message, Object... params) {
        Logger log = getLogger(getClassName());
        log.error(message, params);
    }

    public static void exception(Throwable e) {
        Logger log = getLogger(getClassName());
        log.error(e.toString(), e);
    }

    public static void debug(String message) {
        Logger log = getLogger(getClassName());
        if (log.isDebugEnabled()) {
            log.debug(message);
        }
    }


    public static void info(String message) {
        Logger log = getLogger(getClassName());
        if (log.isInfoEnabled()) {
            log.info(message);
        }
    }


    public static void warn(String message) {
        Logger log = getLogger(getClassName());
        log.warn(message);
    }


    public static void error(String message) {
        Logger log = getLogger(getClassName());
        log.error(message);
    }


    /**
     * 获取最开始的调用者所在类
     *
     * @return
     */
    private static String getClassName() {
        return new Throwable().getStackTrace()[2].getClassName();
    }

    /**
     * 根据类名获得logger对象
     *
     * @param className className
     * @return Logger
     */
    private static Logger getLogger(String className) {
        Logger log = null;
        if (loggerMap.containsKey(className)) {
            log = loggerMap.get(className);
        } else {
            try {
                log = LoggerFactory.getLogger(Class.forName(className));
                loggerMap.put(className, log);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return log;
    }
}
/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.util;

/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/3/24 9:24
 */
public class ObjectUtils extends org.springframework.util.ObjectUtils {
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }
}

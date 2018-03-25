/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/3/25 22:01
 */
public class ObjectUtilsTest {
    @Test
    public void isEmpty() {
        Assert.assertTrue(ObjectUtils.isEmpty(null));
        Assert.assertTrue(ObjectUtils.isEmpty(""));
        Assert.assertTrue(ObjectUtils.isEmpty(new ArrayList<>()));
        Assert.assertTrue(ObjectUtils.isEmpty(new HashMap<>()));
        Assert.assertTrue(ObjectUtils.isEmpty(new int[]{}));
    }

    @Test
    public void isNotEmpty() {
        Assert.assertTrue(ObjectUtils.isNotEmpty(new Integer("123")));
        Assert.assertTrue(ObjectUtils.isNotEmpty("1234"));
        Assert.assertTrue(ObjectUtils.isNotEmpty(Arrays.asList("str1", "str2")));
        Assert.assertTrue(ObjectUtils.isNotEmpty(new HashMap<String, String>() {
            private static final long serialVersionUID = -2527234358375937940L;

            {
                put("k1", "v1");
                put("k2", "v2");
            }
        }));
        Assert.assertTrue(ObjectUtils.isNotEmpty(new int[]{1, 2}));
    }

}
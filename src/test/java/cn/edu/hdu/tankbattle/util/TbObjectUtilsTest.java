/*
 * Copyright (c) 2011-2025 PiChen.
 */

package cn.edu.hdu.tankbattle.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Class Description...
 *
 * @author chenpi
 * @since 2018/3/25 22:01
 */
public class TbObjectUtilsTest {
    @Test
    public void isEmpty() {
        Assert.assertTrue(TbObjectUtils.isEmpty(null));
        Assert.assertTrue(TbObjectUtils.isEmpty(""));
        Assert.assertTrue(TbObjectUtils.isEmpty(new ArrayList<>()));
        Assert.assertTrue(TbObjectUtils.isEmpty(new HashMap<>()));
        Assert.assertTrue(TbObjectUtils.isEmpty(new int[]{}));
    }

    @Test
    public void isNotEmpty() {
        Assert.assertTrue(TbObjectUtils.isNotEmpty(Integer.valueOf("123")));
        Assert.assertTrue(TbObjectUtils.isNotEmpty("1234"));
        Assert.assertTrue(TbObjectUtils.isNotEmpty(Arrays.asList("str1", "str2")));
        Assert.assertTrue(TbObjectUtils.isNotEmpty(new HashMap<String, String>() {
            private static final long serialVersionUID = -2527234358375937940L;

            {
                put("k1", "v1");
                put("k2", "v2");
            }
        }));
        Assert.assertTrue(TbObjectUtils.isNotEmpty(new int[]{1, 2}));
    }

}

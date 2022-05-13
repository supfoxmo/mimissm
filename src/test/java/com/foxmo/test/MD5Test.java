package com.foxmo.test;

import com.foxmo.utils.MD5Util;
import org.junit.Test;

public class MD5Test {
    @Test
    public void test1(){
        String mi = MD5Util.getMD5("123456");
        System.out.println(mi);   //7c4a8d09ca3762af61e59520943dc26494f8941b
    }
}

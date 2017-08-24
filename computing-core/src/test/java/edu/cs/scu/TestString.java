package edu.cs.scu;

import edu.cs.scu.javautils.StringUtil;

/**
 * Created by Wang Han on 2017/6/21 17:50.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class TestString {
    public static void main(String args[]) {
        String a = "A=1|B=2|C=3";
        System.out.println(StringUtil.deleteFieldFromConcatString(a, "\\|", "A"));
        System.out.println(StringUtil.deleteFieldFromConcatString(a, "\\|", "B"));
        System.out.println(StringUtil.deleteFieldFromConcatString(a, "\\|", "C"));

    }
}

package edu.cs.scu;

import edu.cs.scu.constants.TimeConstants;
import edu.cs.scu.javautils.DateUtil;

/**
 * Created by Wang Han on 2017/6/18 16:06.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class TestDate {

    public static void main(String args[]) throws Exception{

        System.out.println(DateUtil.parseTime("2017-05-12 23:33:43", TimeConstants.DATE_FORMAT));
        System.out.println("0e:34:cb:30:15:72".length());

        System.out.println(DateUtil.after("2017-05-12 12:00:00","2017-05-12 12:00:59",TimeConstants.TIME_FORMAT,
                59000));
    }

}

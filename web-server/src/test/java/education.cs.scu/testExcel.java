package education.cs.scu;

import education.cs.scu.entity.UserVisitTimeBean;
import education.cs.scu.service.UserVisitTimeService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wang Han on 2017/6/27 15:47.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 *
 * @author Wang Han
 */
public class testExcel {
    @Resource
    private UserVisitTimeService userVisitTimeService;

    @Test
    public void test1() {
        List<UserVisitTimeBean> userVisitTimeBeanArrayList = userVisitTimeService.getUserVisitTime(0, 2);
        for (UserVisitTimeBean item : userVisitTimeBeanArrayList) {

            System.out.println(item.getVisitTime());
        }

    }

    @Test
    public void test2() {

        int n = 100;//迭代的步数
        int[] rec = new int[4]; //各点状态
        List<Integer> current = new ArrayList<Integer>() ;//满足的当前层级的点
        for (int i = 0; i < n; i++) {
            for (int j = 0;j < 4;j++) {
                if (isFit(rec[j])) { //判断各点是否满足状态
                    current.add(rec[j]); //如果满足则得到当前层次的所有点
                }
            }
        }

    }

    private boolean isFit(int i) {
        return true;
    }
}

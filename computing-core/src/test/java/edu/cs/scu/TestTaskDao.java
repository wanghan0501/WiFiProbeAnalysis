package edu.cs.scu;

import edu.cs.scu.bean.TaskBean;
import edu.cs.scu.dao.impl.TaskDaoImpl;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wang Han on 2017/4/7 10:44.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © Wang Han. SCU. All Rights Reserved.
 */
public class TestTaskDao {
    private static Logger logger = Logger.getLogger(TestTaskDao.class);

    public static void main(String[] args) {

        TaskDaoImpl taskDaoImpl = new TaskDaoImpl();
        int count = taskDaoImpl.getTaskCount();
        System.out.println("----------");
        System.out.println("总数 : " + count);
        System.out.println("----------");
        List<TaskBean> taskBeanList = new ArrayList<>();
        taskBeanList = taskDaoImpl.getTaskInfo();
        for (TaskBean taskBean : taskBeanList) {
            System.out.println("id: " + taskBean.getTaskId() + " 参数：" + taskBean.getTaskParam());
        }
        System.out.println("----------");
        TaskBean taskBean = new TaskBean();
        taskBean = taskDaoImpl.getTaskById(1L);
        System.out.println("1L 的创建时间" + taskBean.getCreateTime());
        System.out.println("----------");
        TaskBean taskBean1 = new TaskBean(3L, "test2", "2017-03-20", "2017-02-20",
                "2017-03-21", "test2", "test2", "{\"A\":2}");
        taskDaoImpl.addTask(taskBean1);
        System.out.println("----------");
        System.out.println("finished");
    }
}

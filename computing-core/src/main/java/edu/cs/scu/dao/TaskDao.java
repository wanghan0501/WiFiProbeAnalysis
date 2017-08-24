package edu.cs.scu.dao;

import edu.cs.scu.bean.TaskBean;

import java.util.List;


/**
 * 任务信息数据库接口
 * <p>
 * Created by Wang Han on 2017/4/6 17:13.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © Wang Han. SCU. All Rights Reserved.
 *
 * @author Wang Han
 */
public interface TaskDao {
    //查询总数
    int getTaskCount();

    // 查询所有任务信息
    List<TaskBean> getTaskInfo();

    //根据条件查询用户信息
    TaskBean getTaskById(Long id);

    void addTask(TaskBean taskBean);
}

package edu.cs.scu.bean;

import java.io.Serializable;

/**
 * 任务类
 * <p>
 * Created by Wang Han on 2017/4/2 23:33.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © Wang Han. SCU. All Rights Reserved.
 *
 * @author Wang Han
 */
public class TaskBean implements Serializable {

    private static final long serialVersinUID = 351877796426921776L;

    private Long taskId;
    private String taskName;
    private String createTime;
    private String startTime;
    private String finishTime;
    private String taskType;
    private String taskStatus;
    private String taskParam;

    public static long getSerialVersinUID() {
        return serialVersinUID;
    }

    public TaskBean(Long taskId, String taskName, String createTime, String startTime,
                    String finishTime, String taskType, String taskStatus, String taskParam) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.createTime = createTime;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.taskType = taskType;
        this.taskStatus = taskStatus;
        this.taskParam = taskParam;
    }

    public TaskBean(){

    }
    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskParam() {
        return taskParam;
    }

    public void setTaskParam(String taskParam) {
        this.taskParam = taskParam;
    }

}
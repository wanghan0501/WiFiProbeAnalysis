package education.cs.scu.entity;

import java.io.Serializable;

/**
 *  任务类
 *
 * @Author lch
 * @Create on 2017/09/2 19:12
 **/
public class TaskBean implements Serializable {

    private static final long serialVersinUID = 351877796426921776L;

    private Long taskId;
    private String taskName;
    private Long createTime;
    private Long startTime;
    private Long finishTime;
    private String taskType;
    private String taskStatus;
    private String taskParam;

    public static long getSerialVersinUID() {
        return serialVersinUID;
    }

    public TaskBean() {
    }

    public TaskBean(Long taskId, String taskName, Long createTime, Long startTime, Long finishTime, String taskType, String taskStatus, String taskParam) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.createTime = createTime;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.taskType = taskType;
        this.taskStatus = taskStatus;
        this.taskParam = taskParam;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Long finishTime) {
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
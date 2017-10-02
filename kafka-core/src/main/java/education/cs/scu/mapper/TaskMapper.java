package education.cs.scu.mapper;

import education.cs.scu.entity.TaskBean;

import java.util.List;

/**
 * @Author lch
 * @Create on 2017/09/03 12:26
 **/
public interface TaskMapper {

    int addTask(TaskBean taskBean) throws Exception;
}

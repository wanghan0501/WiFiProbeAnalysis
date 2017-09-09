package education.cs.scu.mapper;

import education.cs.scu.entity.User;
import education.cs.scu.entity.UserBean;
import education.cs.scu.entity.UserFlow;
import education.cs.scu.entity.UserVisitBean;
import education.cs.scu.javautils.ExcelUtil;

import java.util.List;

/**
 * Created by maicius on 2017/6/18.
 */
public interface UserVisitMapper {
    void addUserVisit(UserVisitBean userVisitBean) throws Exception;
    List<UserVisitBean>  queryUserVisit(List<Integer> shopIdlist) throws Exception;
    List<UserBean> queryUserShop(List<Integer> shopIdlist) throws Exception;
}

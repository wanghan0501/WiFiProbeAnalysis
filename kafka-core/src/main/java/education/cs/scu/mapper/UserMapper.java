package education.cs.scu.mapper;

import education.cs.scu.entity.User;

/**
 * Created by maicius on 2017/3/31.
 */
public interface UserMapper {
    User doUserLogin(User user) throws Exception;
    int updateVerifyCode(User user) throws Exception;
    void doUserRegist(User user) throws Exception;
}

package education.cs.scu.service;

import education.cs.scu.entity.User;

/**
 * Created by maicius on 2017/3/31.
 */
public interface LoginService {
    User doUserLogin(User user) throws Exception;
    String verifyCode(User user) throws Exception;
    int updateVerifyCode(User user) throws Exception;
    void doUserRegist(User user) throws Exception;
}

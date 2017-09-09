package education.cs.scu.Redis;

import education.cs.scu.BaseResources;
import education.cs.scu.entity.User;
import education.cs.scu.service.impl.LoginServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by maicius on 2017/7/25.
 */

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration( classes = {LoginService.class, LoginServiceImpl.class, LoginDAO.class})
public class testRedisLogin extends BaseResources{
//    @Autowired
//    private LoginService loginService;
    private LoginServiceImpl loginService = new LoginServiceImpl();
    @Test
    public void testRedisLogin() throws Exception{
        User user = new User();
        user.setUserName("18996720676");
        String res = loginService.verifyCode(user);
        if(res.length() > 0){
            user.setVerifyCode(res);
            user.setPassword(res);
            User newUser = loginService.doUserLogin(user);
            //登陆成功返回用户名
            System.out.println(newUser.getUserName());
        }
    }
}

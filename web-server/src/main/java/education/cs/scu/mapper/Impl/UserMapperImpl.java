package education.cs.scu.mapper.Impl;


import education.cs.scu.entity.User;
import education.cs.scu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

/**
 * Created by maicius on 2017/7/26.
 * Edited by lch on 2017/8/25
 */
public class UserMapperImpl implements UserMapper {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    private static String USER_KEY = "ADMIN_USER";
    /**
     * 用户登陆类
     * 根据key值从Redis Hash中提取数据并进行验证
     * @param user
     * @return 带有用户昵称的用户对象
     * @throws Exception
     */
    public User doUserLogin2(User user) throws Exception{
        return null;
    }
    public User doUserLogin(User user) throws Exception {
        User loginUser = new User();
        try {
            HashOperations<String,Object,Object> hashOperations = redisTemplate.opsForHash();
            Map<Object, Object> userMap = hashOperations.entries(user.getUserName());
            System.out.println("userpassword:" + user.getPassword());
            System.out.println("userpassword:" + userMap.get("password"));
            if (user.getPassword().equals(userMap.get("password")) &&
                    user.getVerifyCode().equals(userMap.get("verifyCode"))) {
                //登陆成功返回用户名
                System.out.println("登陆成功");
                loginUser.setUserName((String) userMap.get("userName"));
                loginUser.setNickName((String) userMap.get("nickName"));
                //登陆成功清除验证码
                hashOperations.put(loginUser.getUserName(),"verifyCode","");
            }
        }catch(Exception e){
            //发生错误时强制关闭实例
            e.printStackTrace();
        }
        return loginUser;
    }

    /**
     * 更新验证码
     * @param user
     * @return 返回值为int而不是Long是为了与mysql的返回值一致
     * @throws Exception
     */
    public int updateVerifyCode(User user) throws Exception {
        redisTemplate.opsForHash().put(user.getUserName(),"verifyTime",user.getVerifyTime());
        redisTemplate.opsForHash().put(user.getUserName(),"verifyCode",user.getVerifyCode());
        return 1;
    }

    public void doUserRegist(User user) throws Exception {
        this.redisTemplate.opsForHash().put(user.getUserName(),"userName", user.getUserName());
        this.redisTemplate.opsForHash().put(user.getUserName(),"password", user.getPassword());
        this.redisTemplate.opsForHash().put(user.getUserName(),"nickName", user.getNickName());
    }
}

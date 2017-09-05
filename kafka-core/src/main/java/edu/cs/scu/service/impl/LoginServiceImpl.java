package edu.cs.scu.service.impl;

import edu.cs.scu.entity.User;
import edu.cs.scu.mapper.UserMapper;
import edu.cs.scu.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    public User doUserLogin(User user) throws Exception {
        return userMapper.doUserLogin(user);
    }

    public User doUserVerify(User user) throws Exception {
        return userMapper.doUserVerify(user);
    }
}

package com.cs.scu.controller;

import com.cs.scu.entity.User;
import com.cs.scu.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by maicius on 2017/3/31.
 */
@RestController
public class LoginController {

    @Autowired
    LoginService loginService;
    @RequestMapping(value = "/userLogin")
    public String UserLogin(HttpServletRequest request,
                             @RequestParam(value = "userName") String userName,
                             @RequestParam(value = "password") String password) throws Exception {
        User user = new User(userName, password);
        System.out.println(userName);
        User loginUser = loginService.doUserLogin(user);
        if(loginUser !=null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", loginUser);
            System.out.println("Controller finished" + loginUser.getNickName());
            return loginUser.getNickName();
        }
        else{
            //密码错误
            return "failed";
        }
    }

    @RequestMapping("/verifyUser")
    public String UserVerify( @RequestParam(value="userName") String userName) throws Exception {
        User user = new User();
        user.setUserName(userName);
        User res = loginService.doUserVerify(user);
        if(res !=null){
            System.out.println("Verify:" +userName + res.getNickName());
            return res.getNickName();
        }else{
            //该用户不存在
            return "failed";
        }
    }
}

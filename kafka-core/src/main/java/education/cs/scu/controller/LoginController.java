package education.cs.scu.controller;

import education.cs.scu.entity.ProbeUser;
import education.cs.scu.entity.User;
import education.cs.scu.entity.UserFlow;
import education.cs.scu.entity.UserVisitBean;
import education.cs.scu.javautils.ExcelUtil;
import education.cs.scu.service.LoginService;
import education.cs.scu.service.ProbeUserService;
import education.cs.scu.service.ShopService;
import education.cs.scu.service.UserVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 登陆控制类
 * 负责管理用户登陆时的验证信息
 * Created by maicius on 2017/3/31.
 */
@CrossOrigin
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProbeUserService probeUserService;




    List<ProbeUser> probeUsers = new ArrayList<ProbeUser>();

    /**
     * 用来测试的
     */
    @RequestMapping(value = "testRedis", method = RequestMethod.GET)
    public void testRedis(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam("userName") String userName) throws Exception {

        ProbeUser probeUser = new ProbeUser();
        probeUser.setMac("aa.bb.cc.dd.ee.ff");
        probeUser.setAddr("四川省成都市双流区四川大学江安校区西园7舍");
        probeUser.setMmac("10000");
        probeUser.setBrand("brand_01");
        probeUser.setActivity_degree("30");

        ProbeUser probeUser1 = new ProbeUser();
        probeUser1.setMac("FF.FF.FF.FF.FF.FF");
        probeUser1.setAddr("四川省成都市双流区四川大学江安校区西园8舍");
        probeUser1.setMmac("11111");
        probeUser1.setBrand("brand_02");
        probeUser1.setActivity_degree("40");
        //System.out.println("唯一ID:" +  shopService.getUniqueShopId());

        ExcelUtil<ProbeUser> excelUtil = new ExcelUtil<ProbeUser>();

        for (int i = 0;i<10;i++) {
            if (i < 5) {
                probeUsers.add(probeUser);
            }else {
                probeUsers.add(probeUser1);
            }

        }

        try {
            probeUserService.setProbeUser(probeUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<ProbeUser> list = probeUserService.queryProbeUser(new User());
        for (ProbeUser probeUser3 :list) {
            //System.out.println(probeUser3.getAddr()+ ":" +probeUser3.getMac());
        }

        String[] header = {"mmac","addr","mac","brand","first_time","last_time","arise_time","degree"};
        excelUtil.exportExcel(header,list,response.getOutputStream());
    }

    /**
     * 控制用户登登陆
     *
     * @param request    保存session
     * @param userName
     * @param password
     * @param verifyCode
     * @return 返回用户昵称
     * @throws Exception
     */
    @RequestMapping(value = "/userLogin", method = RequestMethod.GET)
    public User userLogin(HttpServletRequest request,
                          @RequestParam(value = "userName") String userName,
                          @RequestParam(value = "password") String password,
                          @RequestParam(value = "verifyCode") String verifyCode) throws Exception {
        HttpSession session = request.getSession();
        System.err.println("login session");
        System.err.println(session);

        User user = new User(userName, password, verifyCode);
        System.err.println(userName);
        User loginUser = loginService.doUserLogin(user);
        //获取当前系统时间，便于Monitor查询时间
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        UserFlow userFlow = new UserFlow();
        userFlow.setTimestamp(timestamp);

        if (loginUser != null) {
            session.setAttribute("user", loginUser);
            System.out.println("finish:" + loginUser.getNickName());
            return loginUser;
        } else {
            //返回一个空对象
            System.out.println("failed to login");
            return new User();
        }
    }

    /**
     * 控制验证码发送
     * 根据用户名发送验证码
     *
     * @param request
     * @param userName
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "verifyCode", method = RequestMethod.GET)
    public User userVerify(HttpServletRequest request,
                           @RequestParam("userName") String userName) throws Exception {
        User user = new User();
        user.setUserName(userName);
        String res = loginService.verifyCode(user);
        if (res.length() > 0) {
            return user;
        } else {
            return new User();
        }
    }

    @RequestMapping(value = "/userRegist", method = RequestMethod.GET)
    public void userRegist(HttpServletRequest request,
                           @RequestParam(value = "userName") String userName,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "nickName") String nickName) throws Exception {
        User user = new User();
        user.setUserName(userName);
        user.setNickName(nickName);
        user.setPassword(password);
        System.out.println(userName);
        loginService.doUserRegist(user);
    }
}

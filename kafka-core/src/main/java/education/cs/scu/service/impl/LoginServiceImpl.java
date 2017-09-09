package education.cs.scu.service.impl;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import education.cs.scu.entity.AlidayuSMS;
import education.cs.scu.entity.User;
import education.cs.scu.javautils.VerifyCodeUtil;
import education.cs.scu.mapper.UserMapper;
import education.cs.scu.service.LoginService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by maicius on 2017/3/31.
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;

    public User doUserLogin(User user) throws Exception {
        //return loginDAO.doUserLogin(user);
        return userMapper.doUserLogin(user);
    }

    public String verifyCode(User user) throws Exception {
        String url = "http://gw.api.taobao.com/router/rest";
        int code = VerifyCodeUtil.createVerifyCode();
        TaobaoClient client = new DefaultTaobaoClient(url,
                "23780335",
                "e158afdc661f0d72cf0855b05900f774");
        AlidayuSMS alidayuSMS = new AlidayuSMS();
        alidayuSMS.setCode(String.valueOf(code));
        alidayuSMS.setName(user.getUserName());
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(alidayuSMS);

        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();

        //必须填写normal
        req.setSmsType("normal");
        //应用名称
        req.setSmsFreeSignName("WIFI探针管理平台");
        //电话号码
        req.setRecNum(user.getUserName());
        //模板
        req.setSmsTemplateCode("SMS_74350014");
        req.setExtend(user.getUserName());
        req.setSmsParamString(json);
        try {
            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
            user.setVerifyCode(String.valueOf(code));
            user.setVerifyTime(String.valueOf(new Date()));
            System.out.println(rsp.getBody());
//            HttpSession session = request.getSession();
//            session.setAttribute("verifyCode", String.valueOf(code));
            //int res = userMapper.updateVerifyCode(user);
            int res = userMapper.updateVerifyCode(user);
            System.err.println("验证码" + code);
            return String.valueOf(code);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public int updateVerifyCode(User user) throws Exception {
        user.setVerifyCode(String.valueOf(VerifyCodeUtil.createVerifyCode()));
        return userMapper.updateVerifyCode(user);
    }

    public void doUserRegist(User user) throws Exception {
        try {
            userMapper.doUserRegist(user);
            System.out.println("注册成功");
        }catch (Exception e){
            System.out.println("注册失败");
            e.printStackTrace();
        }
    }
}

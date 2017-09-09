package education.cs.scu.entity;

import java.io.Serializable;

/**
 * Created by maicius on 2017/3/31.
 */
public class User implements Serializable {
    private String userName;
    private String password;
    private String nickName;
    private String verifyCode;
    private String verifyTime;
    public User(){}

    public User(String userName, String password, String verifyCode){
        this.userName = userName;
        this.password = password;
        this.verifyCode = verifyCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(String verifyTime) {
        this.verifyTime = verifyTime;
    }
}

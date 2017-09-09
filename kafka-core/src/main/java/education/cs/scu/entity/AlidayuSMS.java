package education.cs.scu.entity;

import java.io.Serializable;

/**
 * 阿里大于短信管理实体类
 * Created by maicius on 2017/6/28.
 */
public class AlidayuSMS implements Serializable {
    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

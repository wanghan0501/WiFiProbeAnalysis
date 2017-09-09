package education.cs.scu.entity.entityData;

import java.io.Serializable;

/**
 * @Author lch
 * @Create on 2017/09/05 23:49
 **/
public class Hour implements Serializable{

    private static final long serialVersionUID = -5247104210598820601L;
    private Integer hour;
    private Integer number;
    private Integer checkInnum;

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getCheckInnum() {
        return checkInnum;
    }

    public void setCheckInnum(Integer checkInnum) {
        this.checkInnum = checkInnum;
    }
}

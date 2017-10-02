package education.cs.scu.entity.entityData;

import java.io.Serializable;

/**
 * @Author lch
 * @Create on 2017/09/05 22:56
 **/
public class Month implements Serializable {

    private static final long serialVersionUID = -7816256075766296336L;
    private Integer month;
    private Integer number;
    private Integer checkInnum;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
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

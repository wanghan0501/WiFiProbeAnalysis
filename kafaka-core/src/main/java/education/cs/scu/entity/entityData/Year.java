package education.cs.scu.entity.entityData;

import java.io.Serializable;

/**
 * @Author lch
 * @Create on 2017/09/05 22:54
 **/
public class Year implements Serializable {

    private static final long serialVersionUID = -6998992770158457776L;
    private Integer year;
    private Integer number;
    private Integer checkInnum;
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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

package education.cs.scu.entity.entityData;

import java.io.Serializable;
import java.util.List;

/**
 * @Author lch
 * @Create on 2017/09/05 22:57
 **/
public class Day implements Serializable {

    private static final long serialVersionUID = -6513964700147498985L;
    private Long day;
    private List<Hour> hour;

    public Long getDay() {
        return day;
    }

    public void setDay(Long day) {
        this.day = day;
    }


    public List<Hour> getHour() {
        return hour;
    }

    public void setHour(List<Hour> hour) {
        this.hour = hour;
    }
}

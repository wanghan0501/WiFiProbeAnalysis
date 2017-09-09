package education.cs.scu.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by maicius on 2017/6/28.
 */
public class ProbeUser implements Serializable{

    private static final long serialVersionUID = -114946284919754742L;
    private String mmac;
    private String addr;
    private String mac;
    private String brand;
    private Timestamp first_time;
    private Timestamp last_time;
    private Integer arise_times;
    private String activity_degree;

    public String getMmac() {
        return mmac;
    }

    public void setMmac(String mmac) {
        this.mmac = mmac;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Timestamp getFirst_time() {
        return first_time;
    }

    public void setFirst_time(Timestamp first_time) {
        this.first_time = first_time;
    }

    public Timestamp getLast_time() {
        return last_time;
    }

    public void setLast_time(Timestamp last_time) {
        this.last_time = last_time;
    }

    public Integer getArise_times() {
        return arise_times;
    }

    public void setArise_times(Integer arise_times) {
        this.arise_times = arise_times;
    }

    public String getActivity_degree() {
        return activity_degree;
    }

    public void setActivity_degree(String activity_degree) {
        this.activity_degree = activity_degree;
    }
}

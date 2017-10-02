package education.cs.scu.entity;

import java.io.Serializable;

/**
 * Created by maicius on 2017/6/28.
 */
public class ProbeInfo implements Serializable{

    private static final long serialVersionUID = 5759687546989052462L;
    private String user_name;
    private String mmac;
    private String shop_name;
    private Double lat;
    private Double lon;
    private String addr;
    private String state;
    private Integer total_data;
    private Double rate;
    private Integer shop_id;


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMmac() {
        return mmac;
    }

    public void setMmac(String mmac) {
        this.mmac = mmac;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getTotal_data() {
        return total_data;
    }

    public void setTotal_data(Integer total_data) {
        this.total_data = total_data;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getShop_id() {
        return shop_id;
    }

    public void setShop_id(Integer shop_id) {
        this.shop_id = shop_id;
    }
}

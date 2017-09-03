package edu.cs.scu.bean;
public class macDataBean {
    private String router;
    private Integer rssi;
    private Double range;
    private String tmc;
    private String mac;
    private String tc;
    private String ds;

    public macDataBean(){}
    public macDataBean(String router, Integer rssi, Double range, String tmc, String mac, String tc, String ds){
        this.router = router;
        this.rssi = rssi;
        this.range = range;
        this.tmc = tmc;
        this.mac = mac;
        this.tc = tc;
        this. ds = ds;
    }
    public String getRouter() {
        return router;
    }

    public void setRouter(String router) {
        this.router = router;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public Double getRange() {
        return range;
    }

    public void setRange(Double range) {
        this.range = range;
    }

    public String getTmc() {
        return tmc;
    }

    public void setTmc(String tmc) {
        this.tmc = tmc;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }
}

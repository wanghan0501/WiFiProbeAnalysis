package education.cs.scu.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maicius on 2017/6/28.
 */
public class PropertyBean implements Serializable {

    private static final long serialVersionUID = 351877796426921776L;
    private Integer propertyId;
    private Integer shopId;
    private String mmac;
    private String visitCycle;
    private Double visitRange;
    private Integer visitRSSI;
    private String activityDegree;
    private String visitTimeSplit;
    private boolean propertyType;

    public PropertyBean() {
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getMmac() {
        return mmac;
    }

    public void setMmac(String mmac) {
        this.mmac = mmac;
    }

    public String getVisitCycle() {
        return visitCycle;
    }

    public void setVisitCycle(String visitCycle) {
        this.visitCycle = visitCycle;
    }

    public Double getVisitRange() {
        return visitRange;
    }

    public void setVisitRange(Double visitRange) {
        this.visitRange = visitRange;
    }

    public Integer getVisitRSSI() {
        return visitRSSI;
    }

    public void setVisitRSSI(Integer visitRSSI) {
        this.visitRSSI = visitRSSI;
    }

    public String getActivityDegree() {
        return activityDegree;
    }

    public void setActivityDegree(String activityDegree) {
        this.activityDegree = activityDegree;
    }

    public String getVisitTimeSplit() {
        return visitTimeSplit;
    }

    public void setVisitTimeSplit(String visitTimeSplit) {
        this.visitTimeSplit = visitTimeSplit;
    }

    public boolean isPropertyType() {
        return propertyType;
    }

    public void setPropertyType(boolean propertyType) {
        this.propertyType = propertyType;
    }
}

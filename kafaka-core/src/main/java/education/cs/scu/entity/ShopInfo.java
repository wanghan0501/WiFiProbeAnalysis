package education.cs.scu.entity;

import java.io.Serializable;

/**
 * Created by maicius on 2017/6/27.
 */
public class ShopInfo implements Serializable{

    private static final long serialVersionUID = -8211531756489532620L;
    private Integer shop_id;
    private String shop_owner;
    private String shop_addr;
    private String shop_name;
    private String shop_manager;
    private String shop_telephone;
    private String shop_describe;
    public ShopInfo(){
    }

    public Integer getShop_id() {
        return shop_id;
    }

    public void setShop_id(Integer shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_owner() {
        return shop_owner;
    }

    public void setShop_owner(String shop_owner) {
        this.shop_owner = shop_owner;
    }

    public String getShop_addr() {
        return shop_addr;
    }

    public void setShop_addr(String shop_addr) {
        this.shop_addr = shop_addr;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_manager() {
        return shop_manager;
    }

    public void setShop_manager(String shop_manager) {
        this.shop_manager = shop_manager;
    }

    public String getShop_telephone() {
        return shop_telephone;
    }

    public void setShop_telephone(String shop_telephone) {
        this.shop_telephone = shop_telephone;
    }

    public String getShop_describe() {
        return shop_describe;
    }

    public void setShop_describe(String shop_describe) {
        this.shop_describe = shop_describe;
    }
}

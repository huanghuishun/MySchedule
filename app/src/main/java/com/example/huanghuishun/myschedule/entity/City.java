package com.example.huanghuishun.myschedule.entity;

/**
 * Created by huanguhishun on 2016/8/19.
 */
public class City {
    /*
            + "id integet primary key autoincrement," // id integer
            + "name text,"  //name text 名字
            + "adcode integer," //adcode integer 编码
            + "citycode integet," //citycode integer 区号
            + "location text)"; //location text 所属城市
     */

    private int id;
    private String name;
    private int adCode;
    private int cityCode;
    private String location;

    public int getAdCode() {
        return adCode;
    }

    public void setAdCode(int adCode) {
        this.adCode = adCode;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

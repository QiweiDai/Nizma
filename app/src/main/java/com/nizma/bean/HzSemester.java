package com.nizma.bean;

import com.nizma.utils.DateUtil;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by w-w on 2016/11/3.
 */
public class HzSemester {
    private String id;

    private String name;

    private String startt;

    private String endt;

    private String type;

    private String years;

    /* 自定义 */
    /**
     * 当前周.当当前课程为空，调用这个值
     */
    private String currentWeek;

    private String formatStartt;	//格式化起始日期
    private String formatEndt;		//格式化结束日期

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartt() {
        return startt;
    }

    public void setStartt(String startt) {
        this.startt = startt;
    }

    public String getEndt() {
        return endt;
    }

    public void setEndt(String endt) {
        this.endt = endt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getCurrentWeek() {
        return currentWeek;
    }

    public void setCurrentWeek(String currentWeek) {
        this.currentWeek = currentWeek;
    }

    public String getFormatStartt() {
        return formatStartt;
    }

    public void setFormatStartt(String formatStartt) {
        this.formatStartt = formatStartt;
    }

    public String getFormatEndt() {
        return formatEndt;
    }

    public void setFormatEndt(String formatEndt) {
        this.formatEndt = formatEndt;
    }

    @Override
    public String toString() {
        return "HzSemester{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", startt='" + startt + '\'' +
                ", endt='" + endt + '\'' +
                ", type='" + type + '\'' +
                ", years='" + years + '\'' +
                ", currentWeek='" + currentWeek + '\'' +
                ", formatStartt='" + formatStartt + '\'' +
                ", formatEndt='" + formatEndt + '\'' +
                '}';
    }
}

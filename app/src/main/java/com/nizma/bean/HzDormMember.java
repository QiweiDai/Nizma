package com.nizma.bean;

/**
 * Created by WZW on 2016/9/28.
 */
public class HzDormMember {

    private String studentId;//学生id
    private String studentName;//学生名字
    private String quarter;//生活区
    private String building;//栋号
    private String dormNum;//寝室号
    private String classId;//班级id
    private String className;//班级名字
    private Integer cadreId;//权限

    public Integer getCadreId() {return cadreId;}
    public void setCadreId(Integer cadreId) {this.cadreId = cadreId;}
    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public String getQuarter() {
        return quarter;
    }
    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }
    public String getBuilding() {
        return building;
    }
    public void setBuilding(String building) {
        this.building = building;
    }
    public String getDormNum() {
        return dormNum;
    }
    public void setDormNum(String dormNum) {
        this.dormNum = dormNum;
    }
    public String getClassId() {
        return classId;
    }
    public void setClassId(String classId) {
        this.classId = classId;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    private String id;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    private String dcstate;//签到状态

    public String getDcstate() {
        return dcstate;
    }
    public void setDcstate(String dcstate) {
        this.dcstate = dcstate;
    }
}

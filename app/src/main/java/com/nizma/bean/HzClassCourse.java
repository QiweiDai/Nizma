package com.nizma.bean;

/**
 * Created by WZW on 2016/9/27.
 */
public class HzClassCourse {
    private Integer id;

    private Integer classid;

    private String course;

    private String studyplace;

    private Integer beginweek;

    private Integer endweek;

    private Integer semesterId;

    private String teacherId;

    private String academy;

    private Float point;

    private Integer period;

    private Integer isRequest;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course == null ? null : course.trim();
    }

    public String getStudyplace() {
        return studyplace;
    }

    public void setStudyplace(String studyplace) {
        this.studyplace = studyplace == null ? null : studyplace.trim();
    }

    public Integer getBeginweek() {
        return beginweek;
    }

    public void setBeginweek(Integer beginweek) {
        this.beginweek = beginweek;
    }

    public Integer getEndweek() {
        return endweek;
    }

    public void setEndweek(Integer endweek) {
        this.endweek = endweek;
    }

    public Integer getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Integer semesterId) {
        this.semesterId = semesterId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId == null ? null : teacherId.trim();
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy == null ? null : academy.trim();
    }

    public Float getPoint() {
        return point;
    }

    public void setPoint(Float point) {
        this.point = point;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getIsRequest() {
        return isRequest;
    }

    public void setIsRequest(Integer isRequest) {
        this.isRequest = isRequest;
    }

}

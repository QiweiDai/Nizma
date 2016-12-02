package com.nizma.bean;

/**
 * Created by WZW on 2016/9/21.
 */
/**
 * 班级详情类
 * @author L
 *
 */
public class HzClasses {
    private Integer id;

    private Integer classSn;

    private String className;

    private String teacherId;

    private Integer isFinish;

    private Integer classcheckCadreid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassSn() {
        return classSn;
    }

    public void setClassSn(Integer classSn) {
        this.classSn = classSn;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }

    public Integer getClasscheckCadreid() {
        return classcheckCadreid;
    }

    public void setClasscheckCadreid(Integer classcheckCadreid) {
        this.classcheckCadreid = classcheckCadreid;
    }

    /************* 自定义字段 ****************/
    private String teacherName;// 班主任名字
    private String teacherPhone;// 班主任联系方式

    private Integer studentCount; // 班级人数
    private String classCadre; 	// 上课点到的职位名称

    private boolean addFlag;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName == null ? "" : teacherName;
    }

    public String getTeacherPhone() {
        return teacherPhone;
    }

    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    public String getClassCadre() {
        return classCadre;
    }

    public void setClassCadre(String classCadre) {
        this.classCadre = classCadre;
    }

    public boolean isAddFlag() {
        return addFlag;
    }

    public void setAddFlag(boolean addFlag) {
        this.addFlag = addFlag;
    }


}

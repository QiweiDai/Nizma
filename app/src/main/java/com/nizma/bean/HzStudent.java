package com.nizma.bean;

import com.nizma.utils.DateUtil;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by WZW on 2016/9/21.
 */
public class HzStudent {

    private Integer id;

    private String sno;

    private String name;

    private String sex;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    private String birthday;

    private String political;

    private String idcard;

    private String nation;

    private String address;

    private String postcode;

    private String qq;

    private String wechat;

    private String email;

    private Integer classid;

    private Integer dorid;

    private String password;

    private String phone;

    private String cornet;

    private String fatherName;

    private String fatherPhone;

    private String motherName;

    private String motherPhone;

    private String header;

    private Integer cadreId;

    private String sudentStateStr;

    private Integer special;

    private String preDomicile;

    private String preSchoolStatus;

    private String birthPlace;

    private String major;

    private Date enterSchoolTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno == null ? null : sno.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }


    public String getSudentStateStr() {
        return sudentStateStr;
    }

    public void setSudentStateStr(String sudentStateStr) {
        this.sudentStateStr = sudentStateStr;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political == null ? null : political.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public Integer getDorid() {
        return dorid;
    }

    public void setDorid(Integer dorid) {
        this.dorid = dorid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getCornet() {
        return cornet;
    }

    public void setCornet(String cornet) {
        this.cornet = cornet == null ? null : cornet.trim();
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName == null ? null : fatherName.trim();
    }

    public String getFatherPhone() {
        return fatherPhone;
    }

    public void setFatherPhone(String fatherPhone) {
        this.fatherPhone = fatherPhone == null ? null : fatherPhone.trim();
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName == null ? null : motherName.trim();
    }

    public String getMotherPhone() {
        return motherPhone;
    }

    public void setMotherPhone(String motherPhone) {
        this.motherPhone = motherPhone == null ? null : motherPhone.trim();
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header == null ? null : header.trim();
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Integer getSpecial() {
        return special;
    }

    public void setSpecial(Integer special) {
        this.special = special;
    }

    public String getPreDomicile() {
        return preDomicile;
    }

    public void setPreDomicile(String preDomicile) {
        this.preDomicile = preDomicile == null ? null : preDomicile.trim();
    }

    public String getPreSchoolStatus() {
        return preSchoolStatus;
    }

    public void setPreSchoolStatus(String preSchoolStatus) {
        this.preSchoolStatus = preSchoolStatus == null ? null : preSchoolStatus.trim();
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace == null ? null : birthPlace.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public Date getEnterSchoolTime() {
        return enterSchoolTime;
    }

    public void setEnterSchoolTime(Date enterSchoolTime) {
        this.enterSchoolTime = enterSchoolTime;
        if (enterSchoolTime != null) {
            this.enterSchoolTimeStr = DateUtil.dateFormat("yyyy-MM-dd", enterSchoolTime);
        }
    }

    /*
     * --------------------------------------
     * --------------自定义字段
     * --------------------------------------
    */
    /**
     * 所在班级
     */
    private HzClasses hzClasses;

    /**
     * 临时变量，签到的状态
     */
    private Integer checkinsState;

    /**
     * 统计的数量
     */
    private Integer count;

    private String courseName;	// 课程名
//    private Date courseDate;	//上课日期
    private String courseDateStr;
    private String section;	// 上课节次


    private String teacherName;//班主任名字
    private String className;//班级名字
    private String teacherPhone;//班主任联系方式

    private String classCadre;	// 职位名称

    private String enterSchoolTimeStr; // 入学时间 字符型
    private String birthdayStr;	// 生日字符型

    private String doridName;	//所在的寝室名称
    private boolean addfalg;//数据库是否添加成功

    private Integer isDormitoryLeader;	// 寝室长标志 0表示不是，1表示是寝室长

    public String getDoridName() {
//        if (StringUtils.isBlank(doridName)) {
//            doridName = "";
//        }
        return doridName;
    }

    public Integer getIsDormitoryLeader() {
        return isDormitoryLeader;
    }

    public void setIsDormitoryLeader(Integer isDormitoryLeader) {
        this.isDormitoryLeader = isDormitoryLeader;
    }

    public void setDoridName(String doridName) {
        this.doridName = doridName;
    }



    public String getEnterSchoolTimeStr() {
        return enterSchoolTimeStr;
    }

    public void setEnterSchoolTimeStr(String enterSchoolTimeStr) {
        this.enterSchoolTimeStr = enterSchoolTimeStr;
        try {
            this.enterSchoolTime = DateUtil.toDate(enterSchoolTimeStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getClassCadre() {
        return classCadre;
    }

    public void setClassCadre(String classCadre) {
        this.classCadre = classCadre;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeacherPhone() {
        return teacherPhone;
    }

    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

//    public Date getCourseDate() {
//        return courseDate;
//    }
//
//    public void setCourseDate(Date courseDate) {
//        this.courseDateStr = DateUtil.dateFormat("yyyy-MM-dd", courseDate);
//        this.courseDate = courseDate;
//    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCheckinsState() {
        if (checkinsState == null) {
            checkinsState = 0;
        }
        return checkinsState;
    }

    public void setCheckinsState(Integer checkinsState) {
        this.checkinsState = checkinsState;
    }

    public HzClasses getHzClasses() {
        return hzClasses;
    }

    public void setHzClasses(HzClasses hzClasses) {
        this.hzClasses = hzClasses;
    }

    public String getCourseDateStr() {
        return courseDateStr;
    }

    public void setCourseDateStr(String courseDateStr) {
        this.courseDateStr = courseDateStr;
    }
    public boolean isAddfalg() {
        return addfalg;
    }

    public void setAddfalg(boolean addfalg) {
        this.addfalg = addfalg;
    }
    private String errorstr;

    public String getErrorstr() {
        return errorstr;
    }

    public void setErrorstr(String errorstr) {
        this.errorstr = errorstr;
    }


}
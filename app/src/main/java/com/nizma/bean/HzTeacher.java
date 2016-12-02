package com.nizma.bean;

public class HzTeacher {
	private Integer id;

	private String name;

	private String teacherId;

	private String password;

	private String phone;

	private String memo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? "" : name.trim();
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId == "" ? null : teacherId.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == "" ? null : password.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? "" : phone.trim();
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo == null ? "" : memo.trim();
	}

	/***************** 自定义字段 ******************/
	private String className;

	private String permissionTypeName; // 当前职位名称
	
	private String permissionTypeId;	// 职位Id
	
	public String getPermissionTypeId() {
		return permissionTypeId;
	}

	public void setPermissionTypeId(String permissionTypeId) {
		this.permissionTypeId = permissionTypeId;
	}

	public String getPermissionTypeName() {
		return permissionTypeName;
	}

	public void setPermissionTypeName(String permissionTypeName) {
		this.permissionTypeName = permissionTypeName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className == null ? "" : className;
	}

}
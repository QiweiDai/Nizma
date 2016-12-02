package com.nizma.bean;

/**
 * Created by WZW on 2016/9/26.
 */
public class AppError {
    private Integer errorid;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    private String error;

    public Integer getErrorid() {
        return errorid;
    }

    public void setErrorid(Integer errorid) {
        this.errorid = errorid;
    }
}

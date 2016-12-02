package com.nizma.utils;

import android.os.StrictMode;

import com.nizma.bean.AppError;
import com.nizma.bean.HzStudent;
import com.nizma.bean.HzTeacher;
import com.nizma.bean.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by WZW on 2016/9/21.
 */
public class RequestNet {
    private static String httpPath = "http://192.168.0.106:8080/CheckonworkattendanceServer/";
//    private static String httpPath = "http://www.nizma.cn/";
    static private HttpClient httpClient = new DefaultHttpClient();


    public static Map<String, Object> appLogin(String username, String password) {
        try {
            // 打开浏览器
            //HttpClient httpClient = new DefaultHttpClient();
            // 输入地址
            String path = httpPath + "application/applogin";
            HttpPost httpPost = new HttpPost(path);

            // 指向要提交的实体数据
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();

            parameters.add(new BasicNameValuePair("username", username));
            parameters.add(new BasicNameValuePair("password", password));
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));

            // 敲下回车
            HttpResponse response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();

            if (code == 200) {
                InputStream is = response.getEntity().getContent();
                Map<String, Object> restle = StreamTools.LoginreadInputStream(is);
                return restle;
            } else {
                return null;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> loginClientPost() {
        try {
            // 打开浏览器

            // 输入地址
            String path = httpPath + "application/CheckInfo";
            HttpPost httpPost = new HttpPost(path);

            // 指向要提交的实体数据
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();

            parameters.add(new BasicNameValuePair("classid", UserInfo.StudentUser.getClassid().toString()));
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));

            // 敲下回车
            HttpResponse response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();

            if (code == 200) {
                InputStream is = response.getEntity().getContent();
                Map<String, Object> result = StreamTools.CheckInforeadInputStream(is);
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> ClassCheck(String son, String courseId, String section, String teacherId, String students, String classId) {
        try {
            // 输入地址
            String path = httpPath + "application/Check";
            HttpPost httpPost = new HttpPost(path);

            // 指向要提交的实体数据
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();

            parameters.add(new BasicNameValuePair("son", son));
            parameters.add(new BasicNameValuePair("courseId", courseId));
            parameters.add(new BasicNameValuePair("section", section));
            parameters.add(new BasicNameValuePair("teacherId", teacherId));
            parameters.add(new BasicNameValuePair("students", students));
            parameters.add(new BasicNameValuePair("classId", classId));
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));

            // 敲下回车
            HttpResponse response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();

            if (code == 200) {
                InputStream is = response.getEntity().getContent();
                Map<String, Object> result = StreamTools.ClassCheckreadInputStream(is);
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


    public static Map<String, Object> DormCheckInfo(String son) {
        try {
            // 输入地址
            String path = httpPath + "application/DormitoryCheckInfo";
            HttpPost httpPost = new HttpPost(path);

            // 指向要提交的实体数据
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();

            parameters.add(new BasicNameValuePair("son", son));
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));

            // 敲下回车
            HttpResponse response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();

            if (code == 200) {
                InputStream is = response.getEntity().getContent();
                Map<String, Object> result = StreamTools.DormCheckInforeadInputStream(is);
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> DormCheck(String students) {
        try {
            // 输入地址
            String path = httpPath + "application/checkDorm";
            HttpPost httpPost = new HttpPost(path);

            // 指向要提交的实体数据
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();

            parameters.add(new BasicNameValuePair("students", students));
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));

            // 敲下回车
            HttpResponse response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();

            if (code == 200) {
                InputStream is = response.getEntity().getContent();
                Map<String, Object> result = StreamTools.errorORsuccess(is, "DorType");
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> TeacherQueryClass() {
        try {
            // 输入地址
            String path = httpPath + "application/teacherclass";
            HttpPost httpPost = new HttpPost(path);

            // 指向要提交的实体数据
//            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
//
//            parameters.add(new BasicNameValuePair("son", son));
//            httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));

            // 敲下回车
            HttpResponse response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();

            if (code == 200) {
                InputStream is = response.getEntity().getContent();
                Map<String, Object> result = StreamTools.ClassreadInputStream(is);
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> ClassCheckStudentState(String classId, String ccdate, String studentName) {
        try {
            // 输入地址
            String path = httpPath + "application/queryByDay";
            HttpPost httpPost = new HttpPost(path);

            // 指向要提交的实体数据
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();

            //classId=31421&ccdateStr=2016-9-29&studentName=
            parameters.add(new BasicNameValuePair("classId", classId));
            parameters.add(new BasicNameValuePair("ccdate", ccdate));
            parameters.add(new BasicNameValuePair("studentName", studentName));
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));

            // 敲下回车
            HttpResponse response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();

            if (code == 200) {
                InputStream is = response.getEntity().getContent();
                Map<String, Object> result = StreamTools.StudentStatereadInputStream(is);
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> DormCheckStudentState(String classid, String date, String studentName) {
        try {
            // 输入地址
            String path = httpPath + "application/dormTCheckDormYCDRecord";
            HttpPost httpPost = new HttpPost(path);

            // 指向要提交的实体数据
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();

            //classId=31421&ccdateStr=2016-9-29&studentName=
            parameters.add(new BasicNameValuePair("classid", classid));
            parameters.add(new BasicNameValuePair("date", date));
            parameters.add(new BasicNameValuePair("studentName", studentName));
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));

            // 敲下回车
            HttpResponse response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();

            if (code == 200) {
                InputStream is = response.getEntity().getContent();
                Map<String, Object> result = StreamTools.DormStatereadInputStream(is);
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


    public static Map<String, Object> AlterPassword(String oldpassword, String newpassword) {
        try {
            // 输入地址
            String path = httpPath + "application/alertpass";
            HttpPost httpPost = new HttpPost(path);

            // 指向要提交的实体数据
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();

            parameters.add(new BasicNameValuePair("newPassword", newpassword));
            parameters.add(new BasicNameValuePair("oldPassword", oldpassword));
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));

            // 敲下回车
            HttpResponse response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();

            if (code == 200) {
                InputStream is = response.getEntity().getContent();
                Map<String, Object> result = StreamTools.errorORsuccess(is, "queryType");
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static List<Map<String, Integer>> queryweek() {
        try {
            // 输入地址
            String path = httpPath + "application/queryall";
            HttpPost httpPost = new HttpPost(path);

//            // 指向要提交的实体数据
//            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
//
//            httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
            // 敲下回车
            HttpResponse response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();

            if (code == 200) {
                InputStream is = response.getEntity().getContent();
                List<Map<String, Integer>> result = StreamTools.queryallInputStream(is);
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    public static List<HzStudent> checkStudentInfo(String ccdate, String sno,String ccstate,String classId) {
        try {
            // 输入地址
            String path = httpPath + "application/checkStudentInfo";
            HttpPost httpPost = new HttpPost(path);

            // 指向要提交的实体数据
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();

            parameters.add(new BasicNameValuePair("ccdate", ccdate));
            parameters.add(new BasicNameValuePair("sno", sno));
            parameters.add(new BasicNameValuePair("ccstate", ccstate));
            parameters.add(new BasicNameValuePair("classId", classId));
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));

            // 敲下回车
            HttpResponse response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();

            if (code == 200) {
                InputStream is = response.getEntity().getContent();
                List<HzStudent> result = StreamTools.checkStudentInfoInputStream(is);
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    public static List<Map<String, Integer>> todaycheck() {
        try {
            // 输入地址
            String path = httpPath + "application/todaycheck";
            HttpPost httpPost = new HttpPost(path);

//            // 指向要提交的实体数据
//            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
//
//            httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
            // 敲下回车
            HttpResponse response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();

            if (code == 200) {
                InputStream is = response.getEntity().getContent();
                List<Map<String, Integer>> result = StreamTools.queryallInputStream(is);
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static InputStream update() {
        try {
            // 输入地址
            String path = httpPath + "version.xml";
            HttpPost httpPost = new HttpPost(path);
            // 敲下回车
            HttpResponse response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();

            if (code == 200) {
                InputStream is = response.getEntity().getContent();
                return is;
            } else {
                return null;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static int appCancellation() {
        try {
            // 输入地址
            String path = httpPath + "application/appCancellation";
            HttpPost httpPost = new HttpPost(path);
            // 敲下回车
            HttpResponse response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();

            if (code == 200) {
                //InputStream is = response.getEntity().getContent();
                httpClient =  new DefaultHttpClient();
                UserInfo.TeacherUser = null;
                UserInfo.StudentUser = null;
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }
}

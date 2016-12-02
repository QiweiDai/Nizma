package com.nizma.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.nizma.bean.AppError;
import com.nizma.bean.AppSuccess;
import com.nizma.bean.HzClassCourse;
import com.nizma.bean.HzClasses;
import com.nizma.bean.HzDormMember;
import com.nizma.bean.HzDormitorRecord;
import com.nizma.bean.HzSemester;
import com.nizma.bean.HzStudent;
import com.nizma.bean.HzTeacher;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by WZW on 2016/9/21.
 */
public class StreamTools {
    private HzStudent hStudent;

    public static Map<String, Object> LoginreadInputStream(InputStream is) {
        Map<String, Object> restle = new HashMap<String, Object>();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            byte[] result = baos.toByteArray();
            String result_data = new String(result);
            Log.e("result_data", result_data);
            Gson gson = new Gson();
            Map<String, Object> retMap2 = gson.fromJson(result_data, new TypeToken<Map<String, Object>>() {
            }.getType());
            String loginType = (String) retMap2.get("loginType");
            if (loginType.equals("student")) {
                String student = gson.toJson(retMap2.get("student"));
                Gson gg = new Gson();
                restle.put("student", gg.fromJson(student, HzStudent.class));
            } else if (loginType.equals("teacher")) {
                String teacher = gson.toJson(retMap2.get("teacher"));
                Gson gg = new Gson();
                restle.put("teacher", gg.fromJson(teacher, HzTeacher.class));
            } else if (loginType.equals("error")) {
                String error = gson.toJson(retMap2.get("error"));
                Gson gg = new Gson();
                restle.put("error", gg.fromJson(error, AppError.class));
            }
            return restle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> CheckInforeadInputStream(InputStream is) {
        Map<String, Object> restle = new HashMap<String, Object>();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            byte[] result = baos.toByteArray();
            String result_data = new String(result);
            Log.e("result_data", result_data);

            Gson gson = new Gson();
            Map<String, Object> retMap = gson.fromJson(result_data, new TypeToken<Map<String, Object>>() {
            }.getType());
            String checkType = (String) retMap.get("checkType");
            if (checkType.equals("error")) {
                String error = gson.toJson(retMap.get("error"));
                Gson gg = new Gson();
                restle.put("error", gg.fromJson(error, AppError.class));
            } else {
                Gson gg;
                String course = gson.toJson(retMap.get("course"));
                String semester = gson.toJson(retMap.get("semester"));
                String hzStudent = gson.toJson(retMap.get("hzStudentList"));
                String hzTeacher = gson.toJson(retMap.get("hzTeacherList"));
                String judgeStr = gson.toJson(retMap.get("judgeStr"));
                gg = new Gson();
                List<HzClassCourse> courselist = gg.fromJson(course, new TypeToken<ArrayList<HzClassCourse>>() {
                }.getType());
                Log.e("semester", semester);
                gg = new Gson();
                restle.put("semester", gg.fromJson(semester, HzSemester.class));
                gg = new Gson();
                List<HzStudent> hzStudentList = gg.fromJson(hzStudent, new TypeToken<ArrayList<HzStudent>>() {
                }.getType());

                gg = new Gson();
                List<HzTeacher> hzTeacherList = gg.fromJson(hzTeacher, new TypeToken<ArrayList<HzTeacher>>() {
                }.getType());

                restle.put("courselist", courselist);
                restle.put("hzStudentList", hzStudentList);
                restle.put("hzTeacherList", hzTeacherList);
                gg = new Gson();
                String judge = gg.fromJson(judgeStr, String.class);
                restle.put("judgeStr", judge);
            }

            return restle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


    public static Map<String, Object> ClassCheckreadInputStream(InputStream is) {
        Map<String, Object> restle = new HashMap<String, Object>();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            byte[] result = baos.toByteArray();
            String result_data = new String(result);
            Log.e("result_data", result_data);

            Gson gson = new Gson();
            Map<String, Object> retMap = gson.fromJson(result_data, new TypeToken<Map<String, Object>>() {
            }.getType());
            String checkType = (String) retMap.get("classcheckType");
            if (checkType.equals("error")) {
                String error = gson.toJson(retMap.get("error"));
                Gson gg = new Gson();
                restle.put("error", gg.fromJson(error, AppError.class));
            } else {
                String success = gson.toJson(retMap.get("success"));
                Gson gg = new Gson();
                restle.put("success", gg.fromJson(success, AppSuccess.class));
            }
            return restle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> DormCheckInforeadInputStream(InputStream is) {
        Map<String, Object> restle = new HashMap<String, Object>();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            byte[] result = baos.toByteArray();
            String result_data = new String(result);
            Log.e("result_data", result_data);
            Gson gson = new Gson();
            Map<String, Object> retMap = gson.fromJson(result_data, new TypeToken<Map<String, Object>>() {
            }.getType());
            String checkType = (String) retMap.get("dormitoryType");
            if (checkType.equals("error")) {
                String error = gson.toJson(retMap.get("error"));
                Gson gg = new Gson();
                restle.put("error", gg.fromJson(error, AppError.class));
            } else {
                Gson gg;
                String course = gson.toJson(retMap.get("DormStudent"));

                gg = new Gson();
                List<HzDormMember> dormmember = gg.fromJson(course, new TypeToken<ArrayList<HzDormMember>>() {
                }.getType());
                restle.put("dormmember", dormmember);
            }

            return restle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> ClassreadInputStream(InputStream is) {
        Map<String, Object> restle = new HashMap<String, Object>();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            byte[] result = baos.toByteArray();
            String result_data = new String(result);
            Gson gson = new Gson();
            Map<String, Object> retMap = gson.fromJson(result_data, new TypeToken<Map<String, Object>>() {
            }.getType());
            String checkType = (String) retMap.get("queryType");
            if (checkType.equals("error")) {
                String error = gson.toJson(retMap.get("error"));
                Gson gg = new Gson();
                restle.put("error", gg.fromJson(error, AppError.class));
            } else {
                Gson gg;
                String teacherclass = gson.toJson(retMap.get("classesList"));
                gg = new Gson();
                List<HzClasses> classlist = gg.fromJson(teacherclass, new TypeToken<ArrayList<HzClasses>>() {
                }.getType());
                restle.put("classlist", classlist);
            }

            return restle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


    public static Map<String, Object> StudentStatereadInputStream(InputStream is) {
        Map<String, Object> restle = new HashMap<String, Object>();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            byte[] result = baos.toByteArray();
            String result_data = new String(result);
            Log.e("result_data", result_data);
            Gson gson = new Gson();
            Map<String, Object> retMap = gson.fromJson(result_data, new TypeToken<Map<String, Object>>() {
            }.getType());
            String checkType = (String) retMap.get("queryType");
            if (checkType.equals("error")) {
                String error = gson.toJson(retMap.get("error"));
                Gson gg = new Gson();
                restle.put("error", gg.fromJson(error, AppError.class));
            } else if (checkType.equals("success")) {
                String success = gson.toJson(retMap.get("success"));
                Gson gg = new Gson();
                restle.put("success", gg.fromJson(success, AppSuccess.class));
            } else {
                Gson gg;
                String studentState = gson.toJson(retMap.get("studentState"));
                Log.e("studentState", studentState);
                gg = new Gson();
                Map<Integer, List<HzStudent>> classstatelist = gg.fromJson(studentState, new TypeToken<Map<Integer, List<HzStudent>>>() {
                }.getType());
                restle.put("studentState", classstatelist);
            }
            return restle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> DormStatereadInputStream(InputStream is) {
        Map<String, Object> restle = new HashMap<String, Object>();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            byte[] result = baos.toByteArray();
            String result_data = new String(result);
            Log.e("result_data", result_data);
            Gson gson = new Gson();
            Map<String, Object> retMap = gson.fromJson(result_data, new TypeToken<Map<String, Object>>() {
            }.getType());
            String checkType = (String) retMap.get("DormType");
            if (checkType.equals("error")) {
                String error = gson.toJson(retMap.get("error"));
                Gson gg = new Gson();
                restle.put("error", gg.fromJson(error, AppError.class));
            } else if (checkType.equals("success")) {
                String success = gson.toJson(retMap.get("success"));
                Gson gg = new Gson();
                restle.put("success", gg.fromJson(success, AppSuccess.class));
            } else {
                Gson gg;
                String studentState = gson.toJson(retMap.get("DormQuery"));
                Log.e("HzDormitorRecord", studentState);
                gg = new Gson();
                List<HzDormitorRecord> dormstatelist = gg.fromJson(studentState, new TypeToken<List<HzDormitorRecord>>() {
                }.getType());
                restle.put("studentState", dormstatelist);
            }
            return restle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static List<Map<String, Integer>> queryallInputStream(InputStream is) {
        Map<String, Object> restle = new HashMap<String, Object>();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            byte[] result = baos.toByteArray();
            String result_data = new String(result);
            Log.e("result_data", result_data);
            Gson gson = new Gson();
            List<Map<String, Integer>> retMap = gson.fromJson(result_data, new TypeToken<List<Map<String, Integer>>>() {
            }.getType());
            return retMap;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static List<HzStudent> checkStudentInfoInputStream(InputStream is) {
        Map<String, Object> restle = new HashMap<String, Object>();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            byte[] result = baos.toByteArray();
            String result_data = new String(result);
            List<HzStudent> list = new ArrayList<HzStudent>();
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(result_data).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, HzStudent.class));
            }
            return list;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> errorORsuccess(InputStream is, String type) {
        Map<String, Object> restle = new HashMap<String, Object>();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            byte[] result = baos.toByteArray();
            String result_data = new String(result);
            Log.e("result_data", result_data);
            Gson gson = new Gson();
            Map<String, Object> retMap = gson.fromJson(result_data, new TypeToken<Map<String, Object>>() {
            }.getType());
            String checkType = (String) retMap.get(type);

            if (checkType.equals("error")) {
                String error = gson.toJson(retMap.get("error"));
                Gson gg = new Gson();
                restle.put("error", gg.fromJson(error, AppError.class));
            } else if (checkType.equals("success")) {
                String success = gson.toJson(retMap.get("success"));
                Gson gg = new Gson();
                restle.put("success", gg.fromJson(success, AppSuccess.class));
            }
            return restle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
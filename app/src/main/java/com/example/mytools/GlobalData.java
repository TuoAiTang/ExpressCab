package com.example.mytools;

public class GlobalData {
    private static String order_id;
    private static String sid;
    private static String uid;

    public static String getOrder_id() {
        return order_id;
    }

    public static void setOrder_id(String order_id) {
        GlobalData.order_id = order_id;
    }

    public static String getSid() {
        return sid;
    }

    public static void setSid(String sid) {
        GlobalData.sid = sid;
    }

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        GlobalData.uid = uid;
    }
}

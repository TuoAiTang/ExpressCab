package com.example.mytools;

public class GlobalData {
    private static String order_id;
    private static String sid;
    private static int uid;

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

    public static int getUid() {
        return uid;
    }

    public static void setUid(int uid) {
        GlobalData.uid = uid;
    }
}

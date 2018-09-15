package com.example.mytools;

import android.util.Log;

import com.example.Entity.AvailableCell;
import com.example.Entity.Order;
import com.example.Entity.info.AllListInfo;
import com.example.Entity.info.AllocateCellInfo;
import com.example.Entity.info.CabinetInfo;
import com.example.Entity.info.DeliveryConfirmInfo;
import com.example.Entity.info.LoginInfo;
import com.example.Entity.info.RegisterSendInfo;
import com.example.Entity.info.RetrieveApplyInfo;
import com.example.Entity.info.RetrieveCheckInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class JsonParseUtil {

    public static LoginInfo parseForLogin(String jsonstr){
        Gson gson = new Gson();
        LoginInfo loginInfo =  gson.fromJson(jsonstr, LoginInfo.class);
        return loginInfo;
    }

    public static List<AvailableCell> parseForCabinet(String jsonstr){
        
        List<AvailableCell> avail_cells = new ArrayList<>();
        Gson gson = new Gson();
        CabinetInfo cabinetInfo = gson.fromJson(jsonstr, CabinetInfo.class);
        Log.d(TAG, "parseForCabinet: msg:" + cabinetInfo.getMsg());
        Log.d(TAG, "parseForCabinet: code:" + cabinetInfo.getCode());
        Log.d(TAG, "parseForCabinet: body" + cabinetInfo.getBody());
//        String json_list_str = cabinetInfo.getBody().getAvail_cells();
//        Log.d(TAG, "parseForCabinet: " + json_list_str);
//        JsonParser parser = new JsonParser();
//        JsonArray jsonArray = parser.parse(json_list_str).getAsJsonArray();
        if(cabinetInfo.getBody() == null){
            return avail_cells;
        }
        JsonArray jsonArray = cabinetInfo.getBody().getAvail_cells();
        for (JsonElement je : jsonArray) {
            AvailableCell availableCell = gson.fromJson(je, AvailableCell.class);
            avail_cells.add(availableCell);
        }
        return avail_cells;
    }

    public static AllocateCellInfo parseForAllocateCell(String jsonstr){
        Gson gson = new Gson();
        AllocateCellInfo allocateCellInfo = gson.fromJson(jsonstr, AllocateCellInfo.class);
        return allocateCellInfo;
    }

    public static DeliveryConfirmInfo parseForDeliveryConfirm(String jsonstr){
        Gson gson = new Gson();
        DeliveryConfirmInfo deliveryConfirmInfo = gson.fromJson(jsonstr, DeliveryConfirmInfo.class);
        return deliveryConfirmInfo;
    }

    public static List<Order> parseForAllList(String jsonstr){
        List<Order> orderList = new ArrayList<>();
        Gson gson = new Gson();
        AllListInfo allListInfo = gson.fromJson(jsonstr, AllListInfo.class);
        if(allListInfo.getCode() == 0){
            JsonArray orderArray = allListInfo.getBody().getOrder_list();
            for (JsonElement je: orderArray) {
                Order order = gson.fromJson(je, Order.class);
                orderList.add(order);
            }
        }else{
            orderList = null;
        }
        return orderList;
    }

    public static RetrieveApplyInfo parseForRetriveApply(String jsonstr){
        Gson gson = new Gson();
        RetrieveApplyInfo retrieveApplyInfo = gson.fromJson(jsonstr, RetrieveApplyInfo.class);
        return retrieveApplyInfo;
    }

    public static RetrieveCheckInfo parseForRetrieveChcheckInfo(String str){
        Gson gson = new Gson();
        RetrieveCheckInfo retrieveCheckInfo = gson.fromJson(str, RetrieveCheckInfo.class);
        return retrieveCheckInfo;
    }

    public static RegisterSendInfo parseForRegisterSend(String str){
        Gson gson = new Gson();
        RegisterSendInfo registerSendInfo = gson.fromJson(str, RegisterSendInfo.class);
        return registerSendInfo;
    }

}

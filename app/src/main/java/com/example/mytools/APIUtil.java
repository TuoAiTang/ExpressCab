package com.example.mytools;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.Entity.AvailableCell;
import com.example.Entity.Order;
import com.example.Entity.info.AllocateCellInfo;
import com.example.Entity.info.DeliveryConfirmInfo;
import com.example.Entity.info.LoginInfo;
import com.example.Entity.info.RegiResetCheckInfo;
import com.example.Entity.info.RetrieveApplyInfo;
import com.example.Entity.info.RetrieveCheckInfo;
import com.example.Entity.info.SendInfo;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class APIUtil {

    //返回LoginInfo交由login_handler处理
    public static void invokeLoginAPI(final Handler handler, String account, String password){

        String api_url = "http://101.200.89.170:9000/capp/login/normal";
        RequestBody requestBody = new FormBody.Builder()
                .add("phone", account)
                .add("password", password)
                .build();
        HttpUtil.sendOkHttpRequest(api_url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                LoginInfo login_info = JsonParseUtil.parseForLogin(responseData);
                Message message = new Message();
                message.obj = login_info;
                handler.sendMessage(message);
            }
        }, requestBody);
    }

    //返回柜体信息List<AvailableCell>交由cab_info_handler处理
    public static void invokeCabinetInfoAPI(final Handler handler, String cab_code){
        String api_url = "http://101.200.89.170:9002/capp/cabinet/info";

        RequestBody requestBody = new FormBody.Builder()
                .add("uid", String.valueOf(GlobalData.getUid()))
                .add("cabinet_code", cab_code)
                .build();
        HttpUtil.sendOkHttpRequest(api_url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                List<AvailableCell> availableCells = JsonParseUtil.parseForCabinet(responseData);
                Message message = new Message();
                message.obj = availableCells;
                handler.sendMessage(message);
            }
        }, requestBody);
    }

    //返回打开格口状态信息AllocateCellInfo交由alloc_cell_handler处理
    public static void invokeAllocateCellAPI(final Handler handler, String uid, String cabinet_code, int cell_type,
                                             String exp_code, String consignee_phone){
        String api_url = "http://101.200.89.170:9002/capp/delivery/allocate_cell";

        RequestBody requestBody = new FormBody.Builder()
                .add("uid", uid)
                .add("cabinet_code", cabinet_code)
                .add("cell_type", String.valueOf(cell_type))
                .add("exp_code", exp_code)
                .add("consignee_phone", consignee_phone)
                .build();
        HttpUtil.sendOkHttpRequest(api_url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                AllocateCellInfo allocateCellInfo = JsonParseUtil.parseForAllocateCell(responseData);
                Message msg = new Message();
                msg.obj = allocateCellInfo;
                handler.sendMessage(msg);
            }
        }, requestBody);
    }

    //返回投递确认信息DelieveryConfirmInfo交由handler处理
    public static void invokeDeliveryConfirmAPI(final Handler handler, String uid, String order_id){
        String api_url = "http://101.200.89.170:9002/capp/delivery/confirm";
        RequestBody requestBody = new FormBody.Builder()
                .add("uid", uid)
                .add("order_id", order_id)
                .build();
        HttpUtil.sendOkHttpRequest(api_url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                DeliveryConfirmInfo deliveryConfirmInfo = JsonParseUtil.parseForDeliveryConfirm(responseData);
                Message msg = new Message();
                msg.obj = deliveryConfirmInfo;
                handler.sendMessage(msg);

            }
        }, requestBody);
    }

    //返回验证码发送状态信息SendInfo交由send_pcode_handler处理
    public static void invokeRegisterSendVercodeAPI(final Handler handler, String account){
        String api_url = "http://101.200.89.170:9000/capp/register/send_pcode";
        RequestBody requestBody = new FormBody.Builder()
                .add("phone", account)
                .build();

        HttpUtil.sendOkHttpRequest(api_url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                SendInfo registerSendInfo = JsonParseUtil.parseForSend(responseData);
                Message message = new Message();
                message.obj = registerSendInfo;
                handler.sendMessage(message);
            }
        }, requestBody);
    }
    //返回重置验证码发送状态信息SendInfo
    public static void invokeResetSendVercodeAPI(final Handler handler, String account){
        String api_url = "http://101.200.89.170:9000/capp/password/send_pcode";
        RequestBody requestBody = new FormBody.Builder()
                .add("phone", account)
                .build();

        HttpUtil.sendOkHttpRequest(api_url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                SendInfo resetSendInfo = JsonParseUtil.parseForSend(responseData);
                Message message = new Message();
                message.obj = resetSendInfo;
                handler.sendMessage(message);
            }
        }, requestBody);
    }

    //返回注册状态信息registerCheckInfo交由register_check_handler处理
    public static void invokeRegisterCheckAPI(final Handler handler, String account,
                                              String ver_code, String password){
        String api_url = "http://101.200.89.170:9000/capp/register/phone";
        RequestBody requestBody = new FormBody.Builder()
                .add("phone", account)
                .add("pcode", ver_code)
                .add("password",password)
                .build();
        HttpUtil.sendOkHttpRequest(api_url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                RegiResetCheckInfo registerCheckInfo = JsonParseUtil.parseForRegiResetCheck(responseData);
                Message message = new Message();
                message.obj = registerCheckInfo;
                handler.sendMessage(message);
            }
        }, requestBody);
    }

    //返回所有订单列表List<Order> orderList
    public static void invokeGetAllAPI(final Handler handler, String uid, String status, String sid){
        String api_url = "http://101.200.89.170:9002/sexp/order/all/list";
        RequestBody requestBody = new FormBody.Builder()
                .add("uid", uid)
                .add("status", status)
                .add("sid", sid)
                .build();
        HttpUtil.sendOkHttpRequest(api_url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                List<Order> orderList = JsonParseUtil.parseForAllList(responseData);
                Message message = new Message();
                message.obj = orderList;
                handler.sendMessage(message);
            }
        }, requestBody);
    }

    //返回取件开箱状态信息RetrieveApplyInfo
    public static void invokeRetriveApplyInfo(final Handler handler, String uid, String order_id){

        String api_url = "http://101.200.89.170:9002/capp/retrieve/apply";
        RequestBody requestBody = new FormBody.Builder()
                .add("uid", uid)
                .add("order_id", order_id)
                .build();
        HttpUtil.sendOkHttpRequest(api_url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                RetrieveApplyInfo retrieveApplyInfo = JsonParseUtil.parseForRetriveApply(responseData);
                Message message = new Message();
                message.obj = retrieveApplyInfo;
                handler.sendMessage(message);
            }
        }, requestBody);
    }

    //返回取回检查信息RetrieveCheckInfo
    public static void invokeRetriveCheckAPI(final Handler handler, String uid, String order_id){
        String api_url = "http://101.200.89.170:9002/capp/retrieve/check";
        final RequestBody requestBody = new FormBody.Builder()
                .add("uid", uid)
                .add("order_id", order_id)
                .build();
        HttpUtil.sendOkHttpRequest(api_url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                RetrieveCheckInfo retrieveCheckInfo = JsonParseUtil
                        .parseForRetrieveChcheckInfo(responseData);
                Message msg = new Message();
                msg.obj = retrieveCheckInfo;
                handler.sendMessage(msg);
            }
        }, requestBody);
    }
}

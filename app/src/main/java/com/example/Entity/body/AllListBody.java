package com.example.Entity.body;

import com.google.gson.JsonArray;

public class AllListBody {
    private int count;

    private JsonArray order_list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public JsonArray getOrder_list() {
        return order_list;
    }

    public void setOrder_list(JsonArray order_list) {
        this.order_list = order_list;
    }
}

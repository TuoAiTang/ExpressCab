package com.example.Entity;

import com.example.Entity.info.AddrInfo;

public class Order {

    private String id;

    private String in_time;

    private String addr;

    private AddrInfo addr_info;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIn_time() {
        return in_time;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public AddrInfo getAddr_info() {
        return addr_info;
    }

    public void setAddr_info(AddrInfo addr_info) {
        this.addr_info = addr_info;
    }
}

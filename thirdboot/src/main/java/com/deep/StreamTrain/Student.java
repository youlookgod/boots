package com.deep.StreamTrain;

/**
 * @Author: George
 * @Date 2018/5/14 10:20
 * @Description
 **/
public class Student {
    private String name;
    private String address;
    private String phone;
    private long height;

    public Student() {
    }

    public Student(String name,String address,String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Student(String name,String address,String phone,long height) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }
}

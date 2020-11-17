package com.king.mycompleterwandanfirebaseapp;

public class Student {
    private String name, adm_no, phone, key;

    public Student(String name, String adm_no, String phone, String key) {
        this.name = name;
        this.adm_no = adm_no;
        this.phone = phone;
        this.key = key;
    }

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdm_no() {
        return adm_no;
    }

    public void setAdm_no(String adm_no) {
        this.adm_no = adm_no;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getKey(){
        return  key;
    }

    public void setKey(String key){
        this.key = key;
    }
}

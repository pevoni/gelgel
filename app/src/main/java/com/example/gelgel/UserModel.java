package com.example.gelgel;

public class UserModel {
    String phoneNumber, address, name, age;

    public UserModel(String phoneNumber, String address, String name, String age) {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.name = name;
        this.age = age;
    }

    public UserModel(){

    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}

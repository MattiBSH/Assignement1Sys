package com.example.assignement1sys;

public class Person {
    String ip;
    String name;
    String email;

    public Person(String ip, String name, String email) {
        this.ip = ip;
        this.name = name;
        this.email = email;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

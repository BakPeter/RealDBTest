package com.bpapps.realmdbtest.repository.localdata.model;

import io.realm.RealmObject;

public class RealmStudent extends RealmObject {

    private int age;
    private String Name;
    private String subject;

    public RealmStudent() {
    }

    public RealmStudent(int age, String name, String subject) {
        this.age = age;
        Name = name;
        this.subject = subject;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}

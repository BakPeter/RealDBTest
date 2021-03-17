package com.bpapps.realmdbtest.repository.localdata.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class RealmPerson extends RealmObject {
    public static final String PRIMARY_ID = "mLastName";
    private static int sIdCreator = 0;

//    @PrimaryKey
//    private int mId;

    @PrimaryKey
    private String mLastName;

    @Required
    private String mFirstName;
    private int mAge;

    public RealmPerson(){}

    public RealmPerson(String mLastName, String mFirstName, int mAge) {
        sIdCreator++;

//        this.mId = sIdCreator;
        this.mLastName = mLastName;
        this.mFirstName = mFirstName;
        this.mAge = mAge;
    }

    @Override
    public String toString() {
        return mLastName + " " + mFirstName + ", " + mAge;
    }

//    public int getId() {
//        return mId;
//    }
//
//    public void setId(int mId) {
//        this.mId = mId;
//    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int mAge) {
        this.mAge = mAge;
    }
}
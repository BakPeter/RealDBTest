package com.bpapps.realmdbtest.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bpapps.realmdbtest.RealmDBTestApp;
import com.bpapps.realmdbtest.repository.localdata.RealmDBHandler;
import com.bpapps.realmdbtest.repository.localdata.model.RealmPerson;

import java.util.List;

public class MainViewModel extends ViewModel implements RealmDBHandler.IDataSetChangedListener {
    private RealmDBHandler mRealmDBHandler = null;
//            RealmDBHandler.getInstance();
//            .init(RealmDBTestApp.getAppContext())
//            .registerForDataDataSetChangesListener(this);

    private MutableLiveData<List<RealmPerson>> persons = new MutableLiveData<>();
    private MutableLiveData<RealmPerson> searchedPerson = new MutableLiveData<>();

    public void savePerson(RealmPerson realmPerson) {
        if (mRealmDBHandler == null) {
            initRealmDataBase();
        }
        mRealmDBHandler.savePerson(realmPerson);
    }

    public LiveData<RealmPerson> getPersonByName(String name) {
        if (mRealmDBHandler == null) {
            initRealmDataBase();
        }

        if (name != null) {
            RealmPerson person = mRealmDBHandler.getPersonByName(name);
            searchedPerson.postValue(person);
        } else {
            searchedPerson.postValue(null);
        }

        return searchedPerson;
    }

    public LiveData<List<RealmPerson>> getAllPersons() {
        if (mRealmDBHandler == null) {
            initRealmDataBase();
        }
        List<RealmPerson> dataSet = mRealmDBHandler.getAllPersons();
        persons.postValue(dataSet);
        return persons;
    }

    private void initRealmDataBase() {
        mRealmDBHandler = RealmDBHandler
                .init(RealmDBTestApp.getAppContext())
                .registerForDataDataSetChangesListener(this);
    }

    @Override
    public void onDataSetChanged(List<RealmPerson> dataSet) {
        persons.postValue(dataSet);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mRealmDBHandler.unRegisterForDataDataSetChangesListener();

    }
}
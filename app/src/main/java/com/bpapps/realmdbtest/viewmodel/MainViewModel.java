package com.bpapps.realmdbtest.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bpapps.realmdbtest.RealmDBTestApp;
import com.bpapps.realmdbtest.repository.localdata.RealmDBHandler;
import com.bpapps.realmdbtest.repository.localdata.model.RealmPerson;

import java.util.List;

public class MainViewModel extends ViewModel implements RealmDBHandler.IDataSetChangedListener {
    private RealmDBHandler mRealmDBHandler = RealmDBHandler
//            .getInstance()
            .init(RealmDBTestApp.getAppContext())
            .registerForDataDataSetChangesListener(this);

    private MutableLiveData<List<RealmPerson>> persons = new MutableLiveData<>();

    public void savePerson(RealmPerson realmPerson) {
        mRealmDBHandler.savePerson(realmPerson);
    }

    public LiveData<List<RealmPerson>> getAllPersons() {
        List<RealmPerson> dataSet = mRealmDBHandler.getAllPersons();
        persons.postValue(dataSet);
        return persons;
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
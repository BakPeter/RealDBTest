package com.bpapps.realmdbtest.repository.localdata;

import android.app.Person;
import android.content.Context;
import android.util.Log;

import com.bpapps.realmdbtest.repository.localdata.model.RealmPerson;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmDBHandler extends Realm.Callback {
    private static final String TAG = "TAG.RealmDBHandler";

    private String APP_REALM_DB_FILE = "app_realm_db_file";

    private static RealmDBHandler sInstance = null;

    private Realm mRealm = null;
    private RealmConfiguration mRealmConfiguration;
    private IDataSetChangedListener mCallback = null;

    public static RealmDBHandler init(Context context) {
        if (sInstance == null) {
            sInstance = new RealmDBHandler(context);
        }

        return sInstance;
    }

    public static RealmDBHandler getInstance() throws IllegalStateException {
        if (sInstance == null) {
            throw new IllegalStateException("RealmDBHandler is not initialized. Invoke RealmDBHandler.init(Context) first.");
        }

        return sInstance;
    }

    private RealmDBHandler(Context context) {
        Realm.init(context);
        mRealmConfiguration = new RealmConfiguration.Builder()
                .name(APP_REALM_DB_FILE)
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .compactOnLaunch()
                .build();
//        Realm.getInstanceAsync(mRealmConfiguration, this);
        mRealm = Realm.getInstance(mRealmConfiguration);

//        Log.d(TAG, "realm : " + mRealm.getPath());
//        mRealm.close();
//        Log.d(TAG, "realm : " + mRealm.getPath());
    }

    public void savePerson(RealmPerson realmPerson) {
//        mRealm.executeTransactionAsync(tr -> {
//            tr.insert(realmPerson);
//        });
        mRealm.beginTransaction();
//        RealmPerson person = mRealm.createObject(RealmPerson.class);
//        person.setAge(realmPerson.getAge());
//        mRealm.copyToRealm(realmPerson);
        mRealm.insert(realmPerson);

        mRealm.commitTransaction();

        if (mCallback != null) {
            getAllPersons();
        }
//        mRealm.close();
    }

    public RealmPerson getPersonByName(String name) {
        if (mRealm != null) {
            RealmQuery<RealmPerson> query = mRealm.where(RealmPerson.class);
            RealmResults<RealmPerson> result = query.equalTo(RealmPerson.PRIMARY_ID, name).findAll();

            if(result.size() >0) {
                return  result.first();
            }
        }

        return null;
    }

    public List<RealmPerson> getAllPersons() {
//        mRealm = Realm.getInstance(mRealmConfiguration);
//        RealmResults<RealmPerson> persons = mRealm.where(RealmPerson.class).findAll();
//        List<RealmPerson> retVal = mRealm.copyFromRealm(persons);
//        mRealm.close();

        if (mRealm != null) {
            RealmResults<RealmPerson> persons = mRealm.where(RealmPerson.class).findAll();
            List<RealmPerson> retVal = mRealm.copyFromRealm(persons);

            if (mCallback != null) {
                mCallback.onDataSetChanged(retVal);
            }

            return retVal;
        } else {
            return null;
        }
    }

    @Override
    public void onSuccess(Realm realm) {
        Log.d(TAG, "Successfully opened a realm with reads and writes not allowed on the UI thread");
        mRealm = realm;
    }

    public void destroy() {
        if (mRealm != null) {
            mRealm.close();
        }
    }

    public RealmDBHandler registerForDataDataSetChangesListener(IDataSetChangedListener callback) {
        mCallback = callback;

        return this;
    }

    public void unRegisterForDataDataSetChangesListener() {
        mCallback = null;
    }

    public interface IDataSetChangedListener {
        void onDataSetChanged(List<RealmPerson> dataSet);
    }
}

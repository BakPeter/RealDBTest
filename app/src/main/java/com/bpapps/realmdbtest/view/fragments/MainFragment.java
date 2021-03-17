package com.bpapps.realmdbtest.view.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bpapps.realmdbtest.R;
import com.bpapps.realmdbtest.repository.localdata.model.RealmPerson;
import com.bpapps.realmdbtest.view.adapters.PersonAdapter;
import com.bpapps.realmdbtest.viewmodel.MainViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private static final String TAG = "TAG.MainFragment";

    private MainViewModel mViewModel;

    private AppCompatEditText mEtLastName;
    private AppCompatEditText mEtFirstName;
    private AppCompatEditText mEtAge;
    private AppCompatButton mBtnSaveUser;
    private RecyclerView mRvDataShower;
    private AppCompatImageButton mIBtnSearch;
    private AppCompatEditText mEtSearchName;
    private Snackbar mSnackBar;

    private PersonAdapter mAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mEtLastName = view.findViewById(R.id.etLastName);
        mEtFirstName = view.findViewById(R.id.etFirstName);
        mEtAge = view.findViewById(R.id.etAge);

        mBtnSaveUser = view.findViewById(R.id.btnSaveUser);
        mBtnSaveUser.setOnClickListener(v -> {
            String lastName = mEtLastName.getText().toString();
            String firstName = mEtFirstName.getText().toString();
            int age = Integer.parseInt(mEtAge.getText().toString());

            RealmPerson realmPerson = new RealmPerson(lastName, firstName, age);

//            Log.d(TAG, "save data : " + user.toString());
            mViewModel.savePerson(realmPerson);
        });

        mRvDataShower = view.findViewById(R.id.rvDataShower);
        mRvDataShower.setLayoutManager(new LinearLayoutManager(requireContext()));
        mRvDataShower.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new PersonAdapter(requireContext(), new ArrayList<RealmPerson>());
        mRvDataShower.setAdapter(mAdapter);
        mViewModel.getAllPersons().observe(getViewLifecycleOwner(), new Observer<List<RealmPerson>>() {
            @Override
            public void onChanged(List<RealmPerson> dataSet) {
                Log.d(TAG, dataSet.toString());
//                PersonAdapter adapter = new PersonAdapter(requireContext(), dataSet);
//                mRvDataShower.setAdapter(adapter);
                mAdapter.setDataSet(dataSet);
            }
        });

        mIBtnSearch = view.findViewById(R.id.iBtnSearch);
        mIBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireContext(), "search for " + mEtSearchName.getText(), Toast.LENGTH_SHORT).show();
                mViewModel.getPersonByName(mEtSearchName.getText().toString());
            }
        });
        mEtSearchName = view.findViewById(R.id.etSearchName);
        mViewModel.getPersonByName(null).observe(
                getViewLifecycleOwner(),
                new Observer<RealmPerson>() {
                    @Override
                    public void onChanged(RealmPerson person) {
                        if (person != null) {
                            Snackbar.make(view.findViewById(R.id.mainViewContainer), person.toString(), Snackbar.LENGTH_SHORT)
                                    .setDuration(5000)
                                    .setTextColor(Color.GREEN)
                                    .show();
                        } else {
                            Snackbar.make(view.findViewById(R.id.mainViewContainer), "no user with given name", Snackbar.LENGTH_LONG)
                                    .setDuration(5000)
                                    .setTextColor(Color.RED)
                                    .show();
                        }

                        mEtSearchName.getText().clear();
                    }
                });
    }
}
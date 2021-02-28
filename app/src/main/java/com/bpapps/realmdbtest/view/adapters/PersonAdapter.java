package com.bpapps.realmdbtest.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bpapps.realmdbtest.R;
import com.bpapps.realmdbtest.repository.localdata.model.RealmPerson;

import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private List<RealmPerson> mDataSet;
    private Context mContext;

    public PersonAdapter(@NonNull Context mContext,  @NonNull List<RealmPerson> dataSet ) {
        this.mContext = mContext;
        this.mDataSet = dataSet;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.person_row_item, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        RealmPerson person = mDataSet.get(position);
        holder.tvPersonItemRow.setText(person.toString());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setDataSet(List<RealmPerson> dataSet) {
        mDataSet.clear();
        mDataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvPersonItemRow;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPersonItemRow = itemView.findViewById(R.id.tvPersonItemRow);
        }
    }
}

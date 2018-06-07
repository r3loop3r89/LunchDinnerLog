package com.shra1.lunchdinnerlog.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shra1.lunchdinnerlog.R;
import com.shra1.lunchdinnerlog.dtos.Person;

import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PAViewHolder> {
    Context mCtx;
    List<Person> people;
    ClickHandlers c;
    public PeopleAdapter(Context mCtx, List<Person> people, ClickHandlers c) {
        this.mCtx = mCtx;
        this.people = people;
        this.c=c;
    }

    @NonNull
    @Override
    public PAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mCtx).inflate(R.layout.people_list_item_layout, parent, false);
        return new PAViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PAViewHolder h, int position) {
        h.tvFullName.setText(people.get(position).getFullName());
        h.tvAddress.setText(people.get(position).getAddress());
        h.tvPhone.setText(people.get(position).getPhone());

        if (people.get(position).isSelectedDaysTiffinEntryDone()){
            h.bTiffin.setEnabled(false);
        }else{
            h.bTiffin.setEnabled(true);
        }


        if (people.get(position).isSelectedDaysDinnerEntryDone()){
            h.bDinner.setEnabled(false);
        }else{
            h.bDinner.setEnabled(true);
        }



        h.bTiffin.setOnClickListener(bt->{
            c.onClickTiffinButton(people.get(position), position);
        });

        h.bDinner.setOnClickListener(bd->{
            c.onClickDinnerButton(people.get(position), position);
        });
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    class PAViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFullName;
        private TextView tvAddress;
        private TextView tvPhone;
        private Button bTiffin;
        private Button bDinner;

        public PAViewHolder(View v) {
            super(v);
            tvFullName = (TextView) v.findViewById(R.id.tvFullName);
            tvAddress = (TextView) v.findViewById(R.id.tvAddress);
            tvPhone = (TextView) v.findViewById(R.id.tvPhone);
            bTiffin = (Button) v.findViewById(R.id.bTiffin);
            bDinner = (Button) v.findViewById(R.id.bDinner);
        }
    }

    public interface ClickHandlers{
        public void onClickTiffinButton(Person selectedPerson, int position);
        public void onClickDinnerButton(Person selectedPerson, int position);
    }
}

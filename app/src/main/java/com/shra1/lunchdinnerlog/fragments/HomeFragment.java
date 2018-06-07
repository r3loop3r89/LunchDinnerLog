package com.shra1.lunchdinnerlog.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shra1.lunchdinnerlog.R;
import com.shra1.lunchdinnerlog.adapters.PeopleAdapter;
import com.shra1.lunchdinnerlog.dtos.Person;
import com.shra1.lunchdinnerlog.dtos.TiffinDinnerEntry;
import com.shra1.lunchdinnerlog.utils.Utils;

import org.joda.time.DateTime;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.shra1.lunchdinnerlog.App.adb;
import static com.shra1.lunchdinnerlog.utils.Constants.DATE_FORMAT;

public class HomeFragment extends Fragment {
    Context mCtx;
    DateTime dateTime;
    private ImageButton ibPrevious;
    private TextView tvDate;
    private ImageButton ibNext;
    private RecyclerView rvFHListOfPeople;
    private FloatingActionButton fabFHAddPerson;


    //<editor-fold desc="addPersonDialog_WIDGETS">
    private EditText etAPDLFullName;
    private EditText etAPDLAddress;
    private EditText etAPDLPhone;
    private Button bAPDLSave;
    //</editor-fold>
    private Button bAPDLCancle;

    public static Fragment getInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCtx = container.getContext();
        View v = LayoutInflater.from(mCtx).inflate(R.layout.fragment_home, container, false);

        initViews(v);

        fabFHAddPerson.setOnClickListener(fab -> {

            //<editor-fold desc="DISPLAYMETRICS">
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            //</editor-fold>

            Dialog addPersonDialog = new Dialog(mCtx);
            addPersonDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            addPersonDialog.setContentView(R.layout.add_person_dialog_layout);

            etAPDLFullName = (EditText) addPersonDialog.findViewById(R.id.etAPDLFullName);
            etAPDLAddress = (EditText) addPersonDialog.findViewById(R.id.etAPDLAddress);
            etAPDLPhone = (EditText) addPersonDialog.findViewById(R.id.etAPDLPhone);
            bAPDLSave = (Button) addPersonDialog.findViewById(R.id.bAPDLSave);
            bAPDLCancle = (Button) addPersonDialog.findViewById(R.id.bAPDLCancle);

            bAPDLCancle.setOnClickListener(bc -> {
                addPersonDialog.dismiss();
            });

            bAPDLSave.setOnClickListener(bs -> {
                if (Utils.cantBeEmptyET(etAPDLFullName)) return;

                String FullName = getTextFromET(etAPDLFullName);
                String Address = getTextFromET(etAPDLAddress);
                String Phone = getTextFromET(etAPDLPhone);

                Person person = new Person(FullName, Address, Phone, System.currentTimeMillis());
                Person.DBCommands.addPerson(adb, person, l -> {
                    Utils.showToast(mCtx, "Added");
                });

                addPersonDialog.dismiss();
            });

            addPersonDialog.show();
            addPersonDialog.getWindow().setLayout((6 * width) / 7, WRAP_CONTENT);
        });

        dateTime = new DateTime();
        loadDataForDate();

        ibPrevious.setOnClickListener(ibp -> {
            dateTime = dateTime.minusDays(1);
            loadDataForDate();
        });

        ibNext.setOnClickListener(ibn -> {
            dateTime = dateTime.plusDays(1);
            loadDataForDate();
        });

        tvDate.setOnClickListener(tvd -> {
            dateTime = new DateTime();
            loadDataForDate();
        });


        return v;
    }

    private void loadDataForDate() {
        tvDate.setText(dateTime.toString(DATE_FORMAT));
        Person.DBCommands.getPeopleData(adb, dateTime, people -> {
            PeopleAdapter adapter = new PeopleAdapter(mCtx, people, new PeopleAdapter.ClickHandlers() {
                @Override
                public void onClickTiffinButton(Person selectedPerson, int position) {
                    Utils.showToast(mCtx, "Clicked on " + selectedPerson.getFullName() + "'s Tiffin");
                    TiffinDinnerEntry tiffinDinnerEntry = new TiffinDinnerEntry(selectedPerson.getId(), dateTime.getMillis(), "TIFFIN");
                    TiffinDinnerEntry.DBCommands.addTiffinEntry(adb, tiffinDinnerEntry, id -> {
                        Utils.showToast(mCtx, "Added tiffin entry");
                        loadDataForDate();
                    });
                }

                @Override
                public void onClickDinnerButton(Person selectedPerson, int position) {
                    Utils.showToast(mCtx, "Clicked on " + selectedPerson.getFullName() + "'s Dinner");
                }
            });
            rvFHListOfPeople.setLayoutManager(new LinearLayoutManager(mCtx));
            rvFHListOfPeople.setAdapter(adapter);
        });
    }

    private String getTextFromET(EditText et) {
        if (et.getText().toString().trim().length() == 0) {
            return "";
        }
        return et.getText().toString().trim();
    }

    private void initViews(View v) {
        ibPrevious = (ImageButton) v.findViewById(R.id.ibPrevious);
        tvDate = (TextView) v.findViewById(R.id.tvDate);
        ibNext = (ImageButton) v.findViewById(R.id.ibNext);
        rvFHListOfPeople = (RecyclerView) v.findViewById(R.id.rvFHListOfPeople);
        fabFHAddPerson = (FloatingActionButton) v.findViewById(R.id.fabFHAddPerson);
    }
}

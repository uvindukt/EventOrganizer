package com.mad.uvindu.eventorganizer;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AlarmFragment extends Fragment {

    private MainActivity.AlarmAdapter alarmAdapter;
    private ListView listView;

    public AlarmFragment() {}

    public void setAlarms(MainActivity.AlarmAdapter alarmAdapter) {
        this.alarmAdapter = alarmAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_alarm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = view.findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AlarmActivity.class);
                startActivity(intent);
            }
        });
        listView = view.findViewById(R.id.alarmList);
        listView.setDivider(new ColorDrawable(getResources().getColor(R.color.colorTab1)));
        listView.setDividerHeight(1);
        listView.setAdapter(alarmAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), AlarmActivity.class);
                intent.putExtra("alarm", listView.getItemAtPosition(i).toString());
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Snackbar.make(view, "Delete Alarm " + listView.getItemAtPosition(i).toString() + " ?", Snackbar.LENGTH_INDEFINITE).setAction("DELETE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
                Vibrator vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(20);
                return true;
            }
        });
    }

}

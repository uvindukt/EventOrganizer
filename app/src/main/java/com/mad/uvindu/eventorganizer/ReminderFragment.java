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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class ReminderFragment extends Fragment {

    private MainActivity.ReminderAdapter reminderAdapter;
    private ListView listView;

    public void setReminderAdapter(MainActivity.ReminderAdapter reminderAdapter) {
        this.reminderAdapter = reminderAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reminder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab3 = view.findViewById(R.id.fab3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ReminderActivity.class);
                startActivity(intent);
            }
        });
        final ListView listView = view.findViewById(R.id.reminderList);
        listView.setDivider(new ColorDrawable(getResources().getColor(R.color.colorTab3)));
        listView.setDividerHeight(1);
        listView.setAdapter(reminderAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Reminder reminder = (Reminder) listView.getItemAtPosition(i);
                Intent intent = new Intent(getContext(), ReminderActivity.class);
                intent.putExtra("reminder", reminder.getReminder());
                intent.putExtra("date", reminder.getDate());
                intent.putExtra("time", reminder.getTime());
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Reminder reminder = (Reminder) listView.getItemAtPosition(i);
                Snackbar.make(view, "Delete Reminder \"" + reminder.getReminder() + "\" ?", Snackbar.LENGTH_INDEFINITE).setAction("DELETE", new View.OnClickListener() {
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

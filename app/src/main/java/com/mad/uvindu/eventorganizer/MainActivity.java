package com.mad.uvindu.eventorganizer;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem[] tabItems;
    private String[] alt;
    private ArrayList<Note> notes;
    private ArrayList<Reminder> reminders;
    private ArrayList<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setNavigationBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorTab1Text));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tabItems = new TabItem[tabLayout.getTabCount()];
        tabItems[0] = findViewById(R.id.tab1);
        tabItems[1] = findViewById(R.id.tab2);
        tabItems[2] = findViewById(R.id.tab3);
        tabItems[3] = findViewById(R.id.tab4);

        AlarmAdapter alarmAdapter = new AlarmAdapter();
        NoteAdapter noteAdapter = new NoteAdapter();
        ReminderAdapter reminderAdapter = new ReminderAdapter();
        EventAdapter eventAdapter = new EventAdapter();

        alt = getResources().getStringArray(R.array.alarms);

        DBHelper db = new DBHelper(MainActivity.this);
        notes = db.readAllNotes();
        events = db.readAllEvents();

        String[] reminderTexts = getResources().getStringArray(R.array.reminders);
        String[] reminder_dates = getResources().getStringArray(R.array.reminder_dates);
        String[] reminder_times = getResources().getStringArray(R.array.reminder_times);

        reminders = new ArrayList<>();

        for (int i = 0 ; i < reminderTexts.length ; i++) {
            Reminder reminder = new Reminder(reminderTexts[i], reminder_dates[i], reminder_times[i]);
            reminders.add(i, reminder);
        }

        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), alarmAdapter, noteAdapter, reminderAdapter, eventAdapter);
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorTab1));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorTab1));
                    tabLayout.setTabTextColors(ContextCompat.getColor(MainActivity.this, R.color.colorTab1Text), Color.parseColor("#ffffff"));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorTab1Text));
                        getWindow().setNavigationBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorTab1Text));
                    }
                } else if (tab.getPosition() == 1) {
                    toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorTab2));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorTab2));
                    tabLayout.setTabTextColors(ContextCompat.getColor(MainActivity.this, R.color.colorTab2Text), Color.parseColor("#ffffff"));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorTab2Text));
                        getWindow().setNavigationBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorTab2Text));
                    }
                } else if (tab.getPosition() == 2) {
                    toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorTab3));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorTab3));
                    tabLayout.setTabTextColors(ContextCompat.getColor(MainActivity.this, R.color.colorTab3Text), Color.parseColor("#ffffff"));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorTab3Text));
                        getWindow().setNavigationBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorTab3Text));
                    }
                } else {
                    toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorTab4));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorTab4));
                    tabLayout.setTabTextColors(ContextCompat.getColor(MainActivity.this, R.color.colorTab4Text), Color.parseColor("#ffffff"));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorTab4Text));
                        getWindow().setNavigationBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorTab4Text));
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

    }

    class AlarmAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return alt.length;
        }

        @Override
        public Object getItem(int i) {
            return alt[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.custom_alarm_list, null);
            TextView tw = view.findViewById(R.id.alarmListText);
            final Switch sw = view.findViewById(R.id.alarmListSwitch);
            tw.setText(alt[i]);
            final View v = view;
            final int index = i;
            sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(sw.isChecked()) {
                        Snackbar.make(v, "Alarm set for " + getItem(index), Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
            return view;
        }

    }

    class NoteAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return notes.size();
        }

        @Override
        public Object getItem(int i) {
            return notes.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.custom_note_list, null);
            TextView tw = view.findViewById(R.id.noteListText);
            tw.setText(notes.get(i).getNoteTitle());
            return view;
        }

    }

    class ReminderAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return reminders.size();
        }

        @Override
        public Object getItem(int i) {
            return reminders.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.custom_reminder_list, null);
            TextView tw = view.findViewById(R.id.reminderListText);
            tw.setText(reminders.get(i).getReminder());
            return view;
        }
    }

    class EventAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return events.size();
        }

        @Override
        public Object getItem(int i) {
            return events.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.custom_event_list, null);
            TextView tw = view.findViewById(R.id.eventListText);
            tw.setText(events.get(i).getEventName());
            final Switch sw = view.findViewById(R.id.eventListSwitch);
            final View v = view;
            final int index = i;
            sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (sw.isChecked()) {
                        Snackbar.make(v, "Event \"" + events.get(index).getEventName() + "\" is turned ON", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(v, "Event \"" + events.get(index).getEventName() + "\" is turned OFF", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
            return view;
        }
    }

}

package com.mad.uvindu.eventorganizer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    private int numberOfTabs;
    private MainActivity.AlarmAdapter alarmAdapter;
    private MainActivity.NoteAdapter noteAdapter;
    private MainActivity.ReminderAdapter reminderAdapter;
    private MainActivity.EventAdapter eventAdapter;

    public PageAdapter(FragmentManager fm, int numberOfTabs, MainActivity.AlarmAdapter alarmAdapter, MainActivity.NoteAdapter noteAdapter, MainActivity.ReminderAdapter reminderAdapter, MainActivity.EventAdapter eventAdapter) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.alarmAdapter = alarmAdapter;
        this.noteAdapter = noteAdapter;
        this.reminderAdapter = reminderAdapter;
        this.eventAdapter = eventAdapter;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                AlarmFragment al = new AlarmFragment();
                al.setAlarms(alarmAdapter);
                return al;
            case 1:
                NoteFragment no = new NoteFragment();
                no.setNoteAdapter(noteAdapter);
                return no;
            case 2:
                ReminderFragment re = new ReminderFragment();
                re.setReminderAdapter(reminderAdapter);
                return re;
            case 3:
                EventFragment ev = new EventFragment();
                ev.setEventAdapter(eventAdapter);
                return ev;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

}

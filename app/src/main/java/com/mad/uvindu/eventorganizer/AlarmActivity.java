package com.mad.uvindu.eventorganizer;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    private Button alarmTime;
    private Button saveAlarm;
    private CheckBox vibrate;
    private CheckBox repeat;
    private CheckBox[] week;
    private Calendar alarm;
    private int hour;
    private String half, minute;
    private int H, M;
    private String D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ActionBar actionBar = getSupportActionBar();
        final Bundle bundle = getIntent().getExtras();

        alarmTime = findViewById(R.id.alarm_set_time_button);
        saveAlarm = findViewById(R.id.alarm_save_button);
        vibrate = findViewById(R.id.alarm_vibrate);
        repeat = findViewById(R.id.alarm_repeat);

        week = new CheckBox[7];
        week[0] = findViewById(R.id.monday);
        week[1] = findViewById(R.id.tuesday);
        week[2] = findViewById(R.id.wednesday);
        week[3] = findViewById(R.id.thursday);
        week[4] = findViewById(R.id.friday);
        week[5] = findViewById(R.id.saturday);
        week[6] = findViewById(R.id.sunday);

        if (!repeat.isChecked()) {
            for (CheckBox c : week) {
                c.setEnabled(false);
                c.setTextColor(ContextCompat.getColor(AlarmActivity.this, R.color.gray));
            }
        }

        if (bundle != null) {
            actionBar.setTitle("Edit Alarm");
            String[] alarm = bundle.getString("alarm").split(":");
            alarmTime.setText(alarm[0] + " : " + alarm[1]);
            D = alarm[1].substring(alarm[1].length() - 2);
            H = Integer.parseInt(alarm[0].trim());
            M = Integer.parseInt(alarm[1].replaceAll("AM|PM","").trim());
            if (D.equals("PM")) {
                H += 12;
            } else if (D.equals("AM") && H == 12) {
                H = 0;
            }
        } else {
            actionBar.setTitle("Add Alarm");
        }

        alarmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                if (bundle != null) {
                    c.set(Calendar.HOUR_OF_DAY, H);
                    c.set(Calendar.MINUTE, M);
                }
                new TimePickerDialog(AlarmActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        alarm = Calendar.getInstance();
                        alarm.set(Calendar.HOUR_OF_DAY, i);
                        alarm.set(Calendar.MINUTE, i1);
                        if (alarm.get(Calendar.MINUTE) == 0) {
                            minute = "00";
                        } else {
                            minute = Integer.toString(alarm.get(Calendar.MINUTE));
                        }
                        if (alarm.get(Calendar.HOUR_OF_DAY) == 0) {
                            hour = 12;
                            half = "AM";
                        } else if (alarm.get(Calendar.HOUR_OF_DAY) > 12) {
                            hour = alarm.get(Calendar.HOUR_OF_DAY) - 12;
                            half = "PM";
                        } else {
                            hour = alarm.get(Calendar.HOUR_OF_DAY);
                            half = "AM";
                        }
                        alarmTime.setText(hour + " : " + minute + " " + half);
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), DateFormat.is24HourFormat(AlarmActivity.this)).show();
            }
        });

        saveAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alarm != null) {
                    if (vibrate.isChecked()) {
                        Vibrator vib = (Vibrator) AlarmActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                        vib.vibrate(500);
                    }
                    Toast.makeText(AlarmActivity.this, hour + " : " + minute + " " + half, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AlarmActivity.this, "Please Select a Time", Toast.LENGTH_SHORT).show();
                }
            }
        });

        repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(!isChecked) {
                    for (CheckBox c : week) {
                        c.setEnabled(false);
                        c.setTextColor(ContextCompat.getColor(AlarmActivity.this, R.color.gray));
                    }
                } else {
                    for (CheckBox c : week) {
                        c.setEnabled(true);
                        c.setTextColor(ContextCompat.getColor(AlarmActivity.this, R.color.colorTab1));
                    }
                }
            }
        });

    }
}

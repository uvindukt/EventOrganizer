package com.mad.uvindu.eventorganizer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {

    private Button dateButton;
    private Button timeButton;
    private Button saveButton;
    private EditText reminderContent;
    private Calendar newDate;
    private int H, M, YY, MM, DD;
    private String D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        final ActionBar actionBar = getSupportActionBar();
        final Bundle bundle = getIntent().getExtras();

        dateButton = findViewById(R.id.reminder_set_date_button);
        timeButton = findViewById(R.id.reminder_set_time_button);
        saveButton = findViewById(R.id.reminder_save_button);
        reminderContent = findViewById(R.id.reminderContentId);

        if (bundle != null) {

            String[] date = bundle.getString("date").split("/");
            YY = Integer.parseInt(date[0].trim());
            MM = Integer.parseInt(date[1].trim());
            MM -= 1;
            DD = Integer.parseInt(date[2].trim());

            String[] time = bundle.getString("time").split(":");
            D = time[1].substring(time[1].length() - 2);
            H = Integer.parseInt(time[0].trim());
            M = Integer.parseInt(time[1].replaceAll("AM|PM","").trim());
            if (D.equals("PM")) {
                H += 12;
            } else if (D.equals("AM") && H == 12) {
                H = 0;
            }

            actionBar.setTitle("View Reminder");
            reminderContent.setText(bundle.getString("reminder"));
            dateButton.setText(bundle.getString("date"));
            timeButton.setText(time[0] + " : " + time[1]);
            saveButton.setText("Edit Reminder");

            reminderContent.setEnabled(false);
            dateButton.setEnabled(false);
            timeButton.setEnabled(false);

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionBar.setTitle("Edit Reminder");
                    saveButton.setText("Save");
                    reminderContent.setEnabled(true);
                    dateButton.setEnabled(true);
                    timeButton.setEnabled(true);

                    saveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (reminderContent.getText().toString().equals("")) {
                                reminderContent.setError("Please enter reminder content");
                            } else {
                                if (newDate != null) {
                                    Toast.makeText(ReminderActivity.this, "Reminder Saved", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ReminderActivity.this, MainActivity.class));
                                } else {
                                    Toast.makeText(ReminderActivity.this, "Please Select a Date and Time", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            });
        } else {
            actionBar.setTitle("Add Reminder");
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (reminderContent.getText().toString().equals("")) {
                        reminderContent.setError("Please enter reminder content");
                    } else {
                        if (newDate != null) {
                            Toast.makeText(ReminderActivity.this, "Reminder Saved", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ReminderActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(ReminderActivity.this, "Please Select a Date and Time", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                if (bundle != null) {
                    c.set(YY, MM, DD);
                }
                new DatePickerDialog(ReminderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear+1, dayOfMonth);
                        dateButton.setText(newDate.get(Calendar.YEAR) + " / " + newDate.get(Calendar.MONTH) + " / " + newDate.get(Calendar.DAY_OF_MONTH));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                if (bundle != null) {
                    c.set(Calendar.HOUR_OF_DAY, H);
                    c.set(Calendar.MINUTE, M);
                }
                new TimePickerDialog(ReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        newDate = Calendar.getInstance();
                        newDate.set(Calendar.HOUR_OF_DAY, i);
                        newDate.set(Calendar.MINUTE, i1);
                        int hour;
                        String half;
                        String minute;
                        if (newDate.get(Calendar.MINUTE) == 0) {
                            minute = "00";
                        } else {
                            minute = Integer.toString(newDate.get(Calendar.MINUTE));
                        }
                        if (newDate.get(Calendar.HOUR_OF_DAY) == 0) {
                            hour = 12;
                            half = "AM";
                        } else if (newDate.get(Calendar.HOUR_OF_DAY) > 12) {
                            hour = newDate.get(Calendar.HOUR_OF_DAY) - 12;
                            half = "PM";
                        } else {
                            hour = newDate.get(Calendar.HOUR_OF_DAY);
                            half = "AM";
                        }
                        timeButton.setText(hour + " : " + minute + " " + half);
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), DateFormat.is24HourFormat(ReminderActivity.this)).show();
            }
        });

    }

}
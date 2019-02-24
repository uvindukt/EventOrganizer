package com.mad.uvindu.eventorganizer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static java.lang.Integer.parseInt;

public class EventActivity extends AppCompatActivity {

    private Button setStartDate;
    private Button setEndDate;
    private Button setStartTime;
    private Button setEndTime;
    private EditText eventName;
    private Button save;
    private RadioGroup soundMode;
    private RadioGroup repeatMode;
    private RadioButton vibrate;
    private RadioButton silent;
    private RadioButton general;
    private RadioButton weekly;
    private RadioButton monthly;
    private Calendar start;
    private Calendar end;
    private int H1, M1, YY1, MM1, DD1, H2, M2, YY2, MM2, DD2;
    private String D1, D2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        final ActionBar actionBar = getSupportActionBar();
        final Bundle bundle = getIntent().getExtras();

        soundMode = findViewById(R.id.eventSoundMode);
        repeatMode = findViewById(R.id.eventRepeatMode);
        eventName = findViewById(R.id.eventTitleId);
        setStartDate = findViewById(R.id.event_startDate);
        setEndDate = findViewById(R.id.event_endDate);
        setStartTime = findViewById(R.id.event_start_time);
        setEndTime = findViewById(R.id.event_end_time);
        save = findViewById(R.id.event_save_button);
        vibrate = findViewById(R.id.radioVibrate);
        silent = findViewById(R.id.radioSilent);
        general = findViewById(R.id.radioGeneral);
        weekly = findViewById(R.id.repeatWeek);
        monthly = findViewById(R.id.repeatMonth);

        vibrate.setTag(Event.VIBRATE);
        silent.setTag(Event.SILENT);
        general.setTag(Event.GENERAL);
        weekly.setTag(Event.REPEAT_WEEKLY);
        monthly.setTag(Event.REPEAT_MONTHLY);

        if (bundle != null) {
            actionBar.setTitle("View Event");

            String[] startDates = bundle.getString("startDate").split("/");
            YY1 = parseInt(startDates[0].trim());
            MM1 = parseInt(startDates[1].trim());
            MM1 -= 1;
            DD1 = parseInt(startDates[2].trim());

            String[] startTimes = bundle.getString("startTime").split(":");
            D1 = startTimes[1].substring(startTimes[1].length() - 2);
            H1 = parseInt(startTimes[0].trim());
            M1 = parseInt(startTimes[1].replaceAll("AM|PM","").trim());
            if (D1.equals("PM")) {
                H1 += 12;
            } else if (D1.equals("AM") && H1 == 12) {
                H1 = 0;
            }

            String[] endDates = bundle.getString("endDate").split("/");
            YY2 = parseInt(endDates[0].trim());
            MM2 = parseInt(endDates[1].trim());
            MM2 -= 1;
            DD2 = parseInt(endDates[2].trim());

            String[] endTimes = bundle.getString("endTime").split(":");
            D2 = endTimes[1].substring(endTimes[1].length() - 2);
            H2 = parseInt(endTimes[0].trim());
            M2 = parseInt(endTimes[1].replaceAll("AM|PM","").trim());
            if (D2.equals("PM")) {
                H2 += 12;
            } else if (D2.equals("AM") && H2 == 12) {
                H2 = 0;
            }

            if (bundle.getInt("soundMode") == Event.VIBRATE) {
                soundMode.check(R.id.radioVibrate);
            } else if (bundle.getInt("soundMode") == Event.SILENT) {
                soundMode.check(R.id.radioSilent);
            } else if (bundle.getInt("soundMode") == Event.GENERAL) {
                soundMode.check(R.id.radioGeneral);
            }

            if (bundle.getInt("repeatMode") == Event.REPEAT_WEEKLY) {
                repeatMode.check(R.id.repeatWeek);
            } else if (bundle.getInt("repeatMode") == Event.REPEAT_MONTHLY) {
                repeatMode.check(R.id.repeatMonth);
            }

            eventName.setText(bundle.getString("event"));
            setStartDate.setText(bundle.getString("startDate"));
            setEndDate.setText(bundle.getString("endDate"));
            setStartTime.setText(startTimes[0] + " : " + startTimes[1]);
            setEndTime.setText(endTimes[0] + " : " + endTimes[1]);
            vibrate.setEnabled(false);
            silent.setEnabled(false);
            general.setEnabled(false);
            weekly.setEnabled(false);
            monthly.setEnabled(false);
            eventName.setEnabled(false);
            setStartDate.setEnabled(false);
            setEndDate.setEnabled(false);
            setStartTime.setEnabled(false);
            setEndTime.setEnabled(false);

            save.setText("Edit Event");
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionBar.setTitle("Edit Event");
                    save.setText("Save");
                    eventName.setEnabled(true);
                    vibrate.setEnabled(true);
                    silent.setEnabled(true);
                    general.setEnabled(true);
                    weekly.setEnabled(true);
                    monthly.setEnabled(true);
                    setStartDate.setEnabled(true);
                    setEndDate.setEnabled(true);
                    setStartTime.setEnabled(true);
                    setEndTime.setEnabled(true);
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (eventName.getText().toString().equals("")) {
                                eventName.setError("Please enter event name");
                            } else {
                                if (start != null && end != null) {
                                    Toast.makeText(EventActivity.this, "Event Saved", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(EventActivity.this, MainActivity.class));
                                } else {
                                    Toast.makeText(EventActivity.this, "Please Select Start, End Date and Times ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            });

        } else {
            actionBar.setTitle("Add Event");
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (eventName.getText().toString().equals("")) {
                        eventName.setError("Please enter event name");
                    } else {
                        if (start != null && end != null) {
                            Toast.makeText(EventActivity.this, "Event Saved", Toast.LENGTH_SHORT).show();
                            DBHelper db = new DBHelper(EventActivity.this);
                            Event event = null;
                            RadioButton sound = findViewById(soundMode.getCheckedRadioButtonId());
                            RadioButton repeat = findViewById(repeatMode.getCheckedRadioButtonId());
                            try {
                                event = new Event(eventName.getText().toString().trim(), setStartDate.getText().toString(), setEndDate.getText().toString(), setStartTime.getText().toString(), setEndTime.getText().toString(), Integer.parseInt(sound.getTag().toString()), Integer.parseInt(repeat.getTag().toString()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            db.addEvent(event);
                            startActivity(new Intent(EventActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(EventActivity.this, "Please Select Start, End Date and Times ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        setStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                if (bundle != null) {
                    c.set(YY1, MM1, DD1);
                }
                new DatePickerDialog(EventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        start = Calendar.getInstance();
                        start.set(year, monthOfYear+1, dayOfMonth);
                        setStartDate.setText(start.get(Calendar.YEAR) + " / " + start.get(Calendar.MONTH) + " / " + start.get(Calendar.DAY_OF_MONTH));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        setEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                if (bundle != null) {
                    c.set(YY2, MM2, DD2);
                }
                new DatePickerDialog(EventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        end = Calendar.getInstance();
                        end.set(year, monthOfYear+1, dayOfMonth);
                        setEndDate.setText(end.get(Calendar.YEAR) + " / " + end.get(Calendar.MONTH) + " / " + end.get(Calendar.DAY_OF_MONTH));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        setStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                if (bundle != null) {
                    c.set(Calendar.HOUR_OF_DAY, H1);
                    c.set(Calendar.MINUTE, M1);
                }
                new TimePickerDialog(EventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        start = Calendar.getInstance();
                        start.set(Calendar.HOUR_OF_DAY, i);
                        start.set(Calendar.MINUTE, i1);
                        int hour;
                        String half;
                        String minute;
                        if (start.get(Calendar.MINUTE) == 0) {
                            minute = "00";
                        } else {
                            minute = Integer.toString(start.get(Calendar.MINUTE));
                        }
                        if (start.get(Calendar.HOUR_OF_DAY) == 0) {
                            hour = 12;
                            half = "AM";
                        } else if (start.get(Calendar.HOUR_OF_DAY) > 12) {
                            hour = start.get(Calendar.HOUR_OF_DAY) - 12;
                            half = "PM";
                        } else {
                            hour = start.get(Calendar.HOUR_OF_DAY);
                            half = "AM";
                        }
                        setStartTime.setText(hour + " : " + minute + " " + half);
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), DateFormat.is24HourFormat(EventActivity.this)).show();
            }
        });

        setEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                if (bundle != null) {
                    c.set(Calendar.HOUR_OF_DAY, H2);
                    c.set(Calendar.MINUTE, M2);
                }
                new TimePickerDialog(EventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        end = Calendar.getInstance();
                        end.set(Calendar.HOUR_OF_DAY, i);
                        end.set(Calendar.MINUTE, i1);
                        int hour;
                        String half;
                        String minute;
                        if (end.get(Calendar.MINUTE) == 0) {
                            minute = "00";
                        } else {
                            minute = Integer.toString(end.get(Calendar.MINUTE));
                        }
                        if (end.get(Calendar.HOUR_OF_DAY) == 0) {
                            hour = 12;
                            half = "AM";
                        } else if (end.get(Calendar.HOUR_OF_DAY) > 12) {
                            hour = end.get(Calendar.HOUR_OF_DAY) - 12;
                            half = "PM";
                        } else {
                            hour = end.get(Calendar.HOUR_OF_DAY);
                            half = "AM";
                        }
                        setEndTime.setText(hour + " : " + minute + " " + half);
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), DateFormat.is24HourFormat(EventActivity.this)).show();
            }
        });

    }
}

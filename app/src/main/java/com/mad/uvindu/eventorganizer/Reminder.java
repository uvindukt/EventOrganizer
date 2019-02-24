package com.mad.uvindu.eventorganizer;

public class Reminder {

    private int reminderId;
    private String reminder;
    private String date;
    private String time;

    public Reminder(String reminder, String date, String time) {
        this.reminder = reminder;
        this.date = date;
        this.time = time;
    }

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "reminderId=" + reminderId +
                ", reminder='" + reminder + '\'' +
                ", date=" + date +
                ", time=" + time +
                '}';
    }

}

package com.mad.uvindu.eventorganizer;

public class Event {

    public static final int VIBRATE = 1;
    public static final int SILENT = 2;
    public static final int GENERAL = 3;
    public static final int REPEAT_WEEKLY = 1;
    public static final int REPEAT_MONTHLY = 2;

    private int eventId;
    private String eventName;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private int soundMode;
    private int repeatMode;

    public Event() {}

    public Event(String eventName, String startDate, String endDate, String startTime, String endTime, int soundMode, int repeatMode) {
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.soundMode = soundMode;
        this.repeatMode = repeatMode;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getSoundMode() {
        return soundMode;
    }

    public void setSoundMode(int soundMode) {
        this.soundMode = soundMode;
    }

    public int getRepeatMode() {
        return repeatMode;
    }

    public void setRepeatMode(int repeatMode) {
        this.repeatMode = repeatMode;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", soundMode=" + soundMode +
                ", repeatMode=" + repeatMode +
                '}';
    }

}
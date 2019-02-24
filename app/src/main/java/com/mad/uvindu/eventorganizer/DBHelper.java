package com.mad.uvindu.eventorganizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE = "EventOrganizer.db";

    public DBHelper(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL_CREATE_NOTE =
                "CREATE TABLE " + TableMaster.Note.TABLE + " (" + TableMaster.Note._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + TableMaster.Note.COLUMN_TITLE + " TEXT, "
                        + TableMaster.Note.COLUMN_CONTENT + " TEXT)";

        sqLiteDatabase.execSQL(SQL_CREATE_NOTE);

        String SQL_CREATE_EVENT =
                "CREATE TABLE " + TableMaster.Event.TABLE + " (" + TableMaster.Event._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + TableMaster.Event.COLUMN_NAME + " TEXT, "
                        + TableMaster.Event.COLUMN_START_DATE + " TEXT,"
                        + TableMaster.Event.COLUMN_START_TIME + " TEXT,"
                        + TableMaster.Event.COLUMN_END_DATE + " TEXT,"
                        + TableMaster.Event.COLUMN_END_TIME + " TEXT,"
                        + TableMaster.Event.COLUMN_REPEAT_MODE + " INTEGER,"
                        + TableMaster.Event.COLUMN_SOUND_MODE + " INTEGER)";

        sqLiteDatabase.execSQL(SQL_CREATE_EVENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addEvent(Event event) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TableMaster.Event.COLUMN_NAME, event.getEventName());
        values.put(TableMaster.Event.COLUMN_START_DATE, event.getStartDate());
        values.put(TableMaster.Event.COLUMN_START_TIME, event.getStartTime());
        values.put(TableMaster.Event.COLUMN_END_DATE, event.getEndDate());
        values.put(TableMaster.Event.COLUMN_END_TIME, event.getEndTime());
        values.put(TableMaster.Event.COLUMN_REPEAT_MODE, event.getRepeatMode());
        values.put(TableMaster.Event.COLUMN_SOUND_MODE, event.getSoundMode());

        db.insert(TableMaster.Event.TABLE, null, values);

    }

    public void addNote(Note note) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TableMaster.Note.COLUMN_TITLE, note.getNoteTitle());
        values.put(TableMaster.Note.COLUMN_CONTENT, note.getNoteContent());

        db.insert(TableMaster.Note.TABLE, null, values);

    }

    public ArrayList<Event> readAllEvents() {

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                TableMaster.Event._ID,
                TableMaster.Event.COLUMN_NAME,
                TableMaster.Event.COLUMN_START_DATE,
                TableMaster.Event.COLUMN_START_TIME,
                TableMaster.Event.COLUMN_END_DATE,
                TableMaster.Event.COLUMN_END_TIME,
                TableMaster.Event.COLUMN_REPEAT_MODE,
                TableMaster.Event.COLUMN_SOUND_MODE,
        };

        Cursor cursor = db.query(
                TableMaster.Event.TABLE,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<Event> events = new ArrayList<>();

        while (cursor.moveToNext()) {
            Event e = new Event();
            e.setEventId(cursor.getInt(cursor.getColumnIndexOrThrow(TableMaster.Event._ID)));
            e.setEventName(cursor.getString(cursor.getColumnIndexOrThrow(TableMaster.Event.COLUMN_NAME)));
            e.setStartDate(cursor.getString(cursor.getColumnIndexOrThrow(TableMaster.Event.COLUMN_START_DATE)));
            e.setStartTime(cursor.getString(cursor.getColumnIndexOrThrow(TableMaster.Event.COLUMN_START_TIME)));
            e.setEndDate(cursor.getString(cursor.getColumnIndexOrThrow(TableMaster.Event.COLUMN_END_DATE)));
            e.setEndTime(cursor.getString(cursor.getColumnIndexOrThrow(TableMaster.Event.COLUMN_END_TIME)));
            e.setRepeatMode(cursor.getInt(cursor.getColumnIndexOrThrow(TableMaster.Event.COLUMN_REPEAT_MODE)));
            e.setSoundMode(cursor.getInt(cursor.getColumnIndexOrThrow(TableMaster.Event.COLUMN_SOUND_MODE)));
            events.add(e);
        }

        cursor.close();

        return events;

    }

    public ArrayList<Note> readAllNotes() {

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                TableMaster.Note._ID,
                TableMaster.Note.COLUMN_TITLE,
                TableMaster.Note.COLUMN_CONTENT
        };

        Cursor cursor = db.query(
                TableMaster.Note.TABLE,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<Note> notes = new ArrayList<>();

        while (cursor.moveToNext()) {
            Note n = new Note();
            n.setNoteId(cursor.getInt(cursor.getColumnIndexOrThrow(TableMaster.Note._ID)));
            n.setNoteTitle(cursor.getString(cursor.getColumnIndexOrThrow(TableMaster.Note.COLUMN_TITLE)));
            n.setNoteContent(cursor.getString(cursor.getColumnIndexOrThrow(TableMaster.Note.COLUMN_CONTENT)));
            notes.add(n);
        }

        cursor.close();

        return notes;

    }

}

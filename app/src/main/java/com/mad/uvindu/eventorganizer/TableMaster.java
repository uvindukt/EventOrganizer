package com.mad.uvindu.eventorganizer;

import android.provider.BaseColumns;

public class TableMaster {

    public static class Note implements BaseColumns {

        public static final String TABLE = "note";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CONTENT = "content";

    }

    public static class Event implements  BaseColumns {

        public static final String TABLE = "event";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_START_DATE = "start_date";
        public static final String COLUMN_END_DATE = "end_date";
        public static final String COLUMN_START_TIME = "start_time";
        public static final String COLUMN_END_TIME = "end_time";
        public static final String COLUMN_SOUND_MODE = "sound_mode";
        public static final String COLUMN_REPEAT_MODE = "repeat_mode";

    }

}

package com.example.welll.myvocabulary;

import android.provider.BaseColumns;

public class WordContract {
    public static final String DB_NAME = "Word.db";
    public static final int DB_VERSION = 1;

    public class WordEntry implements BaseColumns {
        public static final String TABLE = "words";

        public static final String COL_1 = "ENGLISH";
        public static final String COL_2 = "CHINESE";
        public static final String COL_3 = "NOTES";
    }
}

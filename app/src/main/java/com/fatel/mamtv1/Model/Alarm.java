package com.fatel.mamtv1.Model;

import android.provider.BaseColumns;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Monthon on 12/10/2558.
 */
@Getter
@Setter
public class Alarm {
    private int id;
    private String starthr;
    private String startmin;
    private String stophr;
    private String stopmin;
    private String startinterval;
    private String stopinterval;
    private String frq;
    private String day;
    private String mode;
    public static final String DATABASE_NAME = "fatel_alarm.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "alarm";

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String START_HR = "start_hr";
        public static final String START_MIN = "start_min";
        public static final String STOP_HR = "stop_hr";
        public static final String STOP_MIN = "stop_min";
        public static final String START_INTERVAL = "start_interval";
        public static final String STOP_INTERVAL = "stop_interval";
        public static final String FRQ = "frq";
        public static final String DAY = "day";
        public static final String MODE = "mode";
    }

    public Alarm() {
    }

    public Alarm(int id, String starthr, String startmin, String stophr, String stopmin,
                 String startinterval, String stopinterval, String frq, String day, String mode) {
        this.id = id;
        this.starthr = starthr;
        this.startmin = startmin;
        this.stophr = stophr;
        this.stopmin = stopmin;
        this.startinterval = startinterval;
        this.stopinterval = stopinterval;
        this.frq = frq;
        this.day = day;
        this.mode = mode;
    }
}


package com.cyborck.dsbMobileAPI.dataReader;

public class TimeTables {
    private final TimeTable today;
    private final TimeTable tomorrow;

    public TimeTables ( TimeTable today, TimeTable tomorrow ) {
        this.today = today;
        this.tomorrow = tomorrow;
    }

    public TimeTable getToday () {
        return today;
    }

    public TimeTable getTomorrow () {
        return tomorrow;
    }
}

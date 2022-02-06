package com.cyborck.dsbMobileAPI.dataReader;

public class TimeTables {
    private final boolean status;
    private final TimeTable today;
    private final TimeTable tomorrow;

    public TimeTables () {
        status = false;
        today = null;
        tomorrow = null;
    }

    public TimeTables ( TimeTable today, TimeTable tomorrow ) {
        status = true;
        this.today = today;
        this.tomorrow = tomorrow;
    }

    public boolean getStatus () {
        return status;
    }

    public TimeTable getToday () {
        return today;
    }

    public TimeTable getTomorrow () {
        return tomorrow;
    }
}

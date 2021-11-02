package com.cyborck.dsbMobileAPI.dataReader;

public class TimeTable {
    private final String date;
    private final String day;
    private final String[] news;
    private final Information[] information;

    public TimeTable ( String date, String day, String[] news, Information[] information ) {
        this.date = date;
        this.day = day;
        this.news = news;
        this.information = information;
    }

    public String getDate () {
        return date;
    }

    public String getDay () {
        return day;
    }

    public String[] getNews () {
        return news;
    }

    public Information[] getInformation () {
        return information;
    }
}

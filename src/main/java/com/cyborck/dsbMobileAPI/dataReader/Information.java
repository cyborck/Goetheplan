package com.cyborck.dsbMobileAPI.dataReader;

public class Information {
    private final String lessons;
    private final String absent;
    private final String replacement;
    private final String subject;
    private final String newRoom;
    private final String type;
    private final String comments;
    private String classes;

    public Information ( String classes, String lessons, String absent, String replacement, String subject, String newRoom, String type, String comments ) {
        this.classes = classes;
        this.lessons = lessons;
        this.absent = absent;
        this.replacement = replacement;
        this.subject = subject;
        this.newRoom = newRoom;
        this.type = type;
        this.comments = comments;
    }

    public void addClasses ( String classes ) {
        this.classes += " " + classes;
    }

    public String getLessons () {
        return lessons;
    }

    public String getAbsent () {
        return absent;
    }

    public String getReplacement () {
        return replacement;
    }

    public String getSubject () {
        return subject;
    }

    public String getNewRoom () {
        return newRoom;
    }

    public String getType () {
        return type;
    }

    public String getComments () {
        return comments;
    }

    public String getClasses () {
        return classes;
    }
}

package com.cyborck.dsbMobileAPI.dataReader;

import com.alibaba.fastjson.JSON;
import de.sematre.dsbmobile.DSBMobile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class DataReader {

    public static String getJSON ( String username, String password ) throws IOException {
        return JSON.toJSONString( getTimeTables( username, password ) );
    }

    public static TimeTables getTimeTables ( String username, String password ) throws IOException {
        Map<String, String> timeTableLinks = getTimeTableLinks( username, password );

        if ( timeTableLinks == null )
            // error occurred (e.g. login failed)
            // return empty timetables object with status false
            return new TimeTables();

        //today
        List<String> todayUrls = new ArrayList<>();
        for ( Map.Entry<String, String> e: timeTableLinks.entrySet() )
            if ( e.getKey().contains( "today" ) )
                todayUrls.add( e.getValue() );
        TimeTable todayTable = getTimeTable( todayUrls );

        //tomorrow
        List<String> tomorrowUrls = new ArrayList<>();
        for ( Map.Entry<String, String> e: timeTableLinks.entrySet() )
            if ( e.getKey().contains( "tomorrow" ) )
                tomorrowUrls.add( e.getValue() );
        TimeTable tomorrowTable = getTimeTable( tomorrowUrls );

        return new TimeTables( todayTable, tomorrowTable );
    }

    private static TimeTable getTimeTable ( List<String> timeTableUrls ) throws IOException {
        //read generell information from first link
        Document firstDoc = Jsoup.connect( timeTableUrls.get( 0 ) ).get();

        String[] dateAndDay = Objects.requireNonNull( firstDoc.selectFirst( "body > center:nth-child(2) > div" ) ).text().split( " " );
        String date = dateAndDay[ 0 ];
        String day = dateAndDay[ 1 ];

        List<String> news = new ArrayList<>();
        Elements newsElement = firstDoc.select( "body > center > table.info > tbody > tr" );
        for ( Element e: newsElement )
            if ( !e.text().equals( "Nachrichten zum Tag" ) )
                news.add( e.text() );

        List<Information> informationList = new ArrayList<>();
        for ( String url: timeTableUrls ) {
            Document doc = Jsoup.connect( url ).get();
            Elements informationElements = doc.select( "body > center > table.mon_list > tbody > tr" );
            for ( int i = 1; i < informationElements.size(); i++ ) {
                //ignore first row, bc it's title row
                Element row = informationElements.get( i );

                String classes = Objects.requireNonNull( row.selectFirst( "td:nth-child(1)" ) ).text();
                String lessons = Objects.requireNonNull( row.selectFirst( "td:nth-child(2)" ) ).text();
                String absent = Objects.requireNonNull( row.selectFirst( "td:nth-child(3)" ) ).text();
                String replacement = Objects.requireNonNull( row.selectFirst( "td:nth-child(4)" ) ).text();
                String subject = Objects.requireNonNull( row.selectFirst( "td:nth-child(5)" ) ).text();
                String newRoom = Objects.requireNonNull( row.selectFirst( "td:nth-child(6)" ) ).text();
                String type = Objects.requireNonNull( row.selectFirst( "td:nth-child(7)" ) ).text();
                String comments = Objects.requireNonNull( row.selectFirst( "td:nth-child(8)" ) ).text();

                if ( !classes.isEmpty() && lessons.isEmpty() && absent.isEmpty() && replacement.isEmpty() && subject.isEmpty() && newRoom.isEmpty() && type.isEmpty() && comments.isEmpty() ) {
                    informationList.get( informationList.size() - 1 ).addClasses( classes );
                    continue;
                }

                if ( classes.isEmpty() ) {
                    if ( "Team".equals( subject ) ) classes = "Team";
                    else classes = "other";
                }

                informationList.add( new Information( classes, lessons, absent, replacement, subject, newRoom, type, comments ) );
            }
        }

        return new TimeTable( date, day, news.toArray( new String[ 0 ] ), informationList.toArray( new Information[ 0 ] ) );
    }

    private static Map<String, String> getTimeTableLinks ( String username, String password ) {
        Map<String, String> timeTables = new TreeMap<>();

        DSBMobile dsbMobile = new DSBMobile( username, password );

        int todayCount = 0;
        int tomorrowCount = 0;

        try {
            for ( DSBMobile.TimeTable timeTable: dsbMobile.getTimeTables() ) {
                String groupName = timeTable.getGroupName();
                if ( groupName.contains( "heute" ) )
                    groupName = "today " + ++todayCount;
                else if ( groupName.contains( "morgen" ) )
                    groupName = "tomorrow " + ++tomorrowCount;
                String url = timeTable.getDetail();
                timeTables.put( groupName, url );
            }
        } catch ( RuntimeException e ) {
            return null;
        }

        return timeTables;
    }
}

package com.cyborck.dsbMobileAPI.restService;

import com.cyborck.dsbMobileAPI.dataReader.DataReader;
import com.cyborck.dsbMobileAPI.dataReader.TimeTables;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TimeTableController {

    @GetMapping ( "/timetables" )
    public TimeTables timeTables ( @RequestParam ( value = "username" ) String username, @RequestParam ( value = "password" ) String password ) throws IOException {
        return DataReader.getTimeTables( username, password );
    }
}

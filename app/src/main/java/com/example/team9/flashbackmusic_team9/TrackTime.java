package com.example.team9.flashbackmusic_team9;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

/**
 * Created by cyrusdeng on 13/02/2018.
 */

public class TrackTime {

    private static Clock clock = Clock.systemDefaultZone();
    private static ZoneId zoneId = ZoneId.of("UTC-08:00");

    public static LocalDateTime now() {
        return LocalDateTime.now(zoneId).truncatedTo(ChronoUnit.SECONDS);
    }

    public static void useFixedClockAt(LocalDateTime date){
        clock = Clock.fixed(date.atZone(zoneId).toInstant(), zoneId);
    }

    public static void useSystemDefaultZoneClock(){
        clock = Clock.systemDefaultZone();
    }

    private static Clock getClock() {
        return clock ;
    }
}

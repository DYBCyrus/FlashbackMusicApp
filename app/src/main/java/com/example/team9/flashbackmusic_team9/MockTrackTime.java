package com.example.team9.flashbackmusic_team9;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * Created by cyrusdeng on 13/02/2018.
 */

public class MockTrackTime implements ILocalDateTime{

    private static ZoneId zoneId = ZoneId.of("UTC-08:00");
    private static Clock clock = Clock.systemDefaultZone().withZone(zoneId);

    public static LocalDateTime now() {
        return LocalDateTime.now(clock).truncatedTo(ChronoUnit.SECONDS);
    }

    public static void useFixedClockAt(LocalDateTime date){
        clock = Clock.fixed(date.atZone(zoneId).toInstant(), zoneId);
    }

    public static void useDefaultClock() {
        clock = Clock.systemDefaultZone();
        clock = clock.withZone(zoneId);
    }

    private static Clock getClock() {
        return clock ;
    }

    public int getYear()
    {
        return this.now().getYear();
    }
    public int getMonthValue()
    {
        return this.now().getMonthValue();
    }
    public int getDayOfMonth()
    {
        return this.now().getDayOfMonth();
    }
    public int getHour()
    {
        return this.now().getHour();
    }
    public int getMinute()
    {
        return this.now().getMinute();
    }
    public int getSecond()
    {
        return this.now().getSecond();
    }

}

package com.example.team9.flashbackmusic_team9;

import java.time.LocalDateTime;

/**
 * Created by Chutong on 3/10/18.
 */

public class LocalDateTimeAdapter implements ILocalDateTime
{
    LocalDateTime time;
    public LocalDateTimeAdapter(LocalDateTime t)
    {
        time = t;
    }

    public int getYear()
    {
        return time.getYear();
    }
    public int getMonthValue()
    {
        return time.getMonthValue();
    }
    public int getDayOfMonth()
    {
        return time.getDayOfMonth();
    }
    public int getHour()
    {
        return time.getHour();
    }
    public int getMinute()
    {
        return time.getMinute();
    }
    public int getSecond()
    {
        return time.getSecond();
    }

    public boolean isAfter(LocalDateTime t)
    {
        return time.isAfter(t);
    }

}

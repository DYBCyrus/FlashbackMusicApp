package com.example.team9.flashbackmusic_team9;

import java.time.LocalDateTime;

/**
 * Created by Chutong on 3/10/18.
 */

public interface ILocalDateTime
{
    int getYear();
    int getMonthValue();
    int getDayOfMonth();
    int getHour();
    int getMinute();
    int getSecond();

    boolean isAfter(LocalDateTime time);
}

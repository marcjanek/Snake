package model;

import java.sql.Time;
import java.util.Date;

class GameStatistics
{
    final int score;
    final Date date;
    final Time time;

    GameStatistics(int score, Date date, Time time)
    {
        this.score = score;
        this.date = date;
        this.time = time;
    }
}

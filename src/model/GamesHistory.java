package model;

import java.sql.Time;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

class GamesHistory
{
    private final int MAX_STORED_SCORES = 10;
    private final LinkedList<GameStatistics> linkedList;

    GamesHistory()
    {
        linkedList = new LinkedList<>();
    }

    void add(int score, Date date, Time time)
    {
        if (linkedList.size() > MAX_STORED_SCORES)
            linkedList.remove(getWorstScore());
        linkedList.add(new GameStatistics(score, date, time));

    }

    GameStatistics getHighScore()
    {
        GameStatistics best = linkedList.getFirst();
        for (GameStatistics i : linkedList)
        {
            if (i.score > best.score)//TODO best time
            {
                best = i;
            }
        }
        return best;
    }

    private GameStatistics getWorstScore()
    {
        GameStatistics worst = linkedList.getFirst();
        for (GameStatistics i : linkedList)
        {
            if (i.score < worst.score)//TODO best time
            {
                worst = i;
            }
        }
        return worst;
    }

}

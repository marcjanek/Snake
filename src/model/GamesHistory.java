package model;

import java.sql.Time;
import java.util.Date;
import java.util.LinkedList;

class GamesHistory
{
    private final int MAX_STORED_SCORES = 10;
    private final LinkedList<GameStatistics> linkedList;
    GameStatistics highScore;

    GamesHistory()
    {
        linkedList = new LinkedList<>();
        highScore = new GameStatistics(0, new Date(System.currentTimeMillis()), System.currentTimeMillis());
    }

    void add(int score, Date date, long time)
    {
        if (linkedList.size() > MAX_STORED_SCORES)
            linkedList.remove(getWorstScore());
        linkedList.add(new GameStatistics(score, date, time));
        setHighScore();
    }

    private void setHighScore()
    {
        GameStatistics best = linkedList.getFirst();
        for (GameStatistics i : linkedList)
        {
            if (i.score > best.score || (i.score == best.score && i.time > best.time))
            {
                best = i;
            }
        }
        highScore = best;
    }

    GameStatistics get(int index)
    {
        return linkedList.get(index);
    }

    int size()
    {
        return linkedList.size();
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

    class GameStatistics
    {
        final int score;
        final Date date;
        final long time;

        GameStatistics(int score, Date date, long time)
        {
            this.score = score;
            this.date = date;
            this.time = time;
        }
    }

}

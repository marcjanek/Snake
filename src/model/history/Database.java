package model.history;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

/**
 * class to connect with Oracle database and perform operations like add and search
 */
public final class Database
{
    /**
     * constant is true if connection with Oracle database was successful
     */
    private final boolean IS_CONNECTED;
    /**
     * constant having name of table in database
     */
    private final String TABLE_NAME = "SCORES";
    /**
     * used for executing statements like INSERT,CREATE and SELECT
     */
    private Statement statement;

    /**
     * constructor try to connect with Oracle database and
     * if connection is successful, creates new table also performing cleaning data in this table
     */
    public Database()
    {
        boolean connect;
        try
        {
            final String url = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
            final String user = "mmozolew";
            final String password = "mmozolew";
            final String createTable = "declare\n" +
                    "    v_sql LONG;\n" +
                    "begin\n" +
                    "\n" +
                    "    v_sql:='create table " + TABLE_NAME + "\n" +
                    "  (\n" +
                    "  ID           NUMBER(3)    PRIMARY KEY,\n" +
                    "  SCORE        NUMBER(3)    NOT NULL,\n" +
                    "  GAME_DATA    NUMBER(20) NOT NULL,\n" +
                    "  TIME         NUMBER(10)  NOT NULL,\n" +
                    "  GAME_LEVEL   VARCHAR2(20)  NOT NULL \n" +
                    "  " +
                    "  )';\n" +
                    "    execute immediate v_sql;\n" +
                    "\n" +
                    "EXCEPTION\n" +
                    "    WHEN OTHERS THEN\n" +
                    "        IF SQLCODE = -955 THEN\n" +
                    "            NULL; -- suppresses ORA-00955 exception\n" +
                    "        ELSE\n" +
                    "            RAISE;\n" +
                    "        END IF;\n" +
                    "END;";
            final String deleteData = "DELETE FROM " + TABLE_NAME + "\n";
            final Connection connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            statement.execute(createTable);
            statement.execute(deleteData);
            connect=true;
        } catch (SQLException e)
        {
            e.printStackTrace(System.out);
            connect=false;
        }
        IS_CONNECTED = connect;
    }

    /**
     * if is connection, method adds new record to database
     *
     * @param score     game score
     * @param gameDate  game end date
     * @param time      time of game
     * @param gameLevel level of game
     */
    public final void add(final int score, final long gameDate, final long time, final String gameLevel)
    {
        if(!IS_CONNECTED)
            return;
        final String selectMaxID = "SELECT NVL(MAX(ID),0) max_id FROM " + TABLE_NAME;
        final String insert = "INSERT INTO " + TABLE_NAME + " VALUES ( ";
        try
        {
            ResultSet resultSet = statement.executeQuery(selectMaxID);
            resultSet.next();
            final int maxID = resultSet.getInt("max_id") + 1;
            statement.execute(insert +
                    maxID + ", " +
                    score + ", " +
                    gameDate + ", " +
                    time + ", " +
                    "'" + gameLevel + "'" + " ) ");
        } catch (SQLException e)
        {
            e.printStackTrace(System.out);
        }
    }

    /**
     * method returning best records
     * @param number number of best records
     * @return records in output format
     */
    public final Queue<String> bestScores(int number)
    {
        final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        final Queue<String> queue = new LinkedList<>();
        if(!IS_CONNECTED)
            return queue;
        if(number<0)
            number=0;
        final String bestScores = "SELECT * FROM " + TABLE_NAME + " WHERE ROWNUM <= " + number + " ORDER BY SCORE DESC,GAME_LEVEL,TIME,GAME_DATE";
        try
        {
            ResultSet resultSet = statement.executeQuery(bestScores);
            while (resultSet.next())
            {
                queue.add("score: " + resultSet.getString("score") +
                        "   level: " + resultSet.getString("game_level") +
                        "   time [s] : " + resultSet.getString("time") +
                        "   date : " + formatter.format(new Date(resultSet.getLong("game_date")))
                );
            }
        } catch (SQLException e)
        {
            e.printStackTrace(System.out);
        }
        return queue;
    }
}
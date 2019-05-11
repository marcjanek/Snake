package model.history;

import java.sql.*;
import java.util.LinkedList;
import java.util.Queue;

public final class DataBase
{
    private Statement statement;
    private final boolean isConnected;

    public DataBase()
    {
        boolean connect;
        try
        {
            final String url = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
            final String user = "";
            final String password = "";
            final String createTable = "declare\n" +
                    "    v_sql LONG;\n" +
                    "begin\n" +
                    "\n" +
                    "    v_sql:='create table scores\n" +
                    "  (\n" +
                    "  ID NUMBER(3) PRIMARY KEY,\n" +
                    "  NAME NUMBER(3) NOT NULL\n" +
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
            final String deleteData = "DELETE FROM scores\n";
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
        isConnected=connect;
    }

    public final void add(final int score)
    {
        if(!isConnected)
            return;
        final String selectMaxID = "SELECT NVL(MAX(ID),0) max_id FROM scores";
        final String insert = "INSERT INTO scores VALUES ( ";
        try
        {
            ResultSet resultSet = statement.executeQuery(selectMaxID);
            resultSet.next();
            statement.execute(insert + resultSet.getString("max_id") + ", " + score + ")");
        } catch (SQLException e)
        {
            e.printStackTrace(System.out);
        }
    }

    public final Queue bestScores(int number)
    {
        final Queue<String> queue = new LinkedList<>();
        if(!isConnected)
            return queue;
        if(number<0)
            number=0;
        final String bestScores = "SELECT * FROM scores WHERE ROWNUM <= " + number + " ORDER BY score";
        try
        {
            ResultSet resultSet = statement.executeQuery(bestScores);
            while (resultSet.next())
            {
                queue.add("score: " + resultSet.getString("score") + " time: " + "XX:YY:ZZ" + " date: " + "01/01/2019");
            }
        } catch (SQLException e)
        {
            e.printStackTrace(System.out);
        }
        return queue;
    }
}
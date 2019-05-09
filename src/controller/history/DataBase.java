package controller.history;

import java.sql.*;
import java.util.LinkedList;
import java.util.Queue;

public class DataBase
{
    private Statement statement;

    DataBase()
    {
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
        } catch (SQLException e)
        {
            e.printStackTrace(System.out);
        }
    }

    void add(final int score)
    {
        final String selectMaxID = "select nvl(MAX(id),0) max_id from scores";
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

    Queue bestScores(final int number)
    {
        final String bestScores = "SELECT * FROM scores WHERE ROWNUM <= " + number + " ORDER BY score";
        final Queue<String> queue = new LinkedList<>();
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

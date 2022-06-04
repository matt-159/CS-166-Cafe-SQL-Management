/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import util.CafeSQLManager;
import util.TextAreaOutputStream;

import javax.swing.*;
import java.io.PrintStream;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 */
public class CS_166_Project {

    public static void main(String[] args) {

        JTextArea text = new JTextArea();

        PrintStream printStream = new PrintStream(new TextAreaOutputStream(text));
        System.setOut(printStream);
        System.setErr(printStream);

        String dbhost = args[0];
        String dbname = args[1];
        String dbport = args[2];
        String user = args[3];
        String password = args[4];

        javax.swing.SwingUtilities.invokeLater(() -> {
            CafeSQLManager.init(dbhost, dbname, dbport, user, password);

            CafeApplication app = new CafeApplication();

            app.frameInit();
//            app.add(text);
            app.setVisible(true);

            app.run();
        });
    }
}


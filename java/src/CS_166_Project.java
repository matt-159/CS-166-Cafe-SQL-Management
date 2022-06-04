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

import gui.CafeApplication;
import util.CafeSQLManager;
import util.TextAreaOutputStream;

import javax.swing.*;
import java.io.PrintStream;

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

            app.run();
        });
    }
}


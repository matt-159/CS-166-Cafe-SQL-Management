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

import data.User;
import gui.CafeApplication;
import gui.CafeApplication.AppStates;
import gui.DebugPanel;
import util.CafeSQLManager;

public class CS_166_Project {

    public static void main(String[] args) {

        String dbhost = args[0];
        String dbname = args[1];
        String dbport = args[2];
        String user = args[3];
        String password = args[4];

        javax.swing.SwingUtilities.invokeLater(() -> {
            DebugPanel debug = new DebugPanel();

            CafeSQLManager.init(dbhost, dbname, dbport, user, password);

            CafeApplication.getInstance().run(null, AppStates.CHOOSE_LOGIN_OR_CREATE_USER, null);
        });
    }
}


package gui;

import data.User;
import util.CafeSQLManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CafeApplication extends JFrame {

    public enum AppStates {
        CHOOSE_LOGIN_OR_CREATE_USER,
        LOGIN,
        CREATE_USER,
        USER_HOME
    }

    private static final String TITLE = "CS 166 Project - Cafe Management";

    private JPanel currentMenu;
    private CafeLoginOrCreateUser loginScreen;
    private CafeUserHome userHome;

    private static CafeApplication instance = null;

    public static CafeApplication getInstance() {
        if (instance == null) {
            instance = new CafeApplication();
        }

        return instance;
    }
    private CafeApplication() throws HeadlessException {
        super(TITLE);

        this.frameInit();

        this.setSize(800, 600);
        this.addWindowListener(new CafeApplicationWindowAdapter());
    }

    @Override
    protected void frameInit() {
        this.setRootPane(createRootPane());
        this.setVisible(true);
    }

    public void run(User user, AppStates state) {
        switch (state) {
            case CHOOSE_LOGIN_OR_CREATE_USER:
                currentMenu = new CafeLoginOrCreateUser();

                break;
            case LOGIN:
                currentMenu = new CafeLogin();

                break;
            case CREATE_USER:
                currentMenu = new CafeCreateUser();

                break;
            case USER_HOME:
                if (user == null) throw new NullPointerException("User should not be null here");

                this.setTitle(TITLE + " - " + user.getLogin());
                currentMenu = new CafeUserHome(user);

                break;
            default:
                System.out.println("Congrats! You managed to make it here somehow!");
                break;
        }

        this.removeAll();
        this.add(currentMenu);
        this.repaint();
        this.revalidate();
    }

    private static class CafeApplicationWindowAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            CafeSQLManager.cleanup();

            System.exit(0);
        }
    }
}

package gui;

import util.CafeSQLManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CafeApplication extends JFrame {

    public CafeApplication() throws HeadlessException {
        super("CS 166 Project - Cafe Management");

        this.frameInit();

        this.setSize(800, 600);
        this.addWindowListener(new CafeApplicationWindowAdapter());
    }

    @Override
    protected void frameInit() {
        this.setRootPane(createRootPane());

        loginScreen = new CafeLoginOrCreateUser(this);
        this.add(loginScreen);

        this.setVisible(true);
    }

    public void run() {

    }


    private class CafeApplicationWindowAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            CafeSQLManager.cleanup();

            System.exit(0);
        }
    }
}

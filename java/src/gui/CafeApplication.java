package gui;

import util.CafeSQLManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CafeApplication extends JFrame {

    public CafeApplication() throws HeadlessException {
        super("CS 166 Project - Cafe Management");

        this.setSize(800, 600);
        this.addWindowListener(new CafeApplicationWindowAdapter());
    }

    @Override
    protected void frameInit() {
        this.setRootPane(createRootPane());

    }



    private class CafeApplicationWindowAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }
}

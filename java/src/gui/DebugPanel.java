package gui;

import util.TextAreaOutputStream;

import javax.swing.*;
import java.io.PrintStream;

public class DebugPanel extends JFrame {

    JTextArea debugOuput;

    public DebugPanel() {
        super("Debug Output");

        debugOuput = new JTextArea();
        this.add(debugOuput);

        this.setSize(800, 600);
        this.setVisible(true);

        PrintStream printStream = new PrintStream(new TextAreaOutputStream(debugOuput));
        System.setOut(printStream);
        System.setErr(printStream);
    }
}

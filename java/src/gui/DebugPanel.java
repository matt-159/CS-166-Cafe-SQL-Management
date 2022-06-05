package gui;

import util.TextAreaOutputStream;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;

public class DebugPanel extends JFrame {

    JScrollPane debugScrollPane;
    JTextArea debugOuput;

    public DebugPanel() {
        super("Debug Output");

        debugOuput = new JTextArea();
        debugOuput.setFont(new Font("Consolas", Font.PLAIN, 12));
        debugScrollPane = new JScrollPane(debugOuput);

        this.add(debugScrollPane);

        this.setSize(800, 600);
        this.setVisible(true);

        PrintStream printStream = new PrintStream(new TextAreaOutputStream(debugOuput));
        System.setOut(printStream);
        System.setErr(printStream);
    }
}

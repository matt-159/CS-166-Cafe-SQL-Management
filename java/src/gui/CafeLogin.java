package gui;

import data.User;
import util.CafeSQLManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CafeLogin extends JPanel {

    private final JTextField usernameField, passwordField;
    private final JButton submitButton;
    private final JLabel usernameLabel, passwordLabel, invalidNotification;

    private final CafeApplication app;

    public CafeLogin(CafeApplication parent) {
        super();
        this.app = parent;

        usernameLabel = new JLabel("Login");
        this.add(usernameLabel);

        usernameField = new JTextField(10);
        this.add(usernameField);

        passwordLabel = new JLabel("Password");
        this.add(passwordLabel);

        passwordField = new JTextField(10);
        this.add(passwordField);

        submitButton = new JButton("Submit");
        submitButton.setActionCommand("submit");
        submitButton.addActionListener(new SubmitButtonActionListener(this));

        this.add(submitButton);

        invalidNotification = new JLabel("Invalid Login");
        invalidNotification.setForeground(Color.RED);
        invalidNotification.setVisible(false);
        this.add(invalidNotification);
    }

    private static class SubmitButtonActionListener implements ActionListener {

        private CafeLogin parent;

        public SubmitButtonActionListener(CafeLogin parent) {
            this.parent = parent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "submit":
                    String username = parent.usernameField.getText();
                    String password = parent.passwordField.getText();

                    User user = CafeSQLManager.login(username, password);

                    if (user != null) {
                        parent.app.run(user, CafeApplication.AppStates.USER_HOME);
                    } else {
                        parent.invalidNotification.setVisible(true);
                    }

                    break;

                default:
                    break;
            }
        }
    }
}

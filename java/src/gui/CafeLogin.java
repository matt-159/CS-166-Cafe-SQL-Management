package gui;

import data.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CafeLogin extends JPanel {

    private final JTextField usernameField, passwordField;
    private final JButton submitButton, backButton;
    private final JLabel usernameLabel, passwordLabel, invalidNotification;

    public CafeLogin() {
        super();

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

        backButton = new JButton("Back");
        backButton.setActionCommand("back");
        backButton.addActionListener(new SubmitButtonActionListener(this));
        this.add(backButton);
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

                    User user = User.login(username, password);

                    if (user != null) {
                        CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_HOME);
                    } else {
                        parent.invalidNotification.setVisible(true);
                    }

                    break;
                case "back":
                    CafeApplication.getInstance().run(null, CafeApplication.AppStates.CHOOSE_LOGIN_OR_CREATE_USER);
                    break;
                default:
                    break;
            }
        }
    }
}

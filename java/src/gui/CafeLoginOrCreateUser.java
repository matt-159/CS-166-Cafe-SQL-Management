package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CafeLoginOrCreateUser extends JPanel {

    private final JButton chooseLogin;
    private final JButton chooseCreateNewUser;

    private final CafeLogin loginPanel;

    private final CafeApplication app;

    public CafeLoginOrCreateUser(CafeApplication parent) {

        this.app = parent;

        chooseLogin = new JButton("Log In");
        chooseLogin.setActionCommand("logIn");
        chooseLogin.setVisible(true);

        chooseLogin.addActionListener(new UserLoginOrCreateUserActionListener(this));
        this.add(chooseLogin);

        chooseCreateNewUser = new JButton("Create Account");
        chooseCreateNewUser.setActionCommand("createAccount");
        chooseCreateNewUser.setVisible(true);

        chooseCreateNewUser.addActionListener(new UserLoginOrCreateUserActionListener(this));
        this.add(chooseCreateNewUser);

        loginPanel = new CafeLogin(this);
        loginPanel.setVisible(false);
        this.add(loginPanel);
    }

    private void openLoginMenu() {
        chooseLogin.setVisible(false);
        chooseCreateNewUser.setVisible(false);

        loginPanel.setVisible(true);
    }

    private void openCreateUserMenu() {
        chooseLogin.setVisible(false);
        chooseCreateNewUser.setVisible(false);


    }

    private static class UserLoginOrCreateUserActionListener implements ActionListener {

        CafeLoginOrCreateUser cafeLoginOrCreateUser = null;

        public UserLoginOrCreateUserActionListener(CafeLoginOrCreateUser parent) {
            this.cafeLoginOrCreateUser = parent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "logIn":
                    cafeLoginOrCreateUser.openLoginMenu();
                    break;
                case "createAccount":
                    cafeLoginOrCreateUser.openCreateUserMenu();
                    break;
                default:
                    break;
            }
        }
    }
}

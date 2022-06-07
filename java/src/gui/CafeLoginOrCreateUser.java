package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CafeLoginOrCreateUser extends JPanel {

    private final JButton chooseLogin;
    private final JButton chooseCreateNewUser;

    public CafeLoginOrCreateUser() {

        chooseLogin = new JButton("Log In");
        chooseLogin.setActionCommand("logIn");
        chooseLogin.addActionListener(new UserLoginOrCreateUserActionListener());
        this.add(chooseLogin);

        chooseCreateNewUser = new JButton("Create Account");
        chooseCreateNewUser.setActionCommand("createAccount");
        chooseCreateNewUser.addActionListener(new UserLoginOrCreateUserActionListener());
        this.add(chooseCreateNewUser);
    }

    private static class UserLoginOrCreateUserActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "logIn":
                    CafeApplication.getInstance().run(null, CafeApplication.AppStates.LOGIN);
                    break;
                case "createAccount":
                    CafeApplication.getInstance().run(null, CafeApplication.AppStates.CREATE_USER);
                    break;
                default:
                    break;
            }
        }
    }
}

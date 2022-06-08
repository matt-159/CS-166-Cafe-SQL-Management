package gui;

import data.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CafeUpdateUser extends JPanel {

    private final User user;

    private final JButton back;

    public CafeUpdateUser(User user) {
        super();

        this.user = user;

        back = new JButton("Back");
        back.setActionCommand("back");
        back.addActionListener(new CafeUpdateUserActionListener(user));
        this.add(back);
    }

    private static class CafeUpdateUserActionListener implements ActionListener {
        private final User user;

        public CafeUpdateUserActionListener(User user) {
            this.user = user;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "back":
                    CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_HOME);

                    break;
                default:
                    break;
            }
        }
    }
}

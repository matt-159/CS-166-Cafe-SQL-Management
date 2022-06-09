package gui;

import data.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CafeViewOrderHistory extends JPanel {

    private final User user;

    private final JButton back;

    public CafeViewOrderHistory(User user) {
        super();

        this.user = user;

        back = new JButton("Back");
        back.setActionCommand("back");
        back.addActionListener(new CafeViewOrderHistoryActionListener(user));
        this.add(back);
    }

    private static class CafeViewOrderHistoryActionListener implements ActionListener {
        private final User user;

        public CafeViewOrderHistoryActionListener(User user) {
            this.user = user;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "back":
                    CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_HOME, null);

                    break;
                default:
                    break;
            }
        }
    }
}

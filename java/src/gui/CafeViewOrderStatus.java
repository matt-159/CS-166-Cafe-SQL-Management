package gui;

import data.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CafeViewOrderStatus extends JPanel {

    private final User user;

    private final JButton back;

    public CafeViewOrderStatus(User user) {
        super();

        this.user = user;

        back = new JButton("Back");
        back.setActionCommand("back");
        back.addActionListener(new CafeViewOrderStatusActionListener(user));
        this.add(back);
    }

    private static class CafeViewOrderStatusActionListener implements ActionListener {
        private final User user;

        public CafeViewOrderStatusActionListener(User user) {
            this.user = user;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "showDetails":
                    CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_HOME, null);

                    break;
                default:
                    break;
            }
        }
    }
}

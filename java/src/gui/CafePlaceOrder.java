package gui;

import data.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CafePlaceOrder extends JPanel {

    private final User user;

    private final JButton back;

    public CafePlaceOrder(User user) {
        super();

        this.user = user;

        back = new JButton("Back");
        back.setActionCommand("back");
        back.addActionListener(new CafePlaceOrderActionListener(user));
        this.add(back);
    }

    private static class CafePlaceOrderActionListener implements ActionListener {
        private final User user;

        public CafePlaceOrderActionListener(User user) {
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

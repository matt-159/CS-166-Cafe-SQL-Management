package gui;

import data.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CafeUpdateItemStatus extends JPanel {

    private final User user;

    private final JButton back;

    public CafeUpdateItemStatus(User user) {
        super();

        this.user = user;

        back = new JButton("Back");
        back.setActionCommand("back");
        back.addActionListener(new CafeUpdateItemStatusActionListener(user));
        this.add(back);
    }

    private static class CafeUpdateItemStatusActionListener implements ActionListener {
        private final User user;

        public CafeUpdateItemStatusActionListener(User user) {
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

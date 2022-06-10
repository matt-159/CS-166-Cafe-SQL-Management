package gui;

import data.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CafeUpdateMenu extends JPanel {

    private final User user;

    private final JButton back;
    private final JButton addNewItem;

    public CafeUpdateMenu(User user) {
        super();

        this.user = user;

        this.addNewItem = new JButton("Add Item");
        this.addNewItem.setActionCommand("addNewItem");
        this.addNewItem.addActionListener(new CafeUpdateMenuActionListener());
        this.add(addNewItem);

        this.back = new JButton("Back");
        this.back.setActionCommand("back");
        this.back.addActionListener(new CafeUpdateMenuActionListener());
        this.add(back);
    }

    private class CafeUpdateMenuActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "addNewItem":
                    CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_ADD_MENU_ITEM, null);

                    break;
                case "back":
                    CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_HOME, null);

                    break;
                default:
                    break;
            }
        }
    }
}

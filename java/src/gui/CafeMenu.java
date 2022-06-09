package gui;

import data.Menu;
import data.MenuItem;
import data.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class CafeMenu extends JPanel {

    private final JButton back;

    private final User user;

    public CafeMenu(User user) {
        super();

        this.user = user;

        Map<String, List<MenuItem>> menu = Menu.getInstance().getCategorizedMenu();

        this.setLayout(new GridLayout(menu.size() + 1, 1));

        menu.keySet().forEach(key -> {
            JPanel panel = new JPanel(new GridLayout(2,1));

            JPanel panel1 = new JPanel(new GridLayout(menu.get(key).size(), 1));
            menu.get(key).forEach(menuItem -> {
                JPanel panel2 = new JPanel(new GridLayout(1, 3));

                panel2.add(new JLabel(menuItem.getItemName()));
                panel2.add(new JLabel(String.format("$%.2f", menuItem.getPrice())));
                panel2.add(new JLabel(menuItem.getDescription()));

                panel1.add(panel2);
            });

            panel.add(new JLabel(key));
            panel.add(panel1);

            this.add(panel);
        });

        back = new JButton("Back");
        back.setActionCommand("back");
        back.addActionListener(new CafeMenuActionListener(this.user));
        this.add(back);
    }

    private static class CafeMenuActionListener implements ActionListener {

        private final User user;

        public CafeMenuActionListener(User user) {
            this.user = user;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "back":
                    CafeApplication.getInstance().run(this.user, CafeApplication.AppStates.USER_HOME, null);
                    break;
                default:
                    break;
            }
        }
    }
}

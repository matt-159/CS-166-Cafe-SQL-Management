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
        super(new GridLayout(0, 1));

        this.user = user;

        Map<String, List<MenuItem>> menu = Menu.getInstance().getCategorizedMenu();

        menu.keySet().forEach(key -> {
            JLabel category = new JLabel(key);
            category.setFont(category.getFont().deriveFont(Font.BOLD, 16));
            this.add(category);

            menu.get(key).forEach(menuItem -> {
                JPanel panel = new JPanel(new GridLayout(0, 3));

                panel.add(new JLabel(menuItem.getItemName()));
                panel.add(new JLabel(String.format("$%.2f", menuItem.getPrice())));
                panel.add(new JLabel(menuItem.getDescription()));

                this.add(panel);
            });
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

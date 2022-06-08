package gui;

import data.Menu;
import data.MenuItem;
import data.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class CafeMenu extends JPanel {

    private final JButton back;

    private final User user;

    public CafeMenu(User user) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.user = user;

        Map<String, List<MenuItem>> menu = Menu.getInstance().getCategorizedMenu();

        menu.keySet().forEach(key -> {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            panel.add(new JLabel(key));

            menu.get(key).forEach(menuItem -> {
                JPanel panel1 = new JPanel();
                panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));

                panel1.add(new JLabel(menuItem.getItemName()));
                panel1.add(new JLabel(String.format("$%.2f", menuItem.getPrice())));
                panel1.add(new JLabel(menuItem.getDescription()));
                panel.add(panel1);
            });

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
                    CafeApplication.getInstance().run(this.user, CafeApplication.AppStates.USER_HOME);
                    break;
                default:
                    break;
            }
        }
    }
}

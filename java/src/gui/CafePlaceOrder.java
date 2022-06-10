package gui;

import data.*;
import data.Menu;
import data.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CafePlaceOrder extends JPanel {

    private final User user;
    private double total;

    private final JButton back, placeOrder;
    private final JLabel displayTotal;

    public CafePlaceOrder(User user) {
        super(new GridLayout(0,1));
        this.user = user;

        Menu.getInstance().getMenu().keySet().forEach(key -> {
            JCheckBox menuItem = new JCheckBox(key);
            menuItem.setActionCommand("updatePrice");
            menuItem.addActionListener(new CafePlaceOrderActionListener());

            this.add(menuItem);
        });

        total = 0;
        this.displayTotal = new JLabel(String.format("Order Total: $%.2f", total));
        this.add(displayTotal);

        this.placeOrder = new JButton("Place Order");
        this.placeOrder.setActionCommand("placeOrder");
        this.placeOrder.addActionListener(new CafePlaceOrderActionListener());
        this.add(placeOrder);

        this.back = new JButton("Back");
        this.back.setActionCommand("back");
        this.back.addActionListener(new CafePlaceOrderActionListener());
        this.add(back);
    }

    private class CafePlaceOrderActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "updatePrice":
                    total = 0;
                    Arrays.stream(getComponents())
                            .filter(component -> component instanceof JCheckBox && ((JCheckBox) component).isSelected())
                            .forEach(component -> total += Menu.getInstance().getMenu().get(((JCheckBox) component).getText()).getPrice());
                    displayTotal.setText(String.format("Order Total: $%.2f", total));

                    break;
                case "placeOrder":
                    int orderid = Order.placeNewOrder(user.getLogin(), total);
                    Map<String, MenuItem> menu = Menu.getInstance().getMenu();
                    Arrays.stream(getComponents())
                            .filter(component -> component instanceof JCheckBox && ((JCheckBox) component).isSelected())
                            .forEach(checkbox -> {
                                ItemStatus.addNewItemStatus(new ItemStatus(orderid,
                                        ((JCheckBox) checkbox).getText(),
                                        Timestamp.from(Instant.now().truncatedTo(ChronoUnit.SECONDS)).toString(),
                                        "Hasn''t started",
                                        ""));
                            });

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

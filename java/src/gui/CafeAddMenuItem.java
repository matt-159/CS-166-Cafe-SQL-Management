package gui;

import data.Menu;
import data.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CafeAddMenuItem extends JPanel {

    private static final int TEXTFIELD_WIDTH = 20;

    private final User user;

    private final JButton back;
    private final JButton addNewItem;
    private final JTextField itemName, type, price, imageURL;
    private final JTextArea description;

    public CafeAddMenuItem(User user) {
        super(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 1;
        c.weighty = 1;

        this.user = user;

        JPanel panel;

        panel = new JPanel(new GridLayout(0, 3));
        this.itemName = new JTextField(TEXTFIELD_WIDTH);
        panel.add(new JLabel("Item Name"));
        panel.add(itemName);
        panel.add(new JLabel("*"));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.add(panel, c);

        panel = new JPanel(new GridLayout(0, 3));
        this.type = new JTextField(TEXTFIELD_WIDTH);
        panel.add(new JLabel("Type"));
        panel.add(type);
        panel.add(new JLabel("*"));
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.add(panel, c);

        panel = new JPanel(new GridLayout(0, 3));
        this.price = new JTextField(TEXTFIELD_WIDTH);
        panel.add(new JLabel("Price"));
        panel.add(price);
        panel.add(new JLabel("*"));
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.add(panel, c);

        panel = new JPanel(new GridLayout(0, 3));
        this.description = new JTextArea(TEXTFIELD_WIDTH, TEXTFIELD_WIDTH);
        panel.add(new JLabel("Description"));
        panel.add(description);
        panel.add(new JLabel("*"));
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 4;
        this.add(panel, c);

        panel = new JPanel(new GridLayout(0, 3));
        this.imageURL = new JTextField(TEXTFIELD_WIDTH);
        panel.add(new JLabel("Image URL"));
        panel.add(imageURL);
        panel.add(new JLabel());
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.add(panel, c);

        this.addNewItem = new JButton("Add Item to Menu");
        this.addNewItem.setActionCommand("addNewItem");
        this.addNewItem.addActionListener(new CafeAddMenuItemActionListener());
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.add(addNewItem, c);

        this.back = new JButton("Back");
        this.back.setActionCommand("back");
        this.back.addActionListener(new CafeAddMenuItemActionListener());
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.add(back, c);
    }

    private class CafeAddMenuItemActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "addNewItem":
                    Menu.addNewMenuItem(itemName.getText().trim(),
                                    type.getText().trim(),
                                    Double.parseDouble(price.getText().trim()),
                                    description.getText().trim(),
                                    imageURL.getText().trim());

                    break;
                case "back":
                    CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_UPDATE_MENU, null);

                    break;
                default:
                    break;
            }
        }
    }
}

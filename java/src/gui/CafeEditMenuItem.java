package gui;

import data.MenuItem;
import data.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CafeEditMenuItem extends JPanel {

    private static final int TEXTFIELD_WIDTH = 20;

    private final User user;
    private final MenuItem menuItem;

    private final JButton back;
    private final JButton addNewItem;
    private final JTextField itemName, type, price, imageURL;
    private final JTextArea description;

    public CafeEditMenuItem(User user, MenuItem menuItem) {
        super(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 1;
        c.weighty = 1;

        this.user = user;
        this.menuItem = menuItem;

        JPanel panel;

        panel = new JPanel(new GridLayout(0, 3));
        this.itemName = new JTextField(menuItem.getItemName(), TEXTFIELD_WIDTH);
        this.itemName.setEditable(false);
        panel.add(new JLabel("Item Name"));
        panel.add(itemName);
        panel.add(new JLabel("*"));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.add(panel, c);

        panel = new JPanel(new GridLayout(0, 3));
        this.type = new JTextField(menuItem.getType(), TEXTFIELD_WIDTH);
        panel.add(new JLabel("Type"));
        panel.add(type);
        panel.add(new JLabel("*"));
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.add(panel, c);

        panel = new JPanel(new GridLayout(0, 3));
        this.price = new JTextField(Double.toString(menuItem.getPrice()), TEXTFIELD_WIDTH);
        panel.add(new JLabel("Price"));
        panel.add(price);
        panel.add(new JLabel("*"));
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.add(panel, c);

        panel = new JPanel(new GridLayout(0, 3));
        this.description = new JTextArea(menuItem.getDescription(), TEXTFIELD_WIDTH, TEXTFIELD_WIDTH);
        panel.add(new JLabel("Description"));
        panel.add(description);
        panel.add(new JLabel("*"));
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 4;
        this.add(panel, c);

        panel = new JPanel(new GridLayout(0, 3));
        this.imageURL = new JTextField(menuItem.getImageURL(), TEXTFIELD_WIDTH);
        panel.add(new JLabel("Image URL"));
        panel.add(imageURL);
        panel.add(new JLabel());
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.add(panel, c);

        this.addNewItem = new JButton("Save Changes");
        this.addNewItem.setActionCommand("saveChanges");
        this.addNewItem.addActionListener(new CafeEditMenuItemActionListener());
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.add(addNewItem, c);

        this.back = new JButton("Back");
        this.back.setActionCommand("back");
        this.back.addActionListener(new CafeEditMenuItemActionListener());
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.add(back, c);
    }

    private class CafeEditMenuItemActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "saveChanges":
                    menuItem.setType(type.getText().trim());
                    menuItem.setPrice(Double.parseDouble(price.getText().trim()));
                    menuItem.setDescription(description.getText().trim());
                    menuItem.setImageURL(imageURL.getText().trim());

                    menuItem.updateDB();
                case "back":
                    CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_UPDATE_MENU, null);

                    break;
                default:
                    break;
            }
        }
    }
}

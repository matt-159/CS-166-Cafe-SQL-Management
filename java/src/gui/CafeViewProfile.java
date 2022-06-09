package gui;

import data.Menu;
import data.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CafeViewProfile extends JPanel {

    private final User user;

    private static final int TEXTFIELD_WIDTH = 20;

    private JButton back;
    private JLabel passwordLabel1, phoneNumLabel, favItemsLabel;
    private JTextField password, phoneNum;

    private JLabel requiredFieldsMessage, errorMessage;

    public CafeViewProfile(User user) {
        super(new GridLayout(0, 1));

        this.user = user;

        JPanel b = new JPanel();
        passwordLabel1 = new JLabel("Password");
        password = new JTextField(user.getPassword(), TEXTFIELD_WIDTH);
        b.add(passwordLabel1);
        b.add(password);
        this.add(b);

        JPanel d = new JPanel();
        phoneNumLabel = new JLabel("Phone Number");
        phoneNum = new JTextField(user.getPhoneNumber(), TEXTFIELD_WIDTH);
        d.add(phoneNumLabel);
        d.add(phoneNum);
        this.add(d);

        favItemsLabel = new JLabel("Favorite Items");
        this.add(favItemsLabel);
        Menu.getInstance().getMenu().keySet().forEach(key -> {
            JCheckBox checkBox = new JCheckBox(key);
            checkBox.setSelected(user.getFavItems().contains(key));
            this.add(checkBox);
        });

        back = new JButton("Save Changes");
        back.setActionCommand("saveChanges");
        back.addActionListener(new CafeViewProfileActionListener(user));
        this.add(back);
    }

    private class CafeViewProfileActionListener implements ActionListener {
        private final User user;

        public CafeViewProfileActionListener(User user) {
            this.user = user;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "saveChanges":
                    List<String> favItemList = new ArrayList<>();
                    Arrays.stream(getComponents())
                            .filter(component -> component instanceof JCheckBox && ((JCheckBox) component).isSelected())
                            .forEach(component -> favItemList.add(((JCheckBox) component).getText()));
                    user.setFavItems(favItemList);
                    user.setPassword(password.getText().trim());
                    user.setPhoneNumber(phoneNum.getText().trim());

                    user.updateDB();

                    CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_HOME, null);
                    break;
                default:
                    break;
            }
        }
    }
}

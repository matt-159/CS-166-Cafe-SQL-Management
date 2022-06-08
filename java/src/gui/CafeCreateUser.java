package gui;

import data.Menu;
import data.User;
import util.CafeSQLManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CafeCreateUser extends JPanel {

    private static final int TEXTFIELD_WIDTH = 20;

    private JButton submit;
    private JLabel usernameLabel, passwordLabel1, passwordLabel2, phoneNumLabel, favItemsLabel;
    private JTextField username, password1, password2, phoneNum;

    private JLabel requiredFieldsMessage, errorMessage;

    public CafeCreateUser() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel a = new JPanel();
        usernameLabel = new JLabel("Username");
        username = new JTextField(TEXTFIELD_WIDTH);
        a.add(usernameLabel);
        a.add(username);
        a.add(new JLabel("*"));
        this.add(a);

        JPanel b = new JPanel();
        passwordLabel1 = new JLabel("Password");
        password1 = new JTextField(TEXTFIELD_WIDTH);
        b.add(passwordLabel1);
        b.add(password1);
        b.add(new JLabel("*"));
        this.add(b);

        JPanel c = new JPanel();
        passwordLabel2 = new JLabel("Re-enter Password");
        password2 = new JTextField(TEXTFIELD_WIDTH);
        c.add(passwordLabel2);
        c.add(password2);
        c.add(new JLabel("*"));
        this.add(c);

        JPanel d = new JPanel();
        phoneNumLabel = new JLabel("Phone Number");
        phoneNum = new JTextField(TEXTFIELD_WIDTH);
        d.add(phoneNumLabel);
        d.add(phoneNum);
        this.add(d);

        favItemsLabel = new JLabel("Favorite Items");
        this.add(favItemsLabel);
        Menu.getInstance().getMenu().keySet().forEach(key -> this.add(new JCheckBox(key.trim())));

        requiredFieldsMessage = new JLabel("Fields marked with a (*) are required.");
        this.add(requiredFieldsMessage);

        errorMessage = new JLabel();
        errorMessage.setForeground(Color.RED);
        errorMessage.setVisible(false);
        this.add(errorMessage);

        submit = new JButton("Create Account");
        submit.setActionCommand("submit");
        submit.addActionListener(new CafeCreateUserActionListener(this));
        this.add(submit);
    }

    private static class CafeCreateUserActionListener implements ActionListener {

        private final CafeCreateUser parent;

        private CafeCreateUserActionListener(CafeCreateUser parent) {
            this.parent = parent;
        }

        private boolean validateUsernameAndPhoneNumber() {
            String rawQuery = "SELECT * FROM USERS WHERE login='%s'";
            String query = String.format(rawQuery, this.parent.username.getText());

            if (!CafeSQLManager.isEmptyResultSet(CafeSQLManager.executeQuery(query))) {
                parent.errorMessage.setText("This username is already taken");
                parent.errorMessage.setVisible(true);

                return false;
            }

            rawQuery = "SELECT * FROM USERS WHERE phoneNum='%s'";
            query = String.format(rawQuery, this.parent.phoneNum.getText());

            if (!CafeSQLManager.isEmptyResultSet(CafeSQLManager.executeQuery(query))) {
                parent.errorMessage.setText("This phone number is already in use");
                parent.errorMessage.setVisible(true);

                return false;
            }

            return true;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "submit":

                    boolean missingField =  parent.username.getText().isEmpty()  ||
                                            parent.password1.getText().isEmpty() ||
                                            parent.password2.getText().isEmpty();

                    if (missingField) {
                        parent.requiredFieldsMessage.setForeground(Color.RED);
                        return;
                    } else {
                        parent.requiredFieldsMessage.setForeground(Color.BLACK);
                    }

                    if (!parent.password1.getText().equals(parent.password2.getText())) {
                        parent.errorMessage.setText("Passwords do not match");
                        parent.errorMessage.setVisible(true);

                        return;
                    }

                    if (!validateUsernameAndPhoneNumber()) {
                        return;
                    }

                    List<String> favItemList = new ArrayList<>();
                    Arrays.stream(parent.getComponents())
                        .filter(component -> component instanceof JCheckBox && ((JCheckBox) component).isSelected())
                        .forEach(component -> favItemList.add(((JCheckBox) component).getText()));

                    User user = new User(   parent.username.getText(),
                                            parent.password1.getText(),
                                            parent.phoneNum.getText(),
                                            favItemList,
                                            User.UserType.Customer  );

                    if (User.createUser(user)) {
                        CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_HOME);
                    } else {
                        System.err.println("Something went wrong during account creation");
                    }

                    break;
                default:
                    System.err.println("Congrats! You managed to get here and I have no idea how!");
            }
        }
    }
}

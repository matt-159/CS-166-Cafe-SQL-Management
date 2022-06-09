package gui;

import data.User;
import util.CafeSQLManager;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CafeUpdateUser extends JPanel {

    private final User user;

    private final JButton back;

    private final TableModel table;
    private final JTable jtable;
    private final JScrollPane jtablePane;

    private final TablePopUp popup;

    private final TableMouseListener tableMouseListener;

    private final List<List<String>> userList;

    public CafeUpdateUser(User user) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.user = user;

        userList = getUserList();

        this.table = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return userList.size();
            }

            @Override
            public int getColumnCount() {
                return 1;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return String.format("%-50s%-10s",
                        userList.get(rowIndex).get(0).replaceAll("\\.", " ").replaceAll("_", " "),
                        userList.get(rowIndex).get(1));
            }
        };

        tableMouseListener = new TableMouseListener();
        popup = new TablePopUp();

        jtable = new JTable(table);
        jtable.setTableHeader(null);
        jtable.addMouseListener(tableMouseListener);
        jtable.setFont(new Font("Consolas", Font.PLAIN, 12));
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jtablePane = new JScrollPane(jtable);
        this.add(jtablePane);

        back = new JButton("Back");
        back.setActionCommand("back");
        back.addActionListener(new CafeUpdateUserActionListener(user));
        this.add(back);
    }

    private List<List<String>> getUserList() {
        String query = String.format("SELECT login, type FROM USERS WHERE login!='%s' ORDER BY login", user.getLogin());

        return CafeSQLManager.executeQuery(query);
    }

    private class TablePopUp extends JPopupMenu {
        private JMenuItem setToCustomer, setToEmployee, setToManager;

        public TablePopUp() {
            setToCustomer = new JMenuItem("Set to Customer");
            setToCustomer.setActionCommand("setToCustomer");
            setToCustomer.addActionListener(new TablePopUpActionListener());
            this.add(setToCustomer);

            setToEmployee = new JMenuItem("Set to Employee");
            setToEmployee.setActionCommand("setToEmployee");
            setToEmployee.addActionListener(new TablePopUpActionListener());
            this.add(setToEmployee);

            setToManager = new JMenuItem("Set to Manager");
            setToManager.setActionCommand("setToManager");
            setToManager.addActionListener(new TablePopUpActionListener());
            this.add(setToManager);
        }

        @Override
        public void show(Component invoker, int x, int y) {
            System.out.printf("TablePopUp invoked on %s at (%d, %d)\n", invoker.getClass().getName(), x, y);
            super.show(invoker, x, y);
        }

        private class TablePopUpActionListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                int index = jtable.getSelectedRow();
                String login = userList.get(index).get(0);
                String type = userList.get(index).get(1);

                switch (e.getActionCommand()) {
                    case "setToCustomer":
                        type = User.UserType.Customer.toString();
                        break;
                    case "setToEmployee":
                        type = User.UserType.Employee.toString();
                        break;
                    case "setToManager":
                        type = User.UserType.Manager.toString();
                        break;
                    default:
                        break;
                }

                String query = String.format("UPDATE USERS SET type='%s' WHERE login='%s'", type, login);
                CafeSQLManager.executeUpdate(query);

                userList.get(index).set(1, type);
                jtable.invalidate();
                jtable.repaint();
            }
        }
    }

    private class TableMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            check(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            check(e);
        }

        private void check(MouseEvent e) {
            if (e.isPopupTrigger()) {
                System.out.printf("Showing popup at (x: %d, y: %d)\n", e.getX(), e.getYOnScreen());
                Point p = jtablePane.getMousePosition();
                p.x += jtable.getX();

                jtable.changeSelection(p.y / jtable.getRowHeight(), 0, false, false);

                popup.show(CafeApplication.getInstance(), p.x, p.y);
            }
        }
    }

    private static class CafeUpdateUserActionListener implements ActionListener {
        private final User user;

        public CafeUpdateUserActionListener(User user) {
            this.user = user;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "back":
                    CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_HOME, null);

                    break;
                default:
                    break;
            }
        }
    }
}

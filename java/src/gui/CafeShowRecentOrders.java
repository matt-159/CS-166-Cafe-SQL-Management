package gui;

import data.ItemStatus;
import data.Order;
import data.User;
import util.CafeSQLManager;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class CafeShowRecentOrders extends JPanel {

    private final User user;
    private final List<Order> orders;

    private final JButton back;
    private final JTable table;
    private final JScrollPane tablePane;

    public CafeShowRecentOrders(User user) {
        super(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.weightx = 1;
        c.weighty = 1;

        this.user = user;
        this.orders = user.fetchRecentOrders();

        this.table = new JTable(new ItemStatusTable());
        this.table.addMouseListener(new TableMouseListener());
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.setFont(new Font("Consolas", Font.PLAIN, 12));
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.tablePane = new JScrollPane(table);
        c.gridy = 0;
        c.gridheight = 9;
        this.add(tablePane, c);

        this.back = new JButton("Back");
        this.back.setActionCommand("back");
        this.back.addActionListener(new CafeUpdateItemStatusActionListener());
        c.gridy = 9;
        c.gridheight = 1;
        this.add(back, c);
    }

    private List<ItemStatus> getItemStatusList() {
        String query = "SELECT * FROM ITEMSTATUS ORDER BY lastupdated DESC";

        List<ItemStatus> list = new ArrayList<>();
        CafeSQLManager.executeQuery(query).forEach(data -> list.add(new ItemStatus(data)));

        return list;
    }

    private class CafeUpdateItemStatusActionListener implements ActionListener {

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

    private class ItemStatusTable extends AbstractTableModel {
        private final String[] COLUMN_NAMES = { "Order ID", "Paid", "Timestamp Received", "Total Price" };

        @Override
        public int getRowCount() {
            return orders.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int column) {
            return COLUMN_NAMES[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return Integer.class;
                case 1:
                    return Boolean.class;
                case 2:
                case 3:
                    return String.class;
            }

            return null;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Order order = orders.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return order.getOrderID();
                case 1:
                    return order.getIsPaid();
                case 2:
                    return order.getTimestampReceived();
                case 3:
                    return String.format("$%.2f", order.getTotal());
                default:
                    return null;
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 1;
        }
    }

    private class TableMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            Point p = table.getMousePosition();
            Order order = orders.get(table.rowAtPoint(p));
            order.setPaid(!order.getIsPaid());

            order.updateDB();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            check(e);
        }

        private void check(MouseEvent e) {
            if (e.isPopupTrigger()) {
                System.out.printf("Showing popup at (x: %d, y: %d)\n", e.getX(), e.getYOnScreen());
                Point p = table.getMousePosition();

                table.changeSelection(table.rowAtPoint(p), 0, false, false);
            }
        }
    }
}

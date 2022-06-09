package gui;

import data.Order;
import data.User;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class CafeViewOrderHistory extends JPanel {

    private final User user;

    private final JTable table;
    private final JButton back;

    public CafeViewOrderHistory(User user) {
        super(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 1;
        c.weighty = 1;
        this.user = user;

        table = new JTable(new OrderHistoryTable(user.getOrderHistory()));
        table.getColumn(" ").setCellRenderer(new ButtonRenderer("Show Details"));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new TableMouseListener());
        JScrollPane tablePane = new JScrollPane(table);
        c.gridy = 0;
        c.gridheight = 9;
        this.add(tablePane, c);

        back = new JButton("Back");
        back.setActionCommand("back");
        back.addActionListener(new CafeViewOrderHistoryActionListener(user));
        c.gridy = 9;
        c.gridheight = 1;
        this.add(back, c);

    }

    private static class CafeViewOrderHistoryActionListener implements ActionListener {
        private final User user;

        public CafeViewOrderHistoryActionListener(User user) {
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

    private class OrderHistoryTable extends AbstractTableModel {

        private final String[] COLUMN_NAMES = { "Order ID", "Paid", "Timestamp Received", "Total Price", " " };

        private List<Order> orders;
        private JButton showDetails;

        public OrderHistoryTable(List<Order> orders) {
            this.orders = orders;

            this.showDetails = new JButton("Show Details");
            this.showDetails.setActionCommand("showDetails");
            this.showDetails.addActionListener(new ShowDetailsActionListener());
        }

        @Override
        public int getRowCount() {
            return orders.size();
        }

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public String getColumnName(int column) {
            return this.COLUMN_NAMES[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return Long.class;
                case 1:
                    return Boolean.class;
                case 2:
                case 3:
                    return String.class;
                case 4:
                    return JButton.class;
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
                case 4:
                    return showDetails;
                default:
                    return null;
            }
        }

        private class ShowDetailsActionListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                switch (e.getActionCommand()) {
                    case "showDetails":
                        int row = table.rowAtPoint(table.getMousePosition());
                        CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_VIEW_ORDER_STATUS, orders.get(row));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private class TableMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            JButton button = getJButton(e);

            if (button != null) {
                button.doClick();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        private JButton getJButton(MouseEvent e) {
            if (e.getComponent() == table) {
                Point p = table.getMousePosition();
                int row = table.rowAtPoint(p);
                Object value = table.getValueAt(row, table.columnAtPoint(p));
                if (value instanceof JButton) {
                    return (JButton) value;
                }
            }
            return null;
        }
    }

    private class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer(String label) {
            super(label);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }
}

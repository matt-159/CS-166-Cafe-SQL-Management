package gui;

import data.ItemStatus;
import data.Order;
import data.User;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CafeViewOrderStatus extends JPanel {

    private final User user;
    private final Order order;
    private final List<ItemStatus> items;

    private final JTable table;
    private final JButton back;

    public CafeViewOrderStatus(User user, Order order) {
        super(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 1;
        c.weighty = 1;

        this.user = user;
        this.order = order;
        this.items = order.getItems();

        table = new JTable(new ItemStatusTable());
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane tablePane = new JScrollPane(table);
        c.gridy = 0;
        c.gridheight = 9;
        this.add(tablePane, c);

        back = new JButton("Back");
        back.setActionCommand("back");
        back.addActionListener(new CafeViewOrderStatusActionListener());
        c.gridy = 9;
        c.gridheight = 1;
        this.add(back, c);
    }

    private class CafeViewOrderStatusActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "back":
                    CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_VIEW_ORDER_HISTORY, order);

                    break;
                default:
                    break;
            }
        }
    }

    private class ItemStatusTable extends AbstractTableModel {

        private final String[] COLUMN_NAMES = { "Item Name", "Last Updated", "Status", "Comments" };

        @Override
        public int getRowCount() {
            return items.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int column) {
            return this.COLUMN_NAMES[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            ItemStatus item = items.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return item.getItemName();
                case 1:
                    return item.getLastUpdated();
                case 2:
                    return item.getStatus();
                case 3:
                    return item.getComments();
                default:
                    return null;
            }
        }
    }
}

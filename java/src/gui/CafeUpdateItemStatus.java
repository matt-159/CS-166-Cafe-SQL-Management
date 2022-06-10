package gui;

import data.ItemStatus;
import data.User;
import util.CafeSQLManager;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class CafeUpdateItemStatus extends JPanel {

    private final User user;
    private final List<ItemStatus> items;

    private final JButton back;
    private final JTable table;
    private final JScrollPane tablePane;
    private final TablePopUp popup;

    public CafeUpdateItemStatus(User user) {
        super(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.weightx = 1;
        c.weighty = 1;

        this.user = user;
        this.items = getItemStatusList();

        this.popup = new TablePopUp();

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
        String query = "SELECT * FROM ITEMSTATUS ORDER BY lastupdated";

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
        private final String[] COLUMN_NAMES = { "Order ID", "Item Name", "Last Updated", "Status", "Comments" };

        @Override
        public int getRowCount() {
            return items.size();
        }

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public String getColumnName(int column) {
            return COLUMN_NAMES[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            ItemStatus item = items.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return item.getOrderID();
                case 1:
                    return item.getItemName();
                case 2:
                    return item.getLastUpdated();
                case 3:
                    return item.getStatus();
                case 4:
                    return item.getComments();
            }

            return null;
        }
    }

    private class TablePopUp extends JPopupMenu {
        private JMenuItem setStarted, setFinished, setHasntStarted;

        public TablePopUp() {
            setHasntStarted = new JMenuItem("Set 'Hasn't Started'");
            setHasntStarted.setActionCommand("setHasntStarted");
            setHasntStarted.addActionListener(new TablePopUpActionListener());
            this.add(setHasntStarted);

            setStarted = new JMenuItem("Set 'Started'");
            setStarted.setActionCommand("setStarted");
            setStarted.addActionListener(new TablePopUpActionListener());
            this.add(setStarted);

            setFinished = new JMenuItem("Set 'Finished'");
            setFinished.setActionCommand("setFinished");
            setFinished.addActionListener(new TablePopUpActionListener());
            this.add(setFinished);
        }

        @Override
        public void show(Component invoker, int x, int y) {
            System.out.printf("TablePopUp invoked on %s at (%d, %d)\n", invoker.getClass().getName(), x, y);
            super.show(invoker, x, y);
        }

        private class TablePopUpActionListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                ItemStatus item = items.get(table.getSelectedRow());
                String status = item.getStatus();

                switch (e.getActionCommand()) {
                    case "setHasntStarted":
                        status = "Hasn't started";
                        break;
                    case "setStarted":
                        status = "Started";
                        break;
                    case "setFinished":
                        status = "Finished";
                        break;
                    default:
                        break;
                }

                item.setStatus(status);
                item.setStatus(Timestamp.from(Instant.now().truncatedTo(ChronoUnit.SECONDS)).toString());
                item.updateDB();

                table.invalidate();
                table.repaint();
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
                Point p = table.getMousePosition();
//                p.x += jtable.getX();

                table.changeSelection(table.rowAtPoint(p), 0, false, false);

                popup.show(CafeApplication.getInstance(), p.x, tablePane.getMousePosition().y);
            }
        }
    }
}

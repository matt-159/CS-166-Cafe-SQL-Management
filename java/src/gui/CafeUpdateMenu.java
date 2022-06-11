package gui;

import data.Menu;
import data.MenuItem;
import data.User;
import util.CafeSQLManager;
import util.Queries;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class CafeUpdateMenu extends JPanel {

    private final User user;
    private Map<String, MenuItem> menuItems;

    private final JButton back, addNewItem;
    private final JTable table;
    private final JScrollPane tablePane;
    private TablePopUp popup;

    public CafeUpdateMenu(User user) {
        super(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.weightx = 1;
        c.weighty = 1;

        this.user = user;
        this.menuItems = Menu.getInstance().getMenu();

        this.popup = new TablePopUp();

        this.table = new JTable(new MenuItemTable());
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.table.addMouseListener(new TableMouseListener());
        this.tablePane = new JScrollPane(table);
        this.add(tablePane, c);

        this.addNewItem = new JButton("Add Item");
        this.addNewItem.setActionCommand("addNewItem");
        this.addNewItem.addActionListener(new CafeUpdateMenuActionListener());
        c.gridwidth = 1;
        this.add(addNewItem, c);

        this.back = new JButton("Back");
        this.back.setActionCommand("back");
        this.back.addActionListener(new CafeUpdateMenuActionListener());
        c.gridx = 1;
        this.add(back, c);
    }

    private class CafeUpdateMenuActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "addNewItem":
                    CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_ADD_MENU_ITEM, null);

                    break;
                case "back":
                    CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_HOME, null);

                    break;
                default:
                    break;
            }
        }
    }

    private class MenuItemTable extends AbstractTableModel {
        private final String[] COLUMN_NAMES = { "Item Name", "Type", "Price", "Description", "Image URL" };

        @Override
        public int getRowCount() {
            return menuItems.values().size();
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
            MenuItem item = (MenuItem) menuItems.values().toArray()[rowIndex];

            switch (columnIndex) {
                case 0:
                    return item.getItemName();
                case 1:
                    return item.getType();
                case 2:
                    return String.format("%.2f", item.getPrice());
                case 3:
                    return item.getDescription();
                case 4:
                    return item.getImageURL();
            }

            return null;
        }
    }

    private class TablePopUp extends JPopupMenu {
        private JMenuItem edit, delete;

        public TablePopUp() {
            edit = new JMenuItem("Edit");
            edit.setActionCommand("edit");
            edit.addActionListener(new TablePopUpActionListener());
            this.add(edit);

            delete = new JMenuItem("Delete");
            delete.setActionCommand("delete");
            delete.addActionListener(new TablePopUpActionListener());
            this.add(delete);
        }

        @Override
        public void show(Component invoker, int x, int y) {
            System.out.printf("TablePopUp invoked on %s at (%d, %d)\n", invoker.getClass().getName(), x, y);
            super.show(invoker, x, y);
        }

        private class TablePopUpActionListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                MenuItem item = (MenuItem) menuItems.values().toArray()[table.getSelectedRow()];

                switch (e.getActionCommand()) {
                    case "edit":
                        CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_EDIT_MENU_ITEM, item);

                        break;
                    case "delete":
                        String query = String.format(Queries.CAFE_UPDATE_MENU_DELETE_MENUITEM_QUERY,
                                item.getItemName());
                        CafeSQLManager.executeUpdate(query);

                        break;
                    default:
                        break;
                }

                Menu.getInstance().invalidate();
                menuItems = Menu.getInstance().getMenu();

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

                table.changeSelection(table.rowAtPoint(p), 0, false, false);

                popup.show(CafeApplication.getInstance(), p.x, tablePane.getMousePosition().y);
            }
        }
    }
}

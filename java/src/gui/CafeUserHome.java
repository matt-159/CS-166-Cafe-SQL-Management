package gui;

import data.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CafeUserHome extends JPanel {

    private final User user;

    //Standard User Options
    private final JButton openMenu, openPlaceNewOrder, openOrderHistory, openProfile, logout;

    //Employee + Manager options
    private final JButton openUpdateItemStatus, openRecentOrders;

    //Manager only
    private final JButton openUpdateMenu, openUpdateUser;

    public CafeUserHome(User user) {
        super(new GridLayout(0, 1));

        this.user = user;

        openMenu = new JButton("View Menu");
        openMenu.setActionCommand("openMenu");
        openMenu.addActionListener(new CafeUserHomeActionListener());
        this.add(openMenu);

        openPlaceNewOrder = new JButton("Place New Order");
        openPlaceNewOrder.setActionCommand("openPlaceNewOrder");
        openPlaceNewOrder.addActionListener(new CafeUserHomeActionListener());
        this.add(openPlaceNewOrder);

        openOrderHistory = new JButton("View Order History");
        openOrderHistory.setActionCommand("openOrderHistory");
        openOrderHistory.addActionListener(new CafeUserHomeActionListener());
        this.add(openOrderHistory);

        openProfile = new JButton("View Profile");
        openProfile.setActionCommand("openProfile");
        openProfile.addActionListener(new CafeUserHomeActionListener());
        this.add(openProfile);

        openRecentOrders = new JButton("Open Recent Orders");
        openRecentOrders.setActionCommand("openRecentOrders");
        openRecentOrders.addActionListener(new CafeUserHomeActionListener());
        this.add(openRecentOrders);

        openUpdateItemStatus = new JButton("Update Item Status");
        openUpdateItemStatus.setActionCommand("openUpdateItemStatus");
        openUpdateItemStatus.addActionListener(new CafeUserHomeActionListener());
        this.add(openUpdateItemStatus);

        openUpdateMenu = new JButton("Update Menu");
        openUpdateMenu.setActionCommand("openUpdateMenu");
        openUpdateMenu.addActionListener(new CafeUserHomeActionListener());
        this.add(openUpdateMenu);

        openUpdateUser = new JButton("Change User Type");
        openUpdateUser.setActionCommand("openUpdateUser");
        openUpdateUser.addActionListener(new CafeUserHomeActionListener());
        this.add(openUpdateUser);

        logout = new JButton("Log Out");
        logout.setActionCommand("logout");
        logout.addActionListener(new CafeUserHomeActionListener());
        this.add(logout);

        this.hideOptions();
    }

    private void hideOptions() {
        switch (this.user.getUserType()) {
            case Customer:
                this.openRecentOrders.setVisible(false);
                this.openUpdateItemStatus.setVisible(false);
            case Employee:
                this.openUpdateUser.setVisible(false);
                this.openUpdateMenu.setVisible(false);
            case Manager:
                break;
        }
    }

    private class CafeUserHomeActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "openMenu":
                    CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_VIEW_MENU, null);
                    break;
                case "openPlaceNewOrder":
                    CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_PLACE_ORDER, null);
                    break;
                case "openOrderHistory":
                    CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_VIEW_ORDER_HISTORY, null);
                    break;
                case "openProfile":
                    CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_VIEW_PROFILE, null);
                    break;
                case "openRecentOrders":
                    CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_VIEW_RECENT_ORDERS, null);
                    break;
                case "openUpdateItemStatus":
                    CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_UPDATE_ITEM_STATUS, null);
                    break;
                case "openUpdateMenu":
                    CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_UPDATE_MENU, null);
                    break;
                case "openUpdateUser":
                    CafeApplication.getInstance().run(user, CafeApplication.AppStates.USER_UPDATE_USER, null);
                    break;
                case "logout":
                    CafeApplication.getInstance().run(null, CafeApplication.AppStates.CHOOSE_LOGIN_OR_CREATE_USER, null);
                    break;
            }
        }
    }
}

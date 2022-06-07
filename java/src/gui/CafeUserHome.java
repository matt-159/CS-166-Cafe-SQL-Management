package gui;

import data.User;

import javax.swing.*;

public class CafeUserHome extends JPanel {

    private final User user;

    //Standard User Options
    private final JButton openMenu, openPlaceNewOrder, openOrderStatus, openOrderHistory, openProfile;

    //Employee + Manager options
    private JButton openUpdateItemStatus;

    //Manager only
    private JButton openUpdateMenu, openUpdateUser;

    public CafeUserHome(User user) {
        super();

        this.user = user;

        openMenu = new JButton("View Menu");
        openMenu.setActionCommand("openMenu");
        this.add(openMenu);

        openPlaceNewOrder = new JButton("Place New Order");
        openPlaceNewOrder.setActionCommand("openPlaceNewOrder");
        this.add(openPlaceNewOrder);

        openOrderStatus = new JButton("View Order Status");
        openOrderStatus.setActionCommand("openOrderStatus");
        this.add(openOrderStatus);

        openOrderHistory = new JButton("View Order History");
        openOrderHistory.setActionCommand("openOrderHistory");
        this.add(openOrderHistory);

        openProfile = new JButton("View Profile");
        openProfile.setActionCommand("openProfile");
        this.add(openProfile);

        openUpdateItemStatus = new JButton("Update Item Status");
        openUpdateItemStatus.setActionCommand("openUpdateItemStatus");
        this.add(openUpdateItemStatus);

        openUpdateMenu = new JButton("Update Menu");
        openUpdateMenu.setActionCommand("openUpdateMenu");
        this.add(openUpdateMenu);

        openUpdateUser = new JButton("Change User Type");
        openUpdateUser.setActionCommand("openUpdateUser");
        this.add(openUpdateUser);
    }
}

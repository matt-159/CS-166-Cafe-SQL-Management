package data;

import util.CafeSQLManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {

    private final String login;
    private String password, phoneNumber, userType;
    private List<String> favItems;

    private List<Order> history;

    public User(String login,
                String password,
                String phoneNumber,
                String favItems,
                String type) {
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.favItems = new ArrayList<>();
        Collections.addAll(this.favItems, favItems.split(","));
        this.userType = type;

        history = fetchOrderHistory();
    }

    public User(List<String> args) {
        this.login = args.get(0);
        this.phoneNumber = args.get(1);
        this.password = args.get(2);
        this.favItems = new ArrayList<>();
        Collections.addAll(this.favItems, args.get(3).split(","));
        this.userType = args.get(4);

        history = fetchOrderHistory();
    }

    private ArrayList<Order> fetchOrderHistory() {
        String rawQuery =
        "SELECT * " +
        "FROM ITEMSTATUS WHERE ITEMSTATUS.orderid IN ( " +
        "SELECT orderid " +
        "FROM " +
        "   (SELECT USERS.login, " +
        "           ORDERS.orderid " +
        "   FROM ORDERS " +
        "   LEFT JOIN USERS ON ORDERS.login=USERS.login) AS ORDERHISTORY WHERE ORDERHISTORY.login='%s')";
        String query = String.format(rawQuery, this.login);

        ArrayList<Order> orderHistory = new ArrayList<>();
        CafeSQLManager.executeQuery(query).forEach(orderData -> orderHistory.add(new Order(this, Integer.parseInt(orderData.get(0)))));

        return orderHistory;
    }

    public boolean updateDB() {
        String query = String.format("UPDATE USERS SET password='%s', phonenum='%s', favitems='%s', type='%s' WHERE login='%s'",
                this.password,
                this.phoneNumber,
                String.join(",", this.favItems),
                this.userType.toString(),
                this.login);

        return CafeSQLManager.executeUpdate(query);
    }

    private void refreshData() {
        String query = String.format("SELECT * FROM USERS WHERE login='%s'", this.login);
        List<String> data = CafeSQLManager.executeQuery(query).get(0);

        this.password = data.get(1);
    }

    public boolean placeOrder() {
        return false;
    }

    public boolean updateOrder() {
        return false;
    }

    public boolean updateItemStatus() {
        return false;
    }

    public boolean updateUser() {
        return false;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return this.getPhoneNumber();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getFavItems() {
        return this.getFavItems();
    }

    public boolean addFavItem(String itemname) {
        return this.favItems.add(itemname);
    }

    public boolean removeFavItem(String itemname) {
        return this.favItems.remove(itemname);
    }
}

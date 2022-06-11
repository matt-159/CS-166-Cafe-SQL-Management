package data;

import util.CafeSQLManager;
import util.Queries;

import java.util.*;
import java.util.stream.Collectors;

public class User {

    public enum UserType {
        Customer, Employee, Manager
    }

    private final String login;
    private String password, phoneNumber;
    private UserType userType;
    private List<String> favItems;

    private List<Order> history;

    public User(String login,
                String password,
                String phoneNumber,
                List<String> favItems,
                UserType type) {
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.favItems = favItems;
        this.userType = type;

        history = fetchOrderHistory();
    }

    public User(List<String> data) {
        this.login = data.get(0);
        refreshData(data);
    }

    public void refreshData() {
        String query = String.format(Queries.USER_REFRESH_DATA_QUERY,
                this.login);

        refreshData(CafeSQLManager.executeQuery(query).get(0));
    }

    private void refreshData(List<String> data) {
        this.phoneNumber = data.get(1);
        this.password = data.get(2);
        this.favItems = new ArrayList<>();
        Collections.addAll(this.favItems, data.get(3).split(","));
        this.userType = UserType.valueOf(data.get(4));

        history = fetchOrderHistory();
    }

    private List<Order> fetchOrderHistory() {
        String query = String.format(Queries.USER_FETCH_ORDER_HISTORY,
                this.login);

        Set<Integer> orderIDSet = new HashSet<>();
        CafeSQLManager.executeQuery(query)
                .forEach(orderData -> orderIDSet.add(Integer.parseInt(orderData.get(0))));

        List<Order> orderHistory = new ArrayList<>();
        orderIDSet.forEach(orderID -> orderHistory.add(new Order(this, orderID)));

        return orderHistory;
    }

    public List<Order> fetchRecentOrders() {
        return fetchOrderHistory()
                .stream()
                .limit(5)
                .collect(Collectors.toList());
    }

    public UserType getUserType() {
        return userType;
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
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getFavItems() {
        return this.favItems;
    }

    public void setFavItems(List<String> favItems) {
        this.favItems = favItems;
    }

    public List<Order> getOrderHistory() {
        return this.history;
    }

    public static User login(String username, String password) {

        String query = String.format(Queries.USER_LOGIN_QUERY,
                username,
                password);

        List<List<String>> results = CafeSQLManager.executeQuery(query);

        if (results != null) {
            CafeSQLManager.printResultSetList(results);
        }

        return (results != null && results.size() > 0) ? new User(results.get(0)) : null;
    }

    public static boolean createUser(User user) {
        return createUser(  user.login,
                            user.password,
                            user.phoneNumber,
                            String.join(",", user.favItems),
                            user.userType.toString()    );
    }

    public static boolean createUser(String login, String password, String phone, String favItems, String type) {
        String query = String.format(Queries.USER_CREATE_USER_QUERY,
                phone,
                login,
                password,
                favItems,
                type);

        return CafeSQLManager.executeUpdate(query);
    }

    public boolean updateDB() {
        String query = String.format(Queries.USER_UPDATE_DB_QUERY,
                this.password,
                this.phoneNumber,
                String.join(",", this.favItems),
                this.userType.toString(),
                this.login);

        return CafeSQLManager.executeUpdate(query);
    }
}

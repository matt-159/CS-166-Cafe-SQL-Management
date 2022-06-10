package data;

import util.CafeSQLManager;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private final User user;
    private final int orderID;
    private Boolean isPaid;
    private String timestampReceived;
    private double total;

    private final List<ItemStatus> items;

    public Order(User user,
                 int orderId,
                 boolean isPaid,
                 String timestampReceived,
                 double total) {
        this.user = user;
        this.orderID = orderId;
        this.isPaid = isPaid;
        this.timestampReceived = timestampReceived;
        this.total = total;

        this.items = getItemStatusInfo();
    }

    public Order(User user,
                 int orderID) {
        this.user = user;
        this.orderID = orderID;

        getOrderInfo();
        items = getItemStatusInfo();
    }

    private void getOrderInfo() {
        String rawQuery =
        "SELECT * " +
        "FROM ORDERS " +
        "WHERE login='%s' AND orderid='%d'";
        String query = String.format(rawQuery, this.user.getLogin(), this.orderID);

        List<String> orderInfo = CafeSQLManager.executeQuery(query).get(0);

        this.isPaid = orderInfo.get(2).trim().equals("t");
        this.timestampReceived = orderInfo.get(3);
        this.total = Double.parseDouble(orderInfo.get(4));
    }

    private List<ItemStatus> getItemStatusInfo() {
        String rawQuery =
        "SELECT * " +
        "FROM ITEMSTATUS " +
        "WHERE ITEMSTATUS.orderid IN ( " +
        "   SELECT orderid " +
        "   FROM " +
        "       (SELECT USERS.login, " +
        "               ORDERS.orderid " +
        "       FROM ORDERS " +
        "       LEFT JOIN USERS ON ORDERS.login=USERS.login) AS ORDERHISTORY " +
        "           WHERE ORDERHISTORY.login='%s' " +
        "               AND ITEMSTATUS.orderid='%d')";

        String query = String.format(rawQuery, user.getLogin(), this.orderID);

        List<ItemStatus> itemInfo = new ArrayList<>();
        CafeSQLManager.executeQuery(query).forEach(item -> itemInfo.add(new ItemStatus(item)));

        return itemInfo;
    }

    public int getOrderID() {
        return orderID;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public String getTimestampReceived() {
        return timestampReceived;
    }

    public double getTotal() {
        return total;
    }

    public List<ItemStatus> getItems() {
        return items;
    }

    public boolean updateDB() {
        String query = String.format("UPDATE ORDERS SET login='%s', paid='%s', timestampRecieved='%s', total='%.2f' WHERE orderid='%d'",
            this.user.getLogin(),
            this.isPaid,
            this.timestampReceived,
            this.total,
            this.orderID);

        return CafeSQLManager.executeUpdate(query);
    }

    public String toString() {
        return String.format("%10s%10s%40s%15.2f",
                this.orderID,
                this.isPaid,
                this.timestampReceived,
                this.total);
    }

    public static int placeNewOrder(String login, double total) {
        List<String> info = CafeSQLManager.executeQuery("SELECT COUNT(orderid) + 1 FROM ORDERS").get(0);
        int orderid = Integer.parseInt(info.get(0));

        String rawQuery = "INSERT INTO ORDERS (orderid, login, paid, timestampRecieved, total) VALUES (%d, '%s', '%s', '%s', %.2f)";
        String query = String.format(rawQuery,
                orderid,
                login,
                "f",
                Timestamp.from(Instant.now().truncatedTo(ChronoUnit.SECONDS)),
                total);

        CafeSQLManager.executeUpdate(query);

        return orderid;
    }
}

package data;

import util.CafeSQLManager;
import util.Queries;

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

        if (this.user != null) {
            getOrderInfo();
            items = getItemStatusInfo();
        } else {
            items = null;
        }
    }

    private void getOrderInfo() {
        String query = String.format(Queries.ORDER_GET_ORDER_INFO_QUERY,
                this.user.getLogin(),
                this.orderID);

        List<String> orderInfo = CafeSQLManager.executeQuery(query).get(0);

        this.isPaid = orderInfo.get(2).trim().equals("t");
        this.timestampReceived = orderInfo.get(3);
        this.total = Double.parseDouble(orderInfo.get(4));
    }

    private List<ItemStatus> getItemStatusInfo() {
        String query = String.format(Queries.ORDER_GET_ITEMSTATUS_INFO_QUERY,
                user.getLogin(),
                this.orderID);

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

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public boolean updateDB() {
        String query = String.format(Queries.ORDER_UPDATEDB_QUERY,
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
        List<String> info = CafeSQLManager.executeQuery(Queries.ORDER_PLACE_NEW_ORDER_GET_NUM_ORDERS_QUERY).get(0);
        int orderid = Integer.parseInt(info.get(0));

        String query = String.format(Queries.ORDER_PLACE_NEW_ORDER_QUERY,
                orderid,
                login,
                "f",
                Timestamp.from(Instant.now().truncatedTo(ChronoUnit.SECONDS)),
                total);

        CafeSQLManager.executeUpdate(query);

        return orderid;
    }

    public static List<Order> getRecentOrders() {
        List<Order> orders = new ArrayList<>();
        CafeSQLManager.executeQuery(Queries.ORDER_GET_RECENT_ORDERS_QUERY)
                .forEach(data -> orders.add(new Order(null, Integer.parseInt(data.get(0)))));

        return orders;
    }
}

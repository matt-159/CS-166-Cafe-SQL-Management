package data;

import util.CafeSQLManager;

import java.util.List;

import static util.Queries.ITEM_STATUS_ADD_NEW_ITEM_STATUS_QUERY;
import static util.Queries.ITEM_STATUS_UPDATE_DB_QUERY;

public class ItemStatus {

    private final int orderID;
    private final String itemName;
    private String lastUpdated;
    private String status;
    private String comments;

    public ItemStatus(int orderID,
                      String itemName,
                      String lastUpdated,
                      String status,
                      String comments) {
        this.orderID = orderID;
        this.itemName = itemName;
        this.lastUpdated = lastUpdated;
        this.status = status;
        this.comments = comments;
    }

    public ItemStatus(List<String> args) {
        this.orderID = Integer.parseInt(args.get(0));
        this.itemName = args.get(1);
        this.lastUpdated = args.get(2);
        this.status = args.get(3);
        this.comments = args.get(4);
    }

    public int getOrderID() {
        return orderID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public String getStatus() {
        return status;
    }

    public String getComments() {
        return comments;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean updateDB() {
        String query = String.format(ITEM_STATUS_UPDATE_DB_QUERY,
                this.lastUpdated,
                this.status,
                this.comments,
                this.itemName,
                this.orderID);

        return CafeSQLManager.executeUpdate(query);
    }

    public static boolean addNewItemStatus(ItemStatus itemStatus) {
        String query = String.format(ITEM_STATUS_ADD_NEW_ITEM_STATUS_QUERY,
                itemStatus.orderID,
                itemStatus.itemName,
                itemStatus.lastUpdated,
                itemStatus.status,
                itemStatus.comments);

        return CafeSQLManager.executeUpdate(query);
    }
}
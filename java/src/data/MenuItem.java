package data;

import util.CafeSQLManager;
import util.Queries;

import java.util.List;

public class MenuItem {

    private final String itemName;
    private String type, description, imageURL;
    private double price;

    public MenuItem(String itemName,
                    String type,
                    double price,
                    String description,
                    String imageURL) {
        this.itemName = itemName;
        this.type = type;
        this.price = price;
        this.description = description;
        this.imageURL = imageURL;
    }

    public MenuItem(List<String> args) {
        this.itemName = args.get(0);
        this.type = args.get(1);
        this.price = Double.parseDouble(args.get(2));
        this.description = args.get(3);
        this.imageURL = args.get(4);
    }

    public String getItemName() {
        return itemName;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean updateDB() {
        String query = String.format(Queries.MENUITEM_UPDATEDB_QUERY,
                this.type,
                this.price,
                this.description,
                this.imageURL,
                this.itemName);

        return CafeSQLManager.executeUpdate(query);
    }
}

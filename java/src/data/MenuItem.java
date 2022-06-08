package data;

import util.CafeSQLManager;

import java.util.List;

public class MenuItem {

    private final String itemName;
    private String type, description, imageURL;
    private double price;

    public MenuItem(String itemName,
                    String type,
                    String description,
                    String imageURL,
                    double price) {
        this.itemName = itemName;
        this.type = type;
        this.description = description;
        this.imageURL = imageURL;
        this.price = price;
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

    public boolean updateDB() {
        String rawQuery = "UPDATE MENU SET type='%s', price='%.2f', description='%s', imageURL='%s' WHERE itemName='%s'";
        String query = String.format(rawQuery,
                this.type,
                this.price,
                this.description,
                this.imageURL,
                this.itemName);

        return CafeSQLManager.executeUpdate(query);
    }
}

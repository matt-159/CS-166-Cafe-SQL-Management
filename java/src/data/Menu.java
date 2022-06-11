package data;

import util.CafeSQLManager;
import util.Queries;

import java.util.*;

public class Menu {

    private static Menu instance = null;

    private ArrayList<MenuItem> menuItems;
    private Set<String> categories;
    private boolean isDirty;

    public static Menu getInstance() {
        if (instance == null || instance.isDirty) {
            instance = new Menu(CafeSQLManager.executeQuery(Queries.MENU_GET_INSTANCE_QUERY));
        }

        return instance;
    }

    private Menu(List<List<String>> menuItemList) {
        updateMenu(menuItemList);
        this.isDirty = false;
    }

    public Map<String, MenuItem> getMenu() {
        Map<String, MenuItem> menu = new HashMap<>();

        this.menuItems.forEach(menuItem -> menu.put(menuItem.getItemName(), menuItem));

        return menu;
    }

    public Map<String, List<MenuItem>> getCategorizedMenu() {
        Map<String, List<MenuItem>> menu = new HashMap<>();

        categories.forEach(category -> menu.put(category, new ArrayList<>()));

        this.menuItems.forEach(menuItem -> menu.get(menuItem.getType()).add(menuItem));

        return menu;
    }

    public void updateMenu(List<List<String>> menuItemList) {
        this.categories = new HashSet<>();
        this.menuItems = getMenuItems(menuItemList);

        this.menuItems.forEach(menuItem -> categories.add(menuItem.getType()));
    }

    private ArrayList<MenuItem> getMenuItems(List<List<String>> menuItemList) {
        ArrayList<MenuItem> menuItems = new ArrayList<>();

        menuItemList.forEach(menuItem -> menuItems.add(new MenuItem(menuItem)));

        return menuItems;
    }

    public boolean updateDB() {
        List<Boolean> status = new ArrayList<>(menuItems.size());

        menuItems.forEach(menuItem -> status.add(menuItem.updateDB()));

        return status.stream().allMatch(b -> b);
    }

    public static boolean addNewMenuItem(String itemName,
                                         String type,
                                         double price,
                                         String description,
                                         String imageURL) {
        String query = String.format(Queries.MENU_ADD_NEW_MENU_ITEM_QUERY,
                itemName,
                type,
                price,
                description,
                imageURL);

        instance.menuItems.add(new MenuItem(itemName, type, price, description, imageURL));

        return CafeSQLManager.executeUpdate(query);
    }

    public void invalidate() {
        this.isDirty = true;
    }
}

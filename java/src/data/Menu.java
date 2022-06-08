package data;

import util.CafeSQLManager;

import java.util.*;

public class Menu {

    private static Menu instance = null;

    private ArrayList<MenuItem> menuItems;
    private Set<String> categories;

    public static Menu getInstance() {
        if (instance == null) {
            String query = "SELECT * FROM MENU";

            instance = new Menu(CafeSQLManager.executeQuery(query));
        }

        return instance;
    }

    private Menu(List<List<String>> menuItemList) {
        updateMenu(menuItemList);
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
        this.menuItems = menuItemListToMap(menuItemList);

        this.menuItems.forEach(menuItem -> categories.add(menuItem.getType()));
    }

    private ArrayList<MenuItem> menuItemListToMap(List<List<String>> menuItemList) {
        ArrayList<MenuItem> menuItems = new ArrayList<>();

        menuItemList.forEach(menuItem -> {
            menuItems.add(new MenuItem(menuItem));
        });

        return menuItems;
    }

    public boolean updateDB() {
        List<Boolean> status = new ArrayList<>(menuItems.size());

        menuItems.forEach(menuItem -> status.add(menuItem.updateDB()));

        return status.stream().allMatch(b -> b);
    }
}

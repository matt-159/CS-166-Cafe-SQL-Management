package data;

import util.CafeSQLManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {

    private static Menu instance = null;
    private Map<String, MenuItem> menuItems;

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
        return this.menuItems;
    }

    public void updateMenu(List<List<String>> menuItemList) {
        menuItems = menuItemListToMap(menuItemList);
    }

    public boolean updateDB() {
        List<Boolean> status = new ArrayList<>(menuItems.size());

        menuItems.keySet().forEach(key -> status.add(menuItems.get(key).updateDB()));

        return status.stream().allMatch(b -> b);
    }

    private static Map<String, MenuItem> menuItemListToMap(List<List<String>> menuItemList) {
        Map<String, MenuItem> menuItemMap = new HashMap<>();

        menuItemList.forEach(menuItem -> {
            menuItemMap.put(menuItem.get(0), new MenuItem(menuItem));
        });

        return menuItemMap;
    }
}

package util;

public final class Queries {

    private Queries() {}

    public static final String ITEM_STATUS_UPDATE_DB_QUERY =
        "UPDATE ITEMSTATUS " +
        "SET lastUpdated='%s', " +
        "    status=E'%s', " +
        "    comments='%s' " +
        "WHERE (itemName='%s' " +
        "        AND orderId=%d)";

    public static final String ITEM_STATUS_ADD_NEW_ITEM_STATUS_QUERY =
        "INSERT INTO ITEMSTATUS (orderid, itemName, lastUpdated, status, comments) " +
        "VALUES (%d, '%s', '%s', '%s', '%s')";

    public static final String MENU_GET_INSTANCE_QUERY =
        "SELECT * FROM MENU";

    public static final String MENU_ADD_NEW_MENU_ITEM_QUERY =
        "INSERT INTO MENU (itemName, type, price, description, imageURL) " +
        "VALUES ('%s', '%s', %.2f, '%s', '%s')";

    public static final String MENUITEM_UPDATEDB_QUERY =
        "UPDATE MENU " +
        "SET TYPE='%s', " +
        "   price='%.2f', " +
        "   description='%s', " +
        "   imageURL='%s' " +
        "WHERE itemName='%s'";

    public static final String ORDER_GET_ORDER_INFO_QUERY =
        "SELECT * " +
        "FROM ORDERS " +
        "WHERE login='%s' " +
        "   AND orderid='%d'";

    public static final String ORDER_GET_ITEMSTATUS_INFO_QUERY =
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

    public static final String ORDER_UPDATEDB_QUERY =
        "UPDATE ORDERS " +
        "SET login='%s', " +
        "    paid='%s', " +
        "    timestampRecieved='%s', " +
        "    total='%.2f' " +
        "WHERE orderid='%d'";

    public static final String ORDER_PLACE_NEW_ORDER_GET_NUM_ORDERS_QUERY =
        "SELECT COUNT(orderid) + 1 " +
        "FROM ORDERS";

    public static final String ORDER_PLACE_NEW_ORDER_QUERY =
        "INSERT INTO ORDERS (orderid, login, paid, timestampRecieved, total) " +
        "VALUES (%d, '%s', '%s', '%s', %.2f)";

    public static final String ORDER_GET_RECENT_ORDERS_QUERY =
        "SELECT * " +
        "FROM ORDERS " +
        "WHERE timestampRecieved >= CURRENT_DATE - 1";

    public static final String USER_REFRESH_DATA_QUERY =
        "SELECT * " +
        "FROM USERS " +
        "WHERE login='%s'";

    public static final String USER_FETCH_ORDER_HISTORY =
        "SELECT * " +
        "FROM ITEMSTATUS WHERE ITEMSTATUS.orderid IN ( " +
        "SELECT orderid " +
        "FROM " +
        "   (SELECT USERS.login, " +
        "           ORDERS.orderid " +
        "   FROM ORDERS " +
        "   LEFT JOIN USERS ON ORDERS.login=USERS.login) AS ORDERHISTORY WHERE ORDERHISTORY.login='%s') " +
        "ORDER BY lastUpdated";

    public static final String USER_LOGIN_QUERY =
        "SELECT * " +
        "FROM USERS " +
        "WHERE login = '%s' " +
        "   AND password = '%s'";

    public static final String USER_CREATE_USER_QUERY =
        "INSERT INTO USERS (phoneNum, login, password, favItems, type) " +
        "VALUES ('%s','%s','%s','%s','%s')";

    public static final String USER_UPDATE_DB_QUERY =
        "UPDATE USERS " +
        "SET password='%s', " +
        "    phonenum='%s', " +
        "    favitems='%s', " +
        "    TYPE='%s' " +
        "WHERE login='%s'";

    public static final String CAFE_CREATE_USER_CHECK_LOGIN_QUERY =
        "SELECT * " +
        "FROM USERS " +
        "WHERE login='%s'";

    public static final String CAFE_CREATE_USER_CHECK_PHONE_NUM_QUERY =
        "SELECT * " +
        "FROM USERS " +
        "WHERE phoneNum='%s'";

    public static final String CAFE_UPDATE_ITEMSTATUS_GET_ITEMSTATUS_LIST_QUERY =
        "SELECT * " +
        "FROM USERS " +
        "WHERE phoneNum='%s'";

    public static final String CAFE_UPDATE_MENU_DELETE_MENUITEM_QUERY =
        "DELETE " +
        "FROM MENU " +
        "WHERE itemName='%s'";

    public static final String CAFE_UPDATE_USER_GET_USER_LIST_QUERY =
        "SELECT login, " +
        "       type " +
        "FROM USERS " +
        "WHERE login!='%s' " +
        "ORDER BY login";

    public static final String CAFE_UPDATE_USER_UPDATE_USER_QUERY =
        "UPDATE USERS " +
        "SET TYPE='%s' " +
        "WHERE login='%s'";
}

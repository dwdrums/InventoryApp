package eu.schallmeiner.inventoryapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * API Contract for Inventory App
 */
public final class InventoryContract {

    // Empty Constructor
    private InventoryContract(){}

    public static final String CONTENT_AUTHORITY = "eu.schallmeiner.inventoryapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_INVENTORY = "inventory";

    /**
     * Inner class that defines constant values for the inventory database table.
     * Each entry represents a single product.
     */
    public static final class InventoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        // Name of database table
        public final static String TABLE_NAME = "products";

        /**
         * Unique ID for each entry
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of product
         * Type: TEXT
         */
        public final static String COLUMN_INV_PRODUCT_NAME = "product_name";

        /**
         * Price of product
         * Type: REAL
         */
        public final static String COLUMN_INV_PRICE = "price";

        /**
         * Quantity of product
         * Type: INTEGER
         */
        public final static String COLUMN_INV_QUANTITY = "quantity";

        /**
         * Name of supplier
         * Type: TEXT
         */
        public final static String COLUMN_INV_SUPPLIER_NAME = "supplier_name";

        /**
         * Phone number of supplier
         * Type: TEXT
         */
        public final static String COLUMN_INV_SUPPLIER_PHONE_NUMBER = "supplier_phone_number";
    }
}

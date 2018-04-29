package eu.schallmeiner.inventoryapp.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import eu.schallmeiner.inventoryapp.data.InventoryContract.InventoryEntry;

/**
 * Database helper for Inventory App.
 */
public class InventoryDbHelper extends SQLiteOpenHelper {

    // Name of the database file
    private static final String DATABASE_NAME = "inventory.db";

    // Version of database
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of InventoryDbHelper
     * @param context of the app
     */
    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create SQL String to create the database
        String SQL_CREATE_PRODUCTS_TABLE = "CREATE TABLE " + InventoryEntry.TABLE_NAME
                + " ("
                + InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InventoryEntry.COLUMN_INV_PRODUCT_NAME + " TEXT NOT NULL, "
                + InventoryEntry.COLUMN_INV_PRICE + " REAL NOT NULL, "
                + InventoryEntry.COLUMN_INV_QUANTITY + " INTEGER, "
                + InventoryEntry.COLUMN_INV_SUPPLIER_NAME + " TEXT NOT NULL, "
                + InventoryEntry.COLUMN_INV_SUPPLIER_PHONE_NUMBER + " TEXT NOT NULL"
                + ");";

        // Execute SQL statement to create database
        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

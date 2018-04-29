package eu.schallmeiner.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.content.CursorLoader;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import eu.schallmeiner.inventoryapp.data.InventoryContract;
import eu.schallmeiner.inventoryapp.data.InventoryContract.InventoryEntry;
import eu.schallmeiner.inventoryapp.data.InventoryDbHelper;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private static final int INVENTORY_LOADER = 0;

    //Database helper that provides us access to database
    private InventoryDbHelper mDbHelper;

    InventoryCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.main_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editorIntent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(editorIntent);
            }
        });

        ListView list = findViewById(R.id.list);

        View emptyView = findViewById(R.id.empty_view);
        list.setEmptyView(emptyView);

        mCursorAdapter = new InventoryCursorAdapter(this, null);
        list.setAdapter(mCursorAdapter);

        getLoaderManager().initLoader(INVENTORY_LOADER, null, this);
    }



    /**
     * Helper method to request data from database an log existing data. For debugging purposes only.
     */
    private void requestProducts(){

        // Gets database in read mode
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection to specify the actually use columns from the database
        String[] projection = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_INV_PRODUCT_NAME,
                InventoryEntry.COLUMN_INV_PRICE,
                InventoryEntry.COLUMN_INV_QUANTITY,
                InventoryEntry.COLUMN_INV_SUPPLIER_NAME,
                InventoryEntry.COLUMN_INV_SUPPLIER_PHONE_NUMBER
        };

        // Perform query
        Cursor cursor = db.query(InventoryEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        try{

            // Figure out the index of each column
            int idColIndex = cursor.getColumnIndex(InventoryEntry._ID);
            int nameColIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INV_PRODUCT_NAME);
            int priceColIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INV_PRICE);
            int quantityColIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INV_QUANTITY);
            int supplierNameColIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INV_SUPPLIER_NAME);
            int supplierPhoneNumberColIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INV_SUPPLIER_PHONE_NUMBER);

            // Iterate through every row, excerpt data and log it
            while(cursor.moveToNext()){
                int cId = cursor.getInt(idColIndex);
                String cName = cursor.getString(nameColIndex);
                Double cPrice = cursor.getDouble(priceColIndex);
                int cQuantity = cursor.getInt(quantityColIndex);
                String cSupplierName = cursor.getString(supplierNameColIndex);
                String cSupplierPhoneNumber = cursor.getString(supplierPhoneNumberColIndex);
                Log.v("Entry: ",
                        cId + " "
                                + cName + " "
                                + cPrice + " "
                                + cQuantity + " "
                                + cSupplierName + " "
                                + cSupplierPhoneNumber
                );
            }
        } finally {
            // Close cursor
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Helper method to insert hardcoded pet data into the database. For debugging purposes only.
     */
    private void insertProduct() {
        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_INV_PRODUCT_NAME, "Hammer");
        values.put(InventoryEntry.COLUMN_INV_PRICE, 100.90);
        values.put(InventoryEntry.COLUMN_INV_QUANTITY, 20);
        values.put(InventoryEntry.COLUMN_INV_SUPPLIER_NAME, "Daniel");
        values.put(InventoryEntry.COLUMN_INV_SUPPLIER_PHONE_NUMBER, "066438");


        Uri newUri = getContentResolver().insert(InventoryEntry.CONTENT_URI, values);
    }

    /**
     * Helper method to delete all products in the database.
     */
    private void deleteAllProducts() {
        int rowsDeleted = getContentResolver().delete(InventoryEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from inventory database");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_insert_dummy:
                insertProduct();
                return true;
            case R.id.action_delete_all:
                deleteAllProducts();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                InventoryContract.InventoryEntry._ID,
                InventoryContract.InventoryEntry.COLUMN_INV_PRODUCT_NAME,
                InventoryContract.InventoryEntry.COLUMN_INV_PRICE,
                InventoryContract.InventoryEntry.COLUMN_INV_QUANTITY,
                InventoryContract.InventoryEntry.COLUMN_INV_SUPPLIER_NAME,
                InventoryContract.InventoryEntry.COLUMN_INV_SUPPLIER_PHONE_NUMBER};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                InventoryEntry.CONTENT_URI,   // Query the content URI for the current product
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}

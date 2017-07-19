package akrger.grocerylist;

import android.provider.BaseColumns;

final class GroceryListContract {
    private GroceryListContract() {
    }

    static class GroceryList implements BaseColumns {
        static final String TABLE_NAME = "grocery_list";
        static final String COLUMN_NAME_TITLE = "title";
    }

    static class GroceryListEntry implements BaseColumns {
        static final String TABLE_NAME = "grocery_list_entry";
        static final String COLUMN_NAME_DESCRIPTION = "description";
        static final String COLUMN_NAME_CATEGORY = "category";
        static final String COLUMN_NAME_QUANTITY = "quantity";
        static final String COLUMN_NAME_GROCERY_LIST_ENTRY_ID = "grocery_list_entry_id";

    }

    private static final String SQL_CREATE_ENTRIES_GROCERY_LIST =
            "CREATE TABLE " + GroceryList.TABLE_NAME + " (" +
                    GroceryList._ID + " INTEGER PRIMARY KEY," +
                    GroceryList.COLUMN_NAME_TITLE + " TEXT)";

    private static final String SQL_CREATE_ENTRIES_GROCERY_LIST_ENTRY =
            "CREATE TABLE " + GroceryListEntry.TABLE_NAME + " (" +
                    GroceryListEntry._ID + " INTEGER PRIMARY KEY," +
                    GroceryListEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    GroceryListEntry.COLUMN_NAME_CATEGORY + " TEXT," +
                    GroceryListEntry.COLUMN_NAME_QUANTITY + " INTEGER," +
                    GroceryListEntry.COLUMN_NAME_GROCERY_LIST_ENTRY_ID + " INTEGER," +
                    "FOREIGN KEY (" +
                    GroceryListEntry.COLUMN_NAME_GROCERY_LIST_ENTRY_ID + ") " + " REFERENCES " +
                    GroceryListEntry.TABLE_NAME + "(" + GroceryList._ID + "))";

    private static final String SQL_DELETE_ENTRIES_GROCERY_LIST =
            "DROP TABLE IF EXISTS " + GroceryList.TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES_GROCERY_LIST_ENTRY =
            "DROP TABLE IF EXISTS " + GroceryListEntry.TABLE_NAME;


    static String getSqlCreateEntriesGroceryList() {
        return SQL_CREATE_ENTRIES_GROCERY_LIST;
    }

    static String getSqlCreateEntriesGroceryListEntry() {
        return SQL_CREATE_ENTRIES_GROCERY_LIST_ENTRY;
    }

    static String getSqlDeleteEntriesGroceryList() {
        return SQL_DELETE_ENTRIES_GROCERY_LIST;
    }

    static String getSqlDeleteEntriesGroceryListEntry() {
        return SQL_DELETE_ENTRIES_GROCERY_LIST_ENTRY;
    }
}

package akrger.grocerylist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

class GroceryListManager {
    private SQLiteDatabase _db;

    private HashMap<GroceryList, GroceryArrayList<GroceryListEntry>> _lists;

    GroceryListManager(SQLiteDatabase db) {
        _db = db;
        _lists = new HashMap<>();

        // read database int memory
        String[] projection = {
                GroceryListContract.GroceryList._ID,
                GroceryListContract.GroceryList.COLUMN_NAME_TITLE};

        Cursor c = _db.query(GroceryListContract.GroceryList.TABLE_NAME, projection, null, null, null, null, null);

        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex(GroceryListContract.GroceryList._ID));
            String title = c.getString(c.getColumnIndex("title"));
            _lists.put(new GroceryList(id, title), new GroceryArrayList<GroceryListEntry>());
        }

        for (GroceryList g : _lists.keySet()) {
            String selection = GroceryListContract.GroceryListEntry.COLUMN_NAME_GROCERY_LIST_ENTRY_ID + " = ?";
            String selectionArgs[] = {String.valueOf(g.get_id())};
            c = _db.query(GroceryListContract.GroceryListEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
            while (c.moveToNext()) {
                long id = c.getLong(c.getColumnIndex("_id"));
                String description = c.getString(c.getColumnIndex("description"));
                GroceryListEntry.Category category = GroceryListEntry.Category.findByKey(c.getInt(c.getColumnIndex("category")));
                int quantity = c.getInt(c.getColumnIndex("quantity"));
                long fk = c.getLong(c.getColumnIndex("grocery_list_entry_id"));
                _lists.get(g).add(new GroceryListEntry(id, description, category, quantity, fk));
            }
        }
        c.close();
    }

    GroceryList createGroceryList(String title) {
        ContentValues values = new ContentValues();
        values.put(GroceryListContract.GroceryList.COLUMN_NAME_TITLE, title);

        GroceryList list = new GroceryList(_db.insert(GroceryListContract.GroceryList.TABLE_NAME, null, values), title);
        _lists.put(list, new GroceryArrayList<GroceryListEntry>());
        return list;
    }

    GroceryListEntry createGroceryListEntry(String description, GroceryListEntry.Category category,
                                            int quantity, GroceryList list) {
        // check if the entry already exists
        GroceryListEntry newEntry = new GroceryListEntry(0, description, category, quantity, 0);
        String[] projection = {
                GroceryListContract.GroceryListEntry.COLUMN_NAME_DESCRIPTION,
                GroceryListContract.GroceryListEntry.COLUMN_NAME_CATEGORY,
                GroceryListContract.GroceryListEntry.COLUMN_NAME_QUANTITY,
        };
        String selection = GroceryListContract.GroceryListEntry.COLUMN_NAME_DESCRIPTION + " = ?" + " and "
                + GroceryListContract.GroceryListEntry.COLUMN_NAME_CATEGORY + " = ?" + " and "
                + GroceryListContract.GroceryListEntry.COLUMN_NAME_GROCERY_LIST_ENTRY_ID + " = ?";
        String[] selectionArgs = {newEntry.get_description(), String.valueOf(newEntry.get_category().value()),
        String.valueOf(list.get_id())};
        Cursor c = _db.query(GroceryListContract.GroceryListEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        if (doesEntryExist(newEntry, c, list)) {
            updateGroceryListEntry(newEntry, c);
            return null;
        }

        long groceryListEntryId = list.get_id();
        ContentValues values = new ContentValues();
        values.put(GroceryListContract.GroceryListEntry.COLUMN_NAME_DESCRIPTION, description);
        values.put(GroceryListContract.GroceryListEntry.COLUMN_NAME_CATEGORY, category.value());
        values.put(GroceryListContract.GroceryListEntry.COLUMN_NAME_QUANTITY, quantity);
        values.put(GroceryListContract.GroceryListEntry.COLUMN_NAME_GROCERY_LIST_ENTRY_ID, groceryListEntryId);

        GroceryListEntry entry = new GroceryListEntry(_db.insert(GroceryListContract.GroceryListEntry.TABLE_NAME, null, values),
                description, category, quantity, groceryListEntryId);
        _lists.get(list).add(entry);

        c.close();
        return entry;
    }

    private boolean doesEntryExist(GroceryListEntry entry, Cursor otherEntry, GroceryList list) {

        if (otherEntry.moveToNext()) {
            return otherEntry.getString(otherEntry.getColumnIndex("description")).equals(entry.get_description())
                    && otherEntry.getInt(otherEntry.getColumnIndex("category")) == entry.get_category().value()
                    || otherEntry.getLong(otherEntry.getColumnIndex("grocery_list_entry_id")) == entry.get_id();
        }
        return false;
    }

    private void updateGroceryListEntry(GroceryListEntry entry, Cursor c) {
        int oldQuantity = 0;

        if (c.moveToNext()) {
            oldQuantity = c.getInt(c.getColumnIndex("quantity"));
        }
        int newQuantity = entry.get_quantity() + oldQuantity;

        ContentValues values = new ContentValues();
        values.put(GroceryListContract.GroceryListEntry.COLUMN_NAME_QUANTITY, newQuantity);
    }

    ArrayList<GroceryListEntry> getAllEntriesFromGroceryList(GroceryList list) {
        return _lists.get(list);
    }

    ArrayList<GroceryListEntry> getAllEntries() {
        ArrayList<GroceryListEntry> arr = new ArrayList<>();
        for (GroceryList list : _lists.keySet()) {
            arr.addAll(_lists.get(list));
        }
        return arr;
    }

    Set<GroceryList> getAllGroceryLists() {
        return _lists.keySet();
    }


}

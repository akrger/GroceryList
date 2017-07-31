package akrger.grocerylist;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GroceryListDBHelper dbHelper;
    GroceryListManager manager;
    ArrayList<GroceryList> groceryLists;
    ArrayList<GroceryListEntry> groceryListsEntries;
    ArrayAdapter groceryListArrayAdapter;
    ArrayAdapter groceryListEntryArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new GroceryListDBHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        manager = new GroceryListManager(db);
        //deleteDatabase("GroceryList.db");


        final FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        groceryLists = new ArrayList<>(manager.getAllGroceryLists());
        groceryListsEntries = new ArrayList<>(manager.getAllEntries());

        GroceryListFragment listFragment = new GroceryListFragment();
        fragmentTransaction.add(R.id.fragment_container, listFragment);
        fragmentTransaction.commit();

        groceryListArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, groceryLists);
        groceryListEntryArrayAdapter = new GroceryListEntryAdapter(this, groceryListsEntries);

    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    public void createNewGroceryList(String title) {
        GroceryList list = manager.createGroceryList(title);

        groceryLists.add(list);
    }

    public void createNewGroceryListEntry(String title, GroceryListEntry.Category category, int quantity, GroceryList list) {


        GroceryListEntry newEntry=  manager.getEntryFromList(list, title, category);
        GroceryListEntry entry = manager.createGroceryListEntry(title, category, quantity, list);

        if (newEntry != null) {
            newEntry.set_quantity(newEntry.get_quantity() + quantity);
        }
        //groceryListEntryArrayAdapter.notifyDataSetChanged();

        groceryListsEntries.add(entry);
    }

    public ArrayAdapter getGroceryListArrayAdapter() {
        return groceryListArrayAdapter;
    }

    public GroceryListManager getManager() {
        return manager;
    }

    public ArrayList<GroceryList> getGroceryLists() {
        return groceryLists;
    }


}

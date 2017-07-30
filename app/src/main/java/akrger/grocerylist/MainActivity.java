package akrger.grocerylist;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

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
        groceryListEntryArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, groceryListsEntries);

    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    public void createNewGroceryList(String title) {
        groceryLists.add(manager.createGroceryList(title));
       // groceryListArrayAdapter.notifyDataSetChanged();
    }

    public void createNewGroceryListEntry(String title, GroceryListEntry.Category category, int quantity, GroceryList list) {
        groceryListsEntries.add(manager.createGroceryListEntry(title, category, quantity, list));
    //groceryListEntryArrayAdapter.notifyDataSetChanged();
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

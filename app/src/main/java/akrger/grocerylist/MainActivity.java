package akrger.grocerylist;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GroceryListDBHelper dbHelper;
    GroceryListManager manager;
    ArrayList<GroceryList> groceryLists;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new GroceryListDBHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        manager = new GroceryListManager(db);
        final ListView view = (ListView) findViewById(R.id.listView);

        groceryLists = new ArrayList<>(manager.getAllGroceryLists());
        final AlertDialog.Builder b = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        b.setView(input);

       arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, groceryLists);
        view.setAdapter(arrayAdapter);
        final GroceryListDialogFragment frag = new GroceryListDialogFragment();



        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent myIntent = new Intent(getApplicationContext(), GroceryListEntryActivity.class);
                //myIntent.putExtra("ArrayList", t);
                //startActivity(myIntent);
                frag.show(getSupportFragmentManager(), "Neue Liste");
            }
        });
        findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groceryLists.removeAll(groceryLists);
                deleteDatabase("GroceryList.db");
                dbHelper.getWritableDatabase();
                arrayAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    public void createNewGroceryList(String title) {
        groceryLists.add(manager.createGroceryList(title));
        arrayAdapter.notifyDataSetChanged();
    }
}

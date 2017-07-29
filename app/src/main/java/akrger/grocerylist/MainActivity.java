package akrger.grocerylist;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GroceryListDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new GroceryListDBHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        final GroceryListManager manager = new GroceryListManager(db);
        final ListView view = (ListView) findViewById(R.id.listView);

        final ArrayList<GroceryList> groceryLists = new ArrayList<>(manager.getAllGroceryLists());
        final AlertDialog.Builder b = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        b.setView(input);

        final ArrayAdapter arr = new ArrayAdapter(this, android.R.layout.simple_list_item_1, groceryLists);
        view.setAdapter(arr);
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                groceryLists.add(manager.createGroceryList(input.getText().toString()));
                input.setText("");
                arr.notifyDataSetChanged();

            }
        });
        final AlertDialog alert = b.create();


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent myIntent = new Intent(getApplicationContext(), GroceryListEntryActivity.class);
                //myIntent.putExtra("ArrayList", t);
                //startActivity(myIntent);
                //b.show();
                alert.show();
            }
        });
        findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groceryLists.removeAll(groceryLists);
                deleteDatabase("GroceryList.db");
                dbHelper.getWritableDatabase();
                arr.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}

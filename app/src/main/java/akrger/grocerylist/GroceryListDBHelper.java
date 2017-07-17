package akrger.grocerylist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class GroceryListDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "GroceryList.db";

    GroceryListDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(GroceryListContract.getSqlCreateEntriesGroceryList());
        sqLiteDatabase.execSQL(GroceryListContract.getSqlCreateEntriesGroceryListEntry());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(GroceryListContract.getSqlDeleteEntriesGroceryList());
        sqLiteDatabase.execSQL(GroceryListContract.getSqlDeleteEntriesGroceryListEntry());
        onCreate(sqLiteDatabase);
    }
}
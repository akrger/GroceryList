package akrger.grocerylist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(AndroidJUnit4.class)
//public class GroceryListTest {
//    @Test
//    public void useAppContext() throws Exception {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();
//
//        assertEquals("akrger.grocerylist", appContext.getPackageName());
//    }
//}


public class GroceryListTest {
    private Context context;
    private GroceryListDBHelper helper;
    private GroceryListManager m;
    private GroceryList l1;
    private GroceryList l2;
    private GroceryListEntry e1;
    private GroceryListEntry e2;
    private GroceryListEntry e3;


    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
        helper = new GroceryListDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        m = new GroceryListManager(db);
        l1 = m.createGroceryList("Liste 1");
        l2 = m.createGroceryList("Liste 2");
        e1 = m.createGroceryListEntry("Vilsa", GroceryListEntry.Category.DRINKS, 10, l1);
        e2 = m.createGroceryListEntry("Vilsa", GroceryListEntry.Category.DRINKS, 5, l1);
        e3 = m.createGroceryListEntry("Bismarck", GroceryListEntry.Category.DRINKS, 10, l1);
    }

    @Test
    public void testCreateGroceryList() {
        assertEquals("Liste 1", l1.get_title());
        assertEquals(1, l1.get_id());
    }

    @Test
    public void testCreateGroceryListEntry() {
        GroceryListEntry e4 = m.createGroceryListEntry("Bismarck", GroceryListEntry.Category.DRINKS, 10, l2);
        assertEquals(2, e4.get_fk());
    }

    @Test
    public void testGetFk() {
        assertEquals(1, e1.get_fk());
        assertEquals(1, e2.get_fk());
        assertEquals(1, e3.get_fk());
        GroceryListEntry e4 = m.createGroceryListEntry("Bismarck", GroceryListEntry.Category.DRINKS, 10, l2);
        assertEquals(2, e4.get_fk());
    }

    @Test
    public void testGetQuantity() {
        ArrayList<GroceryListEntry> arr = m.getAllEntriesFromGroceryList(l1);
        assertEquals(2, arr.size());
        assertEquals(15, arr.get(arr.indexOf(e1)).get_quantity());
        assertEquals(15, arr.get(arr.indexOf(e2)).get_quantity());
    }

    @Test
    public void testGetAllGroceryLists() {
        Set<GroceryList> lists = new HashSet<>();
        lists.add(l1);
        lists.add(l2);
        assertEquals(lists, m.getAllGroceryLists());
    }

    @Test
    public void testGetAllEntriesFromGroceryList() {
        GroceryArrayList<GroceryListEntry> arr = new GroceryArrayList<>();
        arr.add(e1);
        arr.add(e3);
        assertEquals(2, m.getAllEntriesFromGroceryList(l1).size());
    }

    @After
    public void tearDown() {
        context.deleteDatabase(helper.getDatabaseName());
    }
}

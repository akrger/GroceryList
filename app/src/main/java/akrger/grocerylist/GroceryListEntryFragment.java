package akrger.grocerylist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by andre on 7/30/17.
 */

public class GroceryListEntryFragment extends Fragment {

    ArrayAdapter arrayAdapter;
    ArrayList<GroceryListEntry> groceryListsEntries;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.grocery_list_entry, null);
        final GroceryList list = (GroceryList) getArguments().getSerializable("list");
        groceryListsEntries = ((MainActivity)getActivity()).getManager().getAllEntriesFromGroceryList(list);
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, groceryListsEntries);
        ListView listView = view.findViewById(R.id.listViewGroceryListEntries);
        listView.setAdapter(arrayAdapter);

        view.findViewById(R.id.newEntry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                ((MainActivity)getActivity()).createNewGroceryListEntry("bla", GroceryListEntry.Category.DRINKS, 10, list);
                arrayAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }
}

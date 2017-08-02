package akrger.grocerylist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
        arrayAdapter = new GroceryListEntryAdapter(getContext(), groceryListsEntries);
        final ListView listView = view.findViewById(R.id.entryListView);
        listView.setAdapter(arrayAdapter);
        final FragmentManager fragmentManager =  getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        final GroceryListNewEntryFragment newEntryFragment = new GroceryListNewEntryFragment();

        view.findViewById(R.id.newEntry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                fragmentTransaction.replace(R.id.fragment_container, newEntryFragment).addToBackStack("showEntry");

                fragmentTransaction.commit();
                ((MainActivity)getActivity()).createNewGroceryListEntry("üü", GroceryListEntry.Category.DRINKS, 20, list);
                arrayAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }
}

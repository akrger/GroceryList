package akrger.grocerylist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by andre on 7/30/17.
 */

public class GroceryListFragment extends Fragment {
    ArrayList<GroceryList> groceryLists;
    ArrayAdapter arrayAdapter;
    GroceryListDialogFragment dialogFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grocery_list, null);
        groceryLists = ((MainActivity) getActivity()).getGroceryLists();
        arrayAdapter = ((MainActivity) getActivity()).getGroceryListArrayAdapter();

        ListView listView = view.findViewById(R.id.listView);
        dialogFragment = new GroceryListDialogFragment();
        listView.setAdapter(arrayAdapter);
        view.findViewById(R.id.newList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFragment.show(getFragmentManager(), "Neue Liste");
                arrayAdapter.notifyDataSetChanged();

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", groceryLists.get(i));
                GroceryListEntryFragment entryFragment = new GroceryListEntryFragment();
                entryFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, entryFragment).addToBackStack("").commit();
            }
        });

        return view;
    }
}

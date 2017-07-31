package akrger.grocerylist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by andre on 7/30/17.
 */

public class GroceryListEntryAdapter extends ArrayAdapter{
    public GroceryListEntryAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position

        GroceryListEntry entry = (GroceryListEntry) getItem(position);


        Log.d("entry", String.valueOf(position));
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grocery_list_entry_view, parent, false);
        }
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView quantity = (TextView) convertView.findViewById(R.id.quantity);
        TextView category = (TextView) convertView.findViewById(R.id.category);
        description.setText(entry.get_description());
        quantity.setText(String.valueOf(entry.get_quantity()));
        category.setText(String.valueOf(entry.get_category().getCategoryName(entry.get_category(), getContext())));

        // Return the completed view to render on screen
        return convertView;
    }
}

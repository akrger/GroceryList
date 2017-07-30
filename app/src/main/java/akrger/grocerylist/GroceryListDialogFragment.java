package akrger.grocerylist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.EditText;


public class GroceryListDialogFragment extends android.support.v4.app.DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.grocery_list_dialog, null))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String listTitle = ((EditText)getDialog().findViewById(R.id.listTitle)).getText().toString();
                        if (listTitle.length() > 0) {
                            ((MainActivity) getActivity()).createNewGroceryList(listTitle);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GroceryListDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}

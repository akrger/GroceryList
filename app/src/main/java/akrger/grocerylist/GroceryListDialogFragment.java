package akrger.grocerylist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by andre on 7/30/17.
 */

public class GroceryListDialogFragment extends android.support.v4.app.DialogFragment {
    EditText editText;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        editText = new EditText(getContext());
        return new AlertDialog.Builder(getActivity()).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((MainActivity)getActivity()).createNewGroceryList(editText.getText().toString());
            }
        }).setNegativeButton("Abbrechen", null).setView(editText).create();
    }
}

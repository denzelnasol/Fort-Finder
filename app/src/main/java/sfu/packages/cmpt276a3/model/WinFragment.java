package sfu.packages.cmpt276a3.model;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;


import androidx.appcompat.app.AppCompatDialogFragment;

import sfu.packages.cmpt276a3.R;

public class WinFragment extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create the view to show
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.win_layout, null);

        // Create a button Listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        getActivity().finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        // Build the alert dialog
        return new AlertDialog.Builder(getActivity()).setTitle("").setView(view).setPositiveButton(android.R.string.ok, listener).setNegativeButton(android.R.string.cancel, listener).create();
    }
}

package com.myntra.androidui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by prateek.mehra on 17/12/14.
 */
public class MyDialog extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dlg = null;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //getAcitivty gets parent activity of dialog.
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to exit?");
        //builder.setView can be used to create custom dialogs.
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dlg = builder.create();
        return dlg;
    }
}

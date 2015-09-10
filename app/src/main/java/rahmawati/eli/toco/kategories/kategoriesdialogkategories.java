package rahmawati.eli.toco.kategories;

/**
 * Created by eli on 06/09/15.
 */
import android.app.DialogFragment;
import android.app.Dialog;
import android.os.Bundle;
import android.app.AlertDialog;
import rahmawati.eli.toco.R;
import android.content.DialogInterface;
import android.widget.EditText;
import android.view.View;

import rahmawati.eli.toco.kategories.database.Folder;
import rahmawati.eli.toco.kategories.database.FolderDataSource;
public class kategoriesdialogkategories extends DialogFragment {
    Folder folder;
    FolderDataSource dataSource = new FolderDataSource(this.getContext());
    final EditText input = new EditText(this.getActivity());
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.kategoriesdialogkategories, null));
        builder.setMessage(R.string.kategories)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
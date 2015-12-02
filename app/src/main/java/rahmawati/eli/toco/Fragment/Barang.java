package rahmawati.eli.toco.Fragment;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import rahmawati.eli.toco.Admin.add_barang;
import rahmawati.eli.toco.Database.Provider;
import rahmawati.eli.toco.R;

/**
 * Created by eli on 14/10/15.
 */
public class Barang
        extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    //ID ContexMenu Hapus
    private final int hapus = 1;

    private SimpleCursorAdapter adapter;

    public Barang() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerForContextMenu(this.getListView());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //View view = inflater.inflate(R.layout.barang,container);
        this.setHasOptionsMenu(true);

        fillData();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.barang, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_barang) {
            Intent intent = new Intent(this.getActivity().getApplication().getBaseContext(),add_barang.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, hapus,1,"Hapus");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case (hapus):
                //buat dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
                builder.setTitle("Hapus");

                final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                        .getMenuInfo();
                Cursor cursor = (Cursor)adapter.getItem(info.position);
                String text = cursor.getString(cursor.getColumnIndexOrThrow("nama"));
                builder
                        .setMessage("Hapus " + text + " ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                Uri uri = Uri.parse("content://rahmawati.eli.toco/Barang/"+info.id);
                                getActivity().getContentResolver().delete(uri,null,null);
                                //fillData();
                            }
                        })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                Log.d("info", "" + info.id);
                //cek apakah yang dihapus adalah dirinya sendiri jika iya batalkan
                   return true;

        }
        return super.onContextItemSelected(item);
    }

    private void fillData() {

        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] { "nama","harga_jual","etalase","gudang" };
        // Fields on the UI to which we map
        int[] to = new int[] { R.id.nama,R.id.harga,R.id.etalase,R.id.gudang };

        getLoaderManager().initLoader(0, null, this);
        adapter = new SimpleCursorAdapter(this.getActivity(), R.layout.produk, null, from,
                to, 0);
        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder(){
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if( columnIndex == 4 ){ // let's suppose that the column 0 is the date
                    TextView tv = (TextView) view;
                    Integer harga_jual = cursor.getInt(cursor.getColumnIndex("harga_jual"));
                    // here you use SimpleDateFormat to bla blah blah
                    tv.setText(NumberFormat.getCurrencyInstance(Locale.getDefault()).format(harga_jual));
                    return true;
                }
                return false;
            }
        });

        setListAdapter(adapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //String[] projection = { "_id","nama","role" };
        CursorLoader cursorLoader = new CursorLoader(this.getActivity(),
                Uri.parse("content://rahmawati.eli.toco/Barang"), null, null, null, "nama ASC");
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    // Opens the second activity if an entry is clicked
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this.getActivity(), add_barang.class);
        Uri uri = Uri.parse("content://rahmawati.eli.toco/Barang/" + id);
        i.putExtra("Uri", uri);

        startActivity(i);
    }


}
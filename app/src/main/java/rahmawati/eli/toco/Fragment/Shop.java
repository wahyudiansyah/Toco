package rahmawati.eli.toco.Fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import rahmawati.eli.toco.Admin.add_user;
import rahmawati.eli.toco.Database.*;
import rahmawati.eli.toco.R;
import rahmawati.eli.toco.Helper.*;

/**
 * Created by eli on 17/11/15.
 */
public class Shop extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter adapter;
    private ListView listView;
    private EditText inputbarcode;
    private ImageButton zxing;

    public Shop(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.shop, container, false);
        listView = (ListView)view.findViewById(R.id.listView);
        inputbarcode = (EditText)view.findViewById(R.id.inputbarcode);
        inputbarcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    inputbarcode(v.getText().toString());
                    handled = true;
                }
                return handled;
            }
        });
        zxing = (ImageButton)view.findViewById(R.id.zxing);
        zxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
            }
        });
        fillData();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.shop, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clear) {
            Uri uri = Uri.parse("content://rahmawati.eli.toco/Transaksi");
            getActivity().getContentResolver().delete(uri, null, null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fillData(){
        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] { "nama","harga_jual","qty","sub_jual" };
        // Fields on the UI to which we map
        int[] to = new int[] { R.id.nama,R.id.harga,R.id.qty,R.id.subtotal };

        getLoaderManager().initLoader(0, null, this);
        adapter = new daftarbelanja_adapter(this.getActivity(), R.layout.daftar_belanja, null, from,
                to, 0);
        /*adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder(){
            @Override
            public boolean setViewValue(View view, final Cursor cursor, int columnIndex) {
                if(cursor.getPosition()){
                ImageButton trash = (ImageButton)view.findViewById(R.id.trash);
                trash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer row = cursor.getInt(cursor.getColumnIndex("_id"));
                        Log.d("trash",Integer.toString(row));
                    }
                });}
                return false;
            }
        });*/
        /*adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder(){
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if( columnIndex == 3 ){ // let's suppose that the column 0 is the date
                    EditText tv = (EditText) view;
                    Integer e = cursor.getInt(cursor.getColumnIndex("sub_jual"));
                    // here you use SimpleDateFormat to bla blah blah
                    Log.d("viewbinder",Integer.toString(e));
                    tv.setText(Integer.toString(e));
                    return true;
                }
                if( columnIndex == 4 ){ // let's suppose that the column 0 is the date
                    TextView tv = (TextView) view;
                    Integer harga_jual = cursor.getInt(cursor.getColumnIndex("harga_jual"));
                    // here you use SimpleDateFormat to bla blah blah
                    tv.setText(NumberFormat.getCurrencyInstance(Locale.getDefault()).format(harga_jual));
                    return true;
                }
                if( columnIndex == 7 ){ // let's suppose that the column 0 is the date
                    TextView tv = (TextView) view;
                    Integer harga_jual = cursor.getInt(cursor.getColumnIndex("gudang"));
                    // here you use SimpleDateFormat to bla blah blah
                    tv.setText(NumberFormat.getCurrencyInstance(Locale.getDefault()).format(harga_jual));
                    return true;
                }
                return false;
            }
        });*/

        listView.setAdapter(adapter);
    }

    public void inputbarcode(String string){
        Log.d("inputbarcode",string);
        Uri uri = Uri.parse("content://rahmawati.eli.toco/Barang/barcode/"+string);
        Cursor cursor = getActivity().getContentResolver().query(uri,new String[]{"_id"},null,null,null);
        if(cursor.moveToFirst()){
            inputbarcode.setText("");
            String _id = cursor.getString(0);
            Log.d("cursor",cursor.getString(0));
            cursor.close();
            uri = Uri.parse("content://rahmawati.eli.toco/Transaksi/"+_id);
            cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            if(cursor.moveToFirst()){
                Integer qty = cursor.getInt(cursor.getColumnIndex("qty"));
                cursor.close();
                Log.d("qty",Integer.toString(qty));
                qty++;
                ContentValues values = new ContentValues();
                values.put(Transaksi.COLUMN_QTY, qty);
                getActivity().getContentResolver().update(Uri.parse("content://rahmawati.eli.toco/Transaksi/"+_id), values,null,null);
                return;
            }
            ContentValues values = new ContentValues();
            values.put(Transaksi.COLUMN_ID, _id);
            getActivity().getContentResolver().insert(Uri.parse("content://rahmawati.eli.toco/Transaksi"), values);
        }


    }

    public void scan(){
        Log.d("scan","click");
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0){
            if (resultCode == getActivity().RESULT_OK){
                String barcode = data.getStringExtra("SCAN_RESULT");
                inputbarcode(barcode);
                new Timer().schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                scan();
                            }
                        },
                        2000
                );
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //String[] projection = { "_id","nama","role" };
        CursorLoader cursorLoader = new CursorLoader(this.getActivity(),
                Uri.parse("content://rahmawati.eli.toco/Transaksi"), null, null, null, null);
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



}

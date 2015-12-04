package rahmawati.eli.toco.Helper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import rahmawati.eli.toco.Database.Transaksi;
import rahmawati.eli.toco.R;

/**
 * Created by eli on 27/11/15.
 */
public  class  daftarbelanja_adapter extends SimpleCursorAdapter {
    public daftarbelanja_adapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public View newView(Context _context, Cursor _cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(_context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.daftar_belanja, parent, false);
        return view;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void bindView(View view, final Context Context, final Cursor cursor) {
        final Integer _id = cursor.getInt(cursor.getColumnIndex("_id"));
        final EditText qty = (EditText)view.findViewById(R.id.qty);
        qty.setText(cursor.getString(cursor.getColumnIndex(Transaksi.COLUMN_QTY)));

        qty.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    ContentValues values = new ContentValues();
                    values.put(Transaksi.COLUMN_QTY, qty.getText().toString());
                    Context.getContentResolver().update(Uri.parse("content://rahmawati.eli.toco/Transaksi/" + _id), values, null, null);
                    qty.clearFocus();
                    //hideKeyboard(v);
                }
                return false;
            }
        });
        TextView nama = (TextView)view.findViewById(R.id.nama);
        nama.setText(cursor.getString(cursor.getColumnIndex("nama")));
        TextView harga = (TextView)view.findViewById(R.id.harga);
        harga.setText(NumberFormat.getCurrencyInstance(Locale.getDefault()).format(cursor.getInt(cursor.getColumnIndex(Transaksi.COLUMN_HARGA_JUAL))));
        TextView subtotal = (TextView)view.findViewById(R.id.subtotal);
        subtotal.setText(NumberFormat.getCurrencyInstance(Locale.getDefault()).format(cursor.getInt(cursor.getColumnIndex(Transaksi.COLUMN_SUB_JUAL))));
        ImageButton trash = (ImageButton)view.findViewById(R.id.trash);
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("trash", Integer.toString(_id));
                Uri uri = Uri.parse("content://rahmawati.eli.toco/Transaksi/" + _id);
                Context.getContentResolver().delete(uri, null, null);
            }
        });
    }
}
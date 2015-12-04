package rahmawati.eli.toco.Admin;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import rahmawati.eli.toco.Database.Struk;
import rahmawati.eli.toco.Database.StrukDitail;
import rahmawati.eli.toco.R;

/**
 * Created by eli on 04/12/15.
 */
public class struk_ditail extends ListActivity{
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle extras = getIntent().getExtras();
        Uri uri = extras.getParcelable("Uri");
        Log.d("udi", uri.toString());
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        String[] from = new String[] { StrukDitail.COLUMN_NAMA,StrukDitail.COLUMN_HARGA_JUAL,StrukDitail.COLUMN_QTY,StrukDitail.COLUMN_SUB_JUAL };
        // Fields on the UI to which we map
        int[] to = new int[] { R.id.nama,R.id.harga,R.id.etalase,R.id.gudang };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.produk,cursor,from,to);
        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if( columnIndex == 4 ){ // let's suppose that the column 0 is the date
                    TextView tv = (TextView) view;
                    Integer harga_jual = cursor.getInt(cursor.getColumnIndex(StrukDitail.COLUMN_HARGA_JUAL));
                    // here you use SimpleDateFormat to bla blah blah
                    tv.setText(NumberFormat.getCurrencyInstance(Locale.getDefault()).format(harga_jual));
                    return true;
                }
                if( columnIndex == 7 ){ // let's suppose that the column 0 is the date
                    TextView tv = (TextView) view;
                    Integer harga_jual = cursor.getInt(cursor.getColumnIndex(StrukDitail.COLUMN_SUB_JUAL));
                    // here you use SimpleDateFormat to bla blah blah
                    tv.setText(NumberFormat.getCurrencyInstance(Locale.getDefault()).format(harga_jual));
                    return true;
                }
                return false;
            }
        });
        setListAdapter(adapter);
    }
}
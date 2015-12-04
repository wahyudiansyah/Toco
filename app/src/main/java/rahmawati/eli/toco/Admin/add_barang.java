package rahmawati.eli.toco.Admin;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import rahmawati.eli.toco.Database.Barang;
import rahmawati.eli.toco.R;
public class add_barang extends AppCompatActivity {

    private EditText barcode;
    private EditText kode;
    private EditText nama;
    private EditText beli;
    private EditText jual;
    private EditText etalase;
    private EditText gudang;

    private String Barcode;
    private String Kode;
    private String Nama;
    private Integer Beli;
    private Integer Jual;
    private Integer Etalase;
    private Integer Gudang;

    private Uri uri;



    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.add_barang);

        barcode = (EditText)findViewById(R.id.barcode);
        kode = (EditText)findViewById(R.id.kode);
        nama = (EditText)findViewById(R.id.nama);
        jual = (EditText)findViewById(R.id.jual);
        beli = (EditText)findViewById(R.id.beli);
        etalase = (EditText)findViewById(R.id.etalase);
        gudang = (EditText)findViewById(R.id.gudang);


        Bundle extras = getIntent().getExtras();

        uri = (bundle==null)? null:(Uri)bundle.getParcelable("Uri");
        if (extras != null){
            uri = extras.getParcelable("Uri");
            fillData(uri);
        }


    }

    private void fillData(Uri uri){
        //String[] projection = User.ALL_COLUMN;
        Cursor cursor = getContentResolver().query(uri, null, null, null,
                null);
        if(cursor.moveToFirst()){
            Barcode = cursor.getString(cursor.getColumnIndexOrThrow(Barang.COLUMN_BARCODE));
            Kode = cursor.getString(cursor.getColumnIndexOrThrow(Barang.COLUMN_KODE_BARANG));
            Nama = cursor.getString(cursor.getColumnIndexOrThrow(Barang.COLUMN_NAMA));
            Beli = cursor.getInt(cursor.getColumnIndexOrThrow(Barang.COLUMN_HARGA_BELI));
            Jual = cursor.getInt(cursor.getColumnIndexOrThrow(Barang.COLUMN_HARGA_JUAL));
            Etalase = cursor.getInt(cursor.getColumnIndexOrThrow(Barang.COLUMN_ETALASE));
            Gudang = cursor.getInt(cursor.getColumnIndexOrThrow(Barang.COLUMN_GUDANG));

            barcode.setText(Barcode);
            kode.setText(Kode);
            nama.setText(Nama);
            beli.setText(Beli.toString());
            jual.setText(Jual.toString());
            etalase.setText(Etalase.toString());
            gudang.setText(Gudang.toString());

            Log.d(cursor.getString(cursor.getColumnIndexOrThrow(Barang.COLUMN_HARGA_BELI)),cursor.getString(cursor.getColumnIndexOrThrow(Barang.COLUMN_HARGA_JUAL)));


        }
        //always close the cursor
        cursor.close();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("Uri", uri);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.save:
                  Barcode = (barcode.getText().toString().matches(""))? null : barcode.getText().toString();
                  Kode = (kode.getText().toString().matches(""))? null : kode.getText().toString();
                  Nama = (nama.getText().toString().matches(""))? null : nama.getText().toString();
                  Beli = (beli.getText().toString().matches(""))? null : Integer.parseInt(beli.getText().toString());
                  Jual = (jual.getText().toString().matches(""))? null : Integer.parseInt(jual.getText().toString());
                  Etalase = (etalase.getText().toString().matches(""))? null : Integer.parseInt(etalase.getText().toString());
                  Gudang = (gudang.getText().toString().matches(""))? null : Integer.parseInt(gudang.getText().toString());

                ContentValues values = new ContentValues();
                values.put(Barang.COLUMN_BARCODE,Barcode);
                values.put(Barang.COLUMN_KODE_BARANG,Kode);
                values.put(Barang.COLUMN_NAMA,Nama);
                values.put(Barang.COLUMN_HARGA_BELI,Beli);
                values.put(Barang.COLUMN_HARGA_JUAL,Jual);
                values.put(Barang.COLUMN_ETALASE,Etalase);
                values.put(Barang.COLUMN_GUDANG,Gudang);

                if (uri!=null) {
                    try{
                        getContentResolver().update(uri, values, null, null);
                        this.setResult(RESULT_OK);
                        finish();
                    }
                    catch (SQLException e){
                        Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                        return true;}
                }
                if (uri==null){
                    try {
                        getContentResolver().insert(Uri.parse("content://rahmawati.eli.toco/Barang"), values);
                        this.setResult(RESULT_OK);
                        finish();
                    }
                    catch (SQLException e){
                        Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                        return true;
                    }

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void scan(View view){
        Log.d("scan","click");
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0){
            if (resultCode == RESULT_OK){
                barcode.setText(data.getStringExtra("SCAN_RESULT"));
            }
        }
    }
}

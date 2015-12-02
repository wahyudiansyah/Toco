package rahmawati.eli.toco.Admin;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import rahmawati.eli.toco.Database.Provider;
import rahmawati.eli.toco.Database.User;
import rahmawati.eli.toco.R;
public class add_user extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText nama;
    private Spinner role;

    private String Nama;
    private String Username;
    private String Password;
    private String Role;

    private Uri uri;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.add_user);

        nama = (EditText)findViewById(R.id.nama);
        username =(EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        role = (Spinner)findViewById(R.id.role);

        Bundle extras = getIntent().getExtras();

        uri = (bundle==null)? null:(Uri)bundle.getParcelable("Uri");
        if (extras != null){
            uri = extras.getParcelable("Uri");
            fillData(uri);
        }


    }

    private void fillData(Uri uri){
        String[] projection = User.ALL_COLUMN;
        Cursor cursor = getContentResolver().query(uri, projection, null, null,
                null);
        if(cursor.moveToFirst()){
            Nama = cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_NAMA));
            Username = cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_USERNAME));
            Password = cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_PASSWORD));
            Role = cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_ROLE));

            nama.setText(Nama);
            username.setText(Username);
            password.setText(Password);

            for (int i = 0; i < role.getCount();i++){
                String s = (String)role.getItemAtPosition(i);
                if(s.equalsIgnoreCase(Role)){
                    role.setSelection(i);
                }
            }



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
                Nama = nama.getText().toString();
                Username = username.getText().toString();
                Password = password.getText().toString();
                Role = role.getSelectedItem().toString();

                ContentValues values = new ContentValues();
                values.put(User.COLUMN_NAMA,Nama);
                values.put(User.COLUMN_USERNAME,Username);
                values.put(User.COLUMN_PASSWORD,Password);
                values.put(User.COLUMN_ROLE,Role);

                if(Nama.matches("") | Username.matches("") | Password.matches("")){
                    Toast.makeText(getApplicationContext(), "nama, username dan password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (uri!=null) {
                    try {
                        getContentResolver().update(uri, values, null, null);
                    }
                    catch (SQLException e){
                        Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                if (uri==null){
                    try {
                        Log.d("insert","insert");
                        getContentResolver().insert(Uri.parse("content://rahmawati.eli.toco/User"), values);

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

}

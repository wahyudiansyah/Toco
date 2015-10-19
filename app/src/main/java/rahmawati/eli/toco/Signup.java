package rahmawati.eli.toco;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by eli on 08/10/15.
 */
public class Signup extends Activity {
    private EditText username;
    private EditText password;
    private EditText nama;

    private String Nama;
    private String Username;
    private String Password;

    private Button signup;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        //EditText nama
        nama = (EditText)findViewById(R.id.nama);

        //EditText username
        username = (EditText)findViewById(R.id.username);

        //EditText password
        password = (EditText)findViewById(R.id.password);
        password.setTypeface(Typeface.DEFAULT);

        //SQLiteDatabase database = openOrCreateDatabase("Toco.db",MODE_PRIVATE,null);
        //database.execSQL("create table if not exists User (id integer primary key autoincrement, nama text not null, username text not null, password text not null, role text check(role=='admin' or role=='kasir') not null, login int check(login==1 or login = 0) not null);");

        //Button Signup
        signup = (Button)findViewById(R.id.button_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("nama", nama.getText().toString());
                Log.d("username",username.getText().toString());
                Log.d("password",password.getText().toString());
                //buat tabel user
                Nama =  nama.getText().toString();
                Username = username.getText().toString();
                Password = password.getText().toString();

                if(Nama.matches("") | Username.matches("") | Password.matches("")){
                    Toast.makeText(getApplicationContext(),"nama, username dan password tidak boleh kosong",10).show();
                }
                else {
                    signup();
                }
                //signup();
            }
        });

    }

    protected void signup(){
        Log.d("signup", "signup");
        try {
            SQLiteDatabase database = openOrCreateDatabase("Toco.db",MODE_PRIVATE,null);
            database.execSQL("create table if not exists User (_id integer primary key autoincrement, nama text not null, username text unique not null, password text not null, role text check(role=='admin' or role=='kasir') not null, login int check(login==1 or login = 0) not null default 0);");
            /*database.execSQL("insert into User (nama,username,password,role, login)" +
                    "values('Nama', 'Username', 'Password','admin',0);");*/
            database.execSQL("insert into User" +
                    "(nama,username,password,role)" +
                    "values('" +
                    Nama +
                    "','" +
                    Username +
                    "','" +
                    Password +
                    "','" +
                    "admin" +
                    "'"+
                    ")");
            Log.d("signup", "database sukses");
            database.close();
            Toast.makeText(getApplicationContext(),"Signup Sukses, anda akan diredirect ke halaman Signin",Toast.LENGTH_LONG).show();
            thread.start();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        finally {
        }
    }

    Thread thread = new Thread(){
        @Override
        public void run() {
            signup.setClickable(false);
            try {
                Thread.sleep(3500); // As I am using LENGTH_LONG in Toast
                Intent intent = new Intent(Signup.this,Login.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Signup", "Restart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Signup","Pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Signup","Resume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Signup","Destroy");
    }

    /*@Override
    public void onBackPressed() {
        onKeyDown()
    }*/

}

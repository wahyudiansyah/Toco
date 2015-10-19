package rahmawati.eli.toco;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity","Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase database = openOrCreateDatabase("Toco.db",MODE_PRIVATE,null);

        //cek database apakah sudah ada table user jika belum lakukan signup
        //di dalam signup database dibuat pertama kali

        //database.execSQL("create table if not exists User (id integer primary key autoincrement, nama text not null, username text unique not null, password text not null, role text check(role=='admin' or role=='kasir') not null, login int check(login==1 or login = 0) not null);");


        //Cursor cursor = database.rawQuery("select * from User",null);





        //cek tabel User sudah dibuat belum
        cursor = database.rawQuery("select * from sqlite_master where type='table' and name='User'",null);

        //jika sudah
        if (cursor.moveToFirst()){
            Log.d("MainActivity","sudah ada tabel User") ;

            //cek sudah ada admin belum
            cursor = database.rawQuery("select * from User where role='admin'",null);
            //jika sudah ada admin
            if (cursor.moveToFirst()){
                Log.d("MainActivity","sudah ada admin") ;
                for(int i =0; i < cursor.getColumnNames().length; i++) {
                    Log.d("column names", cursor.getColumnName(i));
                }
                //cek jika sudah ada login
                cursor = database.rawQuery("select * from User where login='1'",null);
                if (cursor.moveToFirst()){
                    Log.d("MainActivity","sudah ada login") ;
                    Intent intent = new Intent(this,Shop.class);
                    startActivity(intent);
                    cursor.close();
                    database.close();
                    finish();
                }
                else {
                    Log.d("MainActivity","sudah ada admin");
                    Log.d("MainActivity","belum ada login") ;

                    //Log.d("cursor",cursor.getString(0));
                    Intent intent = new Intent(this,Login.class);
                    startActivity(intent);
                    cursor.close();
                    database.close();
                    finish();}

            }
            else {
            Log.d("MainActivity","belum ada admin");
            Log.d("MainActivity","belum ada login") ;

            //Log.d("cursor",cursor.getString(0));
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);
            cursor.close();
                database.close();
            finish();}
        }
        else {
            Log.d("MainActivity","belum ada tabel User") ;
            Intent intent = new Intent(this,Signup.class);
            startActivity(intent);
            cursor.close();
            database.close();
            finish();
        }



    }
}

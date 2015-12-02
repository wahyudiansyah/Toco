package rahmawati.eli.toco.Database;

/**
 * Created by eli on 28/10/15.
 */
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;

public class User {

    // Nama Tabel
    public static final String TABLE = "User";
    //Nama Kolom
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROLE = "role";
    public static final String COLUMN_LOGIN = "login";

    public static final String[] ALL_COLUMN = {COLUMN_ID,COLUMN_NAMA,COLUMN_USERNAME,COLUMN_PASSWORD,COLUMN_ROLE,COLUMN_LOGIN};

    // Database creation SQL statement
    private static final String TABLE_CREATE = "create table if not exists "
            + TABLE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAMA + " text not null, "
            + COLUMN_USERNAME + " text unique not null, "
            + COLUMN_PASSWORD + " text not null, "
            + COLUMN_ROLE + " text  not null check(role=='admin' or role=='kasir') default 'kasir', "
            + COLUMN_LOGIN + " int  not null check(login==1 or login = 0) default 0 "
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(User.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(database);
    }
}
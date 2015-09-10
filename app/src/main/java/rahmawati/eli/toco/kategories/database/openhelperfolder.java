package rahmawati.eli.toco.kategories.database;

/**
 * Created by eli on 08/09/15.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by eli on 08/09/15.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class openhelperfolder extends SQLiteOpenHelper {

    public static final String TABLE_FOLDER = "folder";
    public static final String ROW_ID = "rowid";
    public static final String COLUMN_FOLDER = "folder";
    public static final String COLUMN_CHILD = "child";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_CREATEDATE = "createdate";
    public static final String COLUMN_EDITDATE = "editdate";


    private static final String DATABASE_NAME = "Toco.db";
    private static final int DATABASE_VERSION = 2;

    // Database creation sql statement
    private static final String TABLE_CREATE = "create table if not exists "
            + TABLE_FOLDER + "("
            + COLUMN_FOLDER + " text primary key , "
            + COLUMN_CHILD  + " int"
            + COLUMN_AUTHOR + " text"
            + COLUMN_CREATEDATE + " datetime"
            + COLUMN_EDITDATE + " datetime"
            + ");";

    public openhelperfolder(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(openhelperfolder.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOLDER);
        onCreate(db);
    }

}
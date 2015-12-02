package rahmawati.eli.toco.Database;

/**
 * Created by eli on 28/10/15.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Toco extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Toco.db";
    private static final int DATABASE_VERSION = 1;

    public Toco(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        User.onCreate(database);
        Barang.onCreate(database);
        Transaksi.onCreate(database);
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        User.onUpgrade(database, oldVersion, newVersion);
    }
}

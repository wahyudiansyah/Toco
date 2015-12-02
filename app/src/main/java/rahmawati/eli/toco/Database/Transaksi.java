package rahmawati.eli.toco.Database;

/**
 * Created by eli on 28/10/15.
 */
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;

public class Transaksi {

    // Nama Tabel
    public static final String TABLE = "Transaksi";
    //Nama Kolom
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_HARGA_JUAL = "harga_jual";
    public static final String COLUMN_QTY = "qty";
    public static final String COLUMN_SUB_JUAL = "sub_jual";

    public static final String[] ALL_COLUMN = {COLUMN_ID,COLUMN_NAMA,COLUMN_NAMA,COLUMN_HARGA_JUAL,COLUMN_QTY,COLUMN_SUB_JUAL};

    // Database creation SQL statement
    private static final String TABLE_CREATE = "create table Transaksi (" +
            COLUMN_ID+" integer ," +
            COLUMN_NAMA+" text ," +
            COLUMN_HARGA_JUAL+" int ," +
            COLUMN_QTY+" int  default 1 ," +
            COLUMN_SUB_JUAL+" int );";
    private static final String TRIGGER = "CREATE TRIGGER Transaksi_subjual_default_value\n" +
            "after insert on Transaksi\n" +
            "begin\n" +
            "update Transaksi set nama = (select nama from barang where _id = new._id) where rowid = new.rowid;\n" +
            "update Transaksi set harga_jual = (select harga_jual from barang where _id = new._id) where rowid = new.rowid;\n" +
            "update Transaksi set sub_jual = harga_jual * qty where rowid = new.rowid;\n" +
            "END;";
    private static final String TRIGGER_UPDATE = "CREATE TRIGGER Transaksi_subjual_default_update\n" +
            "after update on Transaksi\n" +
            "for each row\n" +
            "begin\n" +
            "update Transaksi set nama = (select nama from barang where _id = new._id) where rowid = new.rowid;\n" +
            "update Transaksi set harga_jual = (select harga_jual from barang where _id = new._id) where rowid = new.rowid;\n" +
            "update Transaksi set sub_jual = harga_jual * qty where rowid = new.rowid;\n" +
            "END;";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_CREATE);
        database.execSQL(TRIGGER);
        database.execSQL(TRIGGER_UPDATE);
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
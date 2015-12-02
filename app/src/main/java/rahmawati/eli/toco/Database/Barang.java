package rahmawati.eli.toco.Database;

/**
 * Created by eli on 28/10/15.
 */
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;

public class Barang {

    // Nama Tabel
    public static final String TABLE = "Barang";
    //Nama Kolom
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_BARCODE = "barcode";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_KODE_BARANG = "kode_barang";
    public static final String COLUMN_HARGA_JUAL = "harga_jual";
    public static final String COLUMN_HARGA_BELI = "harga_beli";
    public static final String COLUMN_ETALASE = "etalase";
    public static final String COLUMN_GUDANG = "gudang";

    public static final String[] ALL_COLUMN = {COLUMN_ID,COLUMN_NAMA,COLUMN_BARCODE,COLUMN_NAMA,COLUMN_KODE_BARANG,COLUMN_HARGA_JUAL,COLUMN_HARGA_BELI,COLUMN_ETALASE,COLUMN_GUDANG};

    // Database creation SQL statement
    private static final String TABLE_CREATE = "create table Barang " +
            "(_id integer primary key   autoincrement," +
            " barcode text unique   ," +
            " nama text unique not null  ," +
            " kode_barang text unique   ," +
            " harga_jual int  not null  default 0," +
            " harga_beli int  not null  default 0, etalase int  not null  default 0," +
            " gudang int  not null  default 0);";

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
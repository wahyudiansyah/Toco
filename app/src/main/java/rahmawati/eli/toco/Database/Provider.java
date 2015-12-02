package rahmawati.eli.toco.Database;

/**
 * Created by eli on 28/10/15.
 */
import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import rahmawati.eli.toco.Database.Toco;
import rahmawati.eli.toco.Database.User;

public class Provider extends ContentProvider {

    // database
    private Toco database;

    // used for the UriMacher
    private static final int USER = 10;
    private static final int USER_ID = 11;
    private static final int BARANG = 20;
    private static final int BARANG_ID = 21;
    private static final int BARANG_BARCODE = 22;
    private static final int TRANSAKSI = 30;
    private static final int TRANSAKSI_ID = 31;

    private static final String AUTHORITY = "rahmawati.eli.toco";

    private static final String USERS = "User";
    private static final String BARANGS = "Barang";
    private static final String TRANSAKSIS = "Transaksi";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    /*public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/todos";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/todo";*/

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        //Uri untuk USER
        sURIMatcher.addURI(AUTHORITY, USERS, USER);
        sURIMatcher.addURI(AUTHORITY, USERS + "/#", USER_ID);
        sURIMatcher.addURI(AUTHORITY, BARANGS, BARANG);
        sURIMatcher.addURI(AUTHORITY, BARANGS + "/#", BARANG_ID);
        sURIMatcher.addURI(AUTHORITY, BARANGS + "/barcode/*", BARANG_BARCODE);
        sURIMatcher.addURI(AUTHORITY, TRANSAKSIS, TRANSAKSI);
        sURIMatcher.addURI(AUTHORITY, TRANSAKSIS + "/*", TRANSAKSI_ID);
    }

    @Override
    public boolean onCreate() {
        database = new Toco(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
       // checkColumns(projection);

        // Set the table


        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case USER:
                queryBuilder.setTables(User.TABLE);
                break;
            case USER_ID:
                queryBuilder.setTables(User.TABLE);
                // adding the ID to the original query
                queryBuilder.appendWhere(User.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                break;
            case BARANG:
                queryBuilder.setTables(Barang.TABLE);
                break;
            case BARANG_ID:
                queryBuilder.setTables(Barang.TABLE);
                // adding the ID to the original query
                queryBuilder.appendWhere(Barang.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                break;
            case BARANG_BARCODE:
                queryBuilder.setTables(Barang.TABLE);
                // adding the ID to the original query
                queryBuilder.appendWhere(Barang.COLUMN_BARCODE + "='"
                        + uri.getLastPathSegment()+"'");
                break;
            case TRANSAKSI:
                queryBuilder.setTables(Transaksi.TABLE);
                break;
            case TRANSAKSI_ID:
                queryBuilder.setTables(Transaksi.TABLE);
                // adding the ID to the original query
                queryBuilder.appendWhere(Transaksi.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values){
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        long id = 0;
        switch (uriType) {
            case USER:
                sqlDB.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='"+User.TABLE+"';");
                id = sqlDB.insertOrThrow(User.TABLE, null, values);
                break;
            case BARANG:
                id = sqlDB.insertOrThrow(Barang.TABLE, null, values);
                break;
            case TRANSAKSI:
                id = sqlDB.insertOrThrow(Transaksi.TABLE, null, values);
                Log.d("provider insert","case transaksi");
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(uri.toString()+ "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        Log.d("uriType",""+uriType);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        String id;
        switch (uriType) {
            case USER:
                rowsDeleted = sqlDB.delete(User.TABLE, selection,
                        selectionArgs);
                break;
            case USER_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(User.TABLE,
                            User.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(User.TABLE,
                            User.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            case BARANG_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(Barang.TABLE,
                            Barang.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(Barang.TABLE,
                            Barang.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            case TRANSAKSI:
                rowsDeleted = sqlDB.delete(Transaksi.TABLE, selection,
                        selectionArgs);
                break;
            case TRANSAKSI_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(Transaksi.TABLE,
                            Barang.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(Transaksi.TABLE,
                            Barang.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs)throws SQLException{

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        String id;
        switch (uriType) {
            case USER:
                rowsUpdated = sqlDB.update(User.TABLE,
                        values,
                        selection,
                        selectionArgs);
                break;
            case USER_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(User.TABLE,
                            values,
                            User.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(User.TABLE,
                            values,
                            User.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            case BARANG_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(Barang.TABLE,
                            values,
                            Barang.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(User.TABLE,
                            values,
                            Barang.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            case TRANSAKSI_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(Transaksi.TABLE,
                            values,
                            Transaksi.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(Transaksi.TABLE,
                            values,
                            Transaksi.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection) {
        String[] available = User.ALL_COLUMN;
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }


}
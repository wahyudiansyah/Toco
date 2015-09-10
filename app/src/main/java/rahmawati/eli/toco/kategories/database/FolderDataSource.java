package rahmawati.eli.toco.kategories.database;

/**
 * Created by eli on 08/09/15.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FolderDataSource {

    // Database fields
    private SQLiteDatabase database;
    private openhelperfolder dbHelper;
    private String[] allColumns = { openhelperfolder.COLUMN_FOLDER,
            openhelperfolder.COLUMN_CHILD, openhelperfolder.COLUMN_AUTHOR, openhelperfolder.COLUMN_CREATEDATE,
    openhelperfolder.COLUMN_EDITDATE};

    public FolderDataSource(Context context) {
        dbHelper = new openhelperfolder(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Folder createFolder(String folder) {
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_FOLDER, folder);
        long insertId = database.insert(dbHelper.TABLE_FOLDER, null,
                values);
        Cursor cursor = database.query(dbHelper.TABLE_FOLDER,
                allColumns, dbHelper.ROW_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Folder newComment = cursorToFolder(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteFiolder(Folder f) {
        String folder = f.getFolder();
        System.out.println("Comment deleted with folder: " + folder);
        database.delete(dbHelper.TABLE_FOLDER, dbHelper.COLUMN_FOLDER
                + " = " + folder, null);
    }

    public List<Folder> getAllFolder() {
        List<Folder> f = new ArrayList<Folder>();

        Cursor cursor = database.query(openhelperfolder.TABLE_FOLDER,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Folder folder = cursorToFolder(cursor);
            f.add(folder);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return f;
    }

    private Folder cursorToFolder(Cursor cursor) {
        Folder folder = new Folder();
        folder.setFolder(cursor.getString(0));
        folder.setChild(cursor.getInt(1));

        return folder;
    }
}

package rahmawati.eli.toco.Fragment;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import rahmawati.eli.toco.Database.Provider;
import rahmawati.eli.toco.R;

import rahmawati.eli.toco.Admin.add_user;

/**
 * Created by eli on 14/10/15.
 */
public class Admin extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    //ID ContexMenu Hapus
    private final int hapus = 1;

    private SimpleCursorAdapter adapter;

    public Admin() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerForContextMenu(this.getListView());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);

        fillData();

        /*SQLiteDatabase database = getActivity().openOrCreateDatabase("Toco.db",getActivity().MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("select * from User", null);
        CursorAdapter cursorAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(),R.layout.user,cursor,new String[]{"_id","nama","role"},new int[]{R.id._id,R.id.nama,R.id.role},0);
        setListAdapter(cursorAdapter);*/

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.admin, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_user) {
            Intent intent = new Intent(this.getActivity().getApplication().getBaseContext(),add_user.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, hapus,1,"Hapus");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case (hapus):
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                        .getMenuInfo();
                Log.d("info", "" + info.id);
                //cek apakah yang dihapus adalah dirinya sendiri jika iya batalkan
                Uri uri = Uri.parse("content://rahmawati.eli.toco/User/"+info.id);
                Cursor cursor = getActivity().getContentResolver().query(uri,new String[]{"login"},"login=?",new String[]{"1"},null);
                if(cursor.moveToFirst()){
                    Log.d("cursor.moveToFirst()","1");
                    Toast.makeText(getActivity(), "Tidak Bisa dihapus", Toast.LENGTH_SHORT).show();
                    cursor.close();
                    return true;

                }
                cursor.close();
                getActivity().getContentResolver().delete(uri,null,null);
                //fillData();
                return true;

        }
        return super.onContextItemSelected(item);
    }

    private void fillData() {

        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] { "_id","nama","role" };
        // Fields on the UI to which we map
        int[] to = new int[] { R.id._id,R.id.nama,R.id.role };

        getLoaderManager().initLoader(0, null, this);
        adapter = new SimpleCursorAdapter(this.getActivity(), R.layout.user, null, from,
                to, 0);

        setListAdapter(adapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { "_id","nama","role" };
        CursorLoader cursorLoader = new CursorLoader(this.getActivity(),
                Uri.parse("content://rahmawati.eli.toco/User"), null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    // Opens the second activity if an entry is clicked
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this.getActivity(), add_user.class);
        Uri uri = Uri.parse("content://rahmawati.eli.toco/User/" + id);
        i.putExtra("Uri", uri);

        startActivity(i);
    }


}
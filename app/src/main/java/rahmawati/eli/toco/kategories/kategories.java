package rahmawati.eli.toco.kategories;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import rahmawati.eli.toco.R;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import android.app.Dialog;
import android.widget.TextView;

import rahmawati.eli.toco.R;

import java.util.ArrayList;
import java.util.List;

import rahmawati.eli.toco.kategories.database.Folder;
import rahmawati.eli.toco.kategories.database.FolderDataSource;

public class kategories extends AppCompatActivity {

    FolderDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kategories);
        ListView listView = (ListView)findViewById(R.id.listView);
        List<Item>dir = new ArrayList<Item>();
        Item item = new Item("dir","nama",30,"Rp 7.500","20-09-2015");
        for (int n=0;n<100;n++){
        dir.add(item);}
        FileArrayAdapter adapter = new FileArrayAdapter(this,R.layout.fileview,dir);
        listView.setAdapter(adapter);

        datasource = new FolderDataSource(this);
        datasource.open();
       // List<Folder> values = datasource.getAllFolder();
// use the SimpleCursorAdapter to show the
// elements in a ListView
        //ArrayAdapter<Folder> adapter = new ArrayAdapter<Folder>( this ,                android.R.layout.simple_list_item_1, values);
        //listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.kategories, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.add_kategories:
                Log.d("add","kategories");
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.kategoriesdialogkategories);
                dialog.setTitle("Title...");
                EditText editText = (EditText)dialog.findViewById(R.id.editText);
                Button buttoncancel = (Button)dialog.findViewById(R.id.button);
                Button buttonsave = (Button)dialog.findViewById(R.id.button2);
                buttoncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("dialog", "cancel");
                        dialog.dismiss();
                    }
                });
                dialog.show();

                // Fields on the UI to which we map
                return true;
            case R.id.add_file:
                Log.d("kategories", "add_file");
                createfile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void createfile(){
        Intent i = new Intent(this, kategoriesdetail.class);
        startActivity(i);
    }
}

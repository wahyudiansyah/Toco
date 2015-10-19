package rahmawati.eli.toco;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.database.Cursor;

import rahmawati.eli.toco.Fragment.Admin;
import rahmawati.eli.toco.Fragment.PlanetFragment;

import java.util.ArrayList;
import java.util.List;

public class Shop extends AppCompatActivity {
    private String[] menu = {"a","ascab","logout"};
    private String[] menuAdmin = {"shop","admin","logout"};
    private ActionBarDrawerToggle mDrawerToggle;
    private String user;
    private String userRole;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private int Position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        //ambil username
        SQLiteDatabase database = openOrCreateDatabase("Toco.db", MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("Select username , role from User where login = 1", null);
        cursor.moveToFirst();
        user = cursor.getString(0);
        userRole = cursor.getString(1);
        cursor.close();
        database.close();
        Log.d("userRole", userRole);


        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        /*List<String> menu = new ArrayList<String>();
        for (int i=0; i< 100; i++){
            menu.add(Integer.toString(i));

        }*/



        if (userRole.equals("admin")){
            mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, menuAdmin));
            Log.d("userRole==\"admin\"", userRole);
        }
        else {
            mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, menu));
            Log.d("userRole==\"kasir\"", userRole);

        }

        Fragment fragment = new Admin();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, 3);
        fragment.setArguments(args);

        FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
        fragmentManager.replace(R.id.content_frame, fragment);
        fragmentManager.addToBackStack(null);
        fragmentManager.setTransition(fragmentManager.TRANSIT_FRAGMENT_FADE);
        fragmentManager.commit();


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("position",""+position);
                Log.d("selected",""+Position);
                if (position==Position){
                    mDrawerLayout.closeDrawer(mDrawerList);
                    return;
                }
                //Toast.makeText(Shop.this, mDrawerList.getAdapter().getItem(position).getClass().toString(), Toast.LENGTH_SHORT).show();
                String m = String.valueOf(mDrawerList.getItemAtPosition(position));

                switch (m){
                    case "logout":
                        try {
                            SQLiteDatabase database = openOrCreateDatabase("Toco.db",MODE_PRIVATE,null);
                            //database.execSQL("update User set login=1 where username='eli' and password='rahmawati'");
                            database.execSQL("update User set login=0 where username='" +
                                    user +
                                    "'");
                            database.close();
                            Intent intent = new Intent(Shop.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "admin":
                        Fragment fragment = new Admin();
                        Bundle args = new Bundle();
                        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
                        fragment.setArguments(args);

                        FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
                        fragmentManager.replace(R.id.content_frame, fragment);
                        fragmentManager.addToBackStack(null);
                        fragmentManager.setTransition(fragmentManager.TRANSIT_FRAGMENT_FADE);
                        fragmentManager.commit();

                        Toast.makeText(getApplicationContext(),m,Toast.LENGTH_LONG).show();

                        // update selected item and title, then close the drawer
                        break;
                    case "shop":
                        Toast.makeText(getApplicationContext(),m,Toast.LENGTH_LONG).show();
                        break;

                }
                //mDrawerList.setItemChecked(position, true);
                Position = position;
                setTitle(m);
                mDrawerLayout.closeDrawer(mDrawerList);


            }
        });

        if(getSupportActionBar() != null){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);}

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.shop, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.kategories) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean draweropen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.setGroupVisible(0,!draweropen);
        return super.onPrepareOptionsMenu(menu);
    }
}

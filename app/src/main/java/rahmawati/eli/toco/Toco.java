package rahmawati.eli.toco;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
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
import rahmawati.eli.toco.Fragment.Barang;
import rahmawati.eli.toco.Fragment.Shop;

public class Toco extends AppCompatActivity {
    private String[] menu = {"shop","logout"};
    private String[] menuAdmin = {"shop","laporan","barang","admin","logout"};
    private ActionBarDrawerToggle mDrawerToggle;
    private String user;
    private String userRole;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private int Position;
    private Fragment fragment;
    private Bundle args = new Bundle();
    private FragmentTransaction fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toco);

        SQLiteDatabase database = openOrCreateDatabase("Toco.db", MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("Select username , role from User where login = 1", null);
        cursor.moveToFirst();
        user = cursor.getString(0);
        userRole = cursor.getString(1);
        cursor.close();
        database.close();
        //Log.d("userRole", userRole);


        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);




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

        fragment = new Shop();
        fragmentManager = getFragmentManager().beginTransaction();
        fragmentManager.replace(R.id.content_frame, fragment);
        fragmentManager.addToBackStack(null);
        fragmentManager.setTransition(fragmentManager.TRANSIT_FRAGMENT_FADE);
        fragmentManager.commit();


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position==Position){
                    mDrawerLayout.closeDrawer(mDrawerList);
                    Log.d("drawer","just close");
                    return;
                }

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
                            Intent intent = new Intent(getBaseContext(),MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "barang":
                        fragment = new Barang();
                        Bundle args = new Bundle();
                        fragmentManager = getFragmentManager().beginTransaction();
                        fragmentManager.replace(R.id.content_frame, fragment);
                        fragmentManager.addToBackStack(null);
                        fragmentManager.setTransition(fragmentManager.TRANSIT_FRAGMENT_FADE);
                        fragmentManager.commit();


                        break;
                    case "admin":
                        fragment = new Admin();
                        fragmentManager = getFragmentManager().beginTransaction();
                        fragmentManager.replace(R.id.content_frame, fragment);
                        fragmentManager.addToBackStack(null);
                        fragmentManager.setTransition(fragmentManager.TRANSIT_FRAGMENT_FADE);
                        fragmentManager.commit();

                        //Toast.makeText(getApplicationContext(),m,Toast.LENGTH_LONG).show();

                        // update selected item and title, then close the drawer
                        break;
                    case "shop":
                        fragment = new rahmawati.eli.toco.Fragment.Shop();


                        fragmentManager = getFragmentManager().beginTransaction();
                        fragmentManager.replace(R.id.content_frame, fragment);
                        fragmentManager.addToBackStack(null);
                        fragmentManager.setTransition(fragmentManager.TRANSIT_FRAGMENT_FADE);
                        fragmentManager.commit();

                        break;
                    case "laporan":
                        fragment = new rahmawati.eli.toco.Fragment.Laporan();
                        fragmentManager = getFragmentManager().beginTransaction();
                        fragmentManager.replace(R.id.content_frame, fragment);
                        fragmentManager.addToBackStack(null);
                        fragmentManager.setTransition(fragmentManager.TRANSIT_FRAGMENT_FADE);
                        fragmentManager.commit();
                        break;

                }
                //mDrawerList.setItemChecked(position, true);
                Log.d("drawer","new fragment");
                Position = position;
                if (Position==0){
                    setTitle(R.string.app_name);
                }
                else {
                    setTitle(m);
                }

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

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Toco", "The onStart() event");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Toco", "The onResume() event");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Toco", "The onPause() event");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Toco", "The onStop() event");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void pay(View view){Log.d("pay", "pay");}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
    }



}

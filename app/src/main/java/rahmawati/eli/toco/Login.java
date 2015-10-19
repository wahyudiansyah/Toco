package rahmawati.eli.toco;

/**
 * Created by eli on 08/10/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {

    private  EditText username;
    private  EditText password;
    private String Username;
    private String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //tombol login
        Button login = (Button)findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent signup = new Intent(getApplication().getBaseContext(),Signup.class);
                startActivity(signup);
                finish();*/
                Log.d("username", username.getText().toString());
                Log.d("password", password.getText().toString());
                Login();
            }
        });

        //EditText username
        username = (EditText)findViewById(R.id.username);

        //EditText password
        password = (EditText)findViewById(R.id.password);
        password.setTypeface(Typeface.DEFAULT);



    }

    protected void Login(){
        Username = username.getText().toString();
        Password = password.getText().toString();
        try {
            SQLiteDatabase database = openOrCreateDatabase("Toco.db",MODE_PRIVATE,null);
            //database.execSQL("update User set login=1 where username='eli' and password='rahmawati'");
            database.execSQL("update User set login=1 where username='" +
                    Username +
                    "' and password='" +
                    Password +
                    "'");
            database.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.shop, menu);
        return true;
    }



    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection
        if (id == R.id.kategories) {
            Log.d("This", "setting");
            Intent intent = new Intent(this, kategories.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Login","Restart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Login", "Pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Login", "Resume");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Login", "Destroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("nama","aar");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("instancestate",savedInstanceState.getString("nama"));
    }

    /*@Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }*/

}

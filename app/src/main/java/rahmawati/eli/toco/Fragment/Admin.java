package rahmawati.eli.toco.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import rahmawati.eli.toco.R;

import rahmawati.eli.toco.Admin.add_user;

/**
 * Created by eli on 14/10/15.
 */
public class Admin extends Fragment {

    public Admin() {
        // Empty constructor required for fragment subclasses
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.admin, container, false);

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.admin,menu);
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
}
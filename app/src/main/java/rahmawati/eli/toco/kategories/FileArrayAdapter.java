package rahmawati.eli.toco.kategories;

/**
 * Created by eli on 10/09/15.
 */
import java.util.List;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import rahmawati.eli.toco.R;

public class FileArrayAdapter extends ArrayAdapter<Item>{

    private Context c;
    private int id;
    private List<Item>items;

    public FileArrayAdapter(Context context, int textViewResourceId,
                            List<Item> objects) {
        super(context, textViewResourceId, objects);
        c = context;
        id = textViewResourceId;
        items = objects;
    }
    public Item getItem(int i)
    {
        return items.get(i);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(id, null);
        }

               /* create a new view of my layout and inflate it in the row */
        //convertView = ( RelativeLayout ) inflater.inflate( resource, null );

        final Item o = items.get(position);
        if (o != null) {
            TextView t1 = (TextView) v.findViewById(R.id.username);
            TextView t2 = (TextView) v.findViewById(R.id.stock);
            TextView t4 = (TextView) v.findViewById(R.id.harga);
            TextView t3 = (TextView) v.findViewById(R.id.TextViewDate);
                       /* Take the ImageView from layout and set the city's image */
            ImageView imageCity = (ImageView) v.findViewById(R.id.fd_Icon1);
            int imageResource = c.getResources().getIdentifier("drawable/directory_icon", null, c.getPackageName());
            //Drawable image = c.getResources().getDrawable(imageResource);
            imageCity.setImageResource(R.drawable.directory_icon);

            if(t1!=null)
                t1.setText(o.getFolder());
            if(t2!=null)
                t2.setText(Integer.toString(o.getChild()));
            if(t3!=null)
                t3.setText(o.getEditdate());
            if(t4!=null)
                t4.setText(o.getHarga());
        }
        return v;
    }
}
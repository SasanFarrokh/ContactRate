package ir.cdesign.contactrate;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sasan on 2016-08-21.
 */
public class ContactAdapter extends ArrayAdapter<View> {

    private Context context;

    ContentResolver contentR;

    private List<View> views;
    public static List<String[]> contacts;

    public ContactAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;

        contentR = context.getContentResolver();
        Cursor phones = contentR.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, "upper("+ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ") ASC");
        if (phones != null) {
            while (phones.moveToNext())
            {
                String[] contact = new String[2];
                contact[0] = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                contact[1] = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contacts.add(contact);
            }
            phones.close();
        }
        for ( String[] contact : contacts  ) {
            ViewGroup view = new LinearLayout(context);

            ImageView image = new ImageView(context);
            view.addView(image);

            TextView textView = new TextView(context);
            textView.setText(contact[0]);
            view.addView(textView);

            views.add(view);
        }
    }

    @Override
    public View getItem(int position) {
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }
}

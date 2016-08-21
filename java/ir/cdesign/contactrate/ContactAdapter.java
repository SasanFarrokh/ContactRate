package ir.cdesign.contactrate;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sasan on 2016-08-21.
 */
public class ContactAdapter extends ArrayAdapter<View> {

    private Context context;

    private List<View> views;

    public ContactAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;

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

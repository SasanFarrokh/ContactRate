package ir.cdesign.contactrate.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ir.cdesign.contactrate.ContactShow;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.models.AllModel;

/**
 * Created by amin pc on 21/08/2016.
 */
public class AllAdapter extends RecyclerView.Adapter<AllAdapter.AllHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ContentResolver contentR;
    public static List<String[]> contacts;

    public static String searchPattern = "";

    public AllAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;

        contacts = new ArrayList<>();
        contentR = context.getContentResolver();
        Cursor phones = contentR.query(ContactsContract.Contacts.CONTENT_URI,
                null,null,null, "upper("+ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ") ASC");
        if (phones != null) {
            while (phones.moveToNext())
            {
                if (Integer.parseInt(phones.getString(phones.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    String[] contact = new String[2];
                    contact[0] = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    if (contact[0] == null || contact[0].isEmpty() ||
                            !contact[0].toLowerCase().contains(searchPattern.toLowerCase())) continue;

                    contact[1] = phones.getString(phones.getColumnIndex(ContactsContract.Contacts._ID));

                    contacts.add(contact);
                }
            }
            phones.close();
        }
    }

    @Override
    public AllHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.all_row_layout, parent, false);
        AllHolder holder = new AllHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AllHolder holder, int position) {
        AllModel current = new AllModel();
        current.setTitle(contacts.get(position)[0]);
        holder.setData(current, position);
        holder.view.setTag(contacts.get(position)[1]);
        holder.view.setOnClickListener(new ItemClick());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class AllHolder extends RecyclerView.ViewHolder {

        TextView Title;
        AllModel current;
        int position;
        View view;

        public AllHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.all_tv);
            view = itemView;
        }

        public void setData(AllModel current, int position) {

            this.current = current;
            this.position = position;
            Title.setText(current.getTitle());
        }
    }

    public class ItemClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AllAdapter.this.context, ContactShow.class);
            intent.putExtra("contact_id",  v.getTag().toString());
            AllAdapter.this.context.startActivity(intent);
        }
    }

}

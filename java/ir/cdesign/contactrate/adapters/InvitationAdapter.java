package ir.cdesign.contactrate.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.cdesign.contactrate.ContactShow;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.models.AllModel;

/**
 * Created by amin pc on 21/08/2016.
 */
public class InvitationAdapter extends RecyclerView.Adapter<InvitationAdapter.RankHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ContentResolver contentR;
    public static List<String[]> contacts;

    public InvitationAdapter(Context context){
        this.inflater = LayoutInflater.from(context);
        this.context = context;

        contacts = new ArrayList<>();
        contentR = context.getContentResolver();
        Cursor phones = contentR.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, "upper("+ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ") ASC");
        if (phones != null) {
            while (phones.moveToNext())
            {
                String[] contact = new String[3];
                contact[0] = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                contact[1] = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contact[2] = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                contacts.add(contact);
            }
            phones.close();
        }
    }

    @Override
    public RankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.all_row_layout, parent, false);
        RankHolder holder = new RankHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RankHolder holder, int position) {
        AllModel current = new AllModel();
        current.setTitle(contacts.get(position)[0]);
        holder.setData(current, position);
        holder.view.setTag(contacts.get(position)[2]);
        holder.view.setOnClickListener(new ItemClick());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class RankHolder extends RecyclerView.ViewHolder {

        TextView Title;
        AllModel current;
        int position;
        View view;

        public RankHolder(View itemView) {
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
            Intent intent = new Intent(InvitationAdapter.this.context, ContactShow.class);
            intent.putExtra("contact_id",  v.getTag().toString());
            InvitationAdapter.this.context.startActivity(intent);
        }
    }}

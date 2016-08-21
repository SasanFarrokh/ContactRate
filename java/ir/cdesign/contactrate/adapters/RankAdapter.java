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
import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.MainActivity;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.models.AllModel;

/**
 * Created by amin pc on 21/08/2016.
 */
public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankHolder> {

    private LayoutInflater inflater;
    private Context context;
    public static List<Object[]> contacts;

    public RankAdapter(Context context){
        this.inflater = LayoutInflater.from(context);
        this.context = context;

        contacts = new ArrayList<>();

        contacts = DatabaseCommands.getInstance().getContactsForRank();
    }

    @Override
    public RankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rank_row_layout, parent, false);
        RankHolder holder = new RankHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RankHolder holder, int position) {
        AllModel current = new AllModel();
        current.setTitle((String) contacts.get(position)[0]);
        holder.setData(current, position);
        ((TextView) holder.view.findViewById(R.id.rank_rank)).setText(String.valueOf(position+1));
        ((TextView) holder.view.findViewById(R.id.rank_point)).setText(String.valueOf(contacts.get(position)[1]));
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
            Title = (TextView) itemView.findViewById(R.id.rank_tv);
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
            /*Intent intent = new Intent(RankAdapter.this.context, ContactShow.class);
            intent.putExtra("contact_id",  v.getTag().toString());
            RankAdapter.this.context.startActivity(intent);*/
        }
    }
}

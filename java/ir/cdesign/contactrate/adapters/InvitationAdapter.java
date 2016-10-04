package ir.cdesign.contactrate.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ir.cdesign.contactrate.ContactShowInvite;
import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.MainActivity;
import ir.cdesign.contactrate.NewTaskActivity;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.models.AllModel;

/**
 * Created by amin pc on 21/08/2016.
 */
public class InvitationAdapter extends RecyclerView.Adapter<InvitationAdapter.RankHolder> {

    private LayoutInflater inflater;
    private Context context;
    public List<Object[]> contacts;

    public boolean taskAdder = false;

    public InvitationAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        contacts = DatabaseCommands.getInstance().getContactsForInvitation();

        if (context instanceof MainActivity)
            taskAdder = ((MainActivity) context).getIntent().getBooleanExtra("addtask", false);
    }

    @Override
    public RankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.invite_row_layout, parent, false);
        return new RankHolder(view);
    }

    @Override
    public void onBindViewHolder(RankHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    private final View.OnClickListener itemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            if (taskAdder) {
                intent = new Intent(InvitationAdapter.this.context, NewTaskActivity.class);
            } else {
                intent = new Intent(InvitationAdapter.this.context, ContactShowInvite.class);
            }
            intent.putExtra("contact_id", Long.parseLong(v.getTag().toString()));
            InvitationAdapter.this.context.startActivity(intent);
        }
    };

    public class RankHolder extends RecyclerView.ViewHolder {

        TextView title, point;
        int position;
        View view;

        public RankHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.invite_tv);
            view = itemView;
            point = (TextView) view.findViewById(R.id.invite_point);
        }

        public void setData(int position) {

            this.position = position;
            title.setText((String) contacts.get(position)[0]);
            view.setOnClickListener(itemClick);
            point.setText(String.valueOf(contacts.get(position)[4]));
            view.setTag(contacts.get(position)[3]);
        }
    }
}

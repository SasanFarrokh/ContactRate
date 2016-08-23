package ir.cdesign.contactrate.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.models.ContactShowModel;
import ir.cdesign.contactrate.models.TaskModel;

/**
 * Created by amin pc on 23/08/2016.
 */
public class ContactShowAdapter extends RecyclerView.Adapter<ContactShowAdapter.ContactHolder> {
    private ArrayList<ContactShowModel> ContactModels;
    private List<HashMap> invites;
    private LayoutInflater layoutInflater;
    private Context context;
    private long contactId;

    public ContactShowAdapter(Context context, long contactId) {
        this.contactId = contactId;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;

        //DatabaseCommands.getInstance().addInvite(contactId,1,"s",200);
        invites = DatabaseCommands.getInstance().getInvite(2, (int) contactId);
        Log.i("invite", String.valueOf(invites.size()));
        Log.i("invite / id", String.valueOf(contactId));
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.task_contact_layout, parent, false);
        ContactHolder holder = new ContactHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        ContactShowModel current = new ContactShowModel();
        holder.setData(current, position);
    }

    @Override
    public int getItemCount() {
        return invites.size();
    }

    public class ContactHolder extends RecyclerView.ViewHolder {

        TextView Title;
        ImageView imageView;
        CheckBox checkBox;
        View view;
        ContactShowModel current;

        int position;

        public ContactHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.task_title);
            imageView = (ImageView) itemView.findViewById(R.id.task_img);
            checkBox = (CheckBox) itemView.findViewById(R.id.task_checkbox);
            view = itemView;
        }


        public void setData(ContactShowModel current, int position) {

            HashMap invite = invites.get(position);

            this.current = current;
            this.position = position;
            this.imageView.setImageResource(ContactShowModel.getImages()[(int) invite.get("type")]);
            this.Title.setText(ContactShowModel.getTitles()[(int) invite.get("type")]);
            this.checkBox.setChecked(((int) invite.get("active") != 0));
        }
    }
}

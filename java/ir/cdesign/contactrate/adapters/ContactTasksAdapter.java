package ir.cdesign.contactrate.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.TaskEditToDb;
import ir.cdesign.contactrate.models.ContactShowModel;

/**
 * Created by Sasan on 2016-08-25.
 */
public class ContactTasksAdapter extends ArrayAdapter {

    private List<HashMap> invites;

    public ContactTasksAdapter(Context context) {
        super(context, -1);
        invites = DatabaseCommands.getInstance().getInvite(0,0);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        HashMap invite = invites.get(invites.size()-position-1);

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(R.layout.task_contact_layout, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.task_img);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.task_title);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.task_checkbox);
            viewHolder.checkBox.setChecked(((int) invite.get("active") != 0));
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContactTasksAdapter.ViewHolder viewHolder = (ContactTasksAdapter.ViewHolder) v.getTag();
                    getContext().startActivity(new Intent(getContext(), TaskEditToDb.class).putExtra("invite_id",viewHolder.id));
                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (invite != null) {
            viewHolder.imageView.setImageResource(ContactShowModel.getImages()[(int) invite.get("type")-1]);
            viewHolder.textView.setText(ContactShowModel.getTitles()[(int) invite.get("type")-1]);
            viewHolder.id = (int) invite.get("id");
            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ViewHolder viewHolder = (ViewHolder) ((View) buttonView.getParent()).getTag();
                    DatabaseCommands.getInstance().activateInvite(viewHolder.id,isChecked);
                    Log.i("sasan","checked " + viewHolder.id);
                }
            });
        }
        viewHolder.position = position;

        return convertView;
    }

    @Override
    public int getCount() {
        return invites.size();
    }

    public static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public CheckBox checkBox;
        public int position;
        public int id;
    }

}

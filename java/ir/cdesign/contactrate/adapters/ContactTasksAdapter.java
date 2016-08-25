package ir.cdesign.contactrate.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.models.ContactShowModel;

/**
 * Created by Sasan on 2016-08-25.
 */
public class ContactTasksAdapter extends ArrayAdapter {

    private List<HashMap> invites;

    public ContactTasksAdapter(Context context, int resource) {
        super(context, resource);
        invites = DatabaseCommands.getInstance().getInvite(0,0);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(R.layout.task_contact_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.position = position;
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.task_img);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.task_title);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.task_checkbox);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HashMap invite = invites.get(position);

        if (invite != null) {
            viewHolder.imageView.setImageResource(ContactShowModel.getImages()[(int) invite.get("type")-1]);
            viewHolder.textView.setText(ContactShowModel.getTitles()[(int) invite.get("type")-1]);
            viewHolder.checkBox.setChecked(((int) invite.get("active") != 0));
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return invites.size();
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
        CheckBox checkBox;
        int position;
    }

}

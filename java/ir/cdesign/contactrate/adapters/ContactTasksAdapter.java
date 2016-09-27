package ir.cdesign.contactrate.adapters;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import ir.cdesign.contactrate.ContactShowInvite;
import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.TaskEditToDb;
import ir.cdesign.contactrate.models.ContactShowModel;

/**
 * Created by Sasan on 2016-08-25.
 */
public class ContactTasksAdapter extends ArrayAdapter {

    private List<HashMap> invites;
    private List<Object[]> contacts;
    long contactId;

    public ContactTasksAdapter(Context context, long id) {
        super(context, -1);
        contactId = id;
        if (id == 0)
            invites = DatabaseCommands.getInstance(context).getInvite(5, 0);
        else
            invites = DatabaseCommands.getInstance(context).getInvite(2, id);
        contacts = DatabaseCommands.getInstance().getContactsForInvitation();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        final HashMap invite = invites.get(invites.size() - position - 1);

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(R.layout.task_contact_layout, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.id = (long) invite.get("id");
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.task_img);
            viewHolder.title = (TextView) convertView.findViewById(R.id.task_title);
            viewHolder.subtitle = (TextView) convertView.findViewById(R.id.task_subtitle);
            viewHolder.completed = (TextView) convertView.findViewById(R.id.task_completed);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.task_checkbox);
            viewHolder.checkBox.setChecked(((int) invite.get("active") != 0));
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContactTasksAdapter.ViewHolder viewHolder = (ContactTasksAdapter.ViewHolder) v.getTag();
                    getContext().startActivity(new Intent(getContext(), TaskEditToDb.class).putExtra("invite_id", viewHolder.id));
                }
            });
            convertView.setTag(viewHolder);
            final View finalConvertView = convertView;
            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                    alertBuilder.setItems(new String[]{
                            "Done", "Move On", "Edit", "Delete"
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 3:
                                    DatabaseCommands.getInstance(getContext()).removeInvite(((ViewHolder) v.getTag()).id);
                                    v.animate()
                                            .alpha(0f).translationX(getContext().getResources().getDimension(R.dimen.swipeMove))
                                            .setListener(new TasksAdapter.SimpleAnimationEndListener() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    ((ListView) v.getParent()).setAdapter(new ContactTasksAdapter(getContext(), contactId));
                                                }
                                            })
                                            .start();
                                    break;
                                case 0:
                                    DatabaseCommands.getInstance().activateInvite(viewHolder.id, true);
                                    ((ListView) v.getParent()).setAdapter(new ContactTasksAdapter(getContext(), contactId));
                                    break;
                                case 2:
                                    finalConvertView.callOnClick();
                                    break;
                                case 1:
                                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                                    alertBuilder.setItems(new String[]{
                                            "Tomorrow", "3 Days Later", "Next Week"
                                    }, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case 0:
                                                    DatabaseCommands.getInstance(getContext()).moveOnInvite(viewHolder.id,
                                                            86400000L);
                                                    break;
                                                case 1:
                                                    DatabaseCommands.getInstance(getContext()).moveOnInvite(viewHolder.id,
                                                            86400000L * 3);
                                                    break;
                                                case 2:
                                                    DatabaseCommands.getInstance(getContext()).moveOnInvite(viewHolder.id,
                                                            86400000L * 7);
                                                    break;
                                            }
                                            ((ListView) v.getParent()).setAdapter(new ContactTasksAdapter(getContext(), contactId));
                                        }
                                    });
                                    alertBuilder.show();
                                    break;
                            }
                        }
                    });
                    alertBuilder.show();
                    return true;
                }
            });
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (invite != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis((long) invite.get("timestamp"));

            String contactName = "Unknown";
            for (Object[] contact : contacts) {
                if ((long) contact[3] == (long) invite.get("contact")) {
                    contactName = (String) contact[0];
                }
            }

            viewHolder.imageView.setImageResource(ContactShowModel.getImages()[(int) invite.get("type") - 1]);
            viewHolder.title.setText(ContactShowModel.getTitles()[(int) invite.get("type") - 1]);
            viewHolder.subtitle.setText("With " +
                    contactName + " at : " +
                    calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)
            );
            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final View view = (View) buttonView.getParent();
                    final ViewHolder viewHolder = (ViewHolder) ((View) view.getParent()).getTag();
                    DatabaseCommands.getInstance().activateInvite(viewHolder.id, isChecked);
                    viewHolder.completed.setVisibility(View.VISIBLE);
                    view.animate()
                            .alpha(0f).translationX(getContext().getResources().getDimension(R.dimen.swipeMove))
                            .setListener(new TasksAdapter.SimpleAnimationEndListener() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    ((ListView) view.getParent().getParent()).setAdapter(new ContactTasksAdapter(getContext(), contactId));
                                }
                            })
                            .start();

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
        public TextView title, subtitle,completed;
        public CheckBox checkBox;
        public int position;
        public long id;
    }

}

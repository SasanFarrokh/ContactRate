package ir.cdesign.contactrate.adapters;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.TaskEditToDb;
import ir.cdesign.contactrate.models.ContactShowModel;
import ir.cdesign.contactrate.tasks.TasksActivity;

/**
 * Created by Sasan on 2016-08-25.
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskHolder> {

    public static final int CM_TASKS = 1;
    public static final int PEND_TASKS = 0;
    private final LayoutInflater layoutInflater;

    public int mode;

    private Context context;

    private List<HashMap> invites;
    private List<Object[]> contacts;

    Calendar calendar = Calendar.getInstance();

    public TasksAdapter(Context context, int mode) {
        this.context = context;
        this.mode = mode;
        DatabaseCommands db = DatabaseCommands.getInstance(context);
        switch (mode) {

            case PEND_TASKS:
                invites = db.getInvite(3, 0);
                break;
            case CM_TASKS:
                invites = db.getInvite(4, 0);
                break;

        }
        contacts = db.getContactsForInvitation();
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.task_contact_layout, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return invites.size();
    }


    public class TaskHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView title, subtitle, completed;
        public CheckBox checkBox;
        public int position;
        public long id;
        public View view,strikeThrough;

        public TaskHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.task_img);
            title = (TextView) itemView.findViewById(R.id.task_title);
            subtitle = (TextView) itemView.findViewById(R.id.task_subtitle);
            completed = (TextView) itemView.findViewById(R.id.task_completed);
            checkBox = (CheckBox) itemView.findViewById(R.id.task_checkbox);
            //strikeThrough = itemView.findViewById(R.id.task_strike);
        }

        public void setData(int position) {
            this.position = position;
            HashMap invite = TasksAdapter.this.invites.get(position);
            calendar.setTimeInMillis((long) invite.get("timestamp"));
            String contactName = "Unknown";
            for (Object[] contact : contacts) {
                if ((long) contact[3] == (long) invite.get("contact")) {
                    contactName = (String) contact[0];
                }
            }
            id = (long) invite.get("id");
            imageView.setImageResource(ContactShowModel.getImages()[(int) invite.get("type") - 1]);
            title.setText(ContactShowModel.getTitles()[(int) invite.get("type") - 1]);
            subtitle.setText("With " +
                    contactName + " at : " +
                    calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)
            );
            checkBox.setChecked(((int) invite.get("active") != 0));
            if (mode == CM_TASKS) {
                checkBox.setEnabled(false);
                title.append(" - Completed");
            }
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final View view = (View) buttonView.getParent();
                    TaskHolder viewHolder = (TaskHolder) ((View) view.getParent()).getTag();
                    DatabaseCommands.getInstance().activateInvite(viewHolder.id, isChecked);
                    viewHolder.completed.setVisibility(View.VISIBLE);
                    view.animate()
                            .alpha(0f).translationX(context.getResources().getDimension(R.dimen.swipeMove))
                            .setListener(new SimpleAnimationEndListener() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    try {
                                        ((RecyclerView) view.getParent().getParent()).setAdapter(new TasksAdapter(context, mode));
                                    } catch (Exception ignore) {
                                    }
                                    ((TasksActivity) view.getContext()).viewPager.getAdapter().notifyDataSetChanged();
                                }
                            })
                            .start();


                }
            });
            if (mode == PEND_TASKS) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TaskHolder viewHolder = (TaskHolder) v.getTag();
                        context.startActivity(new Intent(context, TaskEditToDb.class).putExtra("invite_id", viewHolder.id));
                    }
                });
            }
            view.setTag(this);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    String[] options = (mode == PEND_TASKS)?new String[]{ "Done", "Move On", "Edit", "Delete"}:
                            new String[]{ "Delete"};
                    alertBuilder.setItems( options
                    , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 3:
                                    DatabaseCommands.getInstance(context).removeInvite(((TaskHolder) v.getTag()).id);
                                    v.animate()
                                            .alpha(0f).translationX(context.getResources().getDimension(R.dimen.swipeMove))
                                            .setListener(new SimpleAnimationEndListener() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    ((RecyclerView) v.getParent()).setAdapter(new TasksAdapter(context, mode));
                                                }
                                            })
                                            .start();
                                    break;
                                case 0:
                                    if (mode == PEND_TASKS) {
                                        DatabaseCommands.getInstance().activateInvite(id, true);
                                        ((RecyclerView) v.getParent()).setAdapter(new TasksAdapter(context, mode));
                                    } else {
                                        DatabaseCommands.getInstance().removeInvite(id);
                                        v.animate()
                                                .alpha(0f).translationX(context.getResources().getDimension(R.dimen.swipeMove))
                                                .setListener(new SimpleAnimationEndListener() {
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        ((RecyclerView) v.getParent()).setAdapter(new TasksAdapter(context, mode));
                                                    }
                                                })
                                                .start();
                                    }
                                    break;
                                case 2:
                                    view.callOnClick();
                                    break;
                                case 1:
                                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                                    alertBuilder.setItems(new String[]{
                                            "Tomorrow", "3 Days Later", "Next Week"
                                    }, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case 0:
                                                    DatabaseCommands.getInstance(context).moveOnInvite(id,
                                                            86400000L);
                                                    break;
                                                case 1:
                                                    DatabaseCommands.getInstance(context).moveOnInvite(id,
                                                            86400000L * 3);
                                                    break;
                                                case 2:
                                                    DatabaseCommands.getInstance(context).moveOnInvite(id,
                                                            86400000L * 7);
                                                    break;
                                            }
                                            ((RecyclerView) v.getParent()).setAdapter(new TasksAdapter(context, mode));
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
        }
    }

    public static abstract class SimpleAnimationEndListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}

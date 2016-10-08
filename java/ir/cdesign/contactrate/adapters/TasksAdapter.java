package ir.cdesign.contactrate.adapters;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.TaskEditToDb;
import ir.cdesign.contactrate.models.ContactShowModel;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.utils.PersianCalendar;
import ir.cdesign.contactrate.tasks.TasksActivity;
import ir.cdesign.contactrate.utilities.CalendarStrategy;
import ir.cdesign.contactrate.utilities.Settings;

/**
 * Created by Sasan on 2016-08-25.
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskHolder> {

    public static final int CM_TASKS = 1;
    public static final int PEND_TASKS = 0;
    public static final int TYPE_TASK = 0;
    public static final int TYPE_SEPERATOR = 1;
    private final LayoutInflater layoutInflater;

    private List<Integer> types;

    public int mode;

    private Context context;

    private List<HashMap> invites;
    private List<Object[]> contacts;

    CalendarStrategy calendar = new CalendarStrategy(new PersianCalendar());

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
        types = new ArrayList<>();
        int dayTemp = -1;
        boolean todaySet = false;
        if (!invites.isEmpty()) {
            for (int i = 0; i<invites.size(); i++) {
                PersianCalendar persianCalendar = new PersianCalendar();
                persianCalendar.setTimeInMillis((Long) invites.get(i).get("timestamp"));
                int day = persianCalendar.get(Calendar.DAY_OF_MONTH);
                if (day != dayTemp) {
                    String dateStr;
                    if (Settings.calendarType == 1) {
                        dateStr = persianCalendar.getPersianLongDate();
                    } else {
                        dateStr = persianCalendar.get(Calendar.YEAR) + "-"
                                +(persianCalendar.getDisplayName(Calendar.MONTH,Calendar.SHORT, Locale.ENGLISH))+"-"+day
                                + "  "+persianCalendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.SHORT, Locale.ENGLISH);
                    }
                    if (!todaySet) {
                        if (persianCalendar.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)) {
                            dateStr += " - " + context.getResources().getString(R.string.today);
                            todaySet = true;
                        }
                    }

                    HashMap<String, String> date = new HashMap<>();
                    date.put("date", dateStr);
                    invites.add(i, date);
                    dayTemp = day;
                    types.add(i);
                }
            }
        }
        contacts = db.getContactsForRank();
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = layoutInflater.inflate(R.layout.task_contact_layout, parent, false);
            return new TaskHolder(view);
        }
        else {
            view = layoutInflater.inflate(R.layout.task_separator, parent, false);
            return new TaskSeperatorHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return invites.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (types.contains(position)) ? TYPE_SEPERATOR : TYPE_TASK;
    }

    public class TaskHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView title, subtitle, completed;
        public CheckBox checkBox;
        public int position;
        public long id;
        public View view, strikeThrough;

        public TaskHolder(View itemView) {
            super(itemView);
            if (!(this instanceof TaskSeperatorHolder)) {
                view = itemView;
                imageView = (ImageView) itemView.findViewById(R.id.task_img);
                title = (TextView) itemView.findViewById(R.id.task_title);
                subtitle = (TextView) itemView.findViewById(R.id.task_subtitle);
                completed = (TextView) itemView.findViewById(R.id.task_completed);
                checkBox = (CheckBox) itemView.findViewById(R.id.task_checkbox);
                //strikeThrough = itemView.findViewById(R.id.task_strike);
            }
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
            subtitle.setText(context.getString(R.string.with) +
                    contactName + context.getString(R.string.task_at) + " : " +
                    new DecimalFormat("00").format(calendar.get(Calendar.HOUR_OF_DAY)) +
                    ":" + new DecimalFormat("00").format(calendar.get(Calendar.MINUTE))
            );
            boolean active = ((int) invite.get("active") != 0);
            checkBox.setChecked(active);
            if (mode == CM_TASKS) {
                checkBox.setEnabled(false);
                title.append(context.getString(R.string.task_completed));
            }
            checkBox.setOnCheckedChangeListener(itemCheck);
            view.setTag(this);

            if (!active) {
                view.setOnClickListener(pendTaskClick);
                view.setOnLongClickListener(pendTaskLongClick);
            } else {
                view.setOnLongClickListener(doneTaskLongClick);
            }
        }
    }

    private final View.OnClickListener pendTaskClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TaskHolder viewHolder = (TaskHolder) v.getTag();
            context.startActivity(new Intent(context, TaskEditToDb.class).putExtra("invite_id", viewHolder.id));
        }
    };
    private final View.OnLongClickListener pendTaskLongClick = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(final View v) {
            final TaskHolder taskHolder = (TaskHolder) v.getTag();
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            String[] options = new String[]{context.getString(R.string.task_done), context.getString(R.string.task_move_on),
                    context.getString(R.string.task_edit), context.getString(R.string.task_delete)};
            alertBuilder.setItems(options
                    , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 3:
                                    DatabaseCommands.getInstance(context).removeInvite(taskHolder.id);
                                    v.animate()
                                            .alpha(0f).translationX(context.getResources().getDimension(R.dimen.swipeMove))
                                            .setListener(new SimpleAnimationEndListener() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    try {
                                                        ((RecyclerView) v.getParent()).setAdapter(new TasksAdapter(context, mode));
                                                    } finally {
                                                        ((TasksActivity) v.getContext()).viewPager.getAdapter().notifyDataSetChanged();
                                                    }
                                                }
                                            })
                                            .start();
                                    break;
                                case 0:
                                    DatabaseCommands.getInstance().activateInvite(taskHolder.id, true);
                                    try {
                                        ((RecyclerView) v.getParent()).setAdapter(new TasksAdapter(context, mode));
                                    } finally {
                                        ((TasksActivity) v.getContext()).viewPager.getAdapter().notifyDataSetChanged();
                                    }
                                    break;
                                case 2:
                                    v.callOnClick();
                                    break;
                                case 1:
                                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                                    alertBuilder.setItems(new String[]{
                                            context.getString(R.string.tomorrow), context.getString(R.string.task_threedays),
                                            context.getString(R.string.task_nextweek)
                                    }, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case 0:
                                                    DatabaseCommands.getInstance(context).moveOnInvite(taskHolder.id,
                                                            86400000L);
                                                    break;
                                                case 1:
                                                    DatabaseCommands.getInstance(context).moveOnInvite(taskHolder.id,
                                                            86400000L * 3);
                                                    break;
                                                case 2:
                                                    DatabaseCommands.getInstance(context).moveOnInvite(taskHolder.id,
                                                            86400000L * 7);
                                                    break;
                                            }
                                            try {
                                                ((RecyclerView) v.getParent()).setAdapter(new TasksAdapter(context, mode));
                                            } finally {
                                                ((TasksActivity) v.getContext()).viewPager.getAdapter().notifyDataSetChanged();
                                            }
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
    };

    private final View.OnLongClickListener doneTaskLongClick = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(final View v) {
            final TaskHolder taskHolder = (TaskHolder) v.getTag();
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            String[] options = new String[]{context.getString(R.string.task_undone), context.getString(R.string.task_delete)};
            alertBuilder.setItems(options
                    , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    DatabaseCommands.getInstance().activateInvite(taskHolder.id,false);
                                    v.animate()
                                            .alpha(0f).translationX(context.getResources().getDimension(R.dimen.swipeMove))
                                            .setListener(new SimpleAnimationEndListener() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    try {
                                                        ((RecyclerView) v.getParent()).setAdapter(new TasksAdapter(context, mode));
                                                    } finally {
                                                        ((TasksActivity) v.getContext()).viewPager.getAdapter().notifyDataSetChanged();
                                                    }
                                                }
                                            })
                                            .start();
                                    break;
                                case 1:
                                    DatabaseCommands.getInstance().removeInvite(taskHolder.id);
                                    v.animate()
                                            .alpha(0f).translationX(context.getResources().getDimension(R.dimen.swipeMove))
                                            .setListener(new SimpleAnimationEndListener() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    try {
                                                        ((RecyclerView) v.getParent()).setAdapter(new TasksAdapter(context, mode));
                                                    } finally {
                                                        ((TasksActivity) v.getContext()).viewPager.getAdapter().notifyDataSetChanged();
                                                    }
                                                }
                                            })
                                            .start();
                            }
                        }
                    });
            alertBuilder.show();
            return true;
        }
    };

    private final CompoundButton.OnCheckedChangeListener itemCheck = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            final View view = (View) buttonView.getParent();
            TaskHolder viewHolder = (TaskHolder) ((View) view.getParent()).getTag();
            DatabaseCommands.getInstance().activateInvite(viewHolder.id, isChecked);
            viewHolder.completed.setVisibility(View.VISIBLE);
            viewHolder.completed.setAlpha(0f);
            viewHolder.completed.animate().alpha(1).setDuration(250).start();
            view.animate()
                    .alpha(0f).translationX(context.getResources().getDimension(R.dimen.swipeMove))
                    .setListener(new SimpleAnimationEndListener() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            try {
                                ((RecyclerView) view.getParent().getParent()).setAdapter(new TasksAdapter(context, mode));
                            } finally {
                                ((TasksActivity) view.getContext()).viewPager.getAdapter().notifyDataSetChanged();
                            }
                        }
                    })
                    .start();


        }
    };

    private class TaskSeperatorHolder extends TaskHolder {

        private TextView dateText;

        public TaskSeperatorHolder(View itemView) {
            super(itemView);
            dateText = (TextView) itemView.findViewById(R.id.task_date);
        }

        @Override
        public void setData(int position) {
            dateText.setText((String) TasksAdapter.this.invites.get(position).get("date"));
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

package ir.cdesign.contactrate.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.TaskEditToDb;
import ir.cdesign.contactrate.models.TaskModel;

/**
 * Created by amin pc on 23/08/2016.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    private ArrayList<TaskModel> taskModels;
    private LayoutInflater layoutInflater;
    private Context context;
    private long id;


    public TaskAdapter(Context context, ArrayList<TaskModel> task, long contactId) {
        this.taskModels = task;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        id = contactId;
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.task_layout, parent, false);
        TaskHolder holder = new TaskHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        TaskModel current = taskModels.get(position);
        holder.setData(current, position);
        holder.imageView.setTag(position+1);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TaskEditToDb.class);
                intent.putExtra("contact_id", id);
                intent.putExtra("type",(int) v.getTag());
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskModels.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder {
        TextView Title;
        ImageView imageView;
        TaskModel current;
        View view;
        int position;

        public TaskHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.task_title);
            imageView = (ImageView) itemView.findViewById(R.id.task_img);
            view = itemView;
        }


        public void setData(TaskModel current, int position) {

            this.current = current;
            this.position = position;
            this.imageView.setImageResource(current.getImageId());
            this.Title.setText(current.getTitle());

        }
    }

}

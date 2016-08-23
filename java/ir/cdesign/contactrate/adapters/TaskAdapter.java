package ir.cdesign.contactrate.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.models.TaskModel;

/**
 * Created by amin pc on 23/08/2016.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    private ArrayList<TaskModel> taskModels;
    private LayoutInflater layoutInflater;
    private Context context;


    public TaskAdapter(Context context, ArrayList<TaskModel> task) {
        this.taskModels = task;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return taskModels.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder {
        TextView Title;
        CircleImageView imageView;
        TaskModel current;
        int position;

        public TaskHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.task_title);
            imageView = (CircleImageView) itemView.findViewById(R.id.task_img);
        }


        public void setData(TaskModel current, int position) {

            this.current = current;
            this.position = position;
            this.imageView.setImageResource(current.getImageId());
            this.Title.setText(current.getTitle());

        }
    }
}

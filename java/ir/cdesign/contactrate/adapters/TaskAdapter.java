package ir.cdesign.contactrate.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by amin pc on 23/08/2016.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class TaskHolder extends RecyclerView.ViewHolder {
        public TaskHolder(View itemView) {
            super(itemView);
        }
    }
}

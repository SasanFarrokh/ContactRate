package ir.cdesign.contactrate.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.R;

/**
 * Created by Sasan on 2016-09-10.
 */
public class VisionAdapter extends RecyclerView.Adapter<VisionAdapter.VisionHolder> {

    private Context context;
    private List<HashMap> data;


    public VisionAdapter(Context context) {
        data = DatabaseCommands.getInstance().getVision(0);
        this.context = context;
        Log.i("sasan","data size : " + data.size());
    }

    @Override
    public VisionAdapter.VisionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vision_row, parent, false);
        VisionHolder holder = new VisionHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(VisionAdapter.VisionHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class VisionHolder extends RecyclerView.ViewHolder {
        private ProgressBar progress;
        private TextView subject;
        private View view;
        int position;
        int id;

        public VisionHolder(View itemView) {
            super(itemView);
            subject = (TextView) itemView.findViewById(R.id.vision_subject);
            progress = (ProgressBar) itemView.findViewById(R.id.vision_progress);
            view = itemView;
        }

        public void setData(final int position) {
            HashMap vision = data.get(position);
            subject.setText((String) vision.get("subject"));
            id = (int) vision.get("id");

            progress.setProgress(60);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setItems(new String[]{
                            "Delete"
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                default:
                                    DatabaseCommands.getInstance().removeVision(id);
                                    notifyDataSetChanged();
                                    try {
                                        (((AdapterUpdate) context)).updateRecycler(position);
                                    } catch (Exception ignore) {}
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
    public interface AdapterUpdate {
        void updateRecycler(int pos);
    }
}

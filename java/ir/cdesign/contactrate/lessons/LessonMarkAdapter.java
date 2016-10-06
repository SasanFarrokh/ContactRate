package ir.cdesign.contactrate.lessons;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.R;

/**
 * Created by amin pc on 15/09/2016.
 */
public class LessonMarkAdapter extends RecyclerView.Adapter<LessonMarkAdapter.Holder> {

    Context context;
    LayoutInflater inflater;
    List<LessonModel> list = new ArrayList<>();

    public static final String LESSON_ID = "lesson_id";

    public LessonMarkAdapter(Context context) {
        this.context = context;
        this.list = list;
        inflater = inflater.from(context);
        list = DatabaseCommands.getInstance(context).getLessons(0);
    }

    @Override
    public LessonMarkAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lesson_mark_row_layout, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(LessonMarkAdapter.Holder holder, int position) {
        LessonModel current = list.get(position);
        holder.setData(current, position);
    }

    public void notifyDataSet() {
        list = DatabaseCommands.getInstance(context).getLessons(0);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements
            View.OnClickListener,View.OnLongClickListener {

        long id;
        View view;
        TextView title, progress;
        ProgressBar progressBar;
        ImageView imageView;
        int position;

        public Holder(View itemView) {
            super(itemView);
            view = itemView;
            title = (TextView) itemView.findViewById(R.id.lessonMarkTitle);
            progress = (TextView) itemView.findViewById(R.id.progressCount);
            progressBar = (ProgressBar) itemView.findViewById(R.id.simpleProgressBar);
            imageView = (ImageView) itemView.findViewById(R.id.markImage);
        }

        public void setData(LessonModel current, int position) {
            this.position = position;
            this.id = current.id;
            title.setText(current.title);
            progress.setText(String.valueOf(current.getProgress()) + "%");
            progressBar.setProgress(current.getProgress());
            imageView.setImageBitmap(current.getImage());
//            progressBar
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,LessonPartActivity.class);
            intent.putExtra(LESSON_ID,id);
            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setItems(new String[]{"Remove"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseCommands.getInstance(context).removeLesson(id);
                    dialog.dismiss();
                    notifyDataSet();
                }
            });
            alert.show();
            return true;
        }
    }
}

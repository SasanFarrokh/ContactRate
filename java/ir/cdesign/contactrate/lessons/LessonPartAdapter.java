package ir.cdesign.contactrate.lessons;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.cdesign.contactrate.R;

/**
 * Created by amin pc on 19/09/2016.
 */
public class LessonPartAdapter extends RecyclerView.Adapter<LessonPartAdapter.Holder> {

    private LessonPartModel[] parts;
    LessonPartActivity activity;
    final LayoutInflater inflater;

    public LessonPartAdapter(LessonPartActivity activity, LessonPartModel[] parts) {
        this.activity = activity;
        this.parts = parts;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public LessonPartAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lesson_part_row_layout, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(LessonPartAdapter.Holder holder, int position) {
        LessonPartModel current = parts[position];
        holder.setData(current, position);
    }

    @Override
    public int getItemCount() {
        return parts.length;
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, number;
        ImageView image;
        View view;
        LessonPartModel current;
        int position;

        public Holder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.part_title);
            number = (TextView) itemView.findViewById(R.id.part_number);
            image = (ImageView) itemView.findViewById(R.id.part_image);
            view = itemView;
        }

        public void setData(LessonPartModel current, int position) {
            this.current = current;
            this.position = position;
            title.setText(current.title);
            number.setText(String.valueOf(position+1));
            if (current.image != null)
                image.setImageURI(current.image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            activity.lessonPartTitle.setText(current.title);
            activity.lessonPartBody.setText(current.body);
            activity.flipper.getChildAt(0).animate().setDuration(200).alpha(0).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    activity.flipper.getChildAt(1).setAlpha(0);
                    activity.flipper.setDisplayedChild(1);
                    activity.flipper.getChildAt(1).animate().setDuration(200).setListener(null).alpha(1).start();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();

            activity.readingLesson = true;
        }
    }
}

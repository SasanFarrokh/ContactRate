package ir.cdesign.contactrate.lessons;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import ir.cdesign.contactrate.R;

/**
 * Created by amin pc on 15/09/2016.
 */
public class LessonMarkAdapter extends RecyclerView.Adapter<LessonMarkAdapter.Holder>{

    Context context ;
    LayoutInflater inflater;
    List<LessonSubjectModel> list = new ArrayList<>();

    public LessonMarkAdapter(Context context , List<LessonSubjectModel> list ){
        this.context = context ;
        this.list = list ;
        inflater = inflater.from(context);
    }

    @Override
    public LessonMarkAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lesson_mark_row_layout , parent , false );
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(LessonMarkAdapter.Holder holder, int position) {
        LessonSubjectModel current = new LessonSubjectModel();
        holder.setData(current , position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView title , progress ;
        ProgressBar progressBar ;
        LessonSubjectModel current ;
        int position ;

        public Holder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.lessonMarkTitle);
            progress = (TextView) itemView.findViewById(R.id.progressCount);
            progressBar = (ProgressBar) itemView.findViewById(R.id.simpleProgressBar);

        }

        public void setData(LessonSubjectModel current, int position) {
            this.position = position;
            this.current = current ;
        }
    }
}

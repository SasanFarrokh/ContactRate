package ir.cdesign.contactrate.lessons;

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
 * Created by amin pc on 15/09/2016.
 */
public class LessonAllAdapter extends RecyclerView.Adapter<LessonAllAdapter.Holder>{

    Context context;
    List<LessonSubjectModel> list = new ArrayList<>();
    LayoutInflater inflater ;

    public LessonAllAdapter(Context context ,List<LessonSubjectModel> list){
        this.context = context ;
        this.list = list ;
        inflater = inflater.from(context);

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lesson_all_row_layout,parent ,false);
        Holder holder = new Holder(view);
        return holder ;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        LessonSubjectModel current = new LessonSubjectModel();
        holder.setData(current , position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView title , seenCount  ,point , author;
        ImageView image ;
        LessonSubjectModel current;
        int position ;

        public Holder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.allImage);
            title = (TextView) itemView.findViewById(R.id.lessonTitleText);
            seenCount = (TextView) itemView.findViewById(R.id.seenCount);
            point = (TextView) itemView.findViewById(R.id.points);
            author = (TextView) itemView.findViewById(R.id.authorText);

        }

        public void setData(LessonSubjectModel current, int position) {
            this.position = position ;
            this.current = current ;
        }
    }
}

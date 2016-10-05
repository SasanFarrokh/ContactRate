package ir.cdesign.contactrate.lessons;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.utilities.Application;

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
        LessonSubjectModel current = list.get(position);
        holder.setData(current , position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView title , seenCount  ,point , author;
        NetworkImageView image ;
        LessonSubjectModel current;
        int position ;

        ImageLoader imageLoader = Application.getInstance().getImageLoader();

        public Holder(View itemView) {
            super(itemView);
            image = (NetworkImageView) itemView.findViewById(R.id.allImage);
            title = (TextView) itemView.findViewById(R.id.lessonTitleText);
            seenCount = (TextView) itemView.findViewById(R.id.seenCount);
            point = (TextView) itemView.findViewById(R.id.lessonUnlockPoint);
            author = (TextView) itemView.findViewById(R.id.authorText);

        }

        public void setData(LessonSubjectModel current, int position) {
            this.position = position ;
            this.current = current ;
            title.setText(current.getTitle());
            seenCount.setText(String.valueOf(current.getSeenCount()));
            point.setText(String.valueOf(current.getUnlock()));
            author.setText(current.getAuthor());
            image.setImageUrl(  current.getImageUrl(),imageLoader);
        }
    }
}

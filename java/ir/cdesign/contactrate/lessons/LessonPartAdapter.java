package ir.cdesign.contactrate.lessons;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.cdesign.contactrate.R;

/**
 * Created by amin pc on 19/09/2016.
 */
public class LessonPartAdapter extends RecyclerView.Adapter<LessonPartAdapter.Holder>{

    List<LessonPartModel> list = new ArrayList<>();
    Context context ;
    LayoutInflater inflater ;

    public LessonPartAdapter(Context context , List<LessonPartModel> list){
        this.context = context ;
        this.list = list ;
        inflater = inflater.from(context);

    }

    @Override
    public LessonPartAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lesson_part_row_layout, parent , false );
        Holder holder = new Holder(view);
        return  holder ;
    }

    @Override
    public void onBindViewHolder(LessonPartAdapter.Holder holder, int position) {
        LessonPartModel current = list.get(position);
        holder.setData(current , position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView title;
        LessonPartModel current ;
        int position ;

        public Holder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.partTitle);
        }

        public void setData(LessonPartModel current, int position) {
            this.current = current ;
            this.position = position ;
            title.setText(current.title);

        }
    }
}

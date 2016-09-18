package ir.cdesign.contactrate.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ir.cdesign.contactrate.MainActivity;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.models.MedalModel;
import ir.cdesign.contactrate.models.TaskModel;

/**
 * Created by amin pc on 23/08/2016.
 */
public class MedalAdapter extends RecyclerView.Adapter<MedalAdapter.MedalHolder> {

    private ArrayList<MedalModel> medalModels;
    private LayoutInflater layoutInflater;
    private Context context;
//    final float scale = MainActivity.instance.getResources().getDisplayMetrics().density;


    public MedalAdapter(Context context, ArrayList<MedalModel> medals) {
        this.medalModels = medals;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public MedalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.medal_row_layout, parent, false);
//        int padding_in_dp = 18;
//        int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
//        view.setPadding(padding_in_px,padding_in_px,padding_in_px,padding_in_px);
        MedalHolder holder = new MedalHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MedalHolder holder, int position) {
        MedalModel current = medalModels.get(position);
        holder.setData(current, position);
    }

    @Override
    public int getItemCount() {
        return medalModels.size();
    }

    public class MedalHolder extends RecyclerView.ViewHolder {
        TextView Title;
        ImageView imageView;
        MedalModel current;
        int position;

        public MedalHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.task_title);
            imageView = (ImageView) itemView.findViewById(R.id.task_img);
        }


        public void setData(MedalModel current, int position) {

            this.current = current;
            this.position = position;
            this.imageView.setImageResource(current.getImageId());
            this.Title.setText(current.getTitle());

        }
    }
}

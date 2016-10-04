package ir.cdesign.contactrate.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.models.MedalModel;

/**
 * Created by amin pc on 23/08/2016.
 */
public class MedalAdapter extends RecyclerView.Adapter<MedalAdapter.MedalHolder> {

    private List<MedalModel> medalModels;
    private LayoutInflater layoutInflater;
    private Context context;


    public MedalAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;

        medalModels = DatabaseCommands.getInstance(context).getMedals(0);
        if (context instanceof MedalShow)
            ((MedalShow) context).show(medalModels.get(((MedalShow) context).getMedal()));
    }

    @Override
    public MedalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.medal_row_layout, parent, false);
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
        TextView title;
        ImageView imageView, lock;
        View view;
        MedalModel current;
        int position;

        public MedalHolder(View itemView) {
            super(itemView);
            view = itemView;
            title = (TextView) itemView.findViewById(R.id.medal_title);
            imageView = (ImageView) itemView.findViewById(R.id.medal_iv);
            lock = (ImageView) itemView.findViewById(R.id.medal_lock);
        }


        public void setData(final MedalModel current, int position) {
            this.current = current;
            this.position = position;
            imageView.setImageResource(current.imageId);
            title.setText(current.title);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context instanceof MedalShow)
                        ((MedalShow) context).show(current);
                }
            });

            if (current.progress >= current.completeMax) {
                achieved();
            } else {
                imageView.setColorFilter(0x77000000);
            }
        }

        public void achieved() {
            lock.setVisibility(View.GONE);
        }
    }

    public interface MedalShow {
        void show(MedalModel medal);
        int getMedal();
    }
}

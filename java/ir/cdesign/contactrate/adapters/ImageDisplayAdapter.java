package ir.cdesign.contactrate.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.models.ImageDisplayModel;
import ir.cdesign.contactrate.models.TaskModel;

/**
 * Created by amin pc on 06/09/2016.
 */
public class ImageDisplayAdapter extends RecyclerView.Adapter<ImageDisplayAdapter.holder> {

    private ArrayList<ImageDisplayModel> imageDisplayModels;
    private LayoutInflater layoutInflater;
    private Context context;

    public ImageDisplayAdapter(Context context, ArrayList<ImageDisplayModel> imageDisplayModels) {
        this.imageDisplayModels = imageDisplayModels;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ImageDisplayAdapter.holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.image_display_layout, parent, false);
        holder holder = new holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ImageDisplayAdapter.holder holder, int position) {
        ImageDisplayModel current = imageDisplayModels.get(position);
        holder.setData(current, position);
    }

    @Override
    public int getItemCount() {
        return imageDisplayModels.size();
    }

    public class holder extends RecyclerView.ViewHolder{

        ImageView imageView;
        ImageDisplayModel current;
        View view;
        int position;

        public holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            view = itemView;
        }

        public void setData(ImageDisplayModel current, int position) {
            this.current = current;
            this.position = position;
            this.imageView.setImageResource(current.getImageId());
        }
    }
}

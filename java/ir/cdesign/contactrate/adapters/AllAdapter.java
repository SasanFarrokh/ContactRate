package ir.cdesign.contactrate.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.models.AllModel;

/**
 * Created by amin pc on 21/08/2016.
 */
public class AllAdapter extends RecyclerView.Adapter<AllAdapter.AllHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<AllModel> models;

    public AllAdapter(Context context, ArrayList<AllModel> models) {
        this.models = models;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public AllHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.all_row_layout, parent, false);
        AllHolder holder = new AllHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AllHolder holder, int position) {
        AllModel current = models.get(position);
        holder.setData(current, position);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class AllHolder extends RecyclerView.ViewHolder {

        TextView Title;
        AllModel current;
        int position;

        public AllHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.all_tv);
        }

        public void setData(AllModel current, int position) {

            this.current = current;
            this.position = position;
            Title.setText(current.getTitle());
        }
    }

}

package ir.cdesign.contactrate.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.models.ContactShowModel;
import ir.cdesign.contactrate.models.TaskModel;

/**
 * Created by amin pc on 23/08/2016.
 */
public class ContactShowAdapter extends RecyclerView.Adapter<ContactShowAdapter.ContactHolder> {
    private ArrayList<ContactShowModel> ContactModels;
    private LayoutInflater layoutInflater;
    private Context context;

    public ContactShowAdapter(Context context, ArrayList<ContactShowModel> ContactModels) {
        this.ContactModels = ContactModels;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.task_layout, parent, false);
        ContactHolder holder = new ContactHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        ContactShowModel current = ContactModels.get(position);
        holder.setData(current, position);
    }

    @Override
    public int getItemCount() {
        return ContactModels.size();
    }

    public class ContactHolder extends RecyclerView.ViewHolder {

        TextView Title;
        ImageView imageView;
        ContactShowModel current;

        int position;

        public ContactHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.contact_row_tv);
            imageView = (ImageView) itemView.findViewById(R.id.contact_row_iv);
        }


        public void setData(ContactShowModel current, int position) {

            this.current = current;
            this.position = position;
            this.imageView.setImageResource(current.getImageId());
            this.Title.setText(current.getTitle());
        }
    }
}

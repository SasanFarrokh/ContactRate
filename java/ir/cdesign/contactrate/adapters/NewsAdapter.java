package ir.cdesign.contactrate.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.models.TaskModel;
import ir.cdesign.contactrate.utilities.AsyncGetNews;

/**
 * Created by amin pc on 23/08/2016.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<HashMap> data;
    private int limit;

    public NewsAdapter(Context context, List<HashMap> data, int limit) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.limit = limit;
        this.data = data;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.new_row_half, parent, false);
        NewsHolder holder = new NewsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return Math.min(limit,data.size());
    }



    public class NewsHolder extends RecyclerView.ViewHolder {
        TextView title,bodyText;
        ImageView imageView;
        View view;
        int position;

        public NewsHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.news_title_text);
            imageView = (ImageView) itemView.findViewById(R.id.news_image);
            bodyText = (TextView) itemView.findViewById(R.id.news_body_text);
            view = itemView;
        }
        public void setData(int position) {
            final HashMap<String,Object> dataItem = data.get(position);
            title.setText((String) dataItem.get("title"));
            bodyText.setText((String) dataItem.get("text"));
            /*(new Thread(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(AsyncGetNews.downloadImage((String) dataItem.get("image"),context));
                }
            })).start();*/
            /*(new AsyncTask<ImageView,Void,Void>() {
                @Override
                protected Void doInBackground(ImageView... params) {
                    setImage(AsyncGetNews.downloadImage((String) dataItem.get("image"),context));
                    return null;
                }
            }).execute();*/
            (new BitmapWorkerTask(imageView)).execute((String) dataItem.get("image"));
            //imageView.setImageBitmap((Bitmap) dataItem.get("image"));
        }
        public void setImage(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
    public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;

        public BitmapWorkerTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return AsyncGetNews.downloadImage(params[0],context);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}

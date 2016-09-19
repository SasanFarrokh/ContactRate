package ir.cdesign.contactrate.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.dialogs.DialogNews;
import ir.cdesign.contactrate.utilities.AsyncGetNews;

/**
 * Created by amin pc on 23/08/2016.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {


    public static HashMap<Integer,Bitmap> cacheBitmaps;
    private LayoutInflater layoutInflater;
    private Context context;
    private List<HashMap> data;
    private int limit;

    public NewsAdapter(Context context, List<HashMap> data, int limit) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.limit = limit;
        this.data = data;
        cacheBitmaps = new HashMap<>();
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
        public void setData(final int position) {
            final HashMap<String,Object> dataItem = data.get(position);
            title.setText((String) dataItem.get("title"));
            bodyText.setText((String) dataItem.get("text"));
            if (!cacheBitmaps.containsKey(dataItem.get("id"))) {
                (new BitmapWorkerTask(imageView,(int) dataItem.get("id"))).execute((String) dataItem.get("image"));
                Log.i("sasan","bitmap not set");
            } else {
                imageView.setImageBitmap(cacheBitmaps.get(dataItem.get("id")));
                Log.i("sasan","bitmap set: " + dataItem.get("id"));
                imageView.buildDrawingCache();
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogNews dialogNews = new DialogNews();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data",dataItem);
                    bundle.putParcelable("image",(itemView.findViewById(R.id.news_image)).getDrawingCache());
                    dialogNews.setArguments(bundle);
                    dialogNews.show(((AppCompatActivity) context).getSupportFragmentManager(), "news");
                }
            });
        }
        public void setImage(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
    public static class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private Context context;
        int newsId;

        public BitmapWorkerTask(ImageView imageView,int newsId) {
            imageViewReference = new WeakReference<ImageView>(imageView);
            context = imageView.getContext();
            this.newsId = newsId;
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
                    imageView.buildDrawingCache();
                    Log.i("sasan","newsid : " +newsId);
                    cacheBitmaps.put(newsId,bitmap);
                }
            }
        }
    }
}

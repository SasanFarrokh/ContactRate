package ir.cdesign.contactrate.lessons;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.utilities.Application;

/**
 * Created by amin pc on 15/09/2016.
 */
public class LessonAllAdapter extends RecyclerView.Adapter<LessonAllAdapter.Holder> {

    Context context;
    List<LessonModel> list = new ArrayList<>();
    LayoutInflater inflater;
    AlertDialog alertDialog = null;

    public LessonAllAdapter(Context context, List<LessonModel> list) {
        this.context = context;
        this.list = list;
        inflater = inflater.from(context);

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lesson_all_row_layout, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        LessonModel current = list.get(position);
        holder.setData(current, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, seenCount, point, author;
        NetworkImageView image;
        LessonModel current;
        int position;

        ImageLoader imageLoader = Application.getInstance().getImageLoader();

        public Holder(View itemView) {
            super(itemView);
            image = (NetworkImageView) itemView.findViewById(R.id.allImage);
            title = (TextView) itemView.findViewById(R.id.lessonTitleText);
            seenCount = (TextView) itemView.findViewById(R.id.seenCount);
            point = (TextView) itemView.findViewById(R.id.lessonUnlockPoint);
            author = (TextView) itemView.findViewById(R.id.authorText);

        }

        public void setData(LessonModel current, int position) {
            this.position = position;
            this.current = current;
            title.setText(current.getTitle());
            seenCount.setText(String.valueOf(current.getSeenCount()));
            point.setText(String.valueOf(current.getUnlock()));
            author.setText(current.getAuthor());
            image.setImageUrl(current.getImageUrl(), imageLoader);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setPositiveButton("Download", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //some shit

                    image.buildDrawingCache();
                    Bitmap imageBitmap = image.getDrawingCache();
                    File file = new File(context.getFilesDir(),current.getTitle()+ ".jpg");

                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(file);
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (out != null) {
                                out.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    current.setInternalImageUrl(Uri.fromFile(file));
                    DatabaseCommands.getInstance(context).addLesson(current);
                    Log.d("amin" , "fuck  :Q!");
                }
            });
            alertDialogBuilder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });
            alertDialog = alertDialogBuilder.create();
            alertDialog.setMessage("Yeah This is SOme" + "\n" + "BullSHit Network Lesson For you assholes");
//              alertDialog.setMessage(current.getShowCase());

            alertDialog.show();
        }
    }
}

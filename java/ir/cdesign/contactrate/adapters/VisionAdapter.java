package ir.cdesign.contactrate.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.Vision.ActivityVisionAdd;
import ir.cdesign.contactrate.homeScreen.HomeNavigation;
import ir.cdesign.contactrate.homeScreen.HomeScreen;

/**
 * Created by Sasan on 2016-09-10.
 */
public class VisionAdapter extends RecyclerView.Adapter<VisionAdapter.VisionHolder> {

    private Context context;
    private List<HashMap> data;
    public static VisionHolder selectedView;
    CountDownTimer countDownTimer;


    public VisionAdapter(Context context) {
        data = DatabaseCommands.getInstance(context).getVision(0);
        this.context = context;
    }

    @Override
    public VisionAdapter.VisionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vision_row, parent, false);
        VisionHolder holder = new VisionHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(VisionAdapter.VisionHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class VisionHolder extends RecyclerView.ViewHolder {
        private ProgressBar progress;
        private TextView subject,timer;
        long timestamp;
        private View view,options;
        private RelativeLayout MoreInfo;
        private LinearLayout SubjectBox;
        private ImageView visionImage;
        int position;
        long id;

        public VisionHolder(View itemView) {
            super(itemView);
            subject = (TextView) itemView.findViewById(R.id.vision_subject);
            progress = (ProgressBar) itemView.findViewById(R.id.vision_progress);
            MoreInfo = (RelativeLayout) itemView.findViewById(R.id.MoreInfo);
            SubjectBox = (LinearLayout) itemView.findViewById(R.id.subject_box);
            visionImage = (ImageView) itemView.findViewById(R.id.vision_image);
            timer = (TextView) itemView.findViewById(R.id.vision_timer);
            options = itemView.findViewById(R.id.vision_options);
            view = itemView;
        }

        public void setData(final int position) {
            final HashMap vision = data.get(data.size() - 1 - position);
            timestamp = (long) vision.get("timestamp") - System.currentTimeMillis();
            String imagePath = (String) vision.get("image");
            subject.setText((String) vision.get("subject"));
            if (!imagePath.isEmpty()) {
                Bitmap visionBitmap = HomeNavigation.getProfileBitmap(context, Uri.parse(imagePath));
                if (visionBitmap != null) {
                    visionImage.setImageBitmap(visionBitmap);
                    visionImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }

            }
            id = (long) vision.get("id");
            MoreInfo.setTranslationY(-10);
            Double visionPercent = ((double) (System.currentTimeMillis() - (long) vision.get("regdate")) /
                    ((long) vision.get("timestamp") - (long) vision.get("regdate"))) * 100;
            progress.setProgress(visionPercent.intValue());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (VisionHolder.this != selectedView) {
                        MoreInfo.setVisibility(View.VISIBLE);
                        MoreInfo.setAlpha(0);
                        MoreInfo.animate().alpha(1).translationYBy(10).start();
                        SubjectBox.animate().alpha(0).translationYBy(10).start();
                        if (selectedView != null) {
                            selectedView.SubjectBox.animate().alpha(1).translationYBy(-10).start();
                            selectedView.MoreInfo.animate().alpha(1).translationYBy(-10).start();
                            selectedView.MoreInfo.setVisibility(View.GONE);
                        }
                        if (countDownTimer != null) countDownTimer.cancel();
                        timestamp = (long) vision.get("timestamp") - System.currentTimeMillis();
                        countDownTimer = new CountDownTimer(timestamp,1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                timestamp -= 1000;
                                try {
                                    timer.setText(
                                            "" + (int) Math.floor(timestamp / 86400000)
                                                    + " days and \n" + (int) Math.floor(timestamp / 3600000) % 24
                                                    + " : " + (int) Math.floor(timestamp / 60000) % 60 +
                                                    " : " + (int) Math.floor(timestamp / 1000) % 60
                                    );
                                } catch (Exception e) {
                                    cancel();
                                }
                            }

                            @Override
                            public void onFinish() {

                            }
                        };
                        countDownTimer.start();
                        selectedView = VisionHolder.this;

                    } else {
                        selectedView.SubjectBox.animate().alpha(1).translationYBy(-10).start();
                        selectedView.MoreInfo.animate().alpha(1).translationYBy(-10).start();
                        selectedView.MoreInfo.setVisibility(View.GONE);
                        countDownTimer.cancel();
                        selectedView = null;
                    }
                }
            });
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setItems(new String[]{
                            "Remove","Edit"
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    DatabaseCommands.getInstance(context).removeVision(id);
                                    data.remove(data.size() - 1 - position);
                                    notifyDataSetChanged();
                                    break;
                                case 1:
                                    context.startActivity(new Intent(context, ActivityVisionAdd.class)
                                        .putExtra("edit",id));
                                    break;
                            }
                        }
                    });
                    alertBuilder.show();
                }
            };
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    runnable.run();
                    return true;
                }
            });
            options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    runnable.run();
                }
            });

        }
    }


    public interface AdapterUpdate {
        void updateRecycler(int pos,int mode);
    }
}

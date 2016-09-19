package ir.cdesign.contactrate.dialogs;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.adapters.NewsAdapter;

/**
 * Created by amin pc on 05/09/2016.
 */
public class DialogNews extends DialogFragment {

    HashMap data;
    Bitmap bitmap;

    TextView title,body,counter;
    ImageView image, closeBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_dialog, container, false);
        if (getArguments().getSerializable("data") != null)
            data = (HashMap) getArguments().getSerializable("data");
        if (getArguments().getParcelable("image") != null)
            bitmap = getArguments().getParcelable("image");

        title = (TextView) view.findViewById(R.id.news_title_text);
        body = (TextView) view.findViewById(R.id.news_body_text);
        counter = (TextView) view.findViewById(R.id.news_counter);
        image = (ImageView) view.findViewById(R.id.news_image);
        closeBtn = (ImageView) view.findViewById(R.id.news_close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        title.setText((String) data.get("title"));
        body.setText((String) data.get("text"));
        counter.setText((String) data.get("viewed"));
        if (bitmap != null)
            image.setImageBitmap(bitmap);
        else {
            new NewsAdapter.BitmapWorkerTask(image).execute((String) data.get("image"));
        }
        return view;
    }
}

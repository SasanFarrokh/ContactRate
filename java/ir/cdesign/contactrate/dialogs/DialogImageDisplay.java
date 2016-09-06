package ir.cdesign.contactrate.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.adapters.ImageDisplayAdapter;
import ir.cdesign.contactrate.models.ImageDisplayModel;

/**
 * Created by amin pc on 06/09/2016.
 */
public class DialogImageDisplay extends DialogFragment {
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_image_display,container,false);

        getDialog().requestWindowFeature(STYLE_NO_TITLE);

        setRecycler(view);

        return view;
    }

    private void setRecycler(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        ImageDisplayAdapter adapter = new ImageDisplayAdapter(getActivity(), ImageDisplayModel.getData());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

}

package ir.cdesign.contactrate.Vision;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import ir.cdesign.contactrate.R;

/**
 * Created by amin pc on 02/09/2016.
 */
public class FragmentaddVision extends Fragment {
    Button backButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vision_add_fragment,container,false);

        init(view);

        return view;
    }

    private void init(View view){
        backButton = (Button) view.findViewById(R.id.toolbar_iv);

        backButton.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.toolbar_iv:
                    ((Visions)getActivity()).fragmentTransaction = getFragmentManager().beginTransaction().remove(((Visions)getActivity()).fragmentaddVision);
                    ((Visions)getActivity()).fragmentTransaction.commit();
                    ((Visions)getActivity()).fab.show();
            }
        }
    };

}

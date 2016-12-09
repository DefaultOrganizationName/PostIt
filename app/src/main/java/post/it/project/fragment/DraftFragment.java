package post.it.project.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import post.it.project.postit.R;

/**
 * Created by Михаил on 09.12.2016.
 */

public class DraftFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.draft_fragment, container, false);
        return rootView;
    }
}

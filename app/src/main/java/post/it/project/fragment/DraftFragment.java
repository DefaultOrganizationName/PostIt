package post.it.project.fragment;

import android.app.Activity;
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

    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
//    public static DraftFragment newInstance(int page, String title) {
//        DraftFragment fragmentFirst = new DraftFragment();
//        Bundle args = new Bundle();
//        args.putInt("someInt", page);
//        args.putString("someTitle", title);
//        fragmentFirst.setArguments(args);
//        return fragmentFirst;
//    }
//
//    // Store instance variables based on arguments passed
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        page = getArguments().getInt("someInt", 0);
//        title = getArguments().getString("someTitle");
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.draft_fragment, container, false);
        return rootView;
    }
}

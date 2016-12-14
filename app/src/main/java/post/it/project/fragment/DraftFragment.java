package post.it.project.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import post.it.project.postit.R;

import static post.it.project.storage.PersistantStorage.getDraftText;

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
        TextView textView = (TextView) rootView.findViewById(R.id.section_draft);
        if (getDraftText("draft_text") != null) {
            textView.setText(getDraftText("draft_text"));
        }
        return rootView;
    }
}

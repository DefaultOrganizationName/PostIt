package post.it.project.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import post.it.project.adapter.DraftsEntryAdapter;
import post.it.project.database.DraftDatabase;
import post.it.project.postit.Post;
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
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        final DraftsEntryAdapter adapter = new DraftsEntryAdapter(getContext());
        recyclerView.setAdapter(adapter);

        final Context context = getContext();

        new Thread(new Runnable() {
            @Override
            public void run() {
                adapter.setPost(new DraftDatabase(context).getAll());
            }
        }).start();

        if (getDraftText("draft_text") != null) {
            textView.setText(getDraftText("draft_text"));
        }

//        Post mda = PostFragment.ppost;
//
//        if (mda != null) {
//            draftText.setText(mda.post_text);
//            imageView.setImageBitmap(mda.image_bitmap);
//        }
        return rootView;
    }
}

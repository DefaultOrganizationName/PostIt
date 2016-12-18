package post.it.project.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import post.it.project.VK.VkActivity;
import post.it.project.postit.Post;
import post.it.project.postit.R;

/**
 * Created by Kirill Antonov on 18.12.2016.
 */


public class DuringPostingFragment extends Fragment {

    final String LOG_TAG = "during_posting_logs";
    private FragmentTransaction fTrans;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Log.d(LOG_TAG, "Fragment2 onCreateView");
        View view = inflater.inflate(R.layout.during_post_fragment, null);
        LinearLayout cont = (LinearLayout) view.findViewById(R.id.container);
        TextView text = new TextView(getContext());
        text.setText(R.string.vk);
        cont.addView(text);



        return view;
    }


}

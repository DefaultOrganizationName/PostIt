package post.it.project.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import post.it.project.postit.R;

import static post.it.project.storage.PersistantStorage.getProperty;

/**
 * Created by Михаил on 09.12.2016.
 */


public class PostFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.post_fragment, container, false);
        TextView text = (TextView) rootView.findViewById(R.id.textInPost);

        if (!getProperty("vk")) {
            text.setText("VK OFF");
        } else if (getProperty("vk")) {
            text.setText("VK ON");
        }
        return rootView;
    }
}

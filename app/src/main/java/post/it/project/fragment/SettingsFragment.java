package post.it.project.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import post.it.project.postit.R;

import static post.it.project.storage.PersistantStorage.addProperty;


/**
 * Created by Михаил on 09.12.2016.
 */

public class SettingsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.settings_fragment, container, false);
        Button VK = (Button) rootView.findViewById(R.id.vk);
        VK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addProperty("vk", true);
            }
        });
        return rootView;
    }

}

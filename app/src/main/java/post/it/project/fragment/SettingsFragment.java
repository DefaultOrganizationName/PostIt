package post.it.project.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import post.it.project.postit.R;

/**
 * Created by Михаил on 09.12.2016.
 */

public class SettingsFragment extends Fragment {

    public interface onSomeEventListener {
        public void someEvent(String s);
    }

    onSomeEventListener someEventListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);
        Switch VK = (Switch) rootView.findViewById(R.id.vk);
        VK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                someEventListener.someEvent("I CHANGE UUUUUU");
            }
        });

        return rootView;
    }

}

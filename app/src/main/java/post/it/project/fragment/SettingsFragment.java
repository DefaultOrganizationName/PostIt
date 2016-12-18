package post.it.project.fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.model.VKScopes;

import post.it.project.VK.Constants;
import post.it.project.VK.LogIn;
import post.it.project.postit.MainActivity;
import post.it.project.postit.R;
import post.it.project.storage.PersistantStorage;

import static post.it.project.storage.PersistantStorage.addProperty;
import static post.it.project.storage.PersistantStorage.getProperty;


/**
 * Created by Михаил on 09.12.2016.
 */

public class SettingsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        SharedPreferences preferences = this.getActivity().getSharedPreferences(PersistantStorage.STORAGE_NAME, Context.MODE_PRIVATE);
        final View rootView = inflater.inflate(R.layout.settings_fragment, container, false);
        Switch VK = (Switch) rootView.findViewById(R.id.vk);
        Switch FB = (Switch) rootView.findViewById(R.id.fb);
        Switch OK = (Switch) rootView.findViewById(R.id.ok);
        Switch IG = (Switch) rootView.findViewById(R.id.insta);
        VK.setChecked(getProperty("vk"));
        FB.setChecked(getProperty("fb"));
        OK.setChecked(getProperty("ok"));
        IG.setChecked(getProperty("insta"));
        VK.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                addProperty("vk", b);
                if (b) {
                    Intent i = new Intent(getActivity(), LogIn.class);
                    startActivity(i);
                }
            }
        });
        FB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                addProperty("fb", b);
            }
        });
        OK.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                addProperty("ok", b);
            }
        });
        IG.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                addProperty("insta", b);
            }
        });

        return rootView;
    }

}

package post.it.project.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import post.it.project.VK.Constants;
import post.it.project.VK.VkActivity;
import post.it.project.postit.Post;
import post.it.project.postit.R;

import static post.it.project.storage.PersistantStorage.addDraftText;
import static post.it.project.storage.PersistantStorage.getProperty;

/**
 * Created by Михаил on 09.12.2016.
 */


public class PostFragment extends Fragment {
    public static Post ppost;

    private FragmentTransaction fTrans;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        SQLiteDatabase db = MainActivity.dbHelper.getReadableDatabase();
        View rootView = inflater.inflate(R.layout.post_fragment, container, false);
        TextView text = (TextView) rootView.findViewById(R.id.textInPost);
        Button post = (Button) rootView.findViewById(R.id.button);
        final EditText editTx = (EditText) rootView.findViewById(R.id.eText);
        ImageView iw = (ImageView) rootView.findViewById(R.id.imageView);
        iw.buildDrawingCache();
        final Bitmap bm = iw.getDrawingCache();

        if (!getProperty("vk")) {
            text.setText("VK OFF");
        } else if (getProperty("vk")) {
            text.setText("VK ON");
        }


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDraftText("draft_text", editTx.getText().toString());
                ppost = new Post(new int[] {0, 0, 0, 0}, editTx.getText().toString(), bm);
                editTx.clearFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                editTx.getText().clear();

                Intent i = new Intent(getActivity(), VkActivity.class);
                startActivity(i);
            }
        });
        return rootView;
    }
}

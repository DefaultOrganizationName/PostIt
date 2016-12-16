package post.it.project.fragment;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import post.it.project.postit.MainActivity;
import post.it.project.postit.Post;
import post.it.project.postit.R;
import static post.it.project.storage.PersistantStorage.addDraftText;
import static post.it.project.storage.PersistantStorage.getProperty;

/**
 * Created by Михаил on 09.12.2016.
 */


public class PostFragment extends Fragment {
    public static Post ppost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        SQLiteDatabase db = MainActivity.dbHelper.getReadableDatabase();
        View rootView = inflater.inflate(R.layout.post_fragment, container, false);
        TextView text = (TextView) rootView.findViewById(R.id.textInPost);
        Button post = (Button) rootView.findViewById(R.id.button);
        final EditText editTx = (EditText) rootView.findViewById(R.id.editText);
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
//                addDraftText("draft_text", editTx.getText().toString());
//                ppost = new Post(new int[] {0, 0, 0, 0}, editTx.getText().toString(), bm);
//                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        });
        return rootView;
    }
}

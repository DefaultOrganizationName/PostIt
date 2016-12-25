package post.it.project.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
import static post.it.project.storage.PersistantStorage.addProperty;
import static post.it.project.storage.PersistantStorage.getProperty;

/**
 * Created by Михаил on 09.12.2016.
 */


public class PostFragment extends Fragment {
    public static Post ppost;

    private final static int PICK_PHOTO_FROM_GALLERY = 123;
    private final static int REQUEST_PHOTO_FROM_CAMERA = 456;

    private FragmentTransaction fTrans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        SQLiteDatabase db = MainActivity.dbHelper.getReadableDatabase();
        View rootView = inflater.inflate(R.layout.post_fragment, container, false);
        TextView text = (TextView) rootView.findViewById(R.id.textInPost);
        Button camera = (Button) rootView.findViewById(R.id.new_post_camera_button);
        Button gallery = (Button) rootView.findViewById(R.id.new_post_gallery_button);
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

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTx.getText().toString().equals("\\s*")) {
                    addDraftText("draft_text", editTx.getText().toString());
                    ppost = new Post(new int[]{0, 0, 0, 0}, editTx.getText().toString(), bm);
                    editTx.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    editTx.getText().clear();

                    addProperty("update", true);

                    Intent i = new Intent(getActivity(), VkActivity.class);
                    startActivity(i);
                }
            }
        });
        post.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //TODO: set drafts when long click
                return true;
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            //TODO: pick photo from gallery
        }
        if (requestCode == REQUEST_PHOTO_FROM_CAMERA && resultCode == Activity.RESULT_OK) {
            //TODO: pick photo from camera
        }
    }
}

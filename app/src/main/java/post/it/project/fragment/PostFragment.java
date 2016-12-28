package post.it.project.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import post.it.project.database.DraftDatabase;
import post.it.project.exceptions.PostItDatabaseException;
import post.it.project.postit.DraftsEntry;
import post.it.project.postit.ParcelablePost;
import post.it.project.postit.Post;
import post.it.project.postit.R;
import post.it.project.social_networks.Constants;
import post.it.project.social_networks.SocialNetworksActivity;
import post.it.project.utils.Utils;

import static post.it.project.storage.PersistantStorage.addDraftText;
import static post.it.project.storage.PersistantStorage.addProperty;
import static post.it.project.storage.PersistantStorage.getID;
import static post.it.project.storage.PersistantStorage.getProperty;
import static post.it.project.storage.PersistantStorage.setID;

/**
 * Created by Михаил on 09.12.2016.
 */


public class PostFragment extends Fragment {
    public static Post ppost;

    private final static int PICK_PHOTO_FROM_GALLERY = 123;
    private final static int REQUEST_PHOTO_FROM_CAMERA = 456;
    public static ImageView iw;
    String image_path;
    EditText editTx;
    TextView text;
    Bitmap standart;
    public static Bitmap temp;
    protected Post postForNetwork;
    private static final int PERMISSION_REQUEST = 1;
    File f;
    Button clear;
    ImageView clearImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        SQLiteDatabase db = MainActivity.dbHelper.getReadableDatabase();
        final View rootView = inflater.inflate(R.layout.post_fragment, container, false);
        Button camera = (Button) rootView.findViewById(R.id.new_post_camera_button);
        Button gallery = (Button) rootView.findViewById(R.id.new_post_gallery_button);
        clear = (Button) rootView.findViewById(R.id.clear);
        clearImage = (ImageView) rootView.findViewById(R.id.clearImage);
        final Button post = (Button) rootView.findViewById(R.id.button);
        editTx = (EditText) rootView.findViewById(R.id.eText);

        iw = (ImageView) rootView.findViewById(R.id.imageView);
        temp = ((BitmapDrawable) iw.getDrawable()).getBitmap();
        standart = temp;
        f = new File(getContext().getCacheDir(), "icon");
        try {
            f.createNewFile();

//Convert bitmap to byte array
            Bitmap bitmap = temp;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        image_path = null;
//        temp = Bitmap.createBitmap(iw.getDrawingCache());
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iw.setImageBitmap(standart);
                image_path = null;
                setVisible(0);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
                } else {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                }
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
                } else {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);
                }
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEmpty(editTx) || image_path != null) {
                    addDraftText("draft_text", editTx.getText().toString());
//                    ppost = new Post(new int[]{0, 0, 0, 0}, editTx.getText().toString(), bm);

                    postForNetwork = new Post(getNetworks(), editTx.getText().toString(), image_path);

                    editTx.clearFocus();
                    Utils.hidePhoneKeyboard(getActivity());
                    editTx.getText().clear();
                    addProperty("update", true);
                    Intent i = new Intent(getActivity(), SocialNetworksActivity.class);
                    ParcelablePost parcelablePostForNetworks = new ParcelablePost(postForNetwork);
                    i.putExtra(Constants.CURRENT_POST_KEY, parcelablePostForNetworks);
                    startActivity(i);
                }
            }
        });
        post.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //TODO: set drafts when long click
                if (!isEmpty(editTx)) {
                    int[] nw = getNetworks();
                    if (nw[0] == 0 && nw[1] == 0) {
                        return true;
                    } else {
                        addProperty("update", true);
                        addToDrafts(new Post(getNetworks(), editTx.getText().toString(), new String(f.getPath())));
                        clear();
                    }
                }
                return true;
            }
        });
        if (savedInstanceState != null) {
            editTx.setText(savedInstanceState.getString("POST_TEXT"));
            image_path = savedInstanceState.getString("IMAGE_PATH");
            if (image_path != null) {
                Bitmap loadedBitmap = Utils.rotatePic(image_path);
                iw.setImageBitmap(loadedBitmap);
            }
            clearImage.setVisibility((savedInstanceState.getInt("STATE") == View.VISIBLE ? View.VISIBLE : View.INVISIBLE));
            clear.setVisibility((savedInstanceState.getInt("STATE") == View.VISIBLE ? View.VISIBLE : View.INVISIBLE));
        }
        return rootView;
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void clear() {
        editTx.getText().clear();
        iw.setImageBitmap(standart);
        image_path = null;
        Utils.hidePhoneKeyboard(getActivity());
        setVisible(0);
    }

    public void addToDrafts(Post post) {
        try {
            int id = getID("ID");
            setID("ID", id + 1);
            DraftsEntry draft = new DraftsEntry(id, post);
            DraftDatabase.put(draft);
        } catch (PostItDatabaseException e) {
            e.printStackTrace();
        }
    }

//    private int id = 0;
//
//    public void addToDrafts(Post post) {
//        boolean flag = true;
//        while (flag) {
//            DraftsEntry draft = new DraftsEntry(id++, post);
//            try {
//                DraftDatabase.put(draft);
//                flag = false;
//            } catch (PostItDatabaseException e) {
//                //Nothing
//            }
//        }
//    }

    public void setVisible(int flag) {
        switch (flag) {
            case 1:
                clearImage.setVisibility(View.VISIBLE);
                clear.setVisibility(View.VISIBLE);
                break;
            case 0:
                clearImage.setVisibility(View.INVISIBLE);
                clear.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public int[] getNetworks() {
        int[] ans = {(getProperty("vk") ? 1 : 0), (getProperty("ok") ? 1 : 0)};
        return ans;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
//                    Uri selectedImage = data.getData();
//                    iw.setImageURI(selectedImage);
//
            // Get selected gallery image
            Uri selectedPicture = data.getData();
            Log.d(LOG_TAG, "data state: " + selectedPicture);
            // Get and resize profile image
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedPicture, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap loadedBitmap = Utils.rotatePic(picturePath);
            image_path = picturePath;
            temp = loadedBitmap;
            iw.setImageBitmap(loadedBitmap);
            setVisible(1);
        }

    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("POST_TEXT", editTx.getText().toString());
        outState.putString("IMAGE_PATH", image_path);
        outState.putInt("STATE", clearImage.getVisibility());
    }

    String LOG_TAG = "PostFragment";
}
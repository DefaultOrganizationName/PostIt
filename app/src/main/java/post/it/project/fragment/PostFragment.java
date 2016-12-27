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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        SQLiteDatabase db = MainActivity.dbHelper.getReadableDatabase();
        final View rootView = inflater.inflate(R.layout.post_fragment, container, false);
        Button camera = (Button) rootView.findViewById(R.id.new_post_camera_button);
        Button gallery = (Button) rootView.findViewById(R.id.new_post_gallery_button);
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
                if (!editTx.getText().toString().equals("\\s*")) {
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
//            Bitmap loadedBitmap = BitmapFactory.decodeFile(picturePath);
//
//            ExifInterface exif = null;
//            try {
//                File pictureFile = new File(picturePath);
//                exif = new ExifInterface(pictureFile.getAbsolutePath());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            int orientation = ExifInterface.ORIENTATION_NORMAL;
//
//            if (exif != null)
//                orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//
//            switch (orientation) {
//                case ExifInterface.ORIENTATION_ROTATE_90:
//                    loadedBitmap = rotateBitmap(loadedBitmap, 90);
//                    break;
//                case ExifInterface.ORIENTATION_ROTATE_180:
//                    loadedBitmap = rotateBitmap(loadedBitmap, 180);
//                    break;
//
//                case ExifInterface.ORIENTATION_ROTATE_270:
//                    loadedBitmap = rotateBitmap(loadedBitmap, 270);
//                    break;
//            }
            image_path = picturePath;
            temp = loadedBitmap;
            iw.setImageBitmap(loadedBitmap);
        }

    }

//    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
//        Matrix matrix = new Matrix();
//        matrix.postRotate(degrees);
//        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//    }

    String LOG_TAG = "PostFragment";
}
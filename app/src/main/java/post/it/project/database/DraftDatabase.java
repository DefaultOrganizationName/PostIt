package post.it.project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import post.it.project.exceptions.PostItDatabaseException;
import post.it.project.postit.DraftsEntry;
import post.it.project.postit.Post;

import static com.vk.sdk.VKUIHelper.getApplicationContext;

/**
 * Created by Михаил on 14.12.2016.
 */

public class DraftDatabase {
    private final Context context;

    @AnyThread
    public DraftDatabase(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    @WorkerThread
    public static void put(@NonNull DraftsEntry entry) throws PostItDatabaseException {
        SQLiteDatabase db = DatabaseHelper.getInstance(getApplicationContext()).getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.Drafts._ID, entry.id);
        cv.put(DatabaseContract.Drafts.POST_TEXT, entry.post.post_text);

        //get image as byte array to put in SQL table (BLOB)
        Bitmap bmp = entry.post.image_bitmap;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] byteArray = stream.toByteArray();
        cv.put(DatabaseContract.Drafts.POST_IMAGE, byteArray);

        cv.put(DatabaseContract.Drafts.VK_STATE, entry.post.networks[0]);
        cv.put(DatabaseContract.Drafts.OK_STATE, entry.post.networks[1]);

        if (db.insert(DatabaseContract.Drafts.TABLE, null, cv) == -1) {
            throw new PostItDatabaseException("Can't add post into database");
        }
    }

    //TODO: get the list of posts in DB
    @WorkerThread
    public List<DraftsEntry> getAll() {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        List<DraftsEntry> draftsEntries = new ArrayList<>();

        try (Cursor cursor = db.query(
                DatabaseContract.Drafts.TABLE,
                DatabaseContract.Drafts.ALL,
                "1",
                null,
                null,
                null,
                null
        )) {
            if (cursor != null && cursor.moveToFirst()) {
                for ( ; !cursor.isAfterLast(); cursor.moveToNext()) {
                    int i = 0;
                    int id = cursor.getInt(i++);
                    String text = cursor.getString(i++);
                    byte[] image = cursor.getBlob(i++);
                    int vk_state = cursor.getInt(i++);
                    int ok_state = cursor.getInt(i++);
//                    int fb_state = cursor.getInt(i++);
//                    int insta_state = cursor.getInt(i++);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                    int[] networks = {vk_state, ok_state};
                    DraftsEntry entry = new DraftsEntry(id, new Post(networks, text, bitmap));
                    draftsEntries.add(entry);
                }
            }
        }
        return draftsEntries;
    }

    @WorkerThread
    public void delete(DraftsEntry entry) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.execSQL("DELETE FROM " + DatabaseContract.Drafts.TABLE + " WHERE " + DatabaseContract.Drafts._ID + "=" + entry.id);
    }

}

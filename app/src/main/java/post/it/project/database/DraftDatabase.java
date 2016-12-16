package post.it.project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import java.io.ByteArrayOutputStream;

import post.it.project.exceptions.PostItDatabaseException;
import post.it.project.postit.DraftsEntry;

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
    public void put(@NonNull DraftsEntry entry) throws PostItDatabaseException {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.Drafts._ID, entry.id);
        cv.put(DatabaseContract.Drafts.POST_TEXT, entry.post.post_text);

        //get image as byte array to put in SQL table (BLOB)
        Bitmap bmp = entry.post.image_bitmap;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        cv.put(DatabaseContract.Drafts.POST_IMAGE, byteArray);

        cv.put(DatabaseContract.Drafts.VK_STATE, entry.post.networks[0]);
        cv.put(DatabaseContract.Drafts.OK_STATE, entry.post.networks[1]);

        if (db.insert(DatabaseContract.Drafts.TABLE, null, cv) == -1) {
            throw new PostItDatabaseException("Can't add post into database");
        }
    }

    //TODO: get the list of posts in DB

}

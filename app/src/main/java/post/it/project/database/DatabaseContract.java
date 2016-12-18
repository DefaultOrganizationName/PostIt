package post.it.project.database;

import android.provider.BaseColumns;


/**
 * Created by Михаил on 14.12.2016.
 */

public class DatabaseContract {

    private interface DraftsColumns extends BaseColumns, DraftContract.DraftColumns {
        String[] ALL = {
                _ID,
                POST_TEXT,
                POST_IMAGE,
                VK_STATE,
                OK_STATE
        };
    }

    static final class Drafts implements DraftsColumns {
        static final String TABLE = "drafts";


        static final String CREATE_TABLE = "CREATE TABLE" + TABLE
                + " ("
                + _ID + " INTEGER PRIMARY KEY, "
                + POST_TEXT + " TEXT, "
                + POST_IMAGE + " BLOB, "
                + VK_STATE + " INTEGER, "
                + OK_STATE + " INTEGER, ";
//                + "fb_status INTEGER, "
//                + "insta_status INTEGER";
    }
}

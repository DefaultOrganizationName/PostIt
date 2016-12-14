package post.it.project.database;

/**
 * Created by Михаил on 14.12.2016.
 */

public class DatabaseContract {

    static final class Drafts {
        static final String TABLE = "drafts";

        static final String CREATE_TABLE = "CREATE TABLE" + TABLE
                + " ("
                + "post_id INTEGER PRIMARY KEY, "
                + "post_text TEXT, "
                + "post_image BLOB, "
                + "vk_status INTEGER, "
                + "ok_status INTEGER, "
                + "fb_status INTEGER, "
                + "insta_status INTEGER";
    }
}

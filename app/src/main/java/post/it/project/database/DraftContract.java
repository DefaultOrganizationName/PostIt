package post.it.project.database;

import android.provider.BaseColumns;

/**
 * Created by Михаил on 16.12.2016.
 */

public class DraftContract {
    interface DraftColumns extends BaseColumns {
        String POST_TEXT = "post_text";
        String POST_IMAGE = "post_image";
        String VK_STATE = "vk_status";
        String OK_STATE = "ok_status";
    }
}

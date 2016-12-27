package post.it.project.postit;

import android.support.annotation.NonNull;

/**
 * Created by Михаил on 16.12.2016.
 */

public class DraftsEntry {

    @NonNull
    public final int id;

    @NonNull
    public final Draft post;

    public DraftsEntry(int id, @NonNull Draft post) {
        this.id = id;
        this.post = post;
    }
}

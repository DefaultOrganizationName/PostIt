package post.it.project.postit;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * Created by Михаил on 09.12.2016.
 */

public class Post {
    public static int[] networks;

    public static String post_text;
    public static Bitmap image_bitmap;

    // post_text or image_bitmap can be null
    public Post(int[] networks, String post_text, Bitmap image_bitmap) {
        Post.networks = networks;
        Post.post_text = post_text;
        Post.image_bitmap = image_bitmap;
    }
}

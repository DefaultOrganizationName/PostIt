package post.it.project.postit;

import android.graphics.Bitmap;

import post.it.project.social_networks.Constants;

/**
 * Created by Михаил on 09.12.2016.
 */

public class Post {
    public final int[] networks;

    public final String post_text;
    public final Bitmap image_bitmap;

    // post_text or image_bitmap can be null
    public Post(int[] networks, String post_text, Bitmap image_bitmap) {
        this.networks = networks;
        this.post_text = post_text;
        this.image_bitmap = image_bitmap;
        Constants.editText = post_text;
    }
}

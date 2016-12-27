package post.it.project.postit;

/**
 * Created by Михаил on 27.12.2016.
 */

public class Draft {
    public final int[] networks;
    public final String post_text;
    public final String image_path;

    public Draft(int[] networks, String post_text, String image_path) {
        this.networks = networks;
        this.post_text = post_text;
        this.image_path = image_path;
    }


}

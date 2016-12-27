package post.it.project.social_networks;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import post.it.project.postit.Post;
import post.it.project.social_networks.VK.VkPost;
import post.it.project.utils.Utils;

/**
 * Created by Kirill Antonov on 27.12.2016.
 */

public class PostToNetworksService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    protected Bitmap photo = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service is started!");
        Post p = intent.getParcelableExtra(Constants.CURRENT_POST_KEY);
        if (p.image_path != null) photo = Utils.rotatePic(p.image_path);
        VkPost request = new VkPost(photo, p.post_text, this);
        new Thread(request).start();
        return super.onStartCommand(intent, flags, startId);
    }

    private final String TAG = "PostService";
}
